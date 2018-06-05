/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.service.impl;

import cz.muni.fi.pa165.dao.CaloricTableEntryDao;
import cz.muni.fi.pa165.entity.CaloricTableEntry;
import cz.muni.fi.pa165.sports.api.dto.CaloricTableEntryDto;
import cz.muni.fi.pa165.sports.api.service.CaloricTableEntryService;
import java.util.ArrayList;
import java.util.List;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author mato
 */
@Transactional
public class CaloricTableEntryServiceImpl implements CaloricTableEntryService {

    private final String ROLE_USER = "ROLE_USER";
    private final String ROLE_ADMIN = "ROLE_ADMIN";
    
    @Autowired
    private CaloricTableEntryDao caloricTableEntryDao;

    @Autowired
    private Mapper mapper;

    public CaloricTableEntryServiceImpl(CaloricTableEntryDao caloricTableEntryDao, Mapper mapper){
        this.caloricTableEntryDao = caloricTableEntryDao;
        this.mapper = mapper;
    }

    public CaloricTableEntryServiceImpl() {
    }
    
    @Override
    @Secured(ROLE_ADMIN)
    public CaloricTableEntryDto createEntryCaloricTable(CaloricTableEntryDto caloricEntryDto) {
        if (caloricEntryDto == null) {
            throw new IllegalArgumentException("Caloric Entry cannot be null during creating");
        }
        if (!valueChecker(caloricEntryDto)) {
            throw new IllegalArgumentException("Values are not set correct");
        }
        CaloricTableEntry caloricEntry = mapFromDto(caloricEntryDto);
        caloricTableEntryDao.createEntryCaloricTable(caloricEntry);
        caloricEntryDto = mapToDto(caloricEntry);
        return caloricEntryDto;
    }

    @Override
    @Secured(ROLE_ADMIN)
    public CaloricTableEntryDto updateEntryCaloricTable(CaloricTableEntryDto caloricEntryDto) {
        if (caloricEntryDto == null) {
            throw new IllegalArgumentException("Caloric Entry cannot be null during updating");
        }
        if (!valueChecker(caloricEntryDto)) {
            throw new IllegalArgumentException("Values are not set correctly");
        }
        CaloricTableEntry caloricEntry = mapFromDto(caloricEntryDto);
        caloricTableEntryDao.updateEntryCaloricTable(caloricEntry);
        caloricEntryDto = mapToDto(caloricEntry);
        return caloricEntryDto;
    }

    @Override
    @Secured(ROLE_ADMIN)
    public void deleteEntryCaloricTable(CaloricTableEntryDto caloricEntryDto) {
        if (caloricEntryDto == null) {
            throw new IllegalArgumentException("Caloric Entry cannot be null during deleting");
        }
        CaloricTableEntry caloricEntry = mapFromDto(caloricEntryDto);
        caloricTableEntryDao.deleteEntryCaloricTable(caloricEntry);
    }

    @Override
    @Secured({ROLE_USER,ROLE_ADMIN})
    public CaloricTableEntryDto loadCaloricTableEntryById(long id) {        
        CaloricTableEntry calTable = caloricTableEntryDao.loadCaloricTableEntryById(id);
        if (calTable == null) {
            throw new IllegalArgumentException("No entry for given Id was found ");
        }
        return mapToDto(calTable);
    }

    private boolean valueChecker(CaloricTableEntryDto caloricEntryDto) {
        if ((caloricEntryDto.getWeightFrom() == null) || (caloricEntryDto.getWeightTo() == null)) {
            return false;
        }
        return caloricEntryDto.getWeightFrom() < caloricEntryDto.getWeightTo();
    }

    @Override
    @Secured({ROLE_USER,ROLE_ADMIN})
    public List<CaloricTableEntryDto> listAllCaloricTableEntries() {      
      
        List<CaloricTableEntry> listCalTables = caloricTableEntryDao.listAllCaloricTableEntries();
        List<CaloricTableEntryDto> listCalTablesDto = new ArrayList<>();
        for (CaloricTableEntry calEntry : listCalTables) {
            listCalTablesDto.add(mapToDto(calEntry));
        }
        return listCalTablesDto;              
    }
    
    private CaloricTableEntryDto mapToDto(CaloricTableEntry caloricTableEntry) {
        return caloricTableEntry != null ? mapper.map(caloricTableEntry, CaloricTableEntryDto.class) : null;
    }
    
    private CaloricTableEntry mapFromDto(CaloricTableEntryDto caloricTableEntryDto) {
        return caloricTableEntryDto != null ? mapper.map(caloricTableEntryDto, CaloricTableEntry.class) : null;
    }
}
