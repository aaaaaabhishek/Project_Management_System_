package com.modus.Project_Mangement.Service.IService;

import com.modus.Project_Mangement.Entity.Project;
import com.modus.Project_Mangement.Exception.ProjectCreationException;
import com.modus.Project_Mangement.Payload.ProjectDto;
import com.modus.Project_Mangement.Repository.ProjectRepository;
import com.modus.Project_Mangement.Service.ProjectService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Iprojectservice implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ModelMapper mapper;
    private static final Logger logger = LoggerFactory.getLogger(Iprojectservice.class);

    public Iprojectservice(ProjectRepository projectRepository, ModelMapper mapper) {
        this.projectRepository = projectRepository;
        this.mapper = mapper;
    }

    @Override
    public ProjectDto createProject(ProjectDto projectDto) {

        String projectID = UUID.randomUUID().toString();
        Project project = convertToEntity(projectDto);
        project.setProjectID(projectID);

        logger.info("Creating new Project with ID: {}, Details: {}", projectID, projectDto);

        Project savedProject = projectRepository.save(project);
        if (savedProject == null) {
            logger.error("Failed to save project with ID: {}", projectID);
            throw new ProjectCreationException("Failed to save project with ID:" + projectID);
        }
//            logger.info("Successfully saved Project with ID: {}", savedProject.getProjectID());

        return convertToDto(savedProject);
    }

    @Override
    public List<ProjectDto> getAllProject() {
        logger.info("Fetching all projects from the database.");

        List<Project> projectList = projectRepository.findAll();

        logger.info("Retrieved {} projects.", projectList.size());

        return projectList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private ProjectDto convertToDto(Project project) {
        logger.debug("Mapping Project entity to ProjectDto: {}", project);
        return mapper.map(project, ProjectDto.class);
    }

    private Project convertToEntity(ProjectDto projectDto) {
        logger.debug("Mapping ProjectDto to Project entity: {}", projectDto);
        return mapper.map(projectDto, Project.class);
    }
}