package cz.muni.fi.pa165.dao.impl;

import cz.muni.fi.pa165.dao.SportsmanDao;
import cz.muni.fi.pa165.entity.PerformedActivity;
import cz.muni.fi.pa165.entity.SportActivity;
import cz.muni.fi.pa165.entity.Sportsman;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;

/**
 *
 * @author mato
 */
public class SportsmanDaoImpl implements SportsmanDao {

    @PersistenceContext
    private EntityManager em;

    @Deprecated
    public SportsmanDaoImpl(EntityManager em) {
        this.em = em;
    }

    public SportsmanDaoImpl() {
    }


    /*
     * Add new sportsman into the system
     */
    @Override
    public Sportsman createSportsman(Sportsman sportsman) {
        if (sportsman == null || sportsman.getNickname() == null) {
            throw new IllegalArgumentException("Create: sportsman cannot be created, wrong parameter");
        }
        try {
            em.persist(sportsman);
            return sportsman;
        } catch (Exception ex) {
            if (ex instanceof DataAccessException) {
                throw ex;
            } else {
                throw new DataAccessResourceFailureException("Creation of entity was unsuccessfull.", ex);
            }
        }
    }

    /*
     * Delete sportsman if sportsman is not null and if found
     */
    @Override
    public void deleteSportsman(Sportsman sportsman) {
        if (sportsman == null) {
            throw new IllegalArgumentException("Delete: sportsman cannot be deleted, wrong parameter");
        }

        try {
            //em.remove(sportsman);
            em.remove(em.contains(sportsman) ? sportsman : em.merge(sportsman));
        } catch (Exception ex) {
            Logger.getLogger(SportsmanDaoImpl.class.getName()).log(Level.WARNING, "Object not found in DB", ex);
            if (ex instanceof DataAccessException) {
                throw ex;
            } else {
                throw new DataAccessResourceFailureException("Deletion of entity was unsuccessfull.", ex);
            }
        }

    }

    /*
     * List all sportsmen registered in the system
     */
    @Override
    public List<Sportsman> listSportsmen() {
        String sql = "SELECT s FROM Sportsman s";
        Query query = em.createQuery(sql, Sportsman.class);

        try {
            List<Sportsman> sportsmen = (List<Sportsman>) query.getResultList();
            return sportsmen;
        } catch (NoResultException ex) {
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
    public Sportsman findSportsmanById(Long id) {
        try {
            Sportsman sportsman = em.find(Sportsman.class, id);
            return sportsman;
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
    public Sportsman findSportsmanByNickname(String nickname) {
        if (nickname == null) {
            throw new IllegalArgumentException("Find: sportsman cannot be found, wrong parameter");
        }
        String sql = "SELECT s FROM Sportsman s WHERE s.nickname = :sportsmanNickname";
        Query query = em.createQuery(sql, Sportsman.class).setParameter("sportsmanNickname", nickname);
        try {
            Sportsman sportsman = (Sportsman) query.getSingleResult();
            return sportsman;
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

    /*
     * Update existing sportsman
     */
    @Override
    public Sportsman updateSportsman(Sportsman sportsman) {
        if (sportsman == null || sportsman.getNickname() == null) {
            throw new IllegalArgumentException("Update: sportsman cannot be updated, wrong parameter");
        }

        try {
            em.merge(sportsman);
            Sportsman sportsman_upd = em.find(Sportsman.class, sportsman.getId());
            return sportsman_upd;
        } catch (Exception ex) {
            if (ex instanceof DataAccessException) {
                throw ex;
            } else {
                throw new DataAccessResourceFailureException("Update of entity was unsuccessfull.", ex);
            }
        }
    }

    /*
     * List all occasions(sport activity entries) in which a selecetd sports activity (@param sportActivity) was  performed
     * by a selected sportsman (@param sportsman)
     */
    @Override
    public List<PerformedActivity> listPerformedActivitiesOfSelectedSportsmanAndActivity(Sportsman sportsman, SportActivity activity) {
        if (sportsman == null || activity == null || sportsman.getNickname() == null || activity.getName() == null) {
            throw new IllegalArgumentException("List performances (by sportsman of a sertain sports activity): activities cannot be listed; wrong parameter");
        }
        try {
            // String sql = "SELECT perf FROM PerformedActivity perf WHERE perf.sportsman.id = :sportsman_id AND perf.sportActivity.id = :activity_id";
            //  String sql = "SELECT * FROM PerformedActivity";
            //System.out.println("sportsman id " + sportsman.getId() + " activity id " + activity.getId());
            String sql = "SELECT pa FROM PerformedActivity pa WHERE pa.sportsman.id = :sportsman_id AND pa.sportActivity.id = :activity_id";
            Query query = em.createQuery(sql);
            query.setParameter("sportsman_id", sportsman.getId());
            query.setParameter("activity_id", activity.getId());
            List<PerformedActivity> sportActivityEntries = query.getResultList();
            /*List <PerformedActivity> actualQueryResults = new ArrayList<>();
             for (PerformedActivity performedActivity : sportActivityEntries) {
             if (performedActivity.getSportsman().equals(sportsman) && performedActivity.getSportActivity().equals(activity)) {
             actualQueryResults.add(performedActivity);
             }
             }
             return actualQueryResults; */

            return sportActivityEntries;
        } catch (NoResultException ex) {
            return null;
        } catch (Exception ex) {
            if (ex instanceof DataAccessException) {
                throw ex;
            } else {
                throw new DataAccessResourceFailureException("Load of entity was unsuccessfull.", ex);
            }
        }
    }

}
