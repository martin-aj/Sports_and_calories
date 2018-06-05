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
import cz.muni.fi.pa165.sports.api.dto.CaloricTableEntryDto;
import cz.muni.fi.pa165.sports.api.service.CaloricTableEntryService;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
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
@RequestMapping("/rest/caloricTableEntry")
public class CaloricTableEntryRESTController {

    final static Logger log = LoggerFactory.getLogger(CaloricTableEntryRESTController.class);
    final private static String REQUIRED_ROLE = "ROLE_ADMIN";

    @Autowired
    private CaloricTableEntryService caloricTableEntryService;
    
    @Autowired
    private MySecurityConfig mySecurityConfig;
    
    private final ObjectMapper mapper = new ObjectMapper();

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON})//, headers = "Accept=text/plain")
    public String list(HttpServletRequest request) throws JsonProcessingException {
        if (!request.isUserInRole(REQUIRED_ROLE)) {
            mySecurityConfig.login("admin", "heslo", request);
        }
        
        log.debug("REST Caloric Table Entry list()");
        List<CaloricTableEntryDto> result = caloricTableEntryService.listAllCaloricTableEntries();
        dataCorrection(result);

        TypeReference type = new TypeReference<List<CaloricTableEntryDto>>() {
        };
        return mapper.writerWithType(type).writeValueAsString(result);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON})//, headers = "Accept=text/plain")
    public String caloricTableEntryById(@PathVariable Long id, HttpServletRequest request) {
        if (!request.isUserInRole(REQUIRED_ROLE)) {
            mySecurityConfig.login("admin", "heslo", request);
        }
        
        log.debug("REST - caloricTableEntry()");

        try {
            CaloricTableEntryDto result = caloricTableEntryService.loadCaloricTableEntryById(id);
            dataCorrection(result);

            TypeReference type = new TypeReference<CaloricTableEntryDto>() {
            };
            return mapper.writerWithType(type).writeValueAsString(result);
        } catch (JsonProcessingException ex) {
            java.util.logging.Logger.getLogger(CaloricTableEntryRESTController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {

        }
        return null;
    }

    @RequestMapping(value = "/post", method = {RequestMethod.GET, RequestMethod.POST}, consumes = {MediaType.APPLICATION_JSON}, produces = {MediaType.APPLICATION_JSON})//, headers = "Accept=text/plain")
    public String addCaloricTableEntry(@RequestBody CaloricTableEntryDto entry, HttpServletRequest request) throws JsonProcessingException {
        if (!request.isUserInRole(REQUIRED_ROLE)) {
            mySecurityConfig.login("admin", "heslo", request);
        }
        
        log.debug("REST Add Caloric Table Entry");
        CaloricTableEntryDto result = null;
        try {
//            result = mapper.readValue(entry, new TypeReference<CaloricTableEntryDto>(){});
            result = caloricTableEntryService.createEntryCaloricTable(entry);
            TypeReference type = new TypeReference<CaloricTableEntryDto>() {
            };
            return mapper.writerWithType(type).writeValueAsString(result);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(CaloricTableEntryRESTController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    @RequestMapping(value = "/put", method = {RequestMethod.GET, RequestMethod.PUT}, consumes = {MediaType.APPLICATION_JSON}, produces = {MediaType.APPLICATION_JSON})//, headers = "Accept=text/plain")
    public String updateCaloricTableEntry(@RequestBody CaloricTableEntryDto entry, HttpServletRequest request) {
        if (!request.isUserInRole(REQUIRED_ROLE)) {
            mySecurityConfig.login("admin", "heslo", request);
        }
        
        log.debug("REST Update Caloric Table Entry");
        CaloricTableEntryDto result = null;
        try {
//            result = mapper.readValue(entry, new TypeReference<CaloricTableEntryDto>(){});
            result = caloricTableEntryService.updateEntryCaloricTable(entry);
            TypeReference type = new TypeReference<CaloricTableEntryDto>() {
            };
            return mapper.writerWithType(type).writeValueAsString(result);
        } catch (JsonProcessingException ex) {
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(CaloricTableEntryRESTController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    @RequestMapping(value = "/delete/{id}", method = {RequestMethod.DELETE}, produces = {MediaType.APPLICATION_JSON})
    public void deleteCaloricTableEntryById(@PathVariable Long id, HttpServletRequest request) {
        if (!request.isUserInRole(REQUIRED_ROLE)) {
            mySecurityConfig.login("admin", "heslo", request);
        }
        
        log.debug("REST Delete Caloric Table Entry");
        try {
            CaloricTableEntryDto entry = caloricTableEntryService.loadCaloricTableEntryById(id);
            if (entry != null) {
                caloricTableEntryService.deleteEntryCaloricTable(entry);
            } else {
                log.info("Delete wasn't finish");
            }
        } catch (IllegalArgumentException ex) {

        }
    }

    private void dataCorrection(List<CaloricTableEntryDto> result) {
        for (CaloricTableEntryDto entry : result) {
            dataCorrection(entry);
        }
    }

    private void dataCorrection(CaloricTableEntryDto result) {
        result.getSportActivity().setCaloricTableEntries(null);
        result.getSportActivity().setPerformedActivities(null);
    }
}
