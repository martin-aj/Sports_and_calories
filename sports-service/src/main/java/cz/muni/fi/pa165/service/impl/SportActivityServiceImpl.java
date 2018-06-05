/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.service.impl;

import cz.muni.fi.pa165.dao.SportActivityDao;
import cz.muni.fi.pa165.entity.SportActivity;
import cz.muni.fi.pa165.sports.api.dto.SportActivityDto;
import cz.muni.fi.pa165.sports.api.service.SportActivityService;
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
public class SportActivityServiceImpl implements SportActivityService {

    private final String ROLE_USER = "ROLE_USER";
    
    @Autowired
    private SportActivityDao sportActivityDao;

    @Autowired
    private Mapper mapper;

    public SportActivityServiceImpl(SportActivityDao sportActivityDao, Mapper mapper) {
        this.sportActivityDao = sportActivityDao; 
        this.mapper = mapper;
    }
    
    public SportActivityServiceImpl(){
    }
 
    @Override
    @Secured(ROLE_USER)
    public SportActivityDto createSportActivity(SportActivityDto sportActivityDto) {
        if (sportActivityDto == null) {
            throw new IllegalArgumentException("Given sport activity cannot be null for its creating.");
        }
        SportActivity sportActivity = mapFromDto(sportActivityDto);
        sportActivityDao.createSportActivity(sportActivity);
        sportActivityDto.setId(sportActivity.getId());
        return sportActivityDto;
    }

    @Override
    @Secured(ROLE_USER)
    public SportActivityDto updateSportActivity(SportActivityDto sportActivityDto) {
        if (sportActivityDto == null) {
            throw new IllegalArgumentException("Given sport activity cannot be null for its updating.");
        }
        SportActivity sportActivity = mapFromDto(sportActivityDto);
        sportActivityDao.updateSportActivity(sportActivity);
        sportActivityDto.setId(sportActivity.getId());
        return sportActivityDto;
    }

    @Override
    @Secured(ROLE_USER)
    public void deleteSportActivity(SportActivityDto sportActivityDto) {
        if (sportActivityDto == null) {
            throw new IllegalArgumentException("Given sport activity cannot be null for its deleting.");
        }
        SportActivity sportActivity = mapFromDto(sportActivityDto);
        sportActivityDao.deleteSportActivity(sportActivity);
    }

    @Override
    @Secured(ROLE_USER)
    public SportActivityDto loadSportActivityById(long id) {
        SportActivity sportActivity = sportActivityDao.findActivityById(id);
        return mapToDto(sportActivity);
    }

    @Override
    @Secured(ROLE_USER)
    public List<SportActivityDto> listAllSportActivities() {
        List<SportActivity> sportActivityList = sportActivityDao.listAllActivities();
        List<SportActivityDto> sportActivityListDto = new ArrayList<>();
        for (SportActivity sportActivity : sportActivityList) {
            sportActivityListDto.add(mapToDto(sportActivity));
        }
        return sportActivityListDto;
    }
    
    private SportActivityDto mapToDto(SportActivity sportActivity) {
        return sportActivity != null ? mapper.map(sportActivity, SportActivityDto.class) : null;
    }
    
    private SportActivity mapFromDto(SportActivityDto sportActivityDto) {
        return sportActivityDto != null ? mapper.map(sportActivityDto, SportActivity.class) : null;
    }
}