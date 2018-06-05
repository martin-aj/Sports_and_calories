package cz.muni.fi.pa165.dao.impl;

import cz.muni.fi.pa165.dao.PerformedActivityDao;
import cz.muni.fi.pa165.entity.PerformedActivity;
import cz.muni.fi.pa165.entity.SportActivity;
import cz.muni.fi.pa165.entity.Sportsman;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.util.Assert;

/**
 * DAO implementation for PerformedActivity
 *
 * @author mato
 */
public class PerformedActivityDaoImpl implements PerformedActivityDao {

    //=============  Attributes  ===============================================
    @PersistenceContext
    private EntityManager entityManager;

    //=============  Constructors  =============================================
    @Deprecated
    public PerformedActivityDaoImpl(EntityManager em) {
        //this.entityManager = entityManager;
    }

    public PerformedActivityDaoImpl() {
    }

    //=============  Getter/Setters  ===========================================
    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager em) {
        this.entityManager = em;
    }

    //=============  Override, Implements  =====================================
    @Override
    public PerformedActivity createPerformedActivity(PerformedActivity performedActivity) {
        if (performedActivity == null) {
            return null;
        }

        try {
            entityManager.persist(performedActivity);
            return performedActivity;
        } catch (Exception ex) {
            if (ex instanceof DataAccessException) {
                throw ex;
            } else {
                throw new DataAccessResourceFailureException("Creation of entity was unsuccessfull.", ex);
            }
        }
    }

    @Override
    public PerformedActivity loadPerformedActivityById(long id) {
        try {
            PerformedActivity performedActivity = entityManager.find(PerformedActivity.class, id);
            return performedActivity;
        } catch (Exception ex) {
            if (ex instanceof DataAccessException) {
                throw ex;
            } else {
                throw new DataAccessResourceFailureException("Loading of entity was unsuccessfull.", ex);
            }
        }
    }

    @Override
    public List<PerformedActivity> listAllPerformedActivities() {        
        try {
            String sql = "SELECT per FROM PerformedActivity per";
            Query query = entityManager.createQuery(sql, PerformedActivity.class);

            try {
                List<PerformedActivity> tableEntries = (List<PerformedActivity>) query.getResultList();
                return tableEntries;
            } catch (javax.persistence.NoResultException ex) {
                return null;
            }
        } catch (Exception ex) {
            if (ex instanceof DataAccessException) {
                throw ex;
            } else {
                throw new DataAccessResourceFailureException("Load of entity was unsuccessfull.", ex);
            }
        }
    }
    @Override
    public List<PerformedActivity> loadPerformedActivitiesBySportsman(Sportsman sportsman) {
        try {
            Assert.notNull(sportsman, "Sportsman is not set");

            String sql = "SELECT pa FROM PerformedActivity pa WHERE pa.sportsman.id = :sportsmanId";
            Query query = entityManager.createQuery(sql, PerformedActivity.class).setParameter("sportsmanId", sportsman.getId());

            try {
                List<PerformedActivity> performances = query.getResultList();
                return performances;
            } catch (NoResultException ex) {
                return Collections.<PerformedActivity>emptyList();
            }
        } catch (Exception ex) {
            if (ex instanceof DataAccessException) {
                throw ex;
            } else {
                throw new DataAccessResourceFailureException("Loading of entity was unsuccessfull.", ex);
            }
        }
    }

    @Override
    public List<PerformedActivity> loadPerformedActivitiesBySportActivity(SportActivity sportActivity) {
        try {
            Assert.notNull(sportActivity, "SportActivity is not set");

            String sql = "SELECT pa FROM PerformedActivity pa WHERE pa.sportActivity.id = :sportActivityId";
            Query query = entityManager.createQuery(sql, PerformedActivity.class).setParameter("sportActivityId", sportActivity.getId());

            try {
                List<PerformedActivity> performances = query.getResultList();
                return performances;
            } catch (NoResultException ex) {
                return null;
            }
        } catch (Exception ex) {
            if (ex instanceof DataAccessException) {
                throw ex;
            } else {
                throw new DataAccessResourceFailureException("Loading of entity was unsuccessfull.", ex);
            }
        }
    }

    @Override
    public PerformedActivity updatePerformedActivity(PerformedActivity performedActivity) {
        if (performedActivity == null) {
            return null;
        }

        try {
            return entityManager.merge(performedActivity);
        } catch (Exception ex) {
            if (ex instanceof DataAccessException) {
                throw ex;
            } else {
                throw new DataAccessResourceFailureException("Update of entity was unsuccessfull.", ex);
            }
        }
    }

    @Override
    public void deletePerformedActivity(PerformedActivity performedActivity) {
        try {
            Assert.notNull(performedActivity, "PerformedActivity is not set");
            //entityManager.remove(performedActivity);
            entityManager.remove(entityManager.contains(performedActivity) ? performedActivity : entityManager.merge(performedActivity));
        } catch (Exception ex) {
            if (ex instanceof DataAccessException) {
                throw ex;
            } else {
                throw new DataAccessResourceFailureException("Deletion of entity was unsuccessfull.", ex);
            }
        }
    }
}
