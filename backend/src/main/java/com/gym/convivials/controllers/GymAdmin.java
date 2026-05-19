package com.gym.convivials.controllers;

import com.gym.convivials.dto.GymDto;
import com.gym.convivials.dto.GymGroupDto;
import com.gym.convivials.dto.GymSearchCriteria;
import com.gym.convivials.service.GymAdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/admin")
public class GymAdmin {
    @Autowired
    private GymAdminService adminService;

    @PostMapping("/gym")
    public ResponseEntity<?> addGym(@Valid  @RequestBody GymDto gymDto){
        GymDto addedGym=adminService.registerGym(gymDto);
        URI location=URI.create("gyms/"+addedGym.getId());
        return  ResponseEntity.created(location).body(addedGym);
    }

    @PostMapping("/gym-group")
    public ResponseEntity<?> createGroup(@RequestBody GymGroupDto gymGroupDto){
        GymGroupDto addedGroup=adminService.registerGymGroup(gymGroupDto);
        URI location=URI.create("gym-group/"+addedGroup.getId());
        return  ResponseEntity.created(location).body(addedGroup);
    }

    @PatchMapping("/groups/{groupId}")
    public ResponseEntity<GymGroupDto> addGymToGroup(@PathVariable int groupId, @RequestBody GymGroupDto gymGroupDto){
        GymGroupDto updatedGroup=adminService.updateGymGroup(groupId,gymGroupDto);
        return ResponseEntity.ok(updatedGroup);
    }

    @PatchMapping("/gyms/{gymId}")
    public ResponseEntity<?> updateGymGroup(@PathVariable int gymId,@RequestBody GymDto gymDto){
        GymDto updatedGym=adminService.updateGym(gymId,gymDto);
        return  ResponseEntity.ok(updatedGym);
    }

    @GetMapping("/gym")
    public ResponseEntity<?> getGyms(@ModelAttribute GymSearchCriteria gymSearchCriteria){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
