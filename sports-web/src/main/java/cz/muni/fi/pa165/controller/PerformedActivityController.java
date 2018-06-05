/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.controller;

import cz.muni.fi.pa165.sports.api.dto.PerformedActivityDto;
import cz.muni.fi.pa165.sports.api.dto.SportActivityDto;
import cz.muni.fi.pa165.sports.api.dto.SportsmanDto;
import cz.muni.fi.pa165.sports.api.service.PerformedActivityService;
import cz.muni.fi.pa165.sports.api.service.SportActivityService;
import cz.muni.fi.pa165.sports.api.service.SportsmanService;
import cz.muni.fi.pa165.validator.PerformedActivityValidator;
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
@RequestMapping("/performedActivity")
public class PerformedActivityController {

    final static Logger log = LoggerFactory.getLogger(PerformedActivityController.class);

    @Autowired
    private PerformedActivityService performedActivityService;

    @Autowired
    private SportActivityService sportActivityService;

    @Autowired
    private SportsmanService sportsmanService;

    @Autowired
    private PerformedActivityValidator performedActivityValidator;

    @Autowired
    private MessageSource messageSource;

    @ModelAttribute("performedActivities")
    public List<PerformedActivityDto> allPerformedActivities(Model model, Locale locale) {
        List<PerformedActivityDto> result = null;
        try {
            log.debug("allPerformedActivities()");
            result = performedActivityService.listAllPerformedActivities();
            for (PerformedActivityDto x : result) {
                x.setCalories(performedActivityService.calculateCalories(x));
            }
        } catch (Exception ex) {
            log.error("Unexpected error", ex);
            model.addAttribute("error", messageSource.getMessage("error.operationUnsuccessful.message", new Object[]{}, locale));
        }
        return result;
    }

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

    @ModelAttribute("sportsmen")
    public List<SportsmanDto> allSportsmen(Model model, Locale locale) {
        try {
            log.debug("allSportsmen()");
            return sportsmanService.listSportsmen();
        } catch (Exception ex) {
            log.error("Unexpected error", ex);
            model.addAttribute("error", messageSource.getMessage("error.operationUnsuccessful.message", new Object[]{}, locale));
        }
        return null;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        log.debug("list()");
        model.addAttribute("performedActivity", new PerformedActivityDto());
        return "performedActivity/list";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update_form(@PathVariable long id, RedirectAttributes redirectAttributes, Model model, Locale locale) {
        try {
            log.debug("update_form(model={})", model);
            PerformedActivityDto performedActivityDto = performedActivityService.loadPerformedActivityById(id);
            model.addAttribute("performedActivity", performedActivityDto);
        } catch (Exception ex) {
            log.error("Unexpected error", ex);
            model.addAttribute("error", messageSource.getMessage("error.operationUnsuccessful.message", new Object[]{}, locale));
        }
        return "performedActivity/edit";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id, RedirectAttributes redirectAttributes, Locale locale, UriComponentsBuilder uriBuilder, Model model) {
        try {
            log.debug("delete(id={})", id);
            PerformedActivityDto performedActivityDto = performedActivityService.loadPerformedActivityById(id);
            performedActivityService.deletePerformedActivity(performedActivityDto);
            Object[] myMessage = new Object[]{performedActivityDto.getStartOfActivity(), performedActivityDto.getSportActivity().getName(), performedActivityDto.getSportsman().getNickname()};
            String message = messageSource.getMessage("performedActivity.delete.message", myMessage, locale);
           // String message = "ohnive vtaky do matky ku*vz pichi";
            redirectAttributes.addFlashAttribute(
                    "message",
                    message
            );
        } catch (Exception ex) {
            log.error("Unexpected error", ex);
            redirectAttributes.addFlashAttribute("error", messageSource.getMessage("error.operationUnsuccessful.message", new Object[]{}, locale));
        }
        return "redirect:" + uriBuilder.path("/performedActivity/list").build();
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(performedActivityValidator);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute("performedActivity") PerformedActivityDto performedActivityDto, BindingResult bindingResult, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder, Locale locale, Model model) {
        try {
            log.debug("update(locale={}, performedActivity={})", locale, performedActivityDto);

            if (bindingResult.hasErrors()) {
                log.debug("binding errors");
                for (ObjectError ge : bindingResult.getGlobalErrors()) {
                    log.debug("ObjectError: {}", ge);
                }
                for (FieldError fe : bindingResult.getFieldErrors()) {
                    log.debug("FieldError: {}", fe);
                }
                return performedActivityDto.getId() == null ? "performedActivity/list" : "performedActivity/edit";
            }
            if (performedActivityDto.getId() == null) {
                performedActivityService.createPerformedActivity(performedActivityDto);
                Object[] myMessage = new Object[]{performedActivityDto.getStartOfActivity()};
                redirectAttributes.addFlashAttribute(
                        "message",
                        messageSource.getMessage("performedActivity.add.message", myMessage, locale)
                );
            } else {
                performedActivityService.updatePerformedActivity(performedActivityDto);
                Object[] myMessage = new Object[]{performedActivityDto.getStartOfActivity()};
                redirectAttributes.addFlashAttribute(
                        "message",
                        messageSource.getMessage("performedActivity.updated.message", myMessage, locale)
                );
            }
        } catch (Exception ex) {
            log.error("Unexpected error", ex);
            redirectAttributes.addFlashAttribute("error", messageSource.getMessage("error.operationUnsuccessful.message", new Object[]{}, locale));
        }
        model.addAttribute(performedActivityDto);
        return "redirect:" + uriBuilder.path("/performedActivity/list").build();
    }
}
