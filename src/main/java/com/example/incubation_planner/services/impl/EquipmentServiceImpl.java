package com.example.incubation_planner.services.impl;

import com.example.incubation_planner.models.entity.Equipment;
import com.example.incubation_planner.repositories.EquipmentRepository;
import com.example.incubation_planner.services.EquipmentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EquipmentServiceImpl implements EquipmentService {


    private final EquipmentRepository equipmentRepository;
    private final ModelMapper modelMapper;

    public EquipmentServiceImpl(

            EquipmentRepository equipmentRepository,
            ModelMapper modelMapper
    ) {


        this.equipmentRepository = equipmentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedEquipment() {
        if (equipmentRepository.count() == 0) {
            Equipment equipment1 = new Equipment();
            equipment1.setEquipmentName("Wood workshop");
            Equipment equipment2 = new Equipment();
            equipment2.setEquipmentName("Metal workshop");
            Equipment equipment3 = new Equipment();
            equipment3.setEquipmentName("Digital production workshop");
            Equipment equipment4 = new Equipment();
            equipment4.setEquipmentName("Prototyping space");
            Equipment equipment5 = new Equipment();
            equipment5.setEquipmentName("Computers, Multimedia, Printers");
            equipmentRepository.saveAll(List.of(equipment1, equipment2, equipment3, equipment4, equipment5));
        }
    }

    @Override
    public Equipment findEquipment(String equipmentName) {
        return equipmentRepository.findByEquipmentName(equipmentName).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public List<String> getAllEquipments() {
        return equipmentRepository.findAll().stream().map(Equipment::getEquipmentName).collect(Collectors.toList());

    }

}
