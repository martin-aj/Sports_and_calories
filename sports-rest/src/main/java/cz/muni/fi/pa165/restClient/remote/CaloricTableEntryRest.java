/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.restClient.remote;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.muni.fi.pa165.sports.api.dto.CaloricTableEntryDto;
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
@PropertySource("sports-rest.properties")
public class CaloricTableEntryRest {

    @Autowired
    private RestTemplate restTemplate;

    private final ObjectMapper mapper = new ObjectMapper();

    @Value("${rest.caloric.entry.url}")
    private String url;

    public List<Object[]> getAllCaloricTableEntriesArray() {
        List<CaloricTableEntryDto> list = getAllCaloricTableEntries();

        if (list == null || list.isEmpty()) {
            throw new RuntimeException("Connection failed.");
        }

        List<Object[]> listEntriesOut = new ArrayList<Object[]>();
        for (CaloricTableEntryDto caloricTableEntryDto : list) {
            Object[] entry = {caloricTableEntryDto.getId(), caloricTableEntryDto.getSportActivity().getName(), caloricTableEntryDto.getWeightFrom(),
                caloricTableEntryDto.getWeightTo(), caloricTableEntryDto.getCalValue()};
            listEntriesOut.add(entry);
        }
        return listEntriesOut;
    }

    public List<CaloricTableEntryDto> getAllCaloricTableEntries() {
        List<CaloricTableEntryDto> result = null;
        try {
            String string = restTemplate.getForObject(url + "/list", String.class);
            result = mapper.readValue(string, new TypeReference<List<CaloricTableEntryDto>>() {
            });
        } catch (ResourceAccessException ex) {
            throw ex;
        } catch (IOException ex) {
            Logger.getLogger(CaloricTableEntryRest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(CaloricTableEntryRest.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    public CaloricTableEntryDto getCaloricTableEntryById(Long id) {
        if (id == null) {
            // NULL ID WARNING;
            return null;
        }

        CaloricTableEntryDto result = null;
        try {
            String string = restTemplate.getForObject(url + "/{id}", String.class, id);
            result = mapper.readValue(string, new TypeReference<CaloricTableEntryDto>() {
            });
        } catch (ResourceAccessException ex) {
            throw ex;
        } catch (IOException ex) {
            Logger.getLogger(CaloricTableEntryRest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(CaloricTableEntryRest.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    public void postCaloricTableEntry(CaloricTableEntryDto entry) {

        try {
            restTemplate.postForObject(url + "/post", entry, CaloricTableEntryDto.class);
        } catch (ResourceAccessException ex) {
            throw ex;
        } catch (Exception ex) {
            Logger.getLogger(CaloricTableEntryRest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void updateCaloricTableEntry(CaloricTableEntryDto entry) {

        try {
            restTemplate.put(url + "/put", entry, CaloricTableEntryDto.class);
        } catch (ResourceAccessException ex) {
            throw ex;
        } catch (Exception ex) {
            Logger.getLogger(CaloricTableEntryRest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteCaloricTableEntryById(Long id) throws Exception {
        if (id == null) {
            // NULL ID WARNING;
            return;
        }

        CaloricTableEntryDto result = null;
        try {
            restTemplate.delete(url + "/delete/{id}", id);
        } catch (ResourceAccessException ex) {
            throw ex;
        } catch (HttpServerErrorException ex){
            throw ex;
        }
            catch (Exception ex) {

            Logger.getLogger(CaloricTableEntryRest.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
