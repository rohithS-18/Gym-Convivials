package com.gym.convivials.controllers;

import com.gym.convivials.dto.PartnerPreferenceDto;
import com.gym.convivials.dto.PartnerResponse;
import com.gym.convivials.dto.UserDto;
import com.gym.convivials.entities.User;
import com.gym.convivials.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RecommendationController {
    @Autowired
    private RecommendationService recommendationService;

    @GetMapping("/get-friend-recommendations")
    public ResponseEntity<List<User>> friendRecommendations(@RequestParam int userId){
            return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/partner-recommendations/{userId}")
    public ResponseEntity<List<PartnerResponse>> partnerRecommendations(@PathVariable int userId){
        List<PartnerResponse> partnerRecommendation=recommendationService.getPartnerRecommendations(userId);
        return new ResponseEntity<>(partnerRecommendation,HttpStatus.OK);
    }

    @PostMapping("/partner-preferene")
    public ResponseEntity<PartnerPreferenceDto> savePartnerPreferences(@RequestBody PartnerPreferenceDto partnerPreferenceDto){
        partnerPreferenceDto=recommendationService.createPreference(partnerPreferenceDto);
        return new ResponseEntity<>(partnerPreferenceDto,HttpStatus.OK);
    }
}
