package cz.muni.fi.pa165.validator;


import cz.muni.fi.pa165.sports.api.dto.SportActivityDto;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author mato
 */
public class SportActivityValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return SportActivityDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "sportActivity.name.required");
        
        SportActivityDto sportActivity = (SportActivityDto) target;
        if (sportActivity.getName().length() < 3) {
            errors.rejectValue("name", "sportActivity.name.short"); 
        }
        if (sportActivity.getName().length() > 255) {
            errors.rejectValue("name", "sportActivity.name.long"); 
        }
    }
}
