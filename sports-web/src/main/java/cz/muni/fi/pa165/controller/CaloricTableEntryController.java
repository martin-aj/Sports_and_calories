/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.controller;

import cz.muni.fi.pa165.sports.api.dto.CaloricTableEntryDto;
import cz.muni.fi.pa165.sports.api.dto.SportActivityDto;
import cz.muni.fi.pa165.sports.api.service.CaloricTableEntryService;
import cz.muni.fi.pa165.sports.api.service.SportActivityService;
import cz.muni.fi.pa165.validator.CaloricTableEntryValidator;
import java.util.List;
import java.util.Locale;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.annotation.Secured;
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
@RequestMapping("/caloricTableEntry")
public class CaloricTableEntryController {

    final static Logger log = LoggerFactory.getLogger(CaloricTableEntryController.class);

    @Autowired
    private CaloricTableEntryService caloricTableEntryService;

    @Autowired
    private SportActivityService sportActivityService;

    @Autowired
    private CaloricTableEntryValidator caloricTableEntryValidator;

    @Autowired
    private MessageSource messageSource;

    @ModelAttribute("caloricTableEntries")
    public List<CaloricTableEntryDto> allCaloricTableEntries(Model model, Locale locale) {
        try {
            log.debug("allCaloricTableEntries()");
            return caloricTableEntryService.listAllCaloricTableEntries();
        } catch (Exception ex) {
            log.error("Unexpected error", ex);
            model.addAttribute("error", messageSource.getMessage("error.operationUnsuccessful.message", new Object[]{}, locale));
        }
        return null;
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

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        log.debug("list()");
        model.addAttribute("caloricTableEntry", new CaloricTableEntryDto());
        return "caloricTableEntry/list";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update_form(@PathVariable long id, Model model, Locale locale) {
        try {
            CaloricTableEntryDto caloricTableEntryDto = caloricTableEntryService.loadCaloricTableEntryById(id);
            model.addAttribute("caloricTableEntry", caloricTableEntryDto);
            log.debug("update_form(model={})", model);
        } catch (Exception ex) {
            log.error("Unexpected error", ex);
            model.addAttribute("error", messageSource.getMessage("error.operationUnsuccessful.message", new Object[]{}, locale));
        }
        return "caloricTableEntry/edit";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id, RedirectAttributes redirectAttributes, Locale locale, UriComponentsBuilder uriBuilder, Model model) {
        try {
            log.debug("delete(id={})", id);
            CaloricTableEntryDto caloricTableEntryDto = caloricTableEntryService.loadCaloricTableEntryById(id);
            caloricTableEntryService.deleteEntryCaloricTable(caloricTableEntryDto);
            redirectAttributes.addFlashAttribute(
                    "message",
                    messageSource.getMessage("caloricTableEntry.delete.message", new Object[]{caloricTableEntryDto.getCalValue()}, locale)
            );
        } catch (Exception ex) {
            log.error("Unexpected error", ex);
            redirectAttributes.addFlashAttribute("error", messageSource.getMessage("error.operationUnsuccessful.message", new Object[]{}, locale));
        }
        return "redirect:" + uriBuilder.path("/caloricTableEntry/list").build();
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(caloricTableEntryValidator);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute("caloricTableEntry") CaloricTableEntryDto caloricTableEntryDto, BindingResult bindingResult, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder, Locale locale, Model model) {
        log.debug("update(locale={}, caloricTableEntry={})", locale, caloricTableEntryDto);
        try {
            if (bindingResult.hasErrors()) {
                log.debug("binding errors");
                for (ObjectError ge : bindingResult.getGlobalErrors()) {
                    log.debug("ObjectError: {}", ge);
                }
                for (FieldError fe : bindingResult.getFieldErrors()) {
                    log.debug("FieldError: {}", fe);
                }
                return caloricTableEntryDto.getId() == null ? "caloricTableEntry/list" : "caloricTableEntry/edit";
            }
            if (caloricTableEntryDto.getId() == null) {
                caloricTableEntryService.createEntryCaloricTable(caloricTableEntryDto);
                redirectAttributes.addFlashAttribute(
                        "message",
                        messageSource.getMessage("caloricTableEntry.add.message", new Object[]{caloricTableEntryDto.getCalValue()}, locale)
                );
            } else {
                caloricTableEntryService.updateEntryCaloricTable(caloricTableEntryDto);
                redirectAttributes.addFlashAttribute(
                        "message",
                        messageSource.getMessage("caloricTableEntry.updated.message", new Object[]{caloricTableEntryDto.getCalValue()}, locale)
                );
            }
        } catch (Exception ex) {
            log.error("Unexpected error", ex);
            redirectAttributes.addFlashAttribute("error", messageSource.getMessage("error.operationUnsuccessful.message", new Object[]{}, locale));
        }
        model.addAttribute(caloricTableEntryDto);
        return "redirect:" + uriBuilder.path("/caloricTableEntry/list").build();
    }
}
