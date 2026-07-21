package com.fitness.activityservice.service;

import com.fitness.activityservice.ActivityRepository;
import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.model.Activity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final UserValidationService userValidationService;
    private final KafkaTemplate<String, Activity> kafkaTemplate;

    @Value("${kafka.topic.name}")
    private String topicName;

    // we track activity using this method
    // this method will validate the user and then save the activity to the database and
    // send it to the Kafka topic for processing
    // through kafka it process through ActivityAiService
    public ActivityResponse trackActivity(ActivityRequest request) {

        log.info("trackActivity() called");

        // validate user
        boolean isValidUser = userValidationService.validateUser(request.getUserId());

        if (!isValidUser) {
            throw new RuntimeException("Invalid User");
        }

        // here we are creating activity object
        Activity activity = Activity.builder()
                .userId(request.getUserId())
                .type(request.getType())
                .duration(request.getDuration())
                .caloriesBurned(request.getCaloriesBurned())
                .startTime(request.getStartTime())
                .additionalProperties(request.getAdditionalProperties())
                .build();

        Activity savedActivity = activityRepository.save(activity);

        // We send this data asynchronously to Kafka topic for processing
        // Activity Message Listener in ai service listens the kafka topic
        // there we process the activity to create the recommendations
        // generateRecommendation method in ActivityAiService is called to generate the recommendations
        log.info("Sending message to Kafka topic: {}", topicName);

        kafkaTemplate.send(topicName, savedActivity.getUserId(), savedActivity)
                .whenComplete((result, ex) -> {

                    if (ex != null) {
                        log.error("Kafka Send Failed", ex);
                    } else {
                        log.info(
                                "Message sent successfully -> Topic={}, Partition={}, Offset={}",
                                result.getRecordMetadata().topic(),
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset());
                    }

                });

        return mapToResponse(savedActivity);
    }

    private ActivityResponse mapToResponse(Activity activity) {

        ActivityResponse response = new ActivityResponse();

        response.setId(activity.getId());
        response.setUserId(activity.getUserId());
        response.setType(activity.getType());
        response.setDuration(activity.getDuration());
        response.setCaloriesBurned(activity.getCaloriesBurned());
        response.setStartTime(activity.getStartTime());
        response.setAdditionalProperties(activity.getAdditionalProperties());
        response.setCreatedAt(activity.getCreatedAt());
        response.setUpdatedAt(activity.getUpdatedAt());

        return response;
    }

    public List<ActivityResponse> getUserActivities(String userId) {
        List<Activity> activities = activityRepository.findByUserId(userId);
        return activities.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }


    public ActivityResponse getActivityById(String activityId) {
        return activityRepository.findById(activityId)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Activity not found with id: " + activityId));
    }
}