/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.controller;

import cz.muni.fi.pa165.entity.Sex;
import cz.muni.fi.pa165.sports.api.dto.SportsmanDto;
import cz.muni.fi.pa165.sports.api.service.SportsmanService;
import java.util.List;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author mato
 */
@Controller
public class HomeController {

    final static Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private SportsmanService sportsmanService;

    @Autowired
    private MessageSource messageSource;

    @ModelAttribute("sportsmen")
    public List<SportsmanDto> allSportsmen(RedirectAttributes redirectAttributes, Locale locale, Model model) {
        try {
            log.debug("allSportsmen()");
            List<SportsmanDto> result = sportsmanService.listSportsmen();
            long sum = 0;
            for (SportsmanDto x : result) {
                x.setCaloriesSum(sportsmanService.getSumOfCalories(x));
            }
            return sportsmanService.sortSportsmenTopTen(result);
        } catch (AccessDeniedException ex) {
            // do nothing
        } catch (Exception ex) {
            log.error("Unexpected error", ex);
            model.addAttribute("error", messageSource.getMessage("error.operationUnsuccessful.message", new Object[] {}, locale));
        }
        return null;
    }

    @ModelAttribute("sexes")
    public Sex[] sexes() {
        log.debug("sexes()");
        return Sex.values();
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String list(Model model) {
        log.debug("list()");
        model.addAttribute("sportsman", new SportsmanDto());
        return "/home";
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Locale locale) {

            ModelAndView model = new ModelAndView();
            if (error != null) {
                model.addObject("error", messageSource.getMessage("login.error.invalidCredentials", new Object[] {}, locale)); 
            }

            if (logout != null) {
                model.addObject("msg", messageSource.getMessage("login.msg.successfulLogout", new Object[] {}, locale));
            }
            model.setViewName("login");

            return model;
    }
}
