package com.example.Store.Controller;

import com.example.Store.Model.Parameter;
import com.example.Store.Model.Store;
import com.example.Store.Model.StoreParameter;
import com.example.Store.Model.UpgradeData;
import com.example.Store.Service.StoreService;
import com.example.Store.feign.AuthClient;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.rules.ExpectedException.none;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class StoreControllerTest {

    @Mock
    static StoreService service;

    @Mock
    AuthClient authClient;

    @InjectMocks
    static StoreController controller;

    static List<Store> expected = new ArrayList<>();

    @Rule
//    define additional test rules or handling exceptions
    public ExpectedException exceptionRule = none();

    @BeforeAll
    static void init() {
        service=mock(StoreService.class);
        expected.add(new Store(2020,"Kolkata",700001,null));
    }

    @Test
    void storeSearch() {
        when(service.searchStoreByAllDetails(2020,"Kolkata",700001)).thenReturn(expected);
        when(authClient.validate("token")).thenReturn(true);
        ResponseEntity actual=controller.storeSearch("2020","Kolkata","700001","token");
        assertArrayEquals(expected.toArray(),((List<Store>) Objects.requireNonNull(actual.getBody())).toArray());

        when(service.searchStoreByNumberAndZipcode(2020,700001)).thenReturn(expected);
        assertArrayEquals(expected.toArray(),((List<Store>) Objects.requireNonNull(controller.storeSearch("2020",  null,"700001", "token").getBody())).toArray());

        when(service.searchStoreByNumberAndCity(2020,"Kolkata")).thenReturn(expected);
        assertArrayEquals(expected.toArray(),((List<Store>) Objects.requireNonNull(controller.storeSearch("2020", "Kolkata",null,"token").getBody())).toArray());

        when(service.searchStoreByZipcodeAndCity(700001,"Kolkata")).thenReturn(expected);
        assertArrayEquals(expected.toArray(),((List<Store>) Objects.requireNonNull(controller.storeSearch(null, "Kolkata", "700001", "token").getBody())).toArray());

        when(service.searchStoreByNumber(2020)).thenReturn(expected);
        assertArrayEquals(expected.toArray(),((List<Store>) Objects.requireNonNull(controller.storeSearch("2020", null, null, "token").getBody())).toArray());

        when(service.searchStoreByZipCode(700001)).thenReturn(expected);
        assertArrayEquals(expected.toArray(),((List<Store>) Objects.requireNonNull(controller.storeSearch(null, null, "700001", "token").getBody())).toArray());

        when(service.searchStoreByCity("Kolkata")).thenReturn(expected);
        assertArrayEquals(expected.toArray(),((List<Store>) Objects.requireNonNull(controller.storeSearch(null, "Kolkata", null, "token").getBody())).toArray());

        assertEquals(200,actual.getStatusCodeValue());
    }

    @Test
    void upgrade() {
        List<String> li= new ArrayList<>();
        li.add("Enable_ZO");
        UpgradeData uD=new UpgradeData(2020,li);
        when(authClient.validate("token")).thenReturn(true);
        when(authClient.isHelpdesk("token")).thenReturn(true);
        //then
        exceptionRule.expect(Exception.class);
        exceptionRule.expectMessage("You need to Enable Delivery through External Vendors");
        //when
        ResponseEntity actual=controller.upgrade(uD,"token");

        assertEquals(200,actual.getStatusCodeValue());
    }

    @Test
    void upgrade2() {
        List<String> li= new ArrayList<>();
        li.add("Enable Delivery through external vendor");
        UpgradeData uD=new UpgradeData(2020,li);
        when(authClient.validate("token")).thenReturn(false);
        when(authClient.isHelpdesk("token")).thenReturn(false);
        //then
        exceptionRule.expect(Exception.class);
        exceptionRule.expectMessage("");
        //when
        ResponseEntity actual=controller.upgrade(uD,"token");

        assertEquals(401,actual.getStatusCodeValue());
    }

    @Test
    void parameterDetails() {
        StoreParameter sp=new StoreParameter(1,"Enable-DLV",2022,1,null,null);
        when(service.parameterDetails(2020,"Enable_DLV")).thenReturn(sp);
        ResponseEntity actual=controller.parameterDetails("2020","Enable_DLV");
        assertEquals(200,actual.getStatusCodeValue());
        assertEquals(sp,actual.getBody());
    }

    @Test
    void getParameter() {
        List<Parameter> li=new ArrayList<>();
        li.add(new Parameter());
        when(authClient.validate("token")).thenReturn(true);
        when(service.getParameter()).thenReturn(li);
        ResponseEntity actual=controller.getParameter("token");
        assertEquals(200,actual.getStatusCodeValue());
        assertEquals(li,actual.getBody());
    }


}
