package com.example.activityservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@Slf4j

@RequiredArgsConstructor
public class UserValidationService {

    private final WebClient userServiceWebClient;

    public boolean validateUser(String userId){
        log.info("Calling User Service for {} ",userId);
       try{

           return userServiceWebClient.get()
                   .uri("/api/users/{userId}/validate",userId)
                   .retrieve()
                   .bodyToMono(Boolean.class)
                   .block();

       }
       catch (WebClientResponseException e){
           log.info("Failed to Call User service ");
       e.printStackTrace();
       }
       return false;
    }
}
