package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.PerformedActivity;
import cz.muni.fi.pa165.entity.SportActivity;
import cz.muni.fi.pa165.entity.Sportsman;
import java.util.List;

/**
 * DAO for PerformedActivity
 * @author mato
 */
public interface PerformedActivityDao {
    
    /**
     * Create new performed activity
     * @param performedActivity
     * @return created performedActivity 
     */
    public PerformedActivity createPerformedActivity(PerformedActivity performedActivity);

    /**
     * Find performance of activity by given id and returns it.
     * @param id
     * @return performance
     */
    public PerformedActivity loadPerformedActivityById(long id);
    
     /**
     * Lists all caloric tables entries
     * @return list of all caloric table entries
     */    
    public List<PerformedActivity> listAllPerformedActivities();
    /**
     * Find performances, that was performed by given sportsman.
     * @param sportsman
     * @return list of performances
     */
    public List<PerformedActivity> loadPerformedActivitiesBySportsman(Sportsman sportsman);
    
    /**
     * Find performances of given sport activity.
     * @param sportActivity
     * @return list of performances
     */
    public List<PerformedActivity> loadPerformedActivitiesBySportActivity(SportActivity sportActivity);
    
    /**
     * Update existing performed activity
     * @param performedActivity
     * @return 
     */
    public PerformedActivity updatePerformedActivity(PerformedActivity performedActivity);

    /**
     * Delete existing performed activity
     * @param performedActivity
     */
    public void deletePerformedActivity(PerformedActivity performedActivity);
}
