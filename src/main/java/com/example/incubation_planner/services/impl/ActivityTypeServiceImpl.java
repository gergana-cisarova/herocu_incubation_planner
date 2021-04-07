package com.example.incubation_planner.services.impl;

import com.example.incubation_planner.models.entity.ActivityType;
import com.example.incubation_planner.models.service.ActivityTypeServiceModel;
import com.example.incubation_planner.repositories.ActivityTypeRepository;
import com.example.incubation_planner.services.ActivityTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityTypeServiceImpl implements ActivityTypeService {

    private final ActivityTypeRepository activityTypeRepository;
    private final ModelMapper modelMapper;

    public ActivityTypeServiceImpl(
            ActivityTypeRepository activityTypeRepository,
            ModelMapper modelMapper) {

        this.activityTypeRepository = activityTypeRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public void seedActivityTypes() {
        if (activityTypeRepository.count() == 0) {
            ActivityType activityType1 = new ActivityType();
            activityType1.setActivityName("Business Support");
            ActivityType activityType2 = new ActivityType();
            activityType2.setActivityName("Networking Session");
            ActivityType activityType3 = new ActivityType();
            activityType3.setActivityName("Product Innovation Session");
            ActivityType activityType4 = new ActivityType();
            activityType4.setActivityName("Tech Demonstration");
            ActivityType activityType5 = new ActivityType();
            activityType5.setActivityName("Training");
            ActivityType activityType6 = new ActivityType();
            activityType6.setActivityName("Masterclass");
            activityTypeRepository.saveAll(List.of(activityType1, activityType2, activityType3, activityType4, activityType5, activityType6));
        }
    }

    @Override
    public void addNewActivity(ActivityTypeServiceModel activityTypeServiceModel) {
        activityTypeRepository.save(modelMapper.map(activityTypeServiceModel, ActivityType.class));
    }


    @Override
    public List<String> getAllActivities() {
        return activityTypeRepository.findAll().stream().map(ActivityType::getActivityName).collect(Collectors.toList());
    }
}
