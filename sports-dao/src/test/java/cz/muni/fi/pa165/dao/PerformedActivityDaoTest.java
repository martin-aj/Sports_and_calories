package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.PerformedActivity;
import cz.muni.fi.pa165.entity.SportActivity;
import cz.muni.fi.pa165.entity.Sportsman;
import java.util.Date;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Test class for DAO of PerformedActivity.
 *
 * @author mato
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext_dao_test.xml")
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)
@Transactional
public class PerformedActivityDaoTest {

    //=============  Attributes  ===============================================
    @Autowired
    private PerformedActivityDao performedActivityDao = null;

    @Autowired
    private SportActivityDao sportActivityDao = null;

    @Autowired
    private SportsmanDao sportsmanDao = null;

    //=============  Methods  ==================================================
    @Test
    public void testSimplePersist() {
        PerformedActivity performedActivity = initActivity(performedActivityDao, 10L, 120L);

        performedActivityDao.updatePerformedActivity(performedActivity);

        long id = performedActivity.getId();

        PerformedActivity performedActivity1 = performedActivityDao.loadPerformedActivityById(id);
        Assert.assertEquals(performedActivity1.getDistanceInMeters(), (Long) 10L);
        Assert.assertEquals(performedActivity1.getDurationInSeconds(), (Long) 120L);
    }

    @Test
    public void loadPerformedActivityById() {
        PerformedActivity performedActivity = initActivity(performedActivityDao, 10L, 120L);

        PerformedActivity performedActivity2 = initActivity(performedActivityDao, 30L, 150L);

        performedActivityDao.updatePerformedActivity(performedActivity);
        performedActivityDao.updatePerformedActivity(performedActivity2);

        long id = performedActivity.getId();
        long id2 = performedActivity2.getId();

        PerformedActivity performedActivity3 = performedActivityDao.loadPerformedActivityById(id);
        PerformedActivity performedActivity4 = performedActivityDao.loadPerformedActivityById(id2);

        Assert.assertEquals(performedActivity3.getDistanceInMeters(), (Long) 10L);
        Assert.assertEquals(performedActivity3.getDurationInSeconds(), (Long) 120L);
        Assert.assertEquals(performedActivity4.getDistanceInMeters(), (Long) 30L);
        Assert.assertEquals(performedActivity4.getDurationInSeconds(), (Long) 150L);
    }

    @Test
    public void testUpdatePerformedActivity() {
        PerformedActivity performedActivity = initActivity(performedActivityDao, 10L, 30L);
        performedActivityDao.updatePerformedActivity(performedActivity);
        long id = performedActivity.getId();
        PerformedActivity performedActivity2 = performedActivityDao.loadPerformedActivityById(id);
        performedActivity2.setDistanceInMeters(60L);
        performedActivity2.setDurationInSeconds(250L);
        performedActivityDao.updatePerformedActivity(performedActivity2);
        PerformedActivity performedActivity3 = performedActivityDao.loadPerformedActivityById(id);
        Assert.assertEquals(performedActivity3.getDistanceInMeters(), (Long) 60L);
        Assert.assertEquals(performedActivity3.getDurationInSeconds(), (Long) 250L);
    }

    @Test
    public void testUpdatePerformedActivityNull() {
        PerformedActivity performedActivity = performedActivityDao.updatePerformedActivity(null);
        Assert.assertEquals(performedActivity, null);
    }

    @Test
    public void testDeletePerformedActivity() {
        PerformedActivity performedActivity = initActivity(performedActivityDao, 10L, 30L);
        long id = performedActivity.getId();
        performedActivityDao.deletePerformedActivity(performedActivity);
        PerformedActivity performedActivity2 = performedActivityDao.loadPerformedActivityById(id);

        Assert.assertEquals(performedActivity2, null);
    }

    @Test
    @Rollback
    public void testDeletePerformedActivityNotInDB() {
        PerformedActivity performedActivity = new PerformedActivity();
        performedActivity.setId(357L);
        try {
            performedActivityDao.deletePerformedActivity(performedActivity);
        } catch (Exception ex) {
            //OK
        }
    }

    @Test
    public void loadPerformedActivitiesBySportActivity() {
        SportActivity activity1 = new SportActivity();
        activity1.setName("Running");
        SportActivity sportActivity = sportActivityDao.createSportActivity(activity1);

        SportActivity activity2 = new SportActivity();
        activity2.setName("Swimming");
        SportActivity sportActivity2 = sportActivityDao.createSportActivity(activity2);

        PerformedActivity performedActivity = initActivity(performedActivityDao, 10L, 40L);
        PerformedActivity performedActivity2 = initActivity(performedActivityDao, 20L, 50L);
        PerformedActivity performedActivity3 = initActivity(performedActivityDao, 30L, 60L);

        performedActivity.setSportActivity(sportActivity);
        performedActivity2.setSportActivity(sportActivity);
        performedActivity3.setSportActivity(sportActivity2);

        performedActivityDao.updatePerformedActivity(performedActivity);
        performedActivityDao.updatePerformedActivity(performedActivity2);
        performedActivityDao.updatePerformedActivity(performedActivity3);

        List<PerformedActivity> activities1 = performedActivityDao.loadPerformedActivitiesBySportActivity(sportActivity);
        List<PerformedActivity> activities2 = performedActivityDao.loadPerformedActivitiesBySportActivity(sportActivity2);

        Assert.assertEquals(activities1.size(), 2);
        Assert.assertEquals(activities2.size(), 1);
    }

    @Test
    public void loadPerformedActivitiesBySportsman() {
        Sportsman person1 = new Sportsman();
        person1.setNickname("Alex");
        Sportsman sportsman = sportsmanDao.createSportsman(person1);

        Sportsman person2 = new Sportsman();
        person2.setNickname("John");
        Sportsman sportsman2 = sportsmanDao.createSportsman(person2);

        PerformedActivity performedActivity = initActivity(performedActivityDao, 10L, 40L);
        PerformedActivity performedActivity2 = initActivity(performedActivityDao, 20L, 50L);
        PerformedActivity performedActivity3 = initActivity(performedActivityDao, 30L, 60L);

        performedActivity.setSportsman(sportsman);
        performedActivity2.setSportsman(sportsman);
        performedActivity3.setSportsman(sportsman2);

        performedActivityDao.updatePerformedActivity(performedActivity);
        performedActivityDao.updatePerformedActivity(performedActivity2);
        performedActivityDao.updatePerformedActivity(performedActivity3);

        List<PerformedActivity> activities1 = performedActivityDao.loadPerformedActivitiesBySportsman(sportsman);
        List<PerformedActivity> activities2 = performedActivityDao.loadPerformedActivitiesBySportsman(sportsman2);

        Assert.assertEquals(activities1.size(), 2);
        Assert.assertEquals(activities2.size(), 1);
    }

    private PerformedActivity initActivity(PerformedActivityDao performedActivityDao, long distance, long duration) {
        PerformedActivity performedActivity = new PerformedActivity();
        performedActivity.setDistanceInMeters(distance);
        performedActivity.setDurationInSeconds(duration);
        performedActivity.setStartOfActivity(new Date());
        return performedActivityDao.createPerformedActivity(performedActivity);
    }
}
