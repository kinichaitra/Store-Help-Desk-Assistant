package com.example.Store.Service;


import com.example.Store.Exception.NotFoundException;
import com.example.Store.Model.Parameter;
import com.example.Store.Repository.ParameterRepo;
import com.example.Store.Repository.StoreParameterRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ParameterService {
    private final ParameterRepo parameterRepo;
    private final StoreParameterRepo storeParameterRepo;

    public ParameterService(ParameterRepo parameterRepo, StoreParameterRepo storeParameterRepo) {
        this.parameterRepo = parameterRepo;
        this.storeParameterRepo = storeParameterRepo;
    }

//    @Autowired
//    private  ParameterRepo parameterRepo;
//    @Autowired
//    private StoreParameterRepo storeParameterRepo;

     Logger logger = LoggerFactory.getLogger(ParameterService.class);


    public Parameter addParameter(Parameter parameter) {
        Parameter newParameter = parameterRepo.findByParameter(parameter.getParameter());

        if (newParameter != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Parameter already exists");
        }

        try {
            return parameterRepo.save(parameter);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Parameter already exists", e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error adding Parameter", e);
        }
    }
    public Parameter updateParameter(String parameter, Parameter updatedParameter) {
        Parameter existingParameter = parameterRepo.findByParameter(parameter);
        if (existingParameter == null) {
            throw new NotFoundException("Parameter not found");
        }
        existingParameter.setDescription(updatedParameter.getDescription());
        return parameterRepo.save(existingParameter);
    }

    public ResponseEntity<Parameter> deleteParameter(String parameter) {
        logger.info("deleteParameter function being implemented");
        if (!parameterRepo.existsById(parameter)) {
            throw new NotFoundException("Parameter Not found");
        }
        parameterRepo.deleteById(parameter);
        return ResponseEntity.ok().build();

    }
    public List<Parameter> findAllParameters() {
        logger.info("find All Parameters function being implemented");
        return parameterRepo.findAll();
    }

    public Parameter getParameterByParameter(String parameter) {
        Parameter foundParameter = parameterRepo.findByParameter(parameter);
        if (foundParameter == null) {
            throw new NotFoundException("Parameter not found");
        }
        return foundParameter;
    }


}
