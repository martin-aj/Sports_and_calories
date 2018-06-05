package cz.muni.fi.pa165.validator;



import cz.muni.fi.pa165.sports.api.dto.PerformedActivityDto;
import java.util.Date;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author mato
 */
public class PerformedActivityValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return PerformedActivityDto.class.isAssignableFrom(clazz);
    }

    
    
    @Override
    public void validate(Object target, Errors errors) {
        PerformedActivityDto performedActivity = (PerformedActivityDto) target;
        if (performedActivity.getDurationInSeconds() == null) {
            errors.rejectValue("durationInSeconds", "performedActivity.duration.required");
        }
        if (performedActivity.getDurationInSeconds() != null && 
                performedActivity.getDurationInSeconds().compareTo(new Long(1)) < 0) {
            errors.rejectValue("durationInSeconds", "performedActivity.duration.negative");
        }                   
        if (performedActivity.getDistanceInMeters() == null) {
            errors.rejectValue("distanceInMeters", "performedActivity.distance.required");
        }
        if (performedActivity.getStartOfActivity() == null) {
            errors.rejectValue("startOfActivity", "performedActivity.startOfActivity.required");
        }
        if (new Date().before(performedActivity.getStartOfActivity())) {
            errors.rejectValue("startOfActivity", "performedActivity.startOfActivity.future");
        }
        if (performedActivity.getSportActivity() == null) {
            errors.rejectValue("sportActivity", "performedActivity.sportActivity.required");
        }
        if (performedActivity.getSportsman() == null) { 
            errors.rejectValue("sportsman", "performedActivity.sportsman.required");
        }
    }
}
