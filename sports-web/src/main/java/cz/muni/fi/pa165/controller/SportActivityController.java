/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.controller;

import cz.muni.fi.pa165.sports.api.dto.SportActivityDto;
import cz.muni.fi.pa165.sports.api.service.SportActivityService;
import cz.muni.fi.pa165.validator.SportActivityValidator;
import java.util.List;
import java.util.Locale;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
@RequestMapping("/sportActivity")
public class SportActivityController {

    final static Logger log = LoggerFactory.getLogger(SportActivityController.class);

    @Autowired
    private SportActivityService sportActivityService;

    @Autowired
    private SportActivityValidator sportActivityValidator;

    @Autowired
    private MessageSource messageSource;

    @ModelAttribute("sportActivities")
    public List<SportActivityDto> allSportActivities(Model model, Locale locale) {
        try {
            log.debug("allSportActivities()");
            return sportActivityService.listAllSportActivities();
        } catch (Exception ex) {
            log.error("Unexpected error", ex);
            model.addAttribute("error", messageSource.getMessage("error.operationUnsuccessful.message", new Object[]{}, locale));
        }
        return null;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        log.debug("list()");
        model.addAttribute("sportActivity", new SportActivityDto());
        return "sportActivity/list";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update_form(@PathVariable long id, Model model, Locale locale) {
        try {
            SportActivityDto sportActivity = sportActivityService.loadSportActivityById(id);
            model.addAttribute("sportActivity", sportActivity);
            log.debug("update_form(model={})", model);
        } catch (Exception ex) {
            log.error("Unexpected error", ex);
            model.addAttribute("error", messageSource.getMessage("error.operationUnsuccessful.message", new Object[]{}, locale));
        }
        return "sportActivity/edit";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id, RedirectAttributes redirectAttributes, Locale locale, UriComponentsBuilder uriBuilder, Model model) {
        try {
            log.debug("delete(id={})", id);
            SportActivityDto sportActivity = sportActivityService.loadSportActivityById(id);
            sportActivityService.deleteSportActivity(sportActivity);
            redirectAttributes.addFlashAttribute(
                    "message",
                    messageSource.getMessage("sportActivity.delete.message", new Object[]{sportActivity.getId(), sportActivity.getName()}, locale)
            );
        } catch (Exception ex) {
            log.error("Unexpected error", ex);
            redirectAttributes.addFlashAttribute("error", messageSource.getMessage("error.operationUnsuccessful.message", new Object[] {}, locale));
        }
        return "redirect:" + uriBuilder.path("/sportActivity/list").build();
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(sportActivityValidator);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute("sportActivity") SportActivityDto sportActivity, BindingResult bindingResult, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder, Locale locale, Model model) {
        try {
            log.debug("update(locale={}, sportActivity={})", locale, sportActivity);
            if (bindingResult.hasErrors()) {
                log.debug("binding errors");
                for (ObjectError ge : bindingResult.getGlobalErrors()) {
                    log.debug("ObjectError: {}", ge);
                }
                for (FieldError fe : bindingResult.getFieldErrors()) {
                    log.debug("FieldError: {}", fe);
                }
                return sportActivity.getId() == null ? "sportActivity/list" : "sportActivity/edit";
            }
            if (sportActivity.getId() == null) {
                sportActivityService.createSportActivity(sportActivity);
                redirectAttributes.addFlashAttribute(
                        "message",
                        messageSource.getMessage("sportActivity.add.message", new Object[]{sportActivity.getId(), sportActivity.getName()}, locale)
                );
            } else {
                sportActivityService.updateSportActivity(sportActivity);
                redirectAttributes.addFlashAttribute(
                        "message",
                        messageSource.getMessage("sportActivity.updated.message", new Object[]{sportActivity.getId(), sportActivity.getName()}, locale)
                );
            }
            model.addAttribute(sportActivity);
        } catch (Exception ex) {
            log.error("Unexpected error", ex);
            redirectAttributes.addFlashAttribute("error", messageSource.getMessage("error.operationUnsuccessful.message", new Object[] {}, locale));
        }
        return "redirect:" + uriBuilder.path("/sportActivity/list").build();
    }
}
