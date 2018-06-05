/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.sports.api.service;


import cz.muni.fi.pa165.sports.api.dto.SportsmanDto;
import java.util.List;
import java.util.TreeMap;

public interface SportsmanService {

    /**
     * Create new sportsman
     * @param sportsman - data transfer object 
     * @return created sportsman 
     */
    public SportsmanDto createSportsman(SportsmanDto sportsman);


    /**
     * Find sportsman by given id and returns the sportsman.
     * @param id
     * @return sportsman
     */
    public SportsmanDto findSportsmanById(long id);
    
    /**
     * Update existing sportsman
     * @param sportsmanDto
     * @return updated sportsman 
     */
    public SportsmanDto updateSportsman(SportsmanDto sportsmanDto);

    /**
     * Delete existing sportsman
     * @param sportsmanDto
     */
    public void deleteSportsman(SportsmanDto sportsmanDto);
    
    /**
     * List all registered sportsmen
     * @return list of all registered sportsmen
     */
    public List <SportsmanDto> listSportsmen();
    
    /**
     * Compute calories of sportsman
     * @param sportsmanDto - the sportsman who's total of burnt calories from all activities we want to compute
     * @return 
     */
    public Long getSumOfCalories (SportsmanDto sportsmanDto);
    
    /**
     * Compute calories of all sportsmen and returns sorted list
     * @param sportsmanDto
     * @return list of sportsmen sorted by calories
     */
    public List<SportsmanDto> sortSportsmenTopTen(List <SportsmanDto> sportsmanDto);
    
    
}
