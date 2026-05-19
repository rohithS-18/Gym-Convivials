package com.gym.convivials.service;

import com.gym.convivials.dto.PartnerPreferenceDto;
import com.gym.convivials.dto.PartnerResponse;
import com.gym.convivials.entities.PartnerPreference;
import com.gym.convivials.entities.User;
import com.gym.convivials.mapper.PartnerPreferenceMapper;
import com.gym.convivials.mapper.UserMapper;
import com.gym.convivials.repository.GymRepo;
import com.gym.convivials.repository.PartnerPreferenceRepo;
import com.gym.convivials.repository.UserRepo;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecommendationService {
    @Autowired
    private PartnerPreferenceRepo partnerPreferenceRepo;
    @Autowired
    private PartnerPreferenceMapper partnerPreferenceMapper;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private GymRepo gymRepo;


    public PartnerPreferenceDto createPreference(PartnerPreferenceDto partnerPreferenceDto){
        PartnerPreference partnerPreference=partnerPreferenceMapper.toEntity(partnerPreferenceDto);
        partnerPreference=partnerPreferenceRepo.save(partnerPreference);
        return partnerPreferenceMapper.toDto(partnerPreference);
    }

    public List<PartnerResponse> getPartnerRecommendations(int userId) {
        User curUser=userRepo.findByUserId(userId);
        PartnerPreference curUserpartnerPreference =partnerPreferenceRepo.findByUserUserId(userId);
        List<User> userList=null;
        if(curUserpartnerPreference ==null)
            return null;
        if(curUserpartnerPreference.getGym()==null){
            userList=userRepo.findByGroupAndCity(curUserpartnerPreference.getGymGroup().getId(),curUser.getCity());
        }
        else{
            userList=userRepo.findByGymId(curUserpartnerPreference.getGym().getId());
        }
        return filterUsersBasedOnPreference(userList,curUser,curUserpartnerPreference);
    }
    public List<PartnerResponse> filterUsersForFriendRecommendations(){

        return new ArrayList<>();
    }

    public List<PartnerResponse> filterUsersBasedOnPreference(List<User> unFilteredUsers,User currentUser,PartnerPreference curUserPartnerPreference){

        Point curUserLocation=currentUser.getLocation();
        List<PartnerResponse> partnerResponses=new ArrayList<>();
        List<Integer> userIds=unFilteredUsers.stream().map(User::getUserId).toList() ;
        List<PartnerPreference> partnerPreferencesOfUnfilteredUsers=partnerPreferenceRepo.findByUserUserIdIn(userIds);
        for(int i=0;i<unFilteredUsers.size();i++){
            double matches=0.0;
            PartnerResponse partner=new PartnerResponse();
            User filteringUser=unFilteredUsers.get(i);
            PartnerPreference filteringUserPartnerPreference=partnerPreferencesOfUnfilteredUsers.stream().filter(partPref->partPref.getUser().getUserId()==filteringUser.getUserId()).findFirst().orElse(null);
//            PartnerPreference filteringUserPartnerPreference =partnerPreferenceRepo.findByUserUserId(filteringUser.getUserId());
            if(filteringUserPartnerPreference==null)
                continue;
            if(curUserPartnerPreference.getWorkoutGoals()== filteringUserPartnerPreference.getWorkoutGoals()){
                matches+=40.0;
            }
            if(curUserPartnerPreference.getGender()== filteringUserPartnerPreference.getGender()){
                matches+=10.0;
            }
            if(curUserPartnerPreference.getPreferredTime().isBefore(filteringUserPartnerPreference.getPreferredTime().plusMinutes(30)) || curUserPartnerPreference.getPreferredTime().isAfter(filteringUserPartnerPreference.getPreferredTime().minusMinutes(30))){
                matches+=25;
            }
            else if(curUserPartnerPreference.getPreferredTime().isBefore(filteringUserPartnerPreference.getPreferredTime().plusMinutes(45)) || curUserPartnerPreference.getPreferredTime().isAfter(filteringUserPartnerPreference.getPreferredTime().minusMinutes(45))){
                matches+=25*.75;
            }
            else if(curUserPartnerPreference.getPreferredTime().isBefore(filteringUserPartnerPreference.getPreferredTime().plusMinutes(60)) || curUserPartnerPreference.getPreferredTime().isAfter(filteringUserPartnerPreference.getPreferredTime().minusMinutes(60))){
                matches+=25*.50;
            }
            else if(curUserPartnerPreference.getPreferredTime().isBefore(filteringUserPartnerPreference.getPreferredTime().plusMinutes(75)) || curUserPartnerPreference.getPreferredTime().isAfter(filteringUserPartnerPreference.getPreferredTime().minusMinutes(75))){
                matches+=25*.25;
            }
            if(curUserPartnerPreference.getGym().getId()!=null){
                if(curUserPartnerPreference.getGym().getId()==filteringUserPartnerPreference.getGym().getId())
                     matches+=25;
            }
            else{
                double vicinity=curUserPartnerPreference.getVicinity();
                if(curUserLocation.distance(filteringUser.getLocation())<=vicinity){
                    matches+=25;
                }
                else if(curUserLocation.distance(filteringUser.getLocation())<=vicinity+5.0){
                    matches+=25*.50;
                }
                else if(curUserLocation.distance(filteringUser.getLocation())<=vicinity+10.0){
                    matches+=25*.25;
                }
            }
            if(matches>=60){
                partner.setUserId(filteringUser.getUserId());
                partner.setProfilePicUrl(filteringUser.getProfilePic());
                partner.setMatchPercentage(matches);
                partner.setName(filteringUser.getUsername());
                partnerResponses.add(partner);
            }
        }
        return partnerResponses;
    }
}
