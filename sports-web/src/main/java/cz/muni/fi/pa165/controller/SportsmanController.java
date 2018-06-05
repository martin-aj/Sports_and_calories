/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.controller;

import cz.muni.fi.pa165.entity.Sex;
import cz.muni.fi.pa165.sports.api.dto.SportsmanDto;
import cz.muni.fi.pa165.sports.api.service.SportsmanService;
import cz.muni.fi.pa165.validator.SportsmanValidator;
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
@RequestMapping("/sportsman")
public class SportsmanController {

    final static Logger log = LoggerFactory.getLogger(SportsmanController.class);

    @Autowired
    private SportsmanService sportsmanService;

    @Autowired
    private SportsmanValidator sportsmanValidator;

    @Autowired
    private MessageSource messageSource;

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

    @ModelAttribute("sexes")
    public Sex[] sexes() {
        log.debug("sexes()");
        return Sex.values();
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        log.debug("list()");
        model.addAttribute("sportsman", new SportsmanDto());
        return "sportsman/list";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update_form(@PathVariable long id, Model model, Locale locale) {
        try {
            SportsmanDto sportsmanDto = sportsmanService.findSportsmanById(id);
            model.addAttribute("sportsman", sportsmanDto);
            log.debug("update_form(model={})", model);
        } catch (Exception ex) {
            log.error("Unexpected error", ex);
            model.addAttribute("error", messageSource.getMessage("error.operationUnsuccessful.message", new Object[]{}, locale));
        }
        return "sportsman/edit";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id, RedirectAttributes redirectAttributes, Locale locale, UriComponentsBuilder uriBuilder, Model model) {
        try {
            log.debug("delete(id={})", id);
            SportsmanDto sportsmanDto = sportsmanService.findSportsmanById(id);
            sportsmanService.deleteSportsman(sportsmanDto);
            redirectAttributes.addFlashAttribute(
                    "message",
                    messageSource.getMessage("sportsman.delete.message", new Object[]{sportsmanDto.getNickname()}, locale)
            );
        } catch (Exception ex) {
            log.error("Unexpected error", ex);
            redirectAttributes.addFlashAttribute("error", messageSource.getMessage("error.operationUnsuccessful.message", new Object[] {}, locale));
        }
        return "redirect:" + uriBuilder.path("/sportsman/list").build();
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(sportsmanValidator);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute("sportsman") SportsmanDto sportsmanDto, BindingResult bindingResult, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder, Locale locale, Model model) {
        try {
        log.debug("update(locale={}, sportsman={})", locale, sportsmanDto);
        if (bindingResult.hasErrors()) {
            log.debug("binding errors");
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                log.debug("ObjectError: {}", ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                log.debug("FieldError: {}", fe);
            }
            return sportsmanDto.getId() == null ? "sportsman/list" : "sportsman/edit";
        }
        if (sportsmanDto.getId() == null) {
            sportsmanService.createSportsman(sportsmanDto);
            redirectAttributes.addFlashAttribute(
                    "message",
                    messageSource.getMessage("sportsman.add.message", new Object[]{sportsmanDto.getNickname()}, locale)
            );
        } else {
            sportsmanService.updateSportsman(sportsmanDto);
            redirectAttributes.addFlashAttribute(
                    "message",
                    messageSource.getMessage("sportsman.updated.message", new Object[]{sportsmanDto.getNickname()}, locale)
            );
        }
        model.addAttribute(sportsmanDto);
        } catch (Exception ex) {
            log.error("Unexpected error", ex);
            redirectAttributes.addFlashAttribute("error", messageSource.getMessage("error.operationUnsuccessful.message", new Object[] {}, locale));
        }
        return "redirect:" + uriBuilder.path("/sportsman/list").build();
    }
}
