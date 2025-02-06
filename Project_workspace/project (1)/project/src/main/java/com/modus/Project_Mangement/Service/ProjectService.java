package com.modus.Project_Mangement.Service;

import com.modus.Project_Mangement.Payload.ProjectDto;

import java.util.List;

public interface ProjectService {
        public ProjectDto createProject(ProjectDto projectDto);

        List<ProjectDto> getAllProject();
    }
