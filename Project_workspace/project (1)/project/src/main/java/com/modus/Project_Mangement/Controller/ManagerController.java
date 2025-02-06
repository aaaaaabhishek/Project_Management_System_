package com.modus.Project_Mangement.Controller;
import com.modus.Project_Mangement.Payload.ManagerDto;
import com.modus.Project_Mangement.Service.ManagerService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class ManagerController {
    private final ManagerService managerService;
    private static final Logger logger= LoggerFactory.getLogger(ManagerController.class);
    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @CircuitBreaker(name="addManagerbreaker" ,fallbackMethod="addManagerFallback")
    @Retry(name="addManagerRetry" ,fallbackMethod = "addManagerFallback")
    @RateLimiter(name = "ManagerRateLimiter",fallbackMethod = "addManagerFallback")

    public ResponseEntity<ManagerDto> addManager(@RequestBody ManagerDto managerdto){
        ManagerDto managerDto=managerService.addManager(managerdto);
        if(managerDto==null){
            return new ResponseEntity<>(managerDto, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(managerDto, HttpStatus.CREATED);
    }
    public ResponseEntity<ManagerDto> addManagerFallback(ManagerDto managerdto,Exception ex){
        System.out.println("Fallback is exicuted because service is down :"+ex.getMessage());
        logger.error("Fallback is exicuted because service is down :"+ex.getMessage());
        ex.printStackTrace();
        ManagerDto fallbackManagerDto = ManagerDto.builder()
                .managerId(null)
                .managerName("Server down")
//                .projects(Arrays.asList(new Project()))
                .build();
        return new ResponseEntity<>(fallbackManagerDto, HttpStatus.BAD_REQUEST);
    }
}
