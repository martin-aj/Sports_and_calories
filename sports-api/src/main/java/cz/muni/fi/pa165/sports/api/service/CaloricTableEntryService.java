/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.sports.api.service;

import cz.muni.fi.pa165.sports.api.dto.CaloricTableEntryDto;
import java.util.List;

public interface CaloricTableEntryService {
    /**
     * Create new entry for caloric tables
     * @param caloricEntryDto - persistent object
     * @return created CaloricTableEntry
     */
    public CaloricTableEntryDto createEntryCaloricTable(CaloricTableEntryDto caloricEntryDto);

    /**
     * Update existing entry for caloric tables
     * @param caloricEntryDto - entry which will be updated
     * @return updated CaloricTableEntry
     */
    public CaloricTableEntryDto updateEntryCaloricTable(CaloricTableEntryDto caloricEntryDto);

    /**
     * Delete entry for caloric tables
     * @param caloricEntryDto - entry which will be deleted
     */
    public void deleteEntryCaloricTable(CaloricTableEntryDto caloricEntryDto);
    
    /**
     * Lists all caloric tables entries
     * @return list of all caloric table entries
     */
    public List<CaloricTableEntryDto> listAllCaloricTableEntries();
    
    /**
     * Load caloric table entry by id
     * @param id - identifier of caloric table entry
     * @return caloric table entry by id
     */
    public CaloricTableEntryDto loadCaloricTableEntryById(long id);
}
