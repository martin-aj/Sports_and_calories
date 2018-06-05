/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.sports.api.service.SportsmanService;
import cz.muni.fi.pa165.sports.api.service.CaloricTableEntryService;
import cz.muni.fi.pa165.dao.CaloricTableEntryDao;
import cz.muni.fi.pa165.sports.api.dto.CaloricTableEntryDto;
import cz.muni.fi.pa165.sports.api.dto.SportActivityDto;
import cz.muni.fi.pa165.entity.CaloricTableEntry;
import cz.muni.fi.pa165.entity.SportActivity;
import cz.muni.fi.pa165.service.impl.CaloricTableEntryServiceImpl;
import java.util.List;
import org.dozer.Mapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author mato
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext_service_test.xml"})
public class CaloricTableEntryServiceTest {

    CaloricTableEntryService caloricTableEntryService;

    @Mock
    CaloricTableEntryDao caloricTableEntryDao;

    @Mock
    SportsmanService sportsMan;

    @Autowired
    private Mapper mapper;
    
    SportActivityDto sportActivity;

    public CaloricTableEntryServiceTest() {
    }

    @Before
    public void setUp() {
        // For using annotations without MockitoRunner
        MockitoAnnotations.initMocks(this);
        caloricTableEntryService = new CaloricTableEntryServiceImpl(caloricTableEntryDao, mapper);
        sportActivity = initSportActivityDto();
    }

    @After
    public void tearDown() {
    }
    
    
    @Test
    public void loadCaloricTableEntryById() {
        CaloricTableEntryDto calResult;
        CaloricTableEntryDto calTableEntry = initCalTableEnt();
        caloricTableEntryService.createEntryCaloricTable(calTableEntry);
        calTableEntry.setId(1L);

        Mockito.when(caloricTableEntryDao.loadCaloricTableEntryById(calTableEntry.getId())).thenReturn(mapper.map(calTableEntry, CaloricTableEntry.class));

        calResult = caloricTableEntryService.loadCaloricTableEntryById(1L);
        Mockito.verify(caloricTableEntryDao).loadCaloricTableEntryById(calResult.getId());
    }

    /**
     * Test of createEntryCaloricTable method, of class
     * CaloricTableEntryService.
     */
    @Test
    public void testCreateEntryCaloricTable() {
        CaloricTableEntryDto calTableEntry = initCalTableEnt();
        caloricTableEntryService.createEntryCaloricTable(calTableEntry);
        Mockito.verify(caloricTableEntryDao).createEntryCaloricTable(mapper.map(calTableEntry, CaloricTableEntry.class));

    }

    @Test
    public void testUpdateEntryCaloricTable() {
        CaloricTableEntryDto calTableEntry = initCalTableEnt();
        caloricTableEntryService.createEntryCaloricTable(calTableEntry);
        Mockito.verify(caloricTableEntryDao).createEntryCaloricTable(mapper.map(calTableEntry, CaloricTableEntry.class));

        calTableEntry.setCalValue(804);
        caloricTableEntryService.updateEntryCaloricTable(calTableEntry);
        Mockito.verify(caloricTableEntryDao).updateEntryCaloricTable(mapper.map(calTableEntry, CaloricTableEntry.class));
    }

    /**
     * Test of deleteEntryCaloricTable method, of class
     * CaloricTableEntryService.
     */
    @Test
    public void testDeleteEntryCaloricTable() {
        CaloricTableEntryDto calTableEntry = initCalTableEnt();
        caloricTableEntryService.createEntryCaloricTable(calTableEntry);
        Mockito.verify(caloricTableEntryDao).createEntryCaloricTable(mapper.map(calTableEntry, CaloricTableEntry.class));

        calTableEntry.setCalValue(804);
        caloricTableEntryService.deleteEntryCaloricTable(calTableEntry);
        Mockito.verify(caloricTableEntryDao).deleteEntryCaloricTable(mapper.map(calTableEntry, CaloricTableEntry.class));
    }
    
     @Test
    public void testListAllCaloricTableEntries() {
        CaloricTableEntryDto calTableEntry = initCalTableEnt();
        CaloricTableEntryDto calTableEntry2 = initCalTableEnt();
        CaloricTableEntryDto calTableEntry3 = initCalTableEnt();
 
        caloricTableEntryService.createEntryCaloricTable(calTableEntry);
        caloricTableEntryService.createEntryCaloricTable(calTableEntry2);
        caloricTableEntryService.createEntryCaloricTable(calTableEntry3);        
       
        List <CaloricTableEntryDto> caloricTableEntries = caloricTableEntryService.listAllCaloricTableEntries();
        
        Mockito.verify(caloricTableEntryDao).listAllCaloricTableEntries();
        
    }
    //////////////////////
    /* Non-test methods */
    //////////////////////
    private CaloricTableEntryDto initCalTableEnt() {
        CaloricTableEntryDto calTableEntry = new CaloricTableEntryDto();
        calTableEntry.setCalValue(25);
        calTableEntry.setWeightFrom(65);
        calTableEntry.setWeightTo(75);
        calTableEntry.setSportActivity(sportActivity);
        return calTableEntry;
    }
    
    private SportActivityDto initSportActivityDto() {
        SportActivityDto sportActivity = new SportActivityDto();
        sportActivity.setName("NON-NULLING-DTO");
        return sportActivity;
    }
    
    private SportActivity initSportActivity() {
        SportActivity sportActivity = new SportActivity();
        sportActivity.setName("NON-NULLING");
        return sportActivity;
    }
}
