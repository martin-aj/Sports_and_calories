package cz.muni.fi.pa165.sports.api.service;

import cz.muni.fi.pa165.sports.api.dto.SportActivityDto;
import java.util.List;

public interface SportActivityService {
    /**
     * Create new sport activity
     * @param sportActivity
     * @return created sportActivity 
     */
    public SportActivityDto createSportActivity(SportActivityDto sportActivity);

    /**
     * Update existing sport activity
     * @param sportActivity
     * @return updated sport activity
     */
    public SportActivityDto updateSportActivity(SportActivityDto sportActivity);

    /**
     * Delete existing sport activity
     * @param sportActivity
     */
    public void deleteSportActivity(SportActivityDto sportActivity);
    
    /**
     * Find sport activity by given id and returns it.
     * @param id
     * @return sport activity
     */
    public SportActivityDto loadSportActivityById(long id);
    
    /**
     * Find performances, that was sport by given sportsman.     * 
     * @return list of performances
     */
    public List<SportActivityDto> listAllSportActivities(); 

}
