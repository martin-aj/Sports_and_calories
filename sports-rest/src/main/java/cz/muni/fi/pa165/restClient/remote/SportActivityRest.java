/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.restClient.remote;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.muni.fi.pa165.sports.api.dto.SportActivityDto;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author mato
 */
@Component
@PropertySource("classpath:sports-rest.properties")
public class SportActivityRest {

    @Autowired
    private RestTemplate restTemplate;

    private final ObjectMapper mapper = new ObjectMapper();

    @Value("${rest.sport.activity.url}")
    private String url;

    public List<Object[]> getAllSportActivities() {
        List<SportActivityDto> activities = listAllSportActivities();

        if (activities == null || activities.isEmpty()) {
            throw new RuntimeException("Connection failed.");
        }

        List<Object[]> listEntriesOut = new ArrayList<Object[]>();
        for (SportActivityDto sportActivityDto : activities) {
            Object[] entry = {sportActivityDto.getId(), sportActivityDto.getName()};
            listEntriesOut.add(entry);
        }
        return listEntriesOut;
    }

    public List<SportActivityDto> listAllSportActivities() {
        List<SportActivityDto> result = null;
        try {
            String string = restTemplate.getForObject(url + "/list", String.class);
            result = mapper.readValue(string, new TypeReference<List<SportActivityDto>>() {
            });
        } catch (ResourceAccessException ex) {
            throw ex;
        } catch (IOException ex) {
            Logger.getLogger(SportActivityRest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(SportActivityRest.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    public void postSportActivity(SportActivityDto entry) {

        try {
            restTemplate.postForObject(url + "/post", entry, SportActivityDto.class);
        } catch (ResourceAccessException ex) {
            throw ex;
        } catch (Exception ex) {
            Logger.getLogger(SportActivityRest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void updateSportActivity(SportActivityDto entry) {

        try {
            restTemplate.put(url + "/put", entry, SportActivityDto.class);
        } catch (ResourceAccessException ex) {
            throw ex;
        } catch (Exception ex) {
            Logger.getLogger(SportActivityRest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteSportActivityById(Long id) throws Exception {
        if (id == null) {
            // NULL ID WARNING;
            return;
        }

        SportActivityDto result = null;
        try {
            restTemplate.delete(url + "/delete/{id}", id);
        } catch (ResourceAccessException ex) {
            throw ex;
        } catch (HttpServerErrorException ex) {
            throw ex;
        } catch (Exception ex) {
            Logger.getLogger(SportActivityRest.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    public SportActivityDto loadSportActivityById(Long id) {
        if (id == null) {
            // NULL ID WARNING;
            return null;
        }

        SportActivityDto result = null;

        try {
            String string = restTemplate.getForObject(url + "/{id}", String.class, id);
            if (string == null) {
                return null;
            }
            result = mapper.readValue(string, new TypeReference<SportActivityDto>() {
            });
        } catch (ResourceAccessException ex) {
            throw ex;
        } catch (IOException ex) {
            Logger.getLogger(SportActivityRest.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(SportActivityRest.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
