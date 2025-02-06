package com.modus.Project_Mangement.Service.IService;

import com.modus.Project_Mangement.Entity.Manager;
import com.modus.Project_Mangement.Payload.ManagerDto;
import com.modus.Project_Mangement.Repository.ManagerRepository;
import com.modus.Project_Mangement.Service.ManagerService;
import org.modelmapper.ModelMapper;

public class ImanagerService implements ManagerService {
private final ManagerRepository managerRepository;
private final ModelMapper mapper;

public ImanagerService(ManagerRepository managerRepository, ModelMapper mapper) {
    this.managerRepository = managerRepository;
    this.mapper = mapper;
}

@Override
public ManagerDto addManager(ManagerDto managerDto) {
    Manager manager=mapper.map(managerDto,Manager.class);
    Manager savedManager=managerRepository.save(manager);
    return mapper.map(savedManager,ManagerDto.class);
}
}
