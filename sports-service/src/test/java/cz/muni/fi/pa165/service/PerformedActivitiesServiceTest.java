package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.sports.api.service.PerformedActivityService;
import cz.muni.fi.pa165.dao.CaloricTableEntryDao;
import cz.muni.fi.pa165.dao.PerformedActivityDao;
import cz.muni.fi.pa165.sports.api.dto.CaloricTableEntryDto;
import cz.muni.fi.pa165.sports.api.dto.PerformedActivityDto;
import cz.muni.fi.pa165.sports.api.dto.SportActivityDto;
import cz.muni.fi.pa165.sports.api.dto.SportsmanDto;
import cz.muni.fi.pa165.entity.CaloricTableEntry;
import cz.muni.fi.pa165.entity.PerformedActivity;
import cz.muni.fi.pa165.entity.SportActivity;
import cz.muni.fi.pa165.entity.Sportsman;
import cz.muni.fi.pa165.service.impl.PerformedActivityServiceImpl;
import cz.muni.fi.pa165.sports.api.dto.SexDto;
import java.util.ArrayList;
import java.util.List;
import org.dozer.Mapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Proposition of test for service layer
 *
 * @author mato
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext_service_test.xml")
public class PerformedActivitiesServiceTest {

    //=============  Attributes  ===============================================
    @Autowired
    private PerformedActivityService performedActivityService;

    @Mock
    private PerformedActivityDao performedActivityDao;

    @Mock
    private CaloricTableEntryDao caloricTableEntryDao;

    @Autowired
    private Mapper mapper;

    //=============  Methods  ==================================================
    @Before
    public void setUp() {
        // For using annotations without MockitoRunner
        MockitoAnnotations.initMocks(this);
        performedActivityService = new PerformedActivityServiceImpl(performedActivityDao, caloricTableEntryDao, mapper);
    }

    @Test
    public void testLoadPerformedActivityById() {
        PerformedActivityDto storedActivity = initPerformedActivity(11L, 21L, 31L);
        PerformedActivityDto loadedActivity;

        Mockito.when(performedActivityDao.loadPerformedActivityById(Matchers.anyLong()))
                .thenReturn(mapper.map(storedActivity, PerformedActivity.class));

        loadedActivity = performedActivityService.loadPerformedActivityById(storedActivity.getId());
        Mockito.verify(performedActivityDao).loadPerformedActivityById(storedActivity.getId());
        assertPerformedActivity(loadedActivity, 11L, 21L, 31L);
    }

    @Test
    public void testListAllCaloricTableEntries() {
        PerformedActivityDto storedActivity1 = initPerformedActivity(11L, 11L, 11L);
        PerformedActivityDto storedActivity2 = initPerformedActivity(21L, 21L, 21L);
        PerformedActivityDto storedActivity3 = initPerformedActivity(31L, 31L, 31L);

        performedActivityService.createPerformedActivity(storedActivity1);
        performedActivityService.createPerformedActivity(storedActivity2);
        performedActivityService.createPerformedActivity(storedActivity3);

        List<PerformedActivityDto> performedActivities = performedActivityService.listAllPerformedActivities();   
        Mockito.verify(performedActivityDao).listAllPerformedActivities();

    }

    @Test
    public void testCreatePerformedActivity() {
        PerformedActivityDto storedActivity = initPerformedActivity(11L, 21L, 31L);

        Mockito.when(performedActivityDao.createPerformedActivity(Matchers.any(PerformedActivity.class)))
                .thenReturn(mapper.map(storedActivity, PerformedActivity.class));

        PerformedActivityDto loadedActivityDto = performedActivityService.createPerformedActivity(storedActivity);
        Mockito.verify(performedActivityDao).createPerformedActivity(mapper.map(storedActivity, PerformedActivity.class));
    }

