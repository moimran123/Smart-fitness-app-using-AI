package com.example.activityservice.service;

import com.example.activityservice.dto.ActivityRequest;
import com.example.activityservice.dto.ActivityResponse;
import com.example.activityservice.model.Activity;
import com.example.activityservice.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final UserValidationService userValidationService;

    private String topicName;
    public  ActivityResponse trackActivity(ActivityRequest request) {

        boolean isValidUser = userValidationService.validateUser(request.getUserId());

        if (!isValidUser) {
            throw new RuntimeException("Invalid User : " + request.getUserId());
        }

     Activity activity= Activity.builder()
             .userId(request.getUserId())
             .type(request.getType())
             .duration(request.getDuration())
             .caloriesBurned(request.getCaloriesBurned())
             .startTime(request.getStartTime())
             .additionalMatrics(request.getAdditionalMatrics())
             .build();
   Activity savedActivity=activityRepository.save(activity);


   return mapToResponse(savedActivity);

    }

    private ActivityResponse mapToResponse(Activity activity) {
      ActivityResponse response=new ActivityResponse();
      response.setId(activity.getId());
      response.setUserId(activity.getUserId());
      response.setType(activity.getType());
      response.setDuration(activity.getDuration());
      response.setCaloriesBurned(activity.getCaloriesBurned());
      response.setStartTime(activity.getStartTime());
      response.setAdditionalMatrics(activity.getAdditionalMatrics());
      response.setCreatedAt(activity.getCreatedAt());
      response.setUpdatedAt(activity.getUpdatedAt());
      return  response;

    }

}
