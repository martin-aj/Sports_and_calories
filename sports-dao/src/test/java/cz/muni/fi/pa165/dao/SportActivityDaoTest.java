/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.dao;


import cz.muni.fi.pa165.entity.SportActivity;
import java.util.ArrayList;
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
 *
 * @author mato
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext_dao_test.xml")
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@Transactional
public class SportActivityDaoTest {
    
    @Autowired
    SportActivityDao sportActivityDao;
    
    @PersistenceUnit
    public EntityManagerFactory emf;
    
    @Test
    public void testSimplePersistFindById() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

     //   SportActivityDao sportActivityDao = new SportActivityDaoImpl(em);
        SportActivity sportActivity =  sportActivityDao.createSportActivity(newActivity("Crossfit"));
        
        long id = sportActivity.getId();

        SportActivity sportActivityFound = sportActivityDao.findActivityById(id);
        Assert.assertEquals(sportActivity, sportActivityFound);        
        
        em.getTransaction().commit();
        em.close();
    }
    
    @Test
    public void testSimplePersistParameterNull () {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

     //   SportActivityDao sportActivityDao = new SportActivityDaoImpl(em);
        try {
            SportActivity sportActivity =  sportActivityDao.createSportActivity(newActivity(null));
        }
        catch (IllegalArgumentException ex) {
            //success
        }
        finally {
            em.getTransaction().commit();
            em.close();
        }
    }
    
    @Test
    public void testFindByNameOrdinary () {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

    //    SportActivityDao sportActivityDao = new SportActivityDaoImpl(em);
        SportActivity sportActivity =  sportActivityDao.createSportActivity(newActivity("Crossfit"));
    
        SportActivity sportActivityFound = sportActivityDao.findActivityByName(sportActivity.getName());
        Assert.assertEquals(sportActivity, sportActivityFound);
        
        em.getTransaction().commit();
        em.close();
    }
    
    
    @Test
    public void testFindByNameParameterOrdinaryNotFound () {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

     //   SportActivityDao sportActivityDao = new SportActivityDaoImpl(em);
        SportActivity sportActivityFound = sportActivityDao.findActivityByName ("Jogging");
        Assert.assertEquals(null, sportActivityFound);
        
        
        em.getTransaction().commit();
        em.close();
    }
    
    @Test
    public void testFindByNameParameterNull () {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

     //   SportActivityDao sportActivityDao = new SportActivityDaoImpl(em);
        try {
            SportActivity sportActivityFound = sportActivityDao.findActivityByName (null);
        }
        catch (IllegalArgumentException ex) {
            //success
        }
        finally {
            em.getTransaction().commit();
            em.close();
        }
    }
    
    @Test
    public void testFindByIdOrdinaryNotFound () {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

      //  SportActivityDao sportActivityDao = new SportActivityDaoImpl(em);
        SportActivity sportActivity =  sportActivityDao.createSportActivity(newActivity("Crossfit"));
    
        SportActivity sportActivityFound = sportActivityDao.findActivityById(-1);
        Assert.assertEquals(null, sportActivityFound);
        em.getTransaction().commit();
        em.close();
    }
    @Test
    public void testUpdateSportActivityOrdinary () {
        
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

     //   SportActivityDao sportActivityDao = new SportActivityDaoImpl(em);
        SportActivity sportActivity =  sportActivityDao.createSportActivity(newActivity("Crossfit"));
        
        SportActivity sportActivityNew = newActivity("Fitness");
        sportActivityNew.setId(sportActivity.getId());
        
        sportActivityDao.updateSportActivity (sportActivityNew);
        SportActivity sportActivityFound = sportActivityDao.findActivityById(sportActivity.getId());
        
        Assert.assertEquals(sportActivityNew.getName(), sportActivityFound.getName());
        
        em.getTransaction().commit();
        em.close();
        
    }
    
    @Test
    public void testUpdateSportActivityParamterNull () {
        
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
      //      SportActivityDao sportActivityDao = new SportActivityDaoImpl(em);
            SportActivity sportActivity =  sportActivityDao.createSportActivity(newActivity("Crossfit"));

            SportActivity sportActivityNew = newActivity(null);
            sportActivityNew.setId(sportActivity.getId());

            sportActivityDao.updateSportActivity (sportActivityNew);
           
        }
        catch (IllegalArgumentException ex){
            //success
        } 
        finally {
            em.getTransaction().commit();
            em.close();
        }
        
    }
    
    @Test
    public void testListAllSportActivitiesOrdinary() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

      //  SportActivityDao sportActivityDao = new SportActivityDaoImpl(em);
        SportActivity sportActivity1 =  sportActivityDao.createSportActivity(newActivity("Crossfit"));
        SportActivity sportActivity2 =  sportActivityDao.createSportActivity(newActivity("Fitness"));
        

        ArrayList <SportActivity> sportActivitiesFound = (ArrayList <SportActivity>) sportActivityDao.listAllActivities();
        
        //Assert.assertEquals(sportActivity1, sportActivitiesFound.get(0)); 
        //Changed because of other tests also create sport activities and it influences this
        Assert.assertEquals(true,sportActivitiesFound.contains(sportActivity1));        
        
        //Assert.assertEquals(sportActivity2, sportActivitiesFound.get(1)); 
        //Changed because of other tests also create sport activities and it influences this
        Assert.assertEquals(true,sportActivitiesFound.contains(sportActivity2));
        
        em.getTransaction().commit();
        em.close();
    }
    
    // Not gonna work because of other tests which also create sport activities and it influences this
    /*@Test
    public void testListAllSportActivitiesNull() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

      //  SportActivityDao sportActivityDao = new SportActivityDaoImpl(em);

        ArrayList <SportActivity> sportActivitiesFound = (ArrayList <SportActivity>) sportActivityDao.listAllActivities();
        
        Assert.assertEquals(sportActivitiesFound, new ArrayList<>());
        
        em.getTransaction().commit();
        em.close();
    }*/
    
    @Test
    public void testDeleteSportActivityOrdinary () {
        
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
     //   SportActivityDao sportActivityDao = new SportActivityDaoImpl(em);
        SportActivity sportActivity =  sportActivityDao.createSportActivity(newActivity("Crossfit"));

        sportActivityDao.deleteSportActivity (sportActivity);
        SportActivity sportActivityFound = sportActivityDao.findActivityById (sportActivity.getId());

        Assert.assertEquals(sportActivityFound, null);
        
        em.getTransaction().commit();
        em.close();
    }
    
    @Test
    public void testDeleteSportActivityParamterNull () {
        
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
       //     SportActivityDao sportActivityDao = new SportActivityDaoImpl(em);
            SportActivity sportActivity =  sportActivityDao.createSportActivity(newActivity("Crossfit"));

            sportActivityDao.deleteSportActivity (null);           
        }
        catch (IllegalArgumentException ex){
            //success
        } 
        finally {
            em.getTransaction().commit();
            em.close();
        }
    }
    @Test
    public void testDeleteSportActivityNotFound () {
        
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
    //    SportActivityDao sportActivityDao = new SportActivityDaoImpl(em);
        SportActivity sportActivity =  sportActivityDao.createSportActivity(newActivity("Crossfit"));
        SportActivity sportActivityNew = newActivity("Fitness");
        sportActivityNew.setId(123456L);
        try {
            sportActivityDao.deleteSportActivity (sportActivityNew);
        }
        catch (Exception ex){
            //success
        } 
        finally {
            //TO DO - commit som musel zakomentovat lebo to hadzalo chybu 
            //spojenu s rollbackom
            em.getTransaction().commit();
            em.close();
        }
        
    }
    
    
    
    
    private static SportActivity newActivity(String name) {
        SportActivity sportActivity = new SportActivity();
        sportActivity.setName(name);
        return sportActivity;
    } 
}
