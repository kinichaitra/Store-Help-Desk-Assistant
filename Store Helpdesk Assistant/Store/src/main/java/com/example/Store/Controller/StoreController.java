package com.example.Store.Controller;

import com.example.Store.Exception.InvalidTokenException;
import com.example.Store.Exception.NotFoundException;
import com.example.Store.Model.Store;
import com.example.Store.Model.StoreDetail;
import com.example.Store.Model.StoreParameter;
import com.example.Store.Model.UpgradeData;
import com.example.Store.Service.StoreService;
import com.example.Store.feign.AuthClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;
import static org.springframework.http.ResponseEntity.status;

@CrossOrigin(origins = "http://localhost:4200", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
@RestController
@RequestMapping("/store")
public class StoreController {
    Logger logger = LoggerFactory.getLogger(StoreController.class);

//    @Autowired
//    private StoreService service;
//    @Autowired
//    private AuthClient authClient;
    private final StoreService service;
    private final AuthClient authClient;
//constructor injection
public StoreController(StoreService service, AuthClient authClient) {
        this.service = service;
        this.authClient = authClient;
    }

    //localhost:9091/store/findall
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/findall")
    public List<Store> findAllStores() {
        LOGGER.info("find/get all stores");
        return service.findAllStores();
    }

    //localhost:9091/store/add
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<?> addStore(@RequestBody Store store) {
        try {
            logger.info("Creating a new store");
            Store newStore = service.addStore(store);
            return new ResponseEntity<>(newStore, HttpStatus.CREATED);
        } catch (ResponseStatusException e) {
            logger.error("Error creating store: ", e);
            return new ResponseEntity<>(e.getReason(), e.getStatus());
        } catch (Exception e) {
            logger.error("Error creating store: ", e);
            return new ResponseEntity<>("Error creating store", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{number}")
    public ResponseEntity updateStoreByNumber(@PathVariable int number,
                                              @RequestBody Store updatedStore){
//        try {
            logger.info("Store with number + number + Updated succussfully");
            Store updated = service.updateStorebyNumber(number, updatedStore);
            return ResponseEntity.ok(updated);
//
//        } catch (NotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body("Store with number: " + number + " not found");
//
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error updating store: " + e.getMessage());
//        }
    }

    //    http://localhost:9091/store/delete/number
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{number}")
    public ResponseEntity<?> deleteStore(@PathVariable int number) {
        logger.info("store is deleted");
        ResponseEntity<Store> response = service.deleteStore(number);

        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            return status(HttpStatus.NOT_FOUND)
                    .body("Store with number: " + number + " not found");
        }
        return response;
    }

//    http://localhost:9091/store/{number}
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{number}")
    public ResponseEntity<Store> getStoreByNumber(@PathVariable int number) {
        Store store = service.getStoreByNumber(number);
        if (store == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(store);
    }

    @GetMapping("/search")
    public ResponseEntity storeSearch(@RequestHeader(name = "number", required = false) String number,
                                      @RequestHeader(name = "city", required = false) String city,
                                      @RequestHeader(name = "zipcode", required = false) String zipcode,
                                      @RequestHeader(name = "Authorization") String token) {

        if (isNullOrEmpty(number)) {
            number = "-1";
        }
        if (isNullOrEmpty(city)) {
            city = "-1";
        }
        if (isNullOrEmpty(zipcode)) {
            zipcode = "-1";
        }

        logger.info("Inside Store Search Controller| number:{}, city:{}, zipcode:{}", number, city, zipcode);
        StoreDetail storeDetails = new StoreDetail(Integer.parseInt(number), city, Integer.parseInt(zipcode));

        //list storing all the searched store details
        List<Store> list = new ArrayList<>();
        try {
            if (!(authClient.validate(token))) {
                throw new InvalidTokenException("Invalid token Exception" );
            }
            try {
                if (storeDetails.getNumber() != -1 && storeDetails.getZipcode() != -1 && !(storeDetails.getCity().equals("-1"))) {
                    list.addAll(service.searchStoreByAllDetails(storeDetails.getNumber(),storeDetails.getCity(), storeDetails.getZipcode()));
                } else if (storeDetails.getNumber() != -1 && storeDetails.getZipcode() != -1) {
                    list.addAll(service.searchStoreByNumberAndZipcode(storeDetails.getNumber(), storeDetails.getZipcode()));
                } else if (storeDetails.getNumber() != -1 && !(storeDetails.getCity().equals("-1"))) {
                    list.addAll(service.searchStoreByNumberAndCity(storeDetails.getNumber(), storeDetails.getCity()));
                } else if (storeDetails.getZipcode() != -1 && !(storeDetails.getCity().equals("-1"))) {
                    list.addAll(service.searchStoreByZipcodeAndCity(storeDetails.getZipcode(), storeDetails.getCity()));
                } else {
                    if (storeDetails.getNumber() != -1) {
                        list.addAll(service.searchStoreByNumber(storeDetails.getNumber()));
                    }
                    if (storeDetails.getZipcode() != -1) {
                        list.addAll(service.searchStoreByZipCode(storeDetails.getZipcode()));
                    }
                    if (!(storeDetails.getCity().equals("-1"))) {
                        list.addAll(service.searchStoreByCity(storeDetails.getCity()));
                    }
                }
                if (list.isEmpty())
                    throw new NotFoundException("Store not found!!");
                return new ResponseEntity(list, HttpStatus.OK);


            } catch (Exception e) {
                logger.warn("Exception!!! returning exception message| number:{}, city:{}, zipcode:{}", number, city, zipcode);
                return status(HttpStatus.NOT_ACCEPTABLE)
                        .body(e.getMessage());
            }
        } catch (Exception e) {
            return new ResponseEntity(null, HttpStatus.UNAUTHORIZED);
        }
    }

    //Upgrade Part
    //receiving the store number and List of Strings containing those data that are to be updated
    @PostMapping("/upgrade")
    public ResponseEntity upgrade(@RequestBody UpgradeData upgradeData,
                                  @RequestHeader(name = "Authorization") String token) {

        try {
            if (!(authClient.validate(token) && authClient.isHelpdesk(token))) {
//            if(!(authClient.validate(token))) {
                throw new InvalidTokenException("Invalid token");
            }
            System.out.println("Upgrade Controller method");
            System.out.println(upgradeData);
            int number = upgradeData.getNum();
            List<String> para = upgradeData.getPara();
            try {
                service.upgrade(number, para);
                return new ResponseEntity(null, HttpStatus.OK);
            } catch (Exception e) {
                return status(HttpStatus.NOT_ACCEPTABLE)
                        .body(e.getMessage());
            }
        } catch (Exception e) {
            return new ResponseEntity(null, HttpStatus.UNAUTHORIZED);


        }
    }


    //not used
    @GetMapping("/parameterDetails")
    public ResponseEntity parameterDetails(@RequestHeader(name = "number") String num,
                                           @RequestHeader(name = "parameter") String parameter) {
        int number = Integer.parseInt(num);
        StoreParameter sp = service.parameterDetails(number, parameter);
        return new ResponseEntity(sp, HttpStatus.OK);
    }

    //sends all the parameters which is there in the parameter table
    @GetMapping("/parameter")
    public ResponseEntity getParameter(@RequestHeader(name = "Authorization") String token) {
        if(authClient.validate(token)) {
            return new ResponseEntity(service.getParameter(), HttpStatus.OK);
        }
        return null;
    }

    private boolean isNullOrEmpty(String param){
        return param == null || param.isEmpty();
    }

}
