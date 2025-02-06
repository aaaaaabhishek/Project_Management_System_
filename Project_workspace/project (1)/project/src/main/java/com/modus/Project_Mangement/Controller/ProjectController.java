package com.modus.Project_Mangement.Controller;
import com.modus.Project_Mangement.Payload.ProjectDto;
import com.modus.Project_Mangement.Service.ProjectService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ProjectController {
    private final ProjectService projectService;
    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }
    @PostMapping("/createProject")
    @CircuitBreaker(name="createProjectbreaker" ,fallbackMethod="createProjectFallback")
    @Retry(name="createProjectRetry" ,fallbackMethod = "createProjectFallback")
    @RateLimiter(name = "projectRateLimiter",fallbackMethod = "createProjectFallback")
    public ResponseEntity<?> createProject(@RequestBody @Valid ProjectDto projectDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            List<String> errors = getErrorMessages(bindingResult);
            logger.error(" validation error occurred ProductDto:{} ,error:{}", projectDto, errors);
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        ProjectDto result=projectService.createProject(projectDto);
        if (result != null) {
            logger.info("Successfully created a new Project: {}", result);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }
        logger.warn("Project creation is failed");
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/getAllProject")
    @CircuitBreaker(name="getAllProject_breaker" ,fallbackMethod="getAllProjectFallback")
    @Retry(name="getAllProjectRetry" ,fallbackMethod = "getAllProjectFallback")
    @RateLimiter(name = "projectRateLimiter",fallbackMethod = "getAllProjectFallback")
    public List<ProjectDto> getAllProject(){
        List<ProjectDto> projectDtos=projectService.getAllProject();
        return projectDtos;
    }
    private List<String> getErrorMessages(BindingResult bindingResult) {

        return bindingResult.getAllErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());

    }
    public ResponseEntity<ProjectDto> createProjectFallback(ProjectDto projectDto, Exception ex){
        System.out.println("Fallback is exicuted because service is down :"+ex.getMessage());
        logger.error("Fallback is exicuted because service is down :"+ex.getMessage());
        ex.printStackTrace();
        ProjectDto fallbackProjectDto = ProjectDto.builder()
                .projectID(null)
                .projectName("null")
                .projectType(null)
                .managerId(null)
                .startDate(String.valueOf(LocalDate.now()))
                .endDate(null)
                .build();
        return new ResponseEntity<>(fallbackProjectDto, HttpStatus.BAD_REQUEST);
    }
    public ResponseEntity<List<ProjectDto>> getAllProjectFallback(Exception ex){
        System.out.println("Fallback is exicuted because service is down :"+ex.getMessage());
        logger.error("Fallback is exicuted because service is down :"+ex.getMessage());
        ex.printStackTrace();
        ProjectDto fallbackProjectDto = ProjectDto.builder()
                .projectID(null)
                .projectName("null")
                .projectType("server down")
                .managerId(null)
                .startDate(String.valueOf(LocalDate.now()))
                .endDate(null)
                .build();
        return new ResponseEntity<>(Collections.singletonList(fallbackProjectDto), HttpStatus.BAD_REQUEST);
    }
}