    @Test
    public void testUpdatePerformedActivity() {
        PerformedActivityDto storedActivity = initPerformedActivity(12L, 22L, 32L);
        PerformedActivityDto loadedActivity;

        Mockito.when(performedActivityDao.updatePerformedActivity(Matchers.any(PerformedActivity.class)))
                .thenReturn(mapper.map(storedActivity, PerformedActivity.class));

        loadedActivity = performedActivityService.updatePerformedActivity(storedActivity);
        Mockito.verify(performedActivityDao).updatePerformedActivity(mapper.map(storedActivity, PerformedActivity.class));
        assertPerformedActivity(loadedActivity, 12L, 22L, 32L);
    }

    @Test
    public void testDeletePerformedActivity() {
        PerformedActivityDto storedActivity = initPerformedActivity(13L, 23L, 33L);

        performedActivityService.deletePerformedActivity(storedActivity);
        Mockito.verify(performedActivityDao).deletePerformedActivity(mapper.map(storedActivity, PerformedActivity.class));
    }

    @Test
    public void testCalculateCalories() {
        PerformedActivityDto storedActivity = initPerformedActivity(13L, 23L, 3600L);

        Mockito.when(caloricTableEntryDao.loadCaloricTableEntryByActivityAndWeight(Matchers.any(SportActivity.class),
                Matchers.anyInt())).thenReturn(mapper.map(initCalTableEnt(), CaloricTableEntry.class));

        Long calculateCalories1 = performedActivityService.calculateCalories(storedActivity);
        Mockito.verify(caloricTableEntryDao).loadCaloricTableEntryByActivityAndWeight(mapper.map(storedActivity.getSportActivity(),
                SportActivity.class), storedActivity.getSportsman().getWeightKg());
        Assert.assertEquals(750, (long) calculateCalories1);
    }

    private PerformedActivityDto initPerformedActivity(long id, long distance, long duration) {
        PerformedActivityDto performedActivityDto = new PerformedActivityDto();
        performedActivityDto.setId(id);
        performedActivityDto.setDistanceInMeters(distance);
        performedActivityDto.setDurationInSeconds(duration);
        performedActivityDto.setSportsman(initSportsman(null));
        performedActivityDto.setSportActivity(initSportActivity(null));
        return performedActivityDto;
    }

    private CaloricTableEntryDto initCalTableEnt() {
        CaloricTableEntryDto calTableEntry = new CaloricTableEntryDto();
        calTableEntry.setId(1L);
        calTableEntry.setCalValue(750);
        calTableEntry.setWeightFrom(65);
        calTableEntry.setWeightTo(99);
        List<CaloricTableEntryDto> listCal = new ArrayList<>();
        listCal.add(calTableEntry);

        SportActivityDto sportActivity1 = initSportActivity(listCal);
        calTableEntry.setSportActivity(sportActivity1);
        return calTableEntry;
    }

    private SportActivityDto initSportActivity(List<CaloricTableEntryDto> listCal) {
        SportActivityDto sportActivity = new SportActivityDto();
        sportActivity.setId(1L);
        sportActivity.setName("Football");
        sportActivity.setCaloricTableEntries(listCal);
        return sportActivity;
    }

    private SportsmanDto initSportsman(List<PerformedActivityDto> activities) {
        SportsmanDto sportsman = new SportsmanDto();
        sportsman.setId(1L);
        sportsman.setAge(21);
        sportsman.setHeightCm(180);
        sportsman.setNickname("Hellboy");
        sportsman.setSex(SexDto.UNSPECIFIED);
        sportsman.setWeightKg(90);
        sportsman.setPerformedActivities(activities);
        return sportsman;
    }

    private void assertPerformedActivity(PerformedActivityDto performedActivityDto, long id, Long distance, Long duration) {
        Assert.assertNotNull(performedActivityDto);
        Assert.assertEquals(id, performedActivityDto.getId().longValue());
        Assert.assertEquals(distance, performedActivityDto.getDistanceInMeters());
        Assert.assertEquals(duration, performedActivityDto.getDurationInSeconds());
    }
}
