package com.cupidconnect.cupidconnect.controllers;

import com.cupidconnect.cupidconnect.dtos.UserDTO;
import com.cupidconnect.cupidconnect.mappers.Mapper;
import com.cupidconnect.cupidconnect.models.UserEntity;
import com.cupidconnect.cupidconnect.services.impl.JwtServiceImpl;
import com.cupidconnect.cupidconnect.services.impl.UserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.database.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.LocalDate;
import java.time.Period;


@RestController
@RequestMapping("${api.prefix}")
@RequiredArgsConstructor
public class RoomController {
    private final JwtServiceImpl jwtService;
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference collectionUsers = database.getReference("users");
    private final DatabaseReference collectionRoom = database.getReference("rooms");

    private final String VIDEOSDK_API_ENDPOINT = "https://api.videosdk.live/v2";
    private final RestTemplate restTemplate = new RestTemplate();
    private final String apiToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcGlrZXkiOiI2ODY3NGEyYy04NzY5LTQxZDQtOGMzMy0zOWM2ZWI1ODcwZGUiLCJwZXJtaXNzaW9ucyI6WyJhbGxvd19qb2luIl0sImlhdCI6MTcxMjgwMTk4NCwiZXhwIjoxNzE1MzkzOTg0fQ.yWpMIJO4SdL8bIQgqdT-u91vNHJLEhutQS9RPUzq9Jg";
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final UserServiceImpl userService;
    private final Mapper<UserEntity, UserDTO> userMapper;

    static class ApiResponse {
        private final String message;
        private final String roomId;

        private final Integer userId;

        private final Integer participantId;


        public ApiResponse(String message, String roomId, Integer userId, Integer participantId) {
            this.message = message;
            this.roomId = roomId;
            this.userId = userId;
            this.participantId = participantId;

        }


        public String getMessage() {
            return message;
        }

        public String getRoomId() {
            return roomId;
        }

        public Integer getUserId() {
            return userId;
        }

        public Integer getParticipantId() {
            return participantId;
        }

    }


