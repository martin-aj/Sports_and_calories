/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.sports.api.service.SportActivityService;
import cz.muni.fi.pa165.dao.SportActivityDao;
import cz.muni.fi.pa165.sports.api.dto.SportActivityDto;
import cz.muni.fi.pa165.entity.SportActivity;
import cz.muni.fi.pa165.service.impl.SportActivityServiceImpl;
import java.util.List;
import org.dozer.Mapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
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
public class SportActivityServiceTest {

    SportActivityService sportActivityService;

    @Mock
    SportActivityDao sportActivityDao;

    @Autowired
    private Mapper mapper;

    public SportActivityServiceTest() {
    }

    @Before
    public void setUp() {
        // For using annotations without MockitoRunner
        MockitoAnnotations.initMocks(this);
        sportActivityService = new SportActivityServiceImpl(sportActivityDao, mapper);
    }

    @After
    public void tearDown() {
    }
    
    /**
     * Test of SportActivity.findActivityById() method
     */
    @Test
    public void testFindActivityById() {
        SportActivityDto result;
        SportActivityDto sportActivity = initSportActivity();
        sportActivityService.createSportActivity(sportActivity);
        sportActivity.setId(1L);
        
        Mockito.when(sportActivityDao.findActivityById(sportActivity.getId())).thenReturn(mapper.map(sportActivity, SportActivity.class));
        
        result = sportActivityService.loadSportActivityById(1L);
        
        Mockito.verify(sportActivityDao).findActivityById(result.getId());               
    }

    /**
     * Test of SportActivity.createSportActivity() method
     */
    @Test
    public void testCreateSportActivity() {
       SportActivityDto sportActivity = initSportActivity();
       sportActivityService.createSportActivity(sportActivity);       
       Mockito.verify(sportActivityDao).createSportActivity(mapper.map(sportActivity, SportActivity.class));
    }

    /**
     * Test of SportActivity.updateSportActivity() method
     */
    
    @Test
    public void testUpdateSportActivity() {
        SportActivityDto sportActivity = initSportActivity();
        sportActivityService.createSportActivity(sportActivity);             
        
        Mockito.verify(sportActivityDao).createSportActivity(mapper.map(sportActivity, SportActivity.class));
        
        sportActivity.setName("Skating");
        sportActivityService.updateSportActivity(sportActivity);

        Mockito.verify(sportActivityDao).updateSportActivity(mapper.map(sportActivity, SportActivity.class));               
    }
    
    /**
     * Test of SportActivity.deleteSportActivity() method     
     */
    @Test
    public void testDeleteSportActivity() {
        SportActivityDto sportActivity = initSportActivity();
        sportActivityService.createSportActivity(sportActivity);
        Mockito.verify(sportActivityDao).createSportActivity(mapper.map(sportActivity,SportActivity.class));
        
        sportActivity.setName("Basketball");        
        sportActivityService.deleteSportActivity(sportActivity);
        Mockito.verify(sportActivityDao).deleteSportActivity(mapper.map(sportActivity,SportActivity.class));        
    }
    
    /**
     * Test of SportActivity.listAllSportActivities() method     
     */
    @Test
    public void testListAllSportActivities() {
        SportActivityDto sportActivity1 = initSportActivity("Volleyball");
        SportActivityDto newActivity1 = sportActivityService.createSportActivity(sportActivity1);
       newActivity1.setName("Footvolley");
        //Mockito.verify(sportActivityDao).createSportActivity(mapper.map(sportActivity1,SportActivity.class));

        SportActivityDto sportActivity2 = initSportActivity("Petanque");        
        sportActivityService.createSportActivity(sportActivity2);
        Mockito.verify(sportActivityDao,times(2)).createSportActivity(mapper.map(sportActivity2,SportActivity.class));

        List <SportActivityDto> sportActivities = sportActivityService.listAllSportActivities();
        
        
        //System.err.print("\nSportActivity2="+sportActivities.get(1).getName());
        //Assert.assertEquals("Volleyball", sportActivities.get(0).getName());        
        
        Mockito.verify(sportActivityDao).listAllActivities();
    }
    
    /*
    * Help method for initialization of sportActivity
    */
    private SportActivityDto initSportActivity() {
        SportActivityDto sportActivity = new SportActivityDto();
        sportActivity.setName("Dodgeball");
        return sportActivity;
    }

    private SportActivityDto initSportActivity(String name) {
        SportActivityDto sportActivity = new SportActivityDto();
        sportActivity.setName(name);
        return sportActivity;
    }
}
