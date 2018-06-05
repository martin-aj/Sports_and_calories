/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.rest.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.muni.fi.pa165.config.MySecurityConfig;
import cz.muni.fi.pa165.sports.api.dto.SportActivityDto;
import cz.muni.fi.pa165.sports.api.service.SportActivityService;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author mato
 */
@RestController
@RequestMapping("/rest/sportActivity")
public class SportActivityRESTController {

    final static Logger log = LoggerFactory.getLogger(SportActivityRESTController.class);
    final private static String REQUIRED_ROLE = "ROLE_ADMIN";

    @Autowired
    private SportActivityService sportActivityService;
    
    @Autowired
    private MySecurityConfig mySecurityConfig;

    private final ObjectMapper mapper = new ObjectMapper();

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON})
    public String list(HttpServletRequest request) throws JsonProcessingException, Exception {
        if (!request.isUserInRole(REQUIRED_ROLE)) {
            mySecurityConfig.login("admin", "heslo", request);
        }
    
        
        log.debug("called list()");
        List<SportActivityDto> result = sportActivityService.listAllSportActivities();
        dataCorrection(result);
        
        TypeReference type = new TypeReference<List<SportActivityDto>>() {
        };
        return mapper.writerWithType(type).writeValueAsString(result);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON})
    public String loadById(@PathVariable Long id, HttpServletRequest request) {
        if (!request.isUserInRole(REQUIRED_ROLE)) {
            mySecurityConfig.login("admin", "heslo", request);
        }
        
        log.debug("called loadById()");

        try {
            SportActivityDto result = sportActivityService.loadSportActivityById(id);
            if (result == null) {
                return null;
            }
            
            dataCorrection(result);
            
            TypeReference type = new TypeReference<SportActivityDto>() {
            };
            return mapper.writerWithType(type).writeValueAsString(result);
        } catch (JsonProcessingException ex) {
            log.error(null, ex);
        } catch (IllegalArgumentException ex) {
            log.error(null, ex);
        }

        return null;
    }

    @RequestMapping(value = "/post", method = {RequestMethod.GET, RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON}, produces = {MediaType.APPLICATION_JSON})
    public String add(@RequestBody SportActivityDto entry, HttpServletRequest request) throws JsonProcessingException {
        if (!request.isUserInRole(REQUIRED_ROLE)) {
            mySecurityConfig.login("admin", "heslo", request);
        }
        
        log.debug("callec add()");
        SportActivityDto result = null;
        try {
            result = sportActivityService.createSportActivity(entry);
            TypeReference type = new TypeReference<SportActivityDto>() {
            };
            return mapper.writerWithType(type).writeValueAsString(result);
        } catch (IOException ex) {
            log.error(null, ex);
        }
        return null;

    }

    @RequestMapping(value = "/put", method = {RequestMethod.GET, RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON}, produces = {MediaType.APPLICATION_JSON})
    public String update(@RequestBody SportActivityDto entry, HttpServletRequest request) {
        if (!request.isUserInRole(REQUIRED_ROLE)) {
            mySecurityConfig.login("admin", "heslo", request);
        }
        
        log.debug("called update()");
        SportActivityDto result = null;
        try {
            result = sportActivityService.updateSportActivity(entry);
            TypeReference type = new TypeReference<SportActivityDto>() {
            };
            return mapper.writerWithType(type).writeValueAsString(result);
        } catch (JsonProcessingException ex) {
            log.error(null, ex);
        } catch (IOException ex) {
            log.error(null, ex);
        }

        return null;
    }

    @RequestMapping(value = "/delete/{id}", method = {RequestMethod.DELETE}, produces = {MediaType.APPLICATION_JSON})
    public void deleteById(@PathVariable Long id, HttpServletRequest request) {
        if (!request.isUserInRole(REQUIRED_ROLE)) {
            mySecurityConfig.login("admin", "heslo", request);
        }
        
        log.debug("called deleteById()");
        try {
            SportActivityDto entry = sportActivityService.loadSportActivityById(id);
            if (entry != null) {
                sportActivityService.deleteSportActivity(entry);
            } else {
                log.info("Delete unsuccessful: Entity not found.");
            }
        } catch (IllegalArgumentException ex) {
            log.error(null, ex);
        }
    }

    private void dataCorrection(List<SportActivityDto> result) {
        for (SportActivityDto entry : result) {
            dataCorrection(entry);
        }
    }

    private void dataCorrection(SportActivityDto result) {
        result.setCaloricTableEntries(null);
        result.setPerformedActivities(null);
    }

}
