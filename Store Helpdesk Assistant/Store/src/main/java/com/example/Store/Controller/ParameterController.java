package com.example.Store.Controller;


import com.example.Store.Exception.NotFoundException;
import com.example.Store.Model.Parameter;
import com.example.Store.Service.ParameterService;
import com.example.Store.feign.AuthClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;


@CrossOrigin(origins = "http://localhost:4200", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
@RestController
@RequestMapping("/Parameters")
public class ParameterController {
    Logger logger = LoggerFactory.getLogger(ParameterController.class);
    private final ParameterService parameterservice;
    private final AuthClient authClient;
    public ParameterController(ParameterService parameterservice,AuthClient authClient){
        this.parameterservice=parameterservice;
        this.authClient=authClient;
    }
//    private ParameterService parameterservice;
//    @Autowired
//    private AuthClient authClient;

    //   http://localhost:9091/Parameters/add
    @PostMapping("/add")
    public ResponseEntity<Parameter> addParameter(@RequestBody Parameter parameter) {
        LOGGER.info("Parameter added successfully-Controller");
            Parameter newParameter = parameterservice.addParameter(parameter);
            return ResponseEntity.status(HttpStatus.CREATED).body(newParameter);

    }

    //    localhost:9091/Parameters/update/{parameter}
    @PutMapping("/update/{parameter}")
    public ResponseEntity<Parameter> updateParameter(@PathVariable String parameter,
                                                     @RequestBody Parameter updatedParameter) {
        LOGGER.info("Parameter updated successfully-Controller");
        Parameter updated = parameterservice.updateParameter(parameter, updatedParameter);
        return ResponseEntity.ok(updated);
    }


    //   http://localhost:9091/Parameters/delete/{parameter}
    @DeleteMapping("/delete/{parameter}")
    public ResponseEntity<Parameter> deleteParameter(@PathVariable String parameter) {
        LOGGER.info("Parameter deleted successfully");
        return parameterservice.deleteParameter(parameter);

    }

    // http://localhost:9091/Parameters/findallParameters
    @GetMapping("/findallParameters")
    public List<Parameter> findAllParameters() {
        LOGGER.info("find all Parameters");
        return parameterservice.findAllParameters();
    }
//    http://localhost:9091/Parameters/ENABLE_TEST1
    @GetMapping("/{parameter}")
    public ResponseEntity<Parameter> getParameterByParameter(@PathVariable String parameter) {
        try {
            Parameter foundParameter = parameterservice.getParameterByParameter(parameter);
            return ResponseEntity.ok(foundParameter);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}



