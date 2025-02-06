package com.modus.Project_Mangement.Repository;

import com.modus.Project_Mangement.Entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project,String> {
}
