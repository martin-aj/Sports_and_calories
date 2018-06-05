/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.dao.impl;

import cz.muni.fi.pa165.dao.SportActivityDao;
import cz.muni.fi.pa165.entity.SportActivity;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;

/**
 * DAO implementation for SportActivityDao
 *
 * @author mato
 */
public class SportActivityDaoImpl implements SportActivityDao {

    //=============  Constants  ================================================
    //=============  Attributes  ===============================================
    @PersistenceContext
    private EntityManager em;

    //=============  Constructors  =============================================
    @Deprecated
    public SportActivityDaoImpl(EntityManager em) {
        this.em = em;
    }

    public SportActivityDaoImpl() {
    }

    //=============  Getter/Setters  ===========================================
    //=============  Override, Implements  =====================================
    @Override
    public SportActivity createSportActivity(SportActivity sportActivity) {
        if (sportActivity == null || sportActivity.getName() == null) {
            throw new IllegalArgumentException("Sport activity cannot be created");
        }

        try {
            em.persist(sportActivity);
            return sportActivity;
        } catch (Exception ex) {
            if (ex instanceof DataAccessException) {
                throw ex;
            } else {
                throw new DataAccessResourceFailureException("Creation of entity was unsuccessfull.", ex);
            }
        }
    }

    @Override
    public void deleteSportActivity(SportActivity sportActivity) {
        if (sportActivity == null) {
            throw new IllegalArgumentException("Sport activity cannot be deleted");
        }
        try {
//            em.remove(sportActivity);
            em.remove(em.contains(sportActivity) ? sportActivity : em.merge(sportActivity));
        } catch (Exception ex) {
            if (ex instanceof DataAccessException) {
                throw ex;
            } else {
                throw new DataAccessResourceFailureException("Deletion of entity was unsuccessfull.", ex);
            }
        }
    }

    @Override
    public SportActivity updateSportActivity(SportActivity sportActivity) {
        if (sportActivity == null || sportActivity.getName() == null) {
            throw new IllegalArgumentException("Sport activity cannot be updated");
        }
        try {
            em.merge(sportActivity);
            return sportActivity;
        } catch (Exception ex) {
            if (ex instanceof DataAccessException) {
                throw ex;
            } else {
                throw new DataAccessResourceFailureException("Update of entity was unsuccessfull.", ex);
            }
        }
    }

    @Override
    public SportActivity findActivityById(long id) {
        try {
            SportActivity sportActivity = em.find(SportActivity.class, id);
            return sportActivity;
        } catch (javax.persistence.NoResultException ex) {
            return null;
        } catch (Exception ex) {
            if (ex instanceof DataAccessException) {
                throw ex;
            } else {
                throw new DataAccessResourceFailureException("Load of entity was unsuccessfull.", ex);
            }
        }
    }

    @Override
    public SportActivity findActivityByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Sport activity cannot be found with null paramter");
        }
        try {
            String sql = "SELECT sa FROM SportActivity sa WHERE sa.name = :sportActivityName";
            Query query = em.createQuery(sql, SportActivity.class).setParameter("sportActivityName", name);
            try {
                SportActivity sportActivity = (SportActivity) query.getSingleResult();
                return sportActivity;
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
    public List<SportActivity> listAllActivities() {
        try {
            String sql = "SELECT sa FROM SportActivity sa";
            Query query = em.createQuery(sql, SportActivity.class);
            try {
                List<SportActivity> sportActivities = (List<SportActivity>) query.getResultList();
                return sportActivities;
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
}
