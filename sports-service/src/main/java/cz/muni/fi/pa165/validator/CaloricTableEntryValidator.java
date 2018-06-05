package cz.muni.fi.pa165.validator;

import cz.muni.fi.pa165.sports.api.dto.CaloricTableEntryDto;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author mato
 */
public class CaloricTableEntryValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return CaloricTableEntryDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CaloricTableEntryDto caloricTableEntryDto = (CaloricTableEntryDto) target;
        
        if (caloricTableEntryDto.getWeightFrom() == null) {
            errors.rejectValue("weightFrom", "caloricTableEntry.weightFrom.required");
        }
        if (caloricTableEntryDto.getWeightTo() == null) {
            errors.rejectValue("weightTo", "caloricTableEntry.weightTo.required");
        }
        if (caloricTableEntryDto.getWeightTo() != null && caloricTableEntryDto.getWeightFrom() != null && 
                caloricTableEntryDto.getWeightFrom().compareTo(caloricTableEntryDto.getWeightTo()) > 0) {
            errors.rejectValue("weightTo", "caloricTableEntry.weight.To<From");
        }
        if (caloricTableEntryDto.getCalValue() == null) {
            errors.rejectValue("calValue", "caloricTableEntry.calValue.required");
        }
        if (caloricTableEntryDto.getSportActivity() == null) {
            errors.rejectValue("sportActivity", "caloricTableEntry.sportActivity.required");
        }       
    }
}
