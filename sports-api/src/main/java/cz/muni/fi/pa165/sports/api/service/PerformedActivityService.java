package cz.muni.fi.pa165.sports.api.service;

import cz.muni.fi.pa165.sports.api.dto.PerformedActivityDto;
import java.util.List;

public interface PerformedActivityService {

    /**
     * Create new performed activity
     * @param performedActivity
     * @return created performedActivity 
     */
    public PerformedActivityDto createPerformedActivity(PerformedActivityDto performedActivity);

    /**
     * Find performance of activity by given id and returns it.
     * @param id
     * @return performance
     */
    public PerformedActivityDto loadPerformedActivityById(long id);
    
    /**
     * List all performances
     * @return list of performances
     */
    public List<PerformedActivityDto> listAllPerformedActivities();
    
    /**
     * Update existing performed activity
     * @param performedActivity
     * @return 
     */
    public PerformedActivityDto updatePerformedActivity(PerformedActivityDto performedActivity);

    /**
     * Delete existing performed activity
     * @param performedActivity
     */
    public void deletePerformedActivity(PerformedActivityDto performedActivity);
    
    /**
     * Calculate amount of calories burned by performing activity.
     * @param performedActivity
     * @return 
     */
    public Long calculateCalories(PerformedActivityDto performedActivity);
}
