package com.cupidconnect.cupidconnect.services;


import com.cupidconnect.cupidconnect.models.*;
import com.cupidconnect.cupidconnect.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PersonalityDetailsService {
    private final PersonalityTypeRepository personalityTypeRepository;
    private final StrengthRepository strengthRepository;
    private final WeaknessRepository weaknessRepository;
    private final FamousPersonalityRepository famousPersonalityRepository;
    private final RecommendedCareerRepository recommendedCareerRepository;
    private final RelationshipRepository relationshipRepository;
    public PersonalityType getPersonalityDetails(Long id) {
        Optional<PersonalityType> personalityType = personalityTypeRepository.findById(id);
        if(personalityType.isPresent()) {
            PersonalityType detailedPersonalityType = personalityType.get();
            Long typeId = detailedPersonalityType.getId();
            List<Strength> strengthList = strengthRepository.findByPersonalityType_Id(typeId);
            Set<Strength> strengthSet = new HashSet<>(strengthList);
            detailedPersonalityType.setStrengths(strengthSet);
            List<Weakness> weaknessList = weaknessRepository.findByPersonalityType_Id(typeId);
            Set<Weakness> weaknessSet = new HashSet<>(weaknessList);
            detailedPersonalityType.setWeaknesses(weaknessSet);
            List<FamousPersonality> famousPersonalityList = famousPersonalityRepository.findByPersonalityType_Id(typeId);
            Set<FamousPersonality> famousPersonalitySet = new HashSet<>(famousPersonalityList);
            detailedPersonalityType.setFamousPersonalities(famousPersonalitySet);
            List<RecommendedCareer> recommendedCareerList = recommendedCareerRepository.findByPersonalityType_Id(typeId);
            Set<RecommendedCareer> recommendedCareerSet = new HashSet<>(recommendedCareerList);
            detailedPersonalityType.setRecommendedCareers(recommendedCareerSet);
            detailedPersonalityType.setRelationship(relationshipRepository.findByPersonalityTypeId(typeId));
            return detailedPersonalityType;
        } else {

            throw new RuntimeException("Personality type not found");
        }
    }
}
