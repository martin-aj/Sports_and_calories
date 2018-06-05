package cz.muni.fi.pa165.sports.api.dto;

import java.util.Objects;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class CaloricTableEntryDto{


    private Long id;

    private Integer weightFrom;
    private Integer weightTo;
    private Integer calValue;
    private SportActivityDto sportActivity = null;

    public CaloricTableEntryDto() {
    }

    public Integer getWeightFrom() {
        return weightFrom;
    }

    public void setWeightFrom(Integer weightFrom) {
        this.weightFrom = weightFrom;
    }

    public Integer getWeightTo() {
        return weightTo;
    }

    public void setWeightTo(Integer weightTo) {
        this.weightTo = weightTo;
    }

    public Integer getCalValue() {
        return calValue;
    }

    public void setCalValue(Integer calValue) {
        this.calValue = calValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SportActivityDto getSportActivity() {
        return sportActivity;
    }

    public void setSportActivity(SportActivityDto sportActivity) {
        this.sportActivity = sportActivity;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.id);
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
        final CaloricTableEntryDto other = (CaloricTableEntryDto) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CaloricTableEntryDto{" + "id=" + id + ", weightFrom=" + weightFrom + ", weightTo=" + weightTo + ", calValue=" + calValue + ", sportActivity=" + sportActivity + '}';
    }
}
