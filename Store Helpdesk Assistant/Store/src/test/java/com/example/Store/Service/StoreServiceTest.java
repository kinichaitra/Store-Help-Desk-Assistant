package com.example.Store.Service;

import com.example.Store.Model.Parameter;
import com.example.Store.Model.Store;
import com.example.Store.Model.StoreParameter;
import com.example.Store.Repository.ParameterRepo;
import com.example.Store.Repository.StoreParameterRepo;
import com.example.Store.Repository.StoreRepo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class StoreServiceTest {

    @Mock
    static StoreRepo storeRepo;

    @Mock
    static ParameterRepo parameterRepo;

    @Mock
    static StoreParameterRepo storeParameterRepo;

    @InjectMocks
    static StoreService service;

    static List<Store> expected = new ArrayList<>();
    static List<Parameter> parameterList = new ArrayList<>();

//    @BeforeAll
//    static void init() {
//        storeRepo = mock(StoreRepo.class);
//        expected.add(new Store(4, 2020, "Kolkata", 700001,null));
//        parameterList.add(new Parameter(1, "Enable_zo", null));
//    }

    @Test
    void searchStoreByNumber() {
        when(storeRepo.findByNumber(2020)).thenReturn(expected);
        List<Store> actual = service.searchStoreByNumber(2020);
        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    void searchStoreByCity() {
        when(storeRepo.findByCity("Kolkata")).thenReturn(expected);
        List<Store> actual = service.searchStoreByCity("Kolkata");
        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    void searchStoreByZipCode() {
        when(storeRepo.findByZipcode(700001)).thenReturn(expected);
        List<Store> actual = service.searchStoreByZipCode(700001);
        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    void searchStoreByAllDetails() {
        when(storeRepo.findByNumberAndCityAndZipcode(2020, "Kolkata",700001)).thenReturn(expected);
        List<Store> actual = service.searchStoreByAllDetails(2020, "Kolkata", 700001);
        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    void searchStoreByNumberAndZipcode() {
        when(storeRepo.findByNumberAndZipcode(2020, 700001)).thenReturn(expected);
        List<Store> actual = service.searchStoreByNumberAndZipcode(2020, 700001);
        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    void searchStoreByNumberAndCity() {
        when(storeRepo.findByNumberAndCity(2020, "Kolkata")).thenReturn(expected);
        List<Store> actual = service.searchStoreByNumberAndCity(2020, "Kolkata");
        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    void searchStoreByZipcodeAndCity() {
        when(storeRepo.findByZipcodeAndCity(700001, "Kolkata")).thenReturn(expected);
        List<Store> actual = service.searchStoreByZipcodeAndCity(700001, "Kolkata");
        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    void upgrade() throws Exception {

        List<String> li = new ArrayList<>();
        antlr.collections.List list = null;
        list.add("Enable_DLV");

        when(service.getParameter()).thenReturn(parameterList);
        when(storeRepo.findByNumber(2020)).thenReturn(expected);
        when(service.upgradePV(2020, anyString(), anyInt())).thenReturn(new StoreParameter());
        assertEquals("Required parameters updated for Store Number: 2020", service.upgrade(2020, li));
    }

    @Test
    void getParameter() {
        when(parameterRepo.findAll()).thenReturn(parameterList);
        assertEquals(parameterList, service.getParameter());
    }

//    @Test
//    void parameterDetails() {
//        StoreParameter sp = new StoreParameter(1, "Enable_DLV",2020);
//        when(storeParameterRepo.findByNumberAndParameter(2020, "Enable_DLV")).thenReturn(sp);assertEquals(sp, service.parameterDetails(2020, "Enable_DLV"));
//    }
}

