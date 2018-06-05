/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.CaloricTableEntry;
import cz.muni.fi.pa165.entity.SportActivity;
import java.util.List;


/**
 *
 * @author mato
 */
public interface CaloricTableEntryDao {
    
    /**
     * Create new entry for caloric tables
     * @param caloricEntry - persistent object
     * @return created CaloricTableEntry
     */
    public CaloricTableEntry createEntryCaloricTable(CaloricTableEntry caloricEntry);

    /**
     * Update existing entry for caloric tables
     * @param caloricEntry - entry which will be updated
     * @return updated CaloricTableEntry
     */
    public CaloricTableEntry updateEntryCaloricTable(CaloricTableEntry caloricEntry);

    /**
     * Delete entry for caloric tables
     * @param caloricEntry - entry which will be deleted
     */
    public void deleteEntryCaloricTable(CaloricTableEntry caloricEntry);
    
    /**
     * Load caloric tables by sport activity
     * @param sportActivity - includes 0..n caloric table entries
     * @return list of caloric table entries by sport activity
     */
    public List<CaloricTableEntry> loadCaloricTableEntriesBySportActivity(SportActivity sportActivity);
    
     /**
     * Lists all caloric tables entries
     * @return list of all caloric table entries
     */    
    public List<CaloricTableEntry> listAllCaloricTableEntries();
    
    /**
     * Load caloric table entry by id
     * @param id - identifier of caloric table entry
     * @return caloric table entry by id
     */
    public CaloricTableEntry loadCaloricTableEntryById(long id);
    
    /**
     * Load caloric table entry by activity and weight
     * @param weight - weight of sportsman
     * @return caloric table entry by activity and weight
     */
    public CaloricTableEntry loadCaloricTableEntryByActivityAndWeight(SportActivity sportActivity, int weight);
}
