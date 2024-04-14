package com.cupidconnect.cupidconnect.controllers;

import com.cupidconnect.cupidconnect.models.PersonalityType;
import com.cupidconnect.cupidconnect.services.PersonalityDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/personality")
@RequiredArgsConstructor
public class PersonalityDetailsController {
    private final PersonalityDetailsService personalityDetailsService;
    @GetMapping("/{id}")
    public ResponseEntity<PersonalityType> getPersonalityDetailsById(@PathVariable Long id) {
        PersonalityType details = personalityDetailsService.getPersonalityDetails(id);
        if (details != null) {
            return ResponseEntity.ok(details);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
