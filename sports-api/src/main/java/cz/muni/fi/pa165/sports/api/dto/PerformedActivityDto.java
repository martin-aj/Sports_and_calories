/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.sports.api.dto;

import java.util.Date;
import java.util.Objects;

public class PerformedActivityDto{

    private Long id;
    private Date startOfActivity;
    private Long durationInSeconds;
    private Long distanceInMeters;
    private SportActivityDto sportActivity = null;
    private SportsmanDto sportsman = null;
    private Long calories;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartOfActivity() {
        return startOfActivity;
    }

    public void setStartOfActivity(Date startOfActivity) {
        this.startOfActivity = startOfActivity;
    }

    public Long getDurationInSeconds() {
        return durationInSeconds;
    }

    public void setDurationInSeconds(Long durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    public Long getDistanceInMeters() {
        return distanceInMeters;
    }

    public void setDistanceInMeters(Long distanceInMeters) {
        this.distanceInMeters = distanceInMeters;
    }

    public SportActivityDto getSportActivity() {
        return sportActivity;
    }

    public void setSportActivity(SportActivityDto sportActivity) {
        this.sportActivity = sportActivity;
    }

    public SportsmanDto getSportsman() {
        return sportsman;
    }

    public void setSportsman(SportsmanDto sportsman) {
        this.sportsman = sportsman;
    }

    public Long getCalories() {
        return calories;
    }

    public void setCalories(Long calories) {
        this.calories = calories;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.id);
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
        final PerformedActivityDto other = (PerformedActivityDto) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
