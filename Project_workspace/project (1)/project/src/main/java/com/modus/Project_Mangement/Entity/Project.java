package com.modus.Project_Mangement.Entity;

//import jakarta.persistence.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Project")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Project {
    @Id
    public String projectID;
    public String projectName;
    public String startDate;
    public String endDate;
    public String projectType;
    @ManyToOne
    @JoinColumn(name = "manager_id")
    public Manager manager;
    public void setProjectID(String projectID) {
        this.projectID=projectID;
    }
}
