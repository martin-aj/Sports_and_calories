/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.dao;

//import cz.muni.fi.pa165.dao.impl.PerformedActivityDaoImpl;
import cz.muni.fi.pa165.entity.PerformedActivity;
import cz.muni.fi.pa165.entity.Sex;
import cz.muni.fi.pa165.entity.SportActivity;
import cz.muni.fi.pa165.entity.Sportsman;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Test class for DAO of Sportsman
 *
 * @author mato
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext_dao_test.xml")
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@Transactional
public class SportsmanDaoTest {
    //=============  Constants  ================================================

    //=============  Attributes  ===============================================
    @PersistenceUnit
    public EntityManagerFactory emf;
    
    @Autowired
    SportsmanDao sportsmanDao;
    
    @Autowired
    SportActivityDao sportActivityDao;

    //=============  Constructors  =============================================
    //=============  Getter/Setters  ===========================================
    //=============  Override, Implements  =====================================
    //=============  Methods  ==================================================

    @Test
    public void testFindById() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

    //    SportsmanDao sportsmanDao = new SportsmanDaoImpl(em);
        Sportsman sportsman =  sportsmanDao.createSportsman(newSportsman("Michal"));
        
        long id = sportsman.getId();

        Sportsman sportsmanFound = sportsmanDao.findSportsmanById(id);
        Assert.assertEquals(sportsman, sportsmanFound);        
        
