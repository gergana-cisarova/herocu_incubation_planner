package com.example.incubation_planner.services.impl;

import com.example.incubation_planner.models.entity.Lab;
import com.example.incubation_planner.models.entity.Project;
import com.example.incubation_planner.repositories.LabRepository;
import com.example.incubation_planner.services.EquipmentService;
import com.example.incubation_planner.services.LabService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;


@Service
public class LabServiceImpl implements LabService {

    private final LabRepository labRepository;
    private final EquipmentService equipmentService;
    private final ModelMapper modelMapper;

    public LabServiceImpl(
            LabRepository labRepository,
            EquipmentService equipmentService,
            ModelMapper modelMapper
    ) {

        this.labRepository = labRepository;
        this.equipmentService = equipmentService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedLabs() {
        if (labRepository.count() == 0) {
            Lab lab1 = new Lab();
            lab1.setName("Leonardo").setName("Wood workshop");
            Lab lab2 = new Lab();
            lab2.setName("Tesla").setName("Metal workshop");
            Lab lab3 = new Lab();
            lab3.setName("Lumiere").setName("Digital production workshop");
            Lab lab4 = new Lab();
            lab4.setName("Bell").setName("Prototyping space");
            Lab lab5 = new Lab();
            lab5.setName("Monnet").setName("Computers, Multimedia, Printers");
            Lab lab6 = new Lab();
            lab6.setName("Ideation").setName("Computers, Multimedia, Printers");
            Lab lab7 = new Lab();
            lab7.setName("STEM&Art").setName("Computers, Multimedia, Printers");
            Lab lab8 = new Lab();
            lab8.setName("Carnegie").setName("Computers, Multimedia, Printers");
            labRepository.saveAll(List.of(lab1, lab2, lab3, lab4, lab5, lab6, lab7, lab8));

        }
    }

    @Override
    public List<String> getAllLabs() {
        return labRepository.findAll().stream().map(Lab::getName).collect(Collectors.toList());
    }

    @Override
    public List<String> findSuitableLabs(String providedEquipment) {
        return
                labRepository
                        .findAllByEquipment_EquipmentName(providedEquipment)
                        .stream()
                        .map(l -> l.getName())
                        .collect(Collectors.toList());
    }

    @Override
    public Lab findLab(String labName) {
        return labRepository.findByName(labName).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public Map<String, String> getSuitableLabsWithProjects(String neededEquipment) {

        Map<String, String> info = new TreeMap<>();
        List<Lab> labs = labRepository
                .findAllByEquipment_EquipmentName(neededEquipment);
        labs
                .forEach(l -> {
                    String infoForLab = getInfoForLab(l);
                    info.put(l.getName(), infoForLab);
                });

        return info;
    }

    @Override
    public Map<String, String> getAllLabsWithProjects() {
        Map<String, String> info = new TreeMap<>();
        List<Lab> labs = labRepository
                .findAll();
        labs
                .forEach(l -> {
                    String infoForLab = getInfoForLab(l);
                    info.put(l.getName(), infoForLab);
                });

        return info;
    }

    private String getInfoForLab(Lab l) {
        List<Project> projects = l.getProjects();
        List<Project> copyOfProjects = new ArrayList<>(projects);
        copyOfProjects.sort((p1, p2) -> p1.getStartDate().compareTo(p2.getStartDate()));
        StringBuilder sb = new StringBuilder();
        copyOfProjects.forEach(p -> {
            if (p.getEndDate().isAfter(LocalDateTime.now())) {
                String currentProject =
                        String.format("%02d %s %s (%02d:%02d) - %02d %s %s (%02d:%02d) <br />",
                                p.getStartDate().getDayOfMonth(), p.getStartDate().getMonth(), p.getStartDate().getYear(), p.getStartDate().getHour(), p.getStartDate().getMinute(),
                                p.getEndDate().getDayOfMonth(), p.getEndDate().getMonth(), p.getEndDate().getYear(), p.getEndDate().getHour(), p.getEndDate().getMinute());
                sb.append(currentProject);
            }
        });
        return sb.toString();
    }
}
