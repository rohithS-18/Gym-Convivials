package com.gym.convivials.service;

import com.gym.convivials.dto.GymDto;
import com.gym.convivials.dto.GymGroupDto;
import com.gym.convivials.entities.GymGroup;
import com.gym.convivials.entities.Gyms;
import com.gym.convivials.mapper.GymGroupMapper;
import com.gym.convivials.mapper.GymMapper;
import com.gym.convivials.repository.GymGroupRepo;
import com.gym.convivials.repository.GymRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GymAdminService {
    @Autowired
    private GymRepo gymRepo;
    @Autowired
    private GymMapper gymMapper;
    @Autowired
    private GymGroupMapper gymGroupMapper;
    @Autowired
    private GymGroupRepo gymGroupRepo;

    public GymDto registerGym(GymDto gymDto) {
        Gyms gym= gymMapper.toEntity(gymDto);
        gym=gymRepo.save(gym);
        return gymMapper.toDto(gym);
    }

    public GymGroupDto registerGymGroup(GymGroupDto gymGroupDto){
        GymGroup group=gymGroupMapper.toEntity(gymGroupDto);
        group=gymGroupRepo.save(group);
        return gymGroupMapper.toDto(group);
    }

    public GymDto updateGym(int id,GymDto gymDto){
        Gyms gym=gymRepo.findById(id);
        gymMapper.updateGymFromDto(gymDto,gym);
        gym=gymRepo.save(gym);
        return gymMapper.toDto(gym);
    }

    public GymGroupDto updateGymGroup(int id,GymGroupDto gymGroupDto){
        GymGroup group=gymGroupRepo.findById(id);
        gymGroupMapper.updateGymFromDto(gymGroupDto,group);
        gymGroupRepo.save(group);
        return gymGroupMapper.toDto(group);
    }
}
