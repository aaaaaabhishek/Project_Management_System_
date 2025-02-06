package com.modus.Project_Mangement.Payload;

import com.modus.Project_Mangement.Entity.Project;
import jdk.nashorn.internal.runtime.Debug;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ManagerDto {
    public Long managerId;
    public String managerName;
    public List<Project> projects;

}
