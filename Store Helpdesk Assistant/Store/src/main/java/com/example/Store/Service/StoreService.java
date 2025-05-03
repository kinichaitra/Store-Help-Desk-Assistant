package com.example.Store.Service;

import com.example.Store.Exception.NotFoundException;
import com.example.Store.Model.Parameter;
import com.example.Store.Model.Store;
import com.example.Store.Model.StoreParameter;
import com.example.Store.Repository.ParameterRepo;
import com.example.Store.Repository.StoreParameterRepo;
import com.example.Store.Repository.StoreRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.example.Store.Repository.ParameterRepo.*;

@Service
public class StoreService {
    private final StoreRepo storeRepo;
    private final ParameterRepo parameterRepo;
    private final StoreParameterRepo storeParameterRepo;

    public StoreService(StoreRepo storeRepo, ParameterRepo parameterRepo, StoreParameterRepo storeParameterRepo) {
        this.storeRepo = storeRepo;
        this.parameterRepo = parameterRepo;
        this.storeParameterRepo = storeParameterRepo;
    }

//    @Autowired
//    private StoreRepo storeRepo;
//    @Autowired
//    private ParameterRepo parameterRepo;
//    @Autowired
//    private StoreParameterRepo storeParameterRepo;

    Logger logger = LoggerFactory.getLogger(StoreService.class);

    public List<Store> findAllStores() {
        return storeRepo.findAll();

    }
//    @Transactional
    public Store addStore(Store store) {
        try {
            logger.info("Adding a new store");
            return storeRepo.save(store);
        } catch (DataIntegrityViolationException e) {
            logger.error("Error adding store: ", e);
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Store already exists", e);
        } catch (Exception e) {
            logger.error("Error adding store: ", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error adding store", e);
        }
    }


        public Store getStoreByNumber(int number) {
        Optional<Store> storeOptional = storeRepo.findById(number);
        if (storeOptional.isPresent()) {
            return storeOptional.get();
        } else {
            throw new NotFoundException("Store not found with number: " + number);
        }
    }


//@Transactional
    public Store updateStorebyNumber(int number, Store updatedStore) {
        logger.info("updatestore function being implemented");
        Optional<Store> storeOptional = storeRepo.findById(number);
        if (!storeOptional.isPresent()) {
            throw new NotFoundException("Store with number " + number + " not found");
        }
        Store storeToUpdate = storeOptional.get();
        storeToUpdate.setCity(updatedStore.getCity());
        storeToUpdate.setZipcode(updatedStore.getZipcode());
        return storeRepo.save(storeToUpdate);

    }

    public ResponseEntity<Store> deleteStore(int number) {
        try {
            logger.info("deletestore function being implemented");
            storeRepo.deleteById(number);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            logger.error("Store with number: " + number + " not found");
            return ResponseEntity.notFound().build();
        }
    }

//    public Parameter addParameter(Parameter parameter) {
//        try {
//            return parameterRepo.save(parameter);
//        } catch (DataIntegrityViolationException e) {
//            throw new ResponseStatusException(HttpStatus.CONFLICT, "Parameter alrerady exists", e);
//        } catch (Exception e) {
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error adding Parameter", e);
//        }
//    }


//    public ResponseEntity<Parameter> deleteParameter(String parameter) {
//        logger.info("deleteParameter function being implemented");
//        if (!parameterRepo.existsById(parameter)) {
//            throw new NotFoundException("Parameter Not found");
//        }
//        parameterRepo.deleteById(parameter);
//        return ResponseEntity.ok().build();
//
//    }

//    public Parameter editParameter(Parameter parameter) {
//        logger.info("editparameter function being implemented");
//        Parameter existingParameter = parameterRepo.findById(parameter)
//                .orElseThrow(() -> new NotFoundException("parameter:" + parameter + "not found"));
//        existingParameter.setDescription(parameter.getDescription());
//        return parameterRepo.save(existingParameter);
//    }

//    public Parameter updateParameterDescription(String parameter, String newDescription) {
//        Optional<Parameter> optionalParameter = parameterRepository.findById(parameter);
//        if (!optionalParameter.isPresent()) {
//            throw new NotFoundException("Parameter not found");
//        }
//        Parameter existingParameter = optionalParameter.get();
//        existingParameter.setDescription(newDescription);
//        return parameterRepository.save(existingParameter);
//    }




//    public List<Parameter> findAllParameters() {
//        return parameterRepo.findAll();
//    }




    public List<Store> searchStoreByNumber(int number) {
        logger.info("Store Search By Number");
        List<Store> store = storeRepo.findByNumber(number);
        return store;
    }

    public List<Store> searchStoreByCity(String city) {
        logger.info("Store Search By City");
        List<Store> store = storeRepo.findByCity(city);
        return store;
    }

    public List<Store> searchStoreByZipCode(int zipcode) {
        logger.info("Store Search By Zipcode");
        List<Store> store = storeRepo.findByZipcode(zipcode);
        return store;
    }

    public List<Store> searchStoreByAllDetails(int number,String city, int zipcode) {
        List<Store> s = storeRepo.findByNumberAndCityAndZipcode(number, city, zipcode);
        return s;
    }

    public List<Store> searchStoreByNumberAndZipcode(int number, int zipcode) {
        return storeRepo.findByNumberAndZipcode(number, zipcode);
    }

    public List<Store> searchStoreByNumberAndCity(int number, String city) {
        return storeRepo.findByNumberAndCity(number, city);
    }
    public List<Store> searchStoreByZipcodeAndCity(int zipcode, String city) {
        return storeRepo.findByZipcodeAndCity(zipcode, city);
    }


//    upgrade method to update indvidual parameter
    public StoreParameter upgradePV(int number, String parameter, int parameterValue) {
        StoreParameter sp;
        sp = storeParameterRepo.findByNumberAndParameter(number, parameter);
        if (sp != null) {
            sp.setParameterValue(parameterValue);
        } else {
            sp = new StoreParameter();
            Parameter p = parameterRepo.findByParameter(parameter);
            sp.setParameterT(p);
            Store s = storeRepo.findByNumber(number).get(0);
            sp.setStore(s);
            sp.setParameterValue(parameterValue);
        }
        return storeParameterRepo.save(sp);
    }

    public String upgrade(int num, List<String> para) throws Exception {
        List<Parameter> parameterList = getParameter();
        if (para.size() != 0 && !(para.contains("ENABLE_DLV"))) {
            System.out.println("no dlv but more para");
            throw new Exception(" Delivery through Vendors not enabeled");
        } else {
            System.out.println("dlv yes or para is 0");
            for (Parameter i : parameterList) {
                String p = i.getParameter();
                if (para.contains(p)) {
                    upgradePV(num, p, 1);
                } else {
                    upgradePV(num, p, 0);
                }
            }
            return "Required parameters updated for Store Number: " + num;
        }
    }

    public List<Parameter> getParameter() {
        logger.info("get all parameters");
        return parameterRepo.findAll();
    }


    public StoreParameter parameterDetails(int number, String parameter) {
        return storeParameterRepo.findByNumberAndParameter(number, parameter);

    }



}