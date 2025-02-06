package com.modus.Project_Mangement.Payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectDto {
    public String projectID;
    @NotNull
    public String projectName;
    public String startDate;
    @NotNull
    public String endDate;
    @NotNull
    public String projectType;
    public String managerId;
}