    @PostMapping("/take-room")
    public ResponseEntity<?> takeRoom(@RequestHeader(value = "Authorization") String authToken, @RequestBody String requestBody, HttpSession session) throws ExecutionException, InterruptedException, JsonProcessingException {
        try {
            String token = authToken.substring("Bearer ".length());
            String userId = jwtService.extractUserId(token);
            System.out.println("userId: "+userId);
            JsonNode bodyJson = objectMapper.readTree(requestBody);

            int filterGender = bodyJson.get("genderId").asInt();
            int filterMinAge = bodyJson.get("minAge").asInt();
            int filterMaxAge = bodyJson.get("maxAge").asInt();

            Map<String, Object> firebaseMap = new HashMap<>();
            firebaseMap.put("genderId", filterGender);
            firebaseMap.put("minAge", filterMinAge);
            firebaseMap.put("maxAge", filterMaxAge);


            UserEntity currentUser = userService.findById(Integer.valueOf(userId)).get();

            CompletableFuture<List<Map<String, String>>> futureData = fetchDataFromDatabaseHashMap(collectionUsers);
            AtomicReference<String> roomRef = new AtomicReference<>("");
            AtomicReference<String> participantIdRef = new AtomicReference<>("");
            futureData.thenAccept(dataList -> {
                for (Map<String, String> dataMap : dataList) {
                    for (Map.Entry<String, String> entry : dataMap.entrySet()) {
                        String key = entry.getKey();
                        String value = entry.getValue();

                        UserEntity participant = userService.findById(Integer.valueOf(key)).get();

                        Pattern pattern = Pattern.compile("\\b(\\w+)=(\\w+(-\\w+)*)\\b");
                        Map<String, String> keyValuePairs = getStringStringMap(pattern, value);

                        if (isMatched(keyValuePairs, participant, bodyJson, currentUser)) {
                            for (Map.Entry<String, String> x : keyValuePairs.entrySet()) {
                                String entryKey = x.getKey();
                                String entryValue = x.getValue();
                                if (entryKey.equals("roomId")) {
                                    roomRef.set(entryValue);
                                }
                            }
                        }

                        participantIdRef.set(key);

                    }
                }
            }).join();

            String room = roomRef.get();

            if (!room.isEmpty()) {
                Integer participantId = Integer.valueOf(participantIdRef.get());

                return ResponseEntity.ok(objectMapper.writeValueAsString(new ApiResponse("Room taken successfully!", room, Integer.valueOf(userId), participantId)));
            }

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", apiToken);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> requestEntity = new HttpEntity<>("{}", headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    VIDEOSDK_API_ENDPOINT + "/rooms",
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            JsonNode roomResponseJson = objectMapper.readTree(responseEntity.getBody());

            String roomId = roomResponseJson.get("roomId").asText();

            firebaseMap.put("roomId", roomId);

            collectionUsers.child(userId).setValueAsync(firebaseMap);

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                return ResponseEntity.ok(objectMapper.writeValueAsString(new ApiResponse("Room taken successfully!", null, null, null)));
            } else {
                return ResponseEntity.ok(objectMapper.writeValueAsString(new ApiResponse("Failed to take room!", null, null, null)));
            }

        } catch (Exception e) {
            return ResponseEntity.ok(objectMapper.writeValueAsString(new ApiResponse("Error!", null, null, null)));
        }
    }


    private boolean isMatched(Map<String, String> keyValuePairs, UserEntity participant, JsonNode currentFilter, UserEntity currentUser) {

        if (Objects.equals(participant.getEmail(), currentUser.getEmail())) {
            return false;
        }

        int numberOfMatch = 0;

        int filterGenderCurrent = currentFilter.get("genderId").asInt();
        int filterMinAgeCurrent = currentFilter.get("minAge").asInt();
        int filterMaxAgeCurrent = currentFilter.get("maxAge").asInt();

        int filterGenderParticipant = 0;
        int filterMinAgeParticipant = 0;
        int filterMaxAgeParticipant = 0;

        for (Map.Entry<String, String> entry : keyValuePairs.entrySet()) {
            String entryKey = entry.getKey();
            String entryValue = entry.getValue();

            switch (entryKey) {
                case "genderId" -> filterGenderParticipant = Integer.parseInt(entryValue);
                case "minAge" -> filterMinAgeParticipant = Integer.parseInt(entryValue);
                case "maxAge" -> filterMaxAgeParticipant = Integer.parseInt(entryValue);
                default -> {
                }
            }
        }

        int currentUserGenderId = currentUser.getGenderEntity().getId();
        int currentUserAge = calculateAge(String.valueOf(currentUser.getDob()));

        int participantGenderId = participant.getGenderEntity().getId();
        int participantAge = calculateAge(String.valueOf(participant.getDob()));
        
        if (currentUserGenderId == filterGenderParticipant && participantGenderId == filterGenderCurrent) {
            numberOfMatch++;
        }

        if (currentUserAge >= filterMinAgeParticipant && currentUserAge <= filterMaxAgeParticipant &&
                participantAge >= filterMinAgeCurrent && participantAge <= filterMaxAgeCurrent) {
            // Nếu tuổi của cả hai người dùng đều nằm trong khoảng tuổi yêu cầu
            numberOfMatch++;
        }
        return numberOfMatch > 0;
    }

    public static int calculateAge(String dobString) {
        LocalDate dob = LocalDate.parse(dobString);
        LocalDate currentDate = LocalDate.now();
        Period age = Period.between(dob, currentDate);
        return age.getYears();
    }

    private static Map<String, String> getStringStringMap(Pattern pattern, String value) {
        Matcher matcher = pattern.matcher(value);

        // Tạo một map để lưu trữ các cặp key-value
        Map<String, String> keyValuePairs = new HashMap<>();

        // Lặp qua các cặp key-value và thêm chúng vào map
        while (matcher.find()) {
            String myKey = matcher.group(1);
            String val = matcher.group(2);
            keyValuePairs.put(myKey, val);
        }
        return keyValuePairs;
    }

    public CompletableFuture<List<Map<String, String>>> fetchDataFromDatabaseHashMap(DatabaseReference ref) {
        CompletableFuture<List<Map<String, String>>> futureData = new CompletableFuture<>();
        List<Map<String, String>> dataList = new ArrayList<>();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Map<String, String> dataMap = new HashMap<>();
                    dataMap.put(snapshot.getKey(), snapshot.getValue().toString());
                    dataList.add(dataMap);
                }
                futureData.complete(dataList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                futureData.completeExceptionally(databaseError.toException());
            }
        });

        return futureData;
    }

//    @PostMapping("/feedbacks")
//    public ResponseEntity<?> takeRoom(@RequestBody String requestBody) throws ExecutionException, InterruptedException, JsonProcessingException {
//        try {
//            System.out.println("!!!");
//            JsonNode bodyJson = objectMapper.readTree(requestBody);
//
//            int rate = bodyJson.get("rate").asInt();
//            String description = bodyJson.get("description").asText();
//
//            System.out.println(rate);
//            System.out.println(description);
//
//        } catch (Exception err) {
//            System.out.println(err);
//        }
//        return null;
//    }



}