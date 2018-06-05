package cz.muni.fi.pa165.dao.impl;

import cz.muni.fi.pa165.dao.CaloricTableEntryDao;
import cz.muni.fi.pa165.entity.CaloricTableEntry;
import cz.muni.fi.pa165.entity.SportActivity;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mato
 */
@Repository
public class CaloricTableEntryDaoImpl implements CaloricTableEntryDao {

    @PersistenceContext
    private EntityManager em;

    @Deprecated
    public CaloricTableEntryDaoImpl(EntityManager em) {
        this.em = em;
    }

    public CaloricTableEntryDaoImpl() {
    }

    @Override
    public CaloricTableEntry createEntryCaloricTable(CaloricTableEntry caloricEntry) {
        if (caloricEntry != null) {
            try {
                em.persist(caloricEntry);
                return caloricEntry;
            } catch (Exception ex) {
                if (ex instanceof DataAccessException) {
                    throw ex;
                } else {
                    throw new DataAccessResourceFailureException("Creation of entity was unsuccessfull.", ex);
                }
            }
        }
        return null;
    }

    @Override
    public CaloricTableEntry updateEntryCaloricTable(CaloricTableEntry caloricEntry) {
        if (caloricEntry != null) {
            try {
                return em.merge(caloricEntry);
            } catch (Exception ex) {
                if (ex instanceof DataAccessException) {
                    throw ex;
                } else {
                    throw new DataAccessResourceFailureException("Update of entity was unsuccessfull.", ex);
                }
            }
        }
        return null;
    }

    @Override
    public List<CaloricTableEntry> loadCaloricTableEntriesBySportActivity(SportActivity sportActivity) {
        if (sportActivity == null) {
            throw new IllegalArgumentException("Sport activity is not set");
        }
        try {
            String sql = "SELECT cal FROM CaloricTableEntry cal WHERE cal.sportActivity.id = :sportActivityId";
            Query query = em.createQuery(sql, CaloricTableEntry.class).setParameter("sportActivityId", sportActivity.getId());

            try {
                List<CaloricTableEntry> tableEntries = query.getResultList();
                return tableEntries;
            } catch (NoResultException ex) {
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
    public CaloricTableEntry loadCaloricTableEntryById(long id) {
        try {
            CaloricTableEntry caloricEntry = em.find(CaloricTableEntry.class, id);
            return caloricEntry;
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
    public void deleteEntryCaloricTable(CaloricTableEntry caloricEntry) {
        try {
//            em.remove(caloricEntry);
            em.remove(em.contains(caloricEntry) ? caloricEntry : em.merge(caloricEntry));
        } catch (Exception ex) {
            if (ex instanceof DataAccessException) {
                throw ex;
            } else {
                throw new DataAccessResourceFailureException("Deletion of entity was unsuccessfull.", ex);
            }
        }
    }

    @Override
    public CaloricTableEntry loadCaloricTableEntryByActivityAndWeight(SportActivity sportActivity, int weight) {
        try {
            String sql = "SELECT cal FROM CaloricTableEntry cal WHERE cal.sportActivity.id = :sportActivityId and cal.weightFrom <= :weight and cal.weightTo > :weight";
            Query query = em.createQuery(sql, CaloricTableEntry.class);
            query.setParameter("sportActivityId", sportActivity.getId());
            query.setParameter("weight", weight);

            try {
                List<CaloricTableEntry> tableEntries = query.getResultList();
                
                CaloricTableEntry tableEntry;
                if (tableEntries.size() > 0) {
                    tableEntry = tableEntries.get(0);
                } else {
                    tableEntry = null;
                }
                return tableEntry;
            } catch (NoResultException ex) {
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
    public List<CaloricTableEntry> listAllCaloricTableEntries() {        
        try {
            String sql = "SELECT cal FROM CaloricTableEntry cal";
            Query query = em.createQuery(sql, CaloricTableEntry.class);

            try {
                List<CaloricTableEntry> tableEntries = (List<CaloricTableEntry>) query.getResultList();
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
}
