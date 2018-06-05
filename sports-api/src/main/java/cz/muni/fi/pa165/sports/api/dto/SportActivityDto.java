/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.sports.api.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SportActivityDto{


    private Long id;
    private String name;
    private List<PerformedActivityDto> performedActivities;
    private List<CaloricTableEntryDto> caloricTableEntries;

    public SportActivityDto() {
        performedActivities = new ArrayList<>();
        caloricTableEntries = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PerformedActivityDto> getPerformedActivities() {
        return performedActivities;
    }

    public void setPerformedActivities(List<PerformedActivityDto> performedActivities) {
        this.performedActivities = performedActivities;
    }

    public List<CaloricTableEntryDto> getCaloricTableEntries() {
        return caloricTableEntries;
    }

    public void setCaloricTableEntries(List<CaloricTableEntryDto> caloricTableEntries) {
        this.caloricTableEntries = caloricTableEntries;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SportActivityDto other = (SportActivityDto) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    
    
}