        /*try{
            Sportsman sportsmanFound1 = sportsmanDao.findSportsmanById(null);
        }
        catch(Exception ex){
            System.//success
        }        
        
        Sportsman sportsmanFound2 = sportsmanDao.findSportsmanById(54789L);
        Assert.assertEquals(null, sportsmanFound);               
        */
        em.getTransaction().commit();
        em.close();
    }
    
    @Test
    public void testFindByNickname() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();        
        
    //    SportsmanDao sportsmanDao = new SportsmanDaoImpl(em);
        Sportsman sportsman =  sportsmanDao.createSportsman(newSportsman("Pavel"));
        
        String nickname = sportsman.getNickname();

        Sportsman sportsmanFound = sportsmanDao.findSportsmanByNickname(nickname);
        Assert.assertEquals(sportsman, sportsmanFound);        
        
        sportsmanFound = sportsmanDao.findSportsmanByNickname("Pavel");
        Assert.assertEquals(sportsman, sportsmanFound);                                   
        
        em.getTransaction().commit();
        em.close();
    }
    
    @Test
    public void testCreateSportsman(){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();        
        
      //  SportsmanDao sportsmanDao = new SportsmanDaoImpl(em);
        Sportsman sportsman =  sportsmanDao.createSportsman(newSportsman("Pavel"));
        Sportsman sportsman2 =  sportsmanDao.createSportsman(newSportsman("Petr"));

        Assert.assertEquals(sportsman.getNickname(),"Pavel");
        Assert.assertEquals(sportsman2.getNickname(),"Petr");
        try{
            Sportsman sportsman3 =  sportsmanDao.createSportsman(newSportsman("Pavel"));
        }
        catch (Exception ex){
            //System.out.println("exception caught");            
            //not unique nickname
        }                
    }
    
    @Test
    public void testUpdateSportsman() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();        
        
     //   SportsmanDao sportsmanDao = new SportsmanDaoImpl(em);
        Sportsman sportsman =  sportsmanDao.createSportsman(newSportsman("Pavel"));
        Sportsman sportsman2 = sportsmanDao.createSportsman(newSportsman("Petr"));
        
        Assert.assertEquals(sportsman.getNickname(),"Pavel");        
        long old_id = sportsman.getId();        
        
        sportsman = sportsmanDao.updateSportsman(sportsman2);
        long new_id = sportsman.getId();        
        long old_id2 = sportsman.getId();        
        Assert.assertEquals(sportsman.getNickname(),"Petr");
        Assert.assertEquals(old_id2, new_id);                          
        
        em.getTransaction().commit();
        em.close();
    }
    @Test
    public void testListSportsmenAndDeleteSportsman() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();        
        
  //      SportsmanDao sportsmanDao = new SportsmanDaoImpl(em);
        Sportsman sportsman1 = sportsmanDao.createSportsman(newSportsman("Josef"));
        sportsman1.setAge(5);
        sportsman1.setHeightCm(150);
        sportsman1.setSex(Sex.MALE);
        sportsman1.setWeightKg(90);
        
        Sportsman sportsman2 = sportsmanDao.createSportsman(newSportsman("Pepa"));
        sportsman2.setAge(6);
        sportsman2.setHeightCm(160);
        sportsman2.setSex(Sex.FEMALE);
        sportsman2.setWeightKg(80);
        
        Sportsman sportsman3 = sportsmanDao.createSportsman(newSportsman("Jozka"));       
        sportsman3.setAge(15);
        sportsman3.setHeightCm(152);
        sportsman3.setSex(Sex.MALE);
        sportsman3.setWeightKg(95);
        
       
        List <Sportsman> sportsmenList = sportsmanDao.listSportsmen();
        
        Assert.assertEquals(true,sportsmenList.contains(sportsman1));
        Assert.assertEquals(true,sportsmenList.contains(sportsman2));
        Assert.assertEquals(true,sportsmenList.contains(sportsman3));
        
        System.out.println(sportsmenList.toString());
        
        sportsmanDao.deleteSportsman(sportsman3);
        
        sportsmenList = sportsmanDao.listSportsmen();
        
        Assert.assertEquals(false,sportsmenList.contains(sportsman3));
        
        System.out.println(sportsmenList.toString());
        
        em.getTransaction().commit();
        em.close();
    }
    
    //pridal karlos - skusal som vazby medzi entitami ci funguju    
  /*  @Test
    public void testListPerformedActivitiesOfSelectedSportsmanAndActivity() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

   //     SportActivityDao sportActivityDao = new SportActivityDaoImpl(em);
        SportActivity sportActivity1 = sportActivityDao.createSportActivity(newActivity("Crossfit"));
        SportActivity sportActivity2 = sportActivityDao.createSportActivity(newActivity("Fitness"));

     //   SportsmanDao sportsmanDao = new SportsmanDaoImpl(em);
        Sportsman sportsman = sportsmanDao.createSportsman(newSportsman("KutacKladivar"));
        Sportsman sportsman2 = sportsmanDao.createSportsman(newSportsman("Cistic"));

        PerformedActivityDao performedActivityDao = new PerformedActivityDaoImpl();

        PerformedActivity performedActivity1 = new PerformedActivity();
        performedActivity1.setDurationInSeconds(10L);
        performedActivityDao.createPerformedActivity(performedActivity1);                   
        
        PerformedActivity performedActivity1new = newPerformedActivityEntry(9000l, 0l, sportActivity1, sportsman);
        performedActivity1new.setId(performedActivity1.getId());
        performedActivityDao.updatePerformedActivity(performedActivity1new);
        
        sportsman.getPerformedActivities().add(performedActivity1new);
        sportsmanDao.updateSportsman(sportsman);
        
        sportActivity1.getPerformedActivities().add(performedActivity1new);
        sportActivityDao.updateSportActivity(sportActivity1);

        PerformedActivity performedActivity2 = new PerformedActivity();
        performedActivityDao.createPerformedActivity(performedActivity2);
        
        PerformedActivity performedActivity2new = newPerformedActivityEntry(7000l, 0l, sportActivity1, sportsman);
        performedActivity2new.setId(performedActivity2.getId());
        performedActivityDao.updatePerformedActivity(performedActivity2new);
        
        sportsman.getPerformedActivities().add(performedActivity2new);
        sportsmanDao.updateSportsman(sportsman);
        
        sportActivity1.getPerformedActivities().add(performedActivity2new);
        sportActivityDao.updateSportActivity(sportActivity1);

        PerformedActivity performedActivity3 = new PerformedActivity();
        performedActivityDao.createPerformedActivity(performedActivity3);
        
        PerformedActivity performedActivity3new = newPerformedActivityEntry(9500l, 0l, sportActivity2, sportsman);
        performedActivity3new.setId(performedActivity3.getId());        
        performedActivityDao.updatePerformedActivity(performedActivity3new);
        
        sportsman.getPerformedActivities().add(performedActivity3);
        sportsmanDao.updateSportsman(sportsman);
        
        sportActivity2.getPerformedActivities().add(performedActivity3);
        sportActivityDao.updateSportActivity(sportActivity2);

        PerformedActivity performedActivity4 = new PerformedActivity();
        performedActivityDao.createPerformedActivity(performedActivity4);
        
        PerformedActivity performedActivity4new = newPerformedActivityEntry(9500l, 0l, sportActivity1, sportsman2);
        performedActivity4new.setId(performedActivity4.getId());

        performedActivity4new.setSportsman(sportsman2);

        performedActivity4new.setSportActivity(sportActivity1);
        performedActivityDao.updatePerformedActivity(performedActivity4new);

        sportsman2.getPerformedActivities().add(performedActivity4);
        sportsmanDao.updateSportsman(sportsman2);
        sportActivity1.getPerformedActivities().add(performedActivity1);
        sportActivityDao.updateSportActivity(sportActivity1);

         
          
         // nasledujuci blok kodu dokazuje ze zaznamy o aktivite, sportovcovi aj
        // samotna sportova aktivita su realne pridane v systeme vratane odkazov 
         
         
        //update aktivit prebehol uspesne
        PerformedActivity perfAct = performedActivityDao.loadPerformedActivityById(performedActivity4.getId());
        PerformedActivity perfAct2 = performedActivityDao.loadPerformedActivityById(performedActivity4new.getId());
        Assert.assertEquals(perfAct, performedActivity4);
        Assert.assertEquals(perfAct2, performedActivity4new);

        //sportovec sa pridal uspesne
        Sportsman sportsmanFound = sportsmanDao.findSportsmanById(sportsman.getId());
        Assert.assertEquals(sportsmanFound, sportsman);
        //sportova aktivita sa pridala uspesne
        SportActivity sportActFound = sportActivityDao.findActivityById(sportActivity1.getId());
        Assert.assertEquals(sportActFound, sportActivity1);
        //sportova aktivita 1 obsahuje zoznam troch zaznamov o vykonani tejto aktivity
        Assert.assertEquals(sportActivity1.getPerformedActivities().size(), 3);
        //sportovec 1 obsahuje zoznam troch zaznamov o aktivite
        Assert.assertEquals(sportsman.getPerformedActivities().size(), 3);
        //zaznam o aktivite 4 obsahuje odkaz na sportovca a odkaz na sportovu aktivitu 1
        Assert.assertEquals(performedActivity4new.getSportsman().getId(), sportsman2.getId());
        Assert.assertEquals(performedActivity4new.getSportActivity().getId(), sportActivity1.getId());

        ArrayList<PerformedActivity> performedActivitiesOfSportsman = (ArrayList<PerformedActivity>) sportsmanDao.listPerformedActivitiesOfSelectedSportsmanAndActivity(sportsman, sportActivity1);
        Assert.assertEquals(performedActivitiesOfSportsman.size(), 2);

        List<PerformedActivity> performedActivities = performedActivityDao.loadPerformedActivitiesBySportActivity(sportActivity1);
        Assert.assertEquals(performedActivities.size(), 3);
        em.getTransaction().commit();
        em.close();
    }*/

    private static SportActivity newActivity(String name) {
        SportActivity sportActivity = new SportActivity();
        sportActivity.setName(name);
        return sportActivity;
    }

    private static Sportsman newSportsman(String name) {
        Sportsman sportsman = new Sportsman();
        sportsman.setNickname(name);
        return sportsman;
    }

    private static PerformedActivity newPerformedActivityEntry(Long duration, Long distance, SportActivity sportActivity, Sportsman sportsman) {
        PerformedActivity performedActivity = new PerformedActivity();
        performedActivity.setDurationInSeconds(duration);
        performedActivity.setSportsman(sportsman);
        performedActivity.setSportActivity(sportActivity);
        performedActivity.setDistanceInMeters(distance);
        return performedActivity;
    }
}
