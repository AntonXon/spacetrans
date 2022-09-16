package com.company.spacetrans;

import com.company.spacetrans.entity.*;
import com.company.spacetrans.security.DatabaseUserRepository;
import io.jmix.core.DataManager;
import io.jmix.core.Id;
import io.jmix.core.UnconstrainedDataManager;
import io.jmix.core.security.SystemAuthenticator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SpacetransApplicationTests {
    @Autowired
    DataManager dataManager;
    @Autowired
    DatabaseUserRepository databaseUserRepository;
    @Autowired
    SystemAuthenticator systemAuthenticator;

    @BeforeEach
    void setUp() {
        systemAuthenticator.begin();
    }

    @AfterEach
    void tearDown() {
        systemAuthenticator.end();
    }

    @Test
    void contextLoads() {
        assertThat(dataManager).isNotNull();
    }

    @Test
    void testCalculations() {
        //create entities
        Moon moon = dataManager.create(Moon.class);
        moon.setName("MoonTest");
        Spaceport moonSpaceport = dataManager.create(Spaceport.class);
        moonSpaceport.setName("SpaceportMoonTest");
        moonSpaceport.setMoon(moon);
        Coordinates c1 = dataManager.create(Coordinates.class);
        c1.setLatitude(120.12345);
        c1.setLongitude(32.56345);
        moonSpaceport.setCoordinates(c1);
        Planet planet = dataManager.create(Planet.class);
        planet.setName("PlanetTest");
        Spaceport planetSpaceport = dataManager.create(Spaceport.class);
        planetSpaceport.setName("SpaceportPlanetTest");
        planetSpaceport.setPlanet(planet);
        Coordinates c2 = dataManager.create(Coordinates.class);
        c2.setLatitude(101.42345);
        c2.setLongitude(13.9878);
        planetSpaceport.setCoordinates(c2);
        Carrier carrier = dataManager.create(Carrier.class);
        carrier.setName("TestCarrier");
        carrier.setPorts(List.of(planetSpaceport, moonSpaceport));
        Individual moonCustomer = dataManager.create(Individual.class);
        moonCustomer.setName("MoonCust");
        moonCustomer.setFirstName("MoonCust");
        Company planetCustomer = dataManager.create(Company.class);
        planetCustomer.setName("PlanetComp");
        planetCustomer.setRegistrationId("123456");
        Waybill waybill = dataManager.create(Waybill.class);
        waybill.setReference("#TEST");
        waybill.setCreator(databaseUserRepository.getByUsernameLike("admin").get(0));
        waybill.setShipper(moonCustomer);
        waybill.setConsignee(planetCustomer);
        waybill.setDeparturePort(moonSpaceport);
        waybill.setDestinationPort(planetSpaceport);
        waybill.setCarrier(carrier);
        WaybillItem waybillItem1 = dataManager.create(WaybillItem.class);
        waybillItem1.setWaybill(waybill);
        waybillItem1.setNumber(1);
        waybillItem1.setName("pr1");
        waybillItem1.setWeight(12.2);
        Dimensions dim1 = dataManager.create(Dimensions.class);
        dim1.setHeight(1.56);
        dim1.setLength(3.12);
        dim1.setWidth(1.67);
        waybillItem1.setDim(dim1);
        WaybillItem waybillItem2 = dataManager.create(WaybillItem.class);
        waybillItem2.setWaybill(waybill);
        waybillItem2.setNumber(2);
        waybillItem2.setName("pr2");
        waybillItem2.setWeight(4.5);
        Dimensions dim2 = dataManager.create(Dimensions.class);
        dim2.setHeight(1.98);
        dim2.setLength(2.12);
        dim2.setWidth(12.78);
        waybillItem2.setDim(dim2);
        waybill.setItems(List.of(waybillItem1, waybillItem2));
        dataManager.save(moon, planet, moonSpaceport, planetSpaceport, carrier, moonCustomer, planetCustomer,
                waybillItem1, waybillItem2, waybill);
        //test1
        BigDecimal itemCharge1 = dataManager.load(WaybillItem.class).id(waybillItem1.getId()).one().getCharge();
        BigDecimal itemCharge2 = dataManager.load(WaybillItem.class).id(waybillItem2.getId()).one().getCharge();
        BigDecimal totalCharge = dataManager.load(Waybill.class).id(waybill.getId()).one().getTotalCharge();
        Double totalWeight = dataManager.load(Waybill.class).id(waybill.getId()).one().getTotalWeight();
        //test2
        WaybillItem waybillItem3 = dataManager.create(WaybillItem.class);
        waybillItem3.setWaybill(waybill);
        waybillItem3.setNumber(3);
        waybillItem3.setName("pr3");
        waybillItem3.setWeight(234.0);
        Dimensions dim3 = dataManager.create(Dimensions.class);
        dim3.setHeight(2.7);
        dim3.setLength(3.88);
        dim3.setWidth(4.58);
        waybillItem3.setDim(dim3);
        dataManager.save(waybillItem3);
        BigDecimal totalCharge2 = dataManager.load(Waybill.class).id(waybill.getId()).one().getTotalCharge();
        Double totalWeight2 = dataManager.load(Waybill.class).id(waybill.getId()).one().getTotalWeight();
        //test3
        waybillItem1 = dataManager.load(WaybillItem.class).id(waybillItem1.getId()).one();
        waybillItem1.setWeight(14.8);
        dataManager.save(waybillItem1);
        BigDecimal totalCharge3 = dataManager.load(Waybill.class).id(waybill.getId()).one().getTotalCharge();
        Double totalWeight3 = dataManager.load(Waybill.class).id(waybill.getId()).one().getTotalWeight();
        //remove all created entities
        dataManager.remove(Id.of(waybill.getId(), Waybill.class));
        dataManager.remove(carrier, planetSpaceport, moonSpaceport, moon, planet, moonCustomer, planetCustomer);
        // 12.2*1.5 + (1.56+3.12+1.67)*2 = 31
        assertEquals(BigDecimal.valueOf(31.00).setScale(2), itemCharge1);
        // 4.5*1.5 + (1.98+2.12+12.78)*2 = 40.51
        assertEquals(BigDecimal.valueOf(40.51), itemCharge2);
        // 31 + 40.51 = 71.51
        assertEquals(BigDecimal.valueOf(71.51), totalCharge);
        // 12.2 + 4.5 = 16.7
        assertEquals(16.7, totalWeight);
        // 31 + 40.51 + 373.32 = 444.83
        assertEquals(BigDecimal.valueOf(444.83), totalCharge2);
        // 12.2 + 4.5 + 234 = 250.7
        assertEquals(250.7, totalWeight2);
        // 34.9 + 40.51 + 373.32 = 448.73
        assertEquals(BigDecimal.valueOf(448.73), totalCharge3);
        // 14.8 + 4.5 + 234 = 253.3
        assertEquals(253.3, totalWeight3);
    }

}
