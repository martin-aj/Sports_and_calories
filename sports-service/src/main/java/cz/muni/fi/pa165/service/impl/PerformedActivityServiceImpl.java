package cz.muni.fi.pa165.service.impl;

import cz.muni.fi.pa165.dao.CaloricTableEntryDao;
import cz.muni.fi.pa165.dao.PerformedActivityDao;
import cz.muni.fi.pa165.entity.CaloricTableEntry;
import cz.muni.fi.pa165.entity.PerformedActivity;
import cz.muni.fi.pa165.entity.Sex;
import cz.muni.fi.pa165.entity.SportActivity;
import cz.muni.fi.pa165.entity.Sportsman;
import cz.muni.fi.pa165.sports.api.dto.PerformedActivityDto;
import cz.muni.fi.pa165.sports.api.service.PerformedActivityService;
import java.util.ArrayList;
import java.util.List;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for entity PerformedActivity
 *
 * @author mato
 */
@Transactional
public class PerformedActivityServiceImpl implements PerformedActivityService {

    //=============  Constants  ================================================
    private final String ROLE_USER = "ROLE_USER";
    
    //=============  Attributes  ===============================================
    @Autowired
    private PerformedActivityDao performedActivityDao;

    @Autowired
    private CaloricTableEntryDao caloricTableEntryDao;

    @Autowired
    private Mapper mapper;

    //=============  Constructors  =============================================
    public PerformedActivityServiceImpl() {
    }
    
    public PerformedActivityServiceImpl(PerformedActivityDao performedActivityDao, CaloricTableEntryDao caloricTableEntryDao, Mapper mapper){
        this.performedActivityDao = performedActivityDao;
        this.caloricTableEntryDao = caloricTableEntryDao;
        this.mapper = mapper;
    }

    //=============  Getter/Setters  ===========================================

    //=============  Override, Implements  =====================================
    @Override
    @Secured(ROLE_USER)
    public PerformedActivityDto createPerformedActivity(PerformedActivityDto performedActivityDto) {
        if (performedActivityDto == null) {
            throw new IllegalArgumentException("Performed activity cannot be null during creating");
        }
        PerformedActivity performedActivity = mapFromDto(performedActivityDto);
        performedActivityDao.createPerformedActivity(performedActivity);
        performedActivityDto = mapToDto(performedActivity);
        return performedActivityDto;
    }

    @Override
    @Secured(ROLE_USER)
    public PerformedActivityDto loadPerformedActivityById(long id) {
        PerformedActivity performedActivity = performedActivityDao.loadPerformedActivityById(id);
        PerformedActivityDto performedActivityDto = mapToDto(performedActivity);
        return performedActivityDto;
    }

    @Override
    @Secured(ROLE_USER)
    public PerformedActivityDto updatePerformedActivity(PerformedActivityDto performedActivityDto) {
        PerformedActivity performedActivity = mapFromDto(performedActivityDto);
        performedActivity = performedActivityDao.updatePerformedActivity(performedActivity);
        performedActivityDto = mapToDto(performedActivity);
        return performedActivityDto;
    }

    @Override
    @Secured(ROLE_USER)
    public void deletePerformedActivity(PerformedActivityDto performedActivityDto) {
        PerformedActivity performedActivity = mapFromDto(performedActivityDto);
        performedActivityDao.deletePerformedActivity(performedActivity);
    }

    @Override
    @Secured(ROLE_USER)
    public List<PerformedActivityDto> listAllPerformedActivities() {
        List<PerformedActivity> listPerActs = performedActivityDao.listAllPerformedActivities();
        List<PerformedActivityDto> listPerActDto = new ArrayList<>();
        for (PerformedActivity perAct : listPerActs) {
            listPerActDto.add(mapToDto(perAct));
        }
        return listPerActDto;
    }

    @Override
    @Secured(ROLE_USER)
    public Long calculateCalories(PerformedActivityDto performedActivityDto) {
        if (performedActivityDto == null) {
            throw new IllegalArgumentException("Performed activity is null");
        }
        if (performedActivityDto.getDurationInSeconds() == null) {
            throw new IllegalArgumentException("Duration is null");
        }
        if (performedActivityDto.getSportsman() == null) {
            throw new IllegalArgumentException("Sportsmen is null");
        }
        if (performedActivityDto.getSportActivity() == null) {
            throw new IllegalArgumentException("Sport activity is null");
        }

        PerformedActivity performedActivity = mapFromDto(performedActivityDto);

        Sportsman sportsman = performedActivity.getSportsman();
        SportActivity sportActivity = performedActivity.getSportActivity();
        CaloricTableEntry caloricTableEntry = caloricTableEntryDao.loadCaloricTableEntryByActivityAndWeight(sportActivity,
                sportsman.getWeightKg());

        Double calories;
        if (caloricTableEntry == null) {
            calories = 0.0;
        } else if (sportsman.getSex() == Sex.MALE) {
            calories = computingCaloriesMan(caloricTableEntry, sportsman, performedActivity);
        } else if (sportsman.getSex() == Sex.FEMALE) {
            calories = computingCaloriesWoman(caloricTableEntry, sportsman, performedActivity);
        } else {
            calories = caloricTableEntry.getCalValue() * (performedActivity.getDurationInSeconds().doubleValue() / 3600);
        }

        return calories.longValue();

    }

    //=============  Methods  ==================================================
    
    private Double computingCaloriesMan(CaloricTableEntry caloricTableEntry, Sportsman sportsman, PerformedActivity perfActivity) {
        double BMR = (13.75 * sportsman.getWeightKg()) + (5 * sportsman.getHeightCm()) - (6.76 * sportsman.getAge()) + 66;
        double MET = caloricTableEntry.getCalValue() / ((caloricTableEntry.getWeightFrom() + caloricTableEntry.getWeightTo()) / 2);
        double result = BMR / 24 * MET * perfActivity.getDurationInSeconds() / 3600;
        return result;
    }

    private Double computingCaloriesWoman(CaloricTableEntry caloricTableEntry, Sportsman sportsman, PerformedActivity perfActivity) {
        double BMR = (9.56 * sportsman.getWeightKg()) + (1.85 * sportsman.getHeightCm()) - (4.68 * sportsman.getAge()) + 655;
        double MET = caloricTableEntry.getCalValue() / ((caloricTableEntry.getWeightFrom() + caloricTableEntry.getWeightTo()) / 2);
        double result = BMR / 24 * MET * perfActivity.getDurationInSeconds() / 3600;
        return result;
    }
    
    private PerformedActivityDto mapToDto(PerformedActivity performedActivity) {
        return performedActivity != null ? mapper.map(performedActivity, PerformedActivityDto.class) : null;
    }
    
    private PerformedActivity mapFromDto(PerformedActivityDto performedActivityDto) {
        return performedActivityDto != null ? mapper.map(performedActivityDto, PerformedActivity.class) : null;
    }
}
