/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.PerformedActivityDao;
import cz.muni.fi.pa165.dao.SportsmanDao;
import cz.muni.fi.pa165.entity.Sportsman;
import cz.muni.fi.pa165.service.impl.SportsmanServiceImpl;
import cz.muni.fi.pa165.sports.api.dto.CaloricTableEntryDto;
import cz.muni.fi.pa165.sports.api.dto.PerformedActivityDto;
import cz.muni.fi.pa165.sports.api.dto.SexDto;
import cz.muni.fi.pa165.sports.api.dto.SportActivityDto;
import cz.muni.fi.pa165.sports.api.dto.SportsmanDto;
import cz.muni.fi.pa165.sports.api.service.PerformedActivityService;
import cz.muni.fi.pa165.sports.api.service.SportsmanService;
import java.util.Date;
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
public class SportsmanServiceTest {

    SportsmanService sportsmanService;
    
    PerformedActivityDao performedActivityDao;
    
    @Mock
    SportsmanDao sportsmanDao;

    @Autowired
    private Mapper mapper;
    @Autowired
    private PerformedActivityService performedActivityService;

    
    
    public SportsmanServiceTest() {
    }

    @Before
    public void setUp() {
        // For using annotations without MockitoRunner
        MockitoAnnotations.initMocks(this);
        sportsmanService = new SportsmanServiceImpl(sportsmanDao, mapper, performedActivityService);
        
    }

    @After
    public void tearDown() {
    }
    
    /**
     * Test of Sportsman.findSportsmanById() method
     */
    @Test
    public void testFindSportsmanById() {
        SportsmanDto result;
        SportsmanDto sportsman = initSportsman();
        sportsmanService.createSportsman(sportsman);
        sportsman.setId(1L);
        
        Mockito.when(sportsmanDao.findSportsmanById(sportsman.getId())).thenReturn(mapper.map(sportsman, Sportsman.class));
        
        result = sportsmanService.findSportsmanById(1L);
        
        Mockito.verify(sportsmanDao).findSportsmanById(result.getId());               
    }

    /**
     * Test of Sportsman.createSportsman() method
     */
    @Test
    public void testCreateSportsman() {
       SportsmanDto sportsman = initSportsman();
       sportsmanService.createSportsman(sportsman);       
       Mockito.verify(sportsmanDao).createSportsman(mapper.map(sportsman, Sportsman.class));
    }

    /**
     * Test of Sportsman.updateSportsman() method
     */
    @Test
    public void testUpdateSportsman() {
        SportsmanDto sportsman = initSportsman();
        sportsmanService.createSportsman(sportsman);             
        
        Mockito.verify(sportsmanDao).createSportsman(mapper.map(sportsman, Sportsman.class));
        
        sportsman.setAge(50);       
        sportsmanService.updateSportsman(sportsman);

        Mockito.verify(sportsmanDao).updateSportsman(mapper.map(sportsman, Sportsman.class));               
    }
    
    /**
     * Test of Sportsman.deleteSportsman() method     
     */
    @Test
    public void testDeleteSportsman() {
        SportsmanDto sportsman = initSportsman();
        sportsmanService.createSportsman(sportsman);
        Mockito.verify(sportsmanDao).createSportsman(mapper.map(sportsman,Sportsman.class));
        
        sportsman.setNickname("Pavel");        
        sportsmanService.deleteSportsman(sportsman);
        Mockito.verify(sportsmanDao).deleteSportsman(mapper.map(sportsman,Sportsman.class));        
    }
    
    @Test
    public void testTotalCaloriesOfSportsman() {
        SportsmanDto sportsman = initSportsman();
        sportsman.setId(1L);
        sportsman.setWeightKg(75);
        PerformedActivityDto performedActivityDto1 = initActivity(800L, 3600L);
        performedActivityDto1.setId(1L);
        performedActivityDto1.setSportsman(sportsman);
        PerformedActivityDto performedActivityDto2 = initActivity(2500L, 4200L);
        performedActivityDto2.setId(2L);
        performedActivityDto2.setSportsman(sportsman);
        sportsman.getPerformedActivities().add(performedActivityDto1);
        sportsman.getPerformedActivities().add(performedActivityDto2);
        SportActivityDto sportActivityDto = new SportActivityDto();
        sportActivityDto.setId(1L);
        sportActivityDto.setName("Behanie");
        performedActivityDto1.setSportActivity(sportActivityDto);
        performedActivityDto2.setSportActivity(sportActivityDto);        
        sportActivityDto.getPerformedActivities().add(performedActivityDto1);
        sportActivityDto.getPerformedActivities().add(performedActivityDto2);
        CaloricTableEntryDto caloricTableEntryDto = new CaloricTableEntryDto();
        caloricTableEntryDto.setId(1L);
        caloricTableEntryDto.setSportActivity(sportActivityDto);
        caloricTableEntryDto.setCalValue(400);
        caloricTableEntryDto.setWeightFrom(70);
        caloricTableEntryDto.setWeightTo(80);
        sportActivityDto.getCaloricTableEntries().add(caloricTableEntryDto);
    }
    
   /*
    * Help method for initialization of performed activity
    */
    private PerformedActivityDto initActivity(long distance, long duration) {
        PerformedActivityDto performedActivity = new PerformedActivityDto();
        performedActivity.setDistanceInMeters(distance);
        performedActivity.setDurationInSeconds(duration);
        performedActivity.setStartOfActivity(new Date());
        return performedActivity;
    }
    
   /*
    * Help method for initialization of sportsman
    */
    private SportsmanDto initSportsman() {
        SportsmanDto sportsman = new SportsmanDto();
        sportsman.setAge(25);
        sportsman.setHeightCm(185);
        sportsman.setNickname("Pepa");
        sportsman.setWeightKg(75);
        sportsman.setSex(SexDto.MALE.MALE);        
        return sportsman;
    }
}
