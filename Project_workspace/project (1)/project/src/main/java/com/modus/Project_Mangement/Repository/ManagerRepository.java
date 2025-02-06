package com.modus.Project_Mangement.Repository;

import com.modus.Project_Mangement.Entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager,Long> {
}
