package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.CaloricTableEntry;
import cz.muni.fi.pa165.entity.SportActivity;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Test suite for CaloricEntityDao.
 *
 * @author mato
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext_dao_test.xml")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class CaloricTableEntryDaoTest {
    //=============  Attributes  ===============================================
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CaloricTableEntryDao caloricTableEntryDao;
    
    private SportActivity football = null;
    private SportActivity hockey = null;

    CaloricTableEntry caloricTableEntry1 = null;
    CaloricTableEntry caloricTableEntry2 = null;

    //=============  Methods  ==================================================
    @Before
    public void setup() {
        // create some activities
        this.football = new SportActivity();
        this.football.setName("Football");
        entityManager.persist(this.football);

        this.hockey = new SportActivity();
        this.hockey.setName("Hockey");
        entityManager.persist(this.hockey);

        // create some calory categories for football
        this.caloricTableEntry1 = new CaloricTableEntry();
        this.caloricTableEntry1.setCalValue(2000);
        this.caloricTableEntry1.setWeightFrom(100);
        this.caloricTableEntry1.setWeightTo(110);
        this.caloricTableEntry1.setSportActivity(football);
        entityManager.persist(this.caloricTableEntry1);

        this.caloricTableEntry2 = new CaloricTableEntry();
        this.caloricTableEntry2.setCalValue(2500);
        this.caloricTableEntry2.setWeightFrom(110);
        this.caloricTableEntry2.setWeightTo(120);
        this.caloricTableEntry2.setSportActivity(football);
        entityManager.persist(this.caloricTableEntry2);
    }


    @Test
    public void testCreatePerformedActivity() {
        CaloricTableEntry caloricTableEntry = new CaloricTableEntry();
        caloricTableEntry.setCalValue(1250);
        caloricTableEntry.setWeightFrom(80);
        caloricTableEntry.setWeightTo(100);

        SportActivity sportActivity = new SportActivity();
        sportActivity.setName("Football");
        entityManager.persist(sportActivity);
        caloricTableEntry.setSportActivity(sportActivity);

        caloricTableEntryDao.createEntryCaloricTable(caloricTableEntry);

        List<CaloricTableEntry> calories = caloricTableEntryDao.loadCaloricTableEntriesBySportActivity(sportActivity);
        Assert.assertEquals(1, calories.size());
        Assert.assertEquals((Integer) 1250, calories.get(0).getCalValue());
        Assert.assertEquals((Integer) 80, calories.get(0).getWeightFrom());
        Assert.assertEquals((Integer) 100, calories.get(0).getWeightTo());
        Assert.assertEquals("Football", calories.get(0).getSportActivity().getName());
    }

    @Test
    public void testUpdateEntryCaloricTable() {
        caloricTableEntry1.setCalValue(3000);
        caloricTableEntry1.setWeightFrom(200);
        caloricTableEntry1.setWeightTo(250);
        caloricTableEntryDao.updateEntryCaloricTable(caloricTableEntry1);

        caloricTableEntry1 = caloricTableEntryDao.loadCaloricTableEntryById(caloricTableEntry1.getId());
        Assert.assertEquals((Integer) 3000, caloricTableEntry1.getCalValue());
        Assert.assertEquals((Integer) 200, caloricTableEntry1.getWeightFrom());
        Assert.assertEquals((Integer) 250, caloricTableEntry1.getWeightTo());
        Assert.assertEquals("Football", caloricTableEntry1.getSportActivity().getName());
    }

    @Test
    public void testLoadCaloricTableEntriesBySportActivity() {
        List<CaloricTableEntry> footballEntries = caloricTableEntryDao.loadCaloricTableEntriesBySportActivity(football);
        Assert.assertEquals(2, footballEntries.size());
        Assert.assertArrayEquals(new CaloricTableEntry[]{caloricTableEntry1, caloricTableEntry2}, footballEntries.toArray());

    }

    @Test
    public void testLoadCaloricTableEntryById() {
        long id = caloricTableEntry2.getId();
        caloricTableEntry2 = caloricTableEntryDao.loadCaloricTableEntryById(id);
        Assert.assertEquals(id, caloricTableEntry2.getId().longValue());
        Assert.assertEquals((Integer) 2500, caloricTableEntry2.getCalValue());
        Assert.assertEquals((Integer) 110, caloricTableEntry2.getWeightFrom());
        Assert.assertEquals((Integer) 120, caloricTableEntry2.getWeightTo());
        Assert.assertEquals("Football", caloricTableEntry2.getSportActivity().getName());
    }

    @Test
    public void testDeleteEntryCaloricTable() {
        long id = caloricTableEntry1.getId();
        caloricTableEntryDao.deleteEntryCaloricTable(caloricTableEntry1);
        Assert.assertEquals(null, caloricTableEntryDao.loadCaloricTableEntryById(id));
    }

    @Test
    public void testLoadCaloricTableEntryByActivityAndWeight() {
        CaloricTableEntry caloricTableEntry = caloricTableEntryDao.loadCaloricTableEntryByActivityAndWeight(football, 100);
        Assert.assertEquals(caloricTableEntry.getCalValue(), (Integer) 2000);
        Assert.assertEquals(caloricTableEntry.getWeightFrom(), (Integer) 100);
        Assert.assertEquals(caloricTableEntry.getWeightTo(), (Integer) 110);
        Assert.assertEquals(caloricTableEntry.getSportActivity().getName(), "Football");

        caloricTableEntry = caloricTableEntryDao.loadCaloricTableEntryByActivityAndWeight(football, 108);
        Assert.assertEquals(caloricTableEntry.getCalValue(), (Integer) 2000);
        Assert.assertEquals(caloricTableEntry.getWeightFrom(), (Integer) 100);
        Assert.assertEquals(caloricTableEntry.getWeightTo(), (Integer) 110);
        Assert.assertEquals(caloricTableEntry.getSportActivity().getName(), "Football");

        caloricTableEntry = caloricTableEntryDao.loadCaloricTableEntryByActivityAndWeight(football, 110);
        Assert.assertEquals(caloricTableEntry.getCalValue(), (Integer) 2500);
        Assert.assertEquals(caloricTableEntry.getWeightFrom(), (Integer) 110);
        Assert.assertEquals(caloricTableEntry.getWeightTo(), (Integer) 120);
        Assert.assertEquals(caloricTableEntry.getSportActivity().getName(), "Football");

        caloricTableEntry = caloricTableEntryDao.loadCaloricTableEntryByActivityAndWeight(football, 119);
        Assert.assertEquals(caloricTableEntry.getCalValue(), (Integer) 2500);
        Assert.assertEquals(caloricTableEntry.getWeightFrom(), (Integer) 110);
        Assert.assertEquals(caloricTableEntry.getWeightTo(), (Integer) 120);
        Assert.assertEquals(caloricTableEntry.getSportActivity().getName(), "Football");

        caloricTableEntry = caloricTableEntryDao.loadCaloricTableEntryByActivityAndWeight(football, 120);
        Assert.assertEquals(caloricTableEntry, null);

        caloricTableEntry = caloricTableEntryDao.loadCaloricTableEntryByActivityAndWeight(football, 99);
        Assert.assertEquals(caloricTableEntry, null);

        caloricTableEntry = caloricTableEntryDao.loadCaloricTableEntryByActivityAndWeight(hockey, 99);
        Assert.assertEquals(caloricTableEntry, null);

        caloricTableEntry = caloricTableEntryDao.loadCaloricTableEntryByActivityAndWeight(hockey, 105);
        Assert.assertEquals(caloricTableEntry, null);
    }
}
