/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.SportActivity;
import java.util.List;

/**
 * DAO for SportActivity
 * @author mato
 */
public interface SportActivityDao {
     /**
     * Create new sport activity     
     * @param sportActivity
     * @return created sport activity
     */
    public SportActivity createSportActivity(SportActivity sportActivity);
    
    /**
     * Delete sport activity 
     * @param sportActivity
     */
    public void deleteSportActivity(SportActivity sportActivity);
    
    /**
     * Update sport activity 
     * @param sportActivity
     * @return updated sport activity
     */
    public SportActivity updateSportActivity(SportActivity sportActivity);   
    
    
     /**
     * Finds activity by given id 
     * @param id
     * @return
     */
    public SportActivity findActivityById (long id);
    
     /**
     * Finds activity by given name
     * @param name
     * @return 
     */
    public SportActivity findActivityByName (String name);
    
     /**
     * Gives list of all activities  
     * @return 
     */
    public List<SportActivity> listAllActivities ();
}

