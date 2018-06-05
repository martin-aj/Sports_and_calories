/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.validator;

import cz.muni.fi.pa165.sports.api.dto.SportsmanDto;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author mato
 */
public class SportsmanValidator implements Validator {
    
    @Override
    public boolean supports(Class<?> clazz) {
        return SportsmanDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nickname", "sportsman.nickname.required");
        
        SportsmanDto sportsman = (SportsmanDto) target;
        if (sportsman.getNickname().length() < 3) {
            errors.rejectValue("nickname", "sportsman.nickname.short"); 
        }
        if (sportsman.getNickname().length() > 255) {
            errors.rejectValue("nickname", "sportsman.nickname.long"); 
        }
        if (sportsman.getAge() == null) {
            errors.rejectValue("age", "sportsman.age.required"); 
        }
        if (sportsman.getAge() != null && sportsman.getAge().compareTo(new Integer(130)) > 0) {
            errors.rejectValue("age", "sportsman.age.great"); 
        }
        if (sportsman.getWeightKg() == null) {
            errors.rejectValue("weightKg", "sportsman.weight.required"); 
        }
        if (sportsman.getHeightCm() == null) {
            errors.rejectValue("heightCm", "sportsman.height.required"); 
        }
        if (sportsman.getHeightCm() != null && sportsman.getHeightCm().compareTo(new Integer(40)) < 0) {
            errors.rejectValue("heightCm", "sportsman.height.small"); 
        }
        if (sportsman.getHeightCm() != null && sportsman.getHeightCm().compareTo(new Integer(250)) > 0) {
            errors.rejectValue("heightCm", "sportsman.height.great");   
        }
    }
}
