/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.service.impl;

import cz.muni.fi.pa165.dao.SportActivityDao;
import cz.muni.fi.pa165.dao.SportsmanDao;
import cz.muni.fi.pa165.entity.Sportsman;
import cz.muni.fi.pa165.sports.api.dto.PerformedActivityDto;
import cz.muni.fi.pa165.sports.api.dto.SportsmanDto;
import cz.muni.fi.pa165.sports.api.service.PerformedActivityService;
import cz.muni.fi.pa165.sports.api.service.SportsmanService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
public class SportsmanServiceImpl implements SportsmanService {

    //=============  Constants  ================================================
    private final String ROLE_USER = "ROLE_USER";
    
    //=============  Attributes  ===============================================
    @Autowired
    private SportsmanDao sportsmanDao;

    @Autowired
    private SportActivityDao sportActivityDao;

    @Autowired
    private Mapper mapper;

    @Autowired
    private PerformedActivityService performedActivityService;

    public SportsmanServiceImpl() {
    }

    public SportsmanServiceImpl(SportsmanDao sportsmanDao, Mapper mapper, PerformedActivityService performedActivityService) {
        this.sportsmanDao = sportsmanDao;
        this.mapper = mapper;
        this.performedActivityService = performedActivityService;
    }

    //=============  Methods  ==================================================
    @Override
//    @Secured(ROLE_USER)
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public SportsmanDto createSportsman(SportsmanDto sportsmanDto) {
        if (sportsmanDto == null) {
            throw new IllegalArgumentException("Sportsman nickname cannot be null");
        }
        Sportsman sportsman1 = mapFromDto(sportsmanDto);
        sportsmanDao.createSportsman(sportsman1);
        sportsmanDto.setId(sportsman1.getId());
        return sportsmanDto;
    }

    @Override
//    @Secured(ROLE_USER)
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public SportsmanDto findSportsmanById(long id) {
        Sportsman sportsman = sportsmanDao.findSportsmanById(id);
        return mapToDto(sportsman);
    }

    @Override
//    @Secured(ROLE_USER)
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public SportsmanDto updateSportsman(SportsmanDto sportsmanDto) {
        if (sportsmanDto == null) {
            throw new IllegalArgumentException("Sportsman nickname cannot be null");
        }
        Sportsman sportsman = mapFromDto(sportsmanDto);
        sportsmanDao.updateSportsman(sportsman);
        sportsmanDto.setId(sportsman.getId());
        return sportsmanDto;
    }

    @Override
//    @Secured(ROLE_USER)
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public void deleteSportsman(SportsmanDto sportsmanDto) {
        if (sportsmanDto == null) {
            throw new IllegalArgumentException("Sportsman nickname cannot be null");
        }
        Sportsman sportsman = mapFromDto(sportsmanDto);
        sportsmanDao.deleteSportsman(sportsman);
    }

    @Override
//    @Secured(ROLE_USER)
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public List<SportsmanDto> listSportsmen() {
        ArrayList<Sportsman> sportsmanList = (ArrayList<Sportsman>) sportsmanDao.listSportsmen();
        List<SportsmanDto> sportsmanDtoList = new ArrayList<>();
        for (Sportsman sportsman : sportsmanList) {
            sportsmanDtoList.add(mapToDto(sportsman));
        }
        return sportsmanDtoList;
    }

    @Override
//    @Secured(ROLE_USER)
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public Long getSumOfCalories(SportsmanDto sportsmanDto) {
        Long sumLong = 0L;
        List<PerformedActivityDto> performedActivitiesDto = sportsmanDto.getPerformedActivities();
        for (PerformedActivityDto performedActivityDto : performedActivitiesDto) {
            sumLong = sumLong + performedActivityService.calculateCalories(performedActivityDto);
        }
        return sumLong;
    }

    @Override
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public List<SportsmanDto> sortSportsmenTopTen(List<SportsmanDto> result) {
        Collections.sort(result, new Comparator<SportsmanDto>() {

            @Override
            public int compare(SportsmanDto o1, SportsmanDto o2) {
                return Long.compare(o2.getCaloriesSum(), o1.getCaloriesSum());
            }
        });
        List<SportsmanDto> topTen = topTenSubroutine(result);
        return topTen;
    }

    public List<SportsmanDto> topTenSubroutine(List<SportsmanDto> result) {
        int size = result.size();
        if (size < 10) {
            return result.subList(0, size);
        } else {
            return result.subList(0, 10);
        }
    }

    private SportsmanDto mapToDto(Sportsman sportsman) {
        return sportsman != null ? mapper.map(sportsman, SportsmanDto.class) : null;
    }

    private Sportsman mapFromDto(SportsmanDto sportsmanDto) {
        return sportsmanDto != null ? mapper.map(sportsmanDto, Sportsman.class) : null;
    }
}
