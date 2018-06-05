/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.controller;

import cz.muni.fi.pa165.entity.RoleTypes;
import cz.muni.fi.pa165.entity.Users;
import cz.muni.fi.pa165.sports.api.dto.UserDto;
import cz.muni.fi.pa165.sports.api.dto.UserRegDto;
import cz.muni.fi.pa165.sports.api.dto.UserRoleDto;
import cz.muni.fi.pa165.sports.api.service.UserRoleService;
import cz.muni.fi.pa165.sports.api.service.UserService;
import cz.muni.fi.pa165.validator.UserValidator;
import java.util.List;
import java.util.Locale;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author mato
 */
@Controller
@RequestMapping("/user")
public class UserController {

    final static Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private MessageSource messageSource;

    @ModelAttribute("userRoles")
    public List<UserRoleDto> userRoles(Model model, Locale locale) {
        try {
            log.debug("userRoles()");           
            return userRoleService.listUserRoles();
        } catch (Exception ex) {
            log.error("Unexpected error", ex);
            model.addAttribute("error", messageSource.getMessage("error.operationUnsuccessful.message", new Object[]{}, locale));
        }
        return null;
    }

    @ModelAttribute("roleTypes")
    public RoleTypes[] roleTypes(Model model, Locale locale) {
        return RoleTypes.values();
    }

    /*@RequestMapping(value = "/list", method = RequestMethod.GET)
     public String list(Model model) {
     log.debug("list()");
     model.addAttribute("user", new UserDto());
     return "user/list";
     } */

    /*@RequestMapping(value = "/update/{username}", method = RequestMethod.POST)
     public String update_form(@PathVariable String username, Model model, Locale locale, UriComponentsBuilder uriBuilder) {
        
     try {
     UserDto userDto = userService.findUser(username);
     model.addAttribute("users", userDto);
            
     log.debug("update_form(model={})", model);
     } catch (Exception ex) {
     log.error("Unexpected error", ex);
     model.addAttribute("error", messageSource.getMessage("error.operationUnsuccessful.message", new Object[]{}, locale));
     }
     return "user/register";
     } */

    /*@RequestMapping(value = "/delete/{username}", method = RequestMethod.POST)
     public String delete(@PathVariable String username, RedirectAttributes redirectAttributes, Locale locale, UriComponentsBuilder uriBuilder, Model model) {
     try {
     log.debug("delete(id={})", username);
     UserDto userDto = userService.findUser(username);
     userService.deleteUser(userDto);
     redirectAttributes.addFlashAttribute(
     "message",
     messageSource.getMessage("user.delete.message", new Object[]{userDto.getUsername()}, locale)
     );
     } catch (Exception ex) {
     log.error("Unexpected error", ex);
     redirectAttributes.addFlashAttribute("error", messageSource.getMessage("error.operationUnsuccessful.message", new Object[] {}, locale));
     }
     return "redirect:" + uriBuilder.path("/user/list").build();
     } */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(userValidator);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute("userRegDto") UserRegDto userRegDto, BindingResult bindingResult, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder, Locale locale, Model model) {
        try {
            log.debug("update(locale={}, users={})", locale, userRegDto);
            if (bindingResult.hasErrors()) {
                log.debug("binding errors");
                for (ObjectError ge : bindingResult.getGlobalErrors()) {
                    log.debug("ObjectError: {}", ge);
                }
                for (FieldError fe : bindingResult.getFieldErrors()) {
                    log.debug("FieldError: {}", fe);
                }
                return userRegDto.getUsername() == null ? "user/list" : "user/edit";
            }
            if (userRegDto.getUsername() != null) {
                UserDto userDto = new UserDto();
                userDto.setUsername(userRegDto.getUsername());
                String generatedSecuredPasswordHash = BCrypt.hashpw(userRegDto.getPassword(), BCrypt.gensalt(12));
                userDto.setPassword(generatedSecuredPasswordHash);
                

                UserRoleDto userRoleDto = new UserRoleDto();
                userRoleDto.setRole(userRegDto.getRoleForUser());
                userRoleDto.setUsers(userDto);
                userService.createUser(userDto);
                userRoleService.createUserRole(userRoleDto);
                redirectAttributes.addFlashAttribute(
                        "message",
                        messageSource.getMessage("user.add.message", new Object[]{userRegDto.getUsername()}, locale)
                );
            } /*else {

             userService.updateUser(userDto);
             redirectAttributes.addFlashAttribute(
             "message",
             messageSource.getMessage("user.updated.message", new Object[]{userDto.getUsername()}, locale)
             );
             }*/

            model.addAttribute(userRegDto);// nie som isty...
        } catch (Exception ex) {
            log.error("Unexpected error", ex);
            redirectAttributes.addFlashAttribute("error", messageSource.getMessage("error.operationUnsuccessful.message", new Object[]{}, locale));
        }
        return "redirect:" + uriBuilder.path("/").build();
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(Model model) {
        log.debug("register()");
        //model.addAttribute("user", new UserDto());
        return "user/register";
    }
}
