/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.validator;

import cz.muni.fi.pa165.sports.api.dto.UserDto;
import cz.muni.fi.pa165.sports.api.dto.UserRegDto;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author mato
 */
public class UserValidator implements Validator {
    
    @Override
    public boolean supports(Class<?> clazz) {
        return UserRegDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "user.username.required");
        
        UserRegDto user = (UserRegDto) target;
        if (user.getUsername().length() < 5) {
            errors.rejectValue("username", "user.username.short"); 
        }
        if (user.getUsername().length() > 255) {
            errors.rejectValue("username", "user.username.long"); 
        }
       
    }
}
