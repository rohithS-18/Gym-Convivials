package com.gym.convivials.repository;

import com.gym.convivials.entities.PartnerPreference;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.List;

@RepositoryDefinition(domainClass = PartnerPreference.class,idClass=Integer.class)
public interface PartnerPreferenceRepo {
    public PartnerPreference save(PartnerPreference partnerPreference);
    public PartnerPreference findByUserUserId(int userId);
    public List<PartnerPreference> findByUserUserIdIn(List<Integer> userId);
}
