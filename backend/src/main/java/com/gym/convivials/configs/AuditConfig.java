package com.gym.convivials.configs;

import com.gym.convivials.entities.User;
import com.gym.convivials.repository.UserRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class AuditConfig {
           @Bean
        public AuditorAware<String> auditorAware(UserRepo userRepository) {
               return () -> Optional.ofNullable(
                       SecurityContextHolder.getContext().getAuthentication().getName());
           }
}
