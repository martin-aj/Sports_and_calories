/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.PerformedActivity;
import cz.muni.fi.pa165.entity.SportActivity;
import cz.muni.fi.pa165.entity.Sportsman;
import java.util.List;

/**
 *
 * @author mato
 */
public interface SportsmanDao {
    /**
     * Stores new sportsman into database. Id for the new sportsman is automatically
     * generated and stored into id attribute.
     * Returns the added entry.
     * 
     * @param sportsman sportsman to be created.
     * @return 
     * @throws IllegalArgumentException when sportsman is null, or sportsman has already 
     * assigned id.
     */
    Sportsman createSportsman(Sportsman sportsman);
    /**
     * Deletes sportsman from database. 
     * 
     * @param sportsman sportsman to be deleted from db.
     * @throws IllegalArgumentException when sportsman is null, or sportsman has null id.
     */
    void deleteSportsman(Sportsman sportsman);
    /**
     * Returns list of all sportsmen in the database.
     * 
     * @return list of all sportsmen in database.
     */
    List<Sportsman> listSportsmen ();
    /**
     * Returns sportsman with given id.
     * 
     * @param id primary key of requested sportsman.
     * @return sportsman with given id or null if such sportsman does not exist.
     * @throws IllegalArgumentException when given id is null.
     */
    Sportsman findSportsmanById (Long id);
    /**
     * Returns sportsman with given Nickname.
     * 
     * @param name nickname of requested sportsman.
     * @return sportsman with given nickname or null if such sportsman does not exist.
     * @throws IllegalArgumentException when given nickname is null.
     */
    Sportsman findSportsmanByNickname (String name);
    /**
     * Updates sportsman in database. Returns the updated entry.
     * 
     * @param sportsman updated sportsman to be stored into database.
     * @return 
     * @throws IllegalArgumentException when sportsman is null, or sportsman has null id.
     */
    Sportsman updateSportsman (Sportsman sportsman);
    /**
     * Returns list of performed activities of one selected kind of a selected sportsmen.
     * 
     * @param sportsman the sportsman who's activities we want to list
     * @param activity the activity selected to be listed
     * @return list of all activities of selected kind of a sportsman, null if there are none.
     */
    List<PerformedActivity> listPerformedActivitiesOfSelectedSportsmanAndActivity (Sportsman sportsman, SportActivity activity);
}
