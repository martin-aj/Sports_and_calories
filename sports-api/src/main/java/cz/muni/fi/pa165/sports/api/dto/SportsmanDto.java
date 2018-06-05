/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.sports.api.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SportsmanDto{

    private Long id;
    private String nickname;
    private Integer weightKg;
    private Integer age;
    private Integer heightCm;
    private SexDto sex;
    private List<PerformedActivityDto> performedActivities;
    private Long caloriesSum;

    public SportsmanDto() {
        performedActivities = new ArrayList<>();
    }

    /**
     * @return the nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname the nickname to set
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @return the weightKg
     */
    public Integer getWeightKg() {
        return weightKg;
    }

    /**
     * @param weightKg the weightKg to set
     */
    public void setWeightKg(Integer weightKg) {
        this.weightKg = weightKg;
    }

    /**
     * @return the age
     */
    public Integer getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * @return the heightCm
     */
    public Integer getHeightCm() {
        return heightCm;
    }

    /**
     * @param heightCm the heightCm to set
     */
    public void setHeightCm(Integer heightCm) {
        this.heightCm = heightCm;
    }

    /**
     * @return the sex
     */
    public SexDto getSex() {
        return sex;
    }

    /**
     * @param sex the sex to set
     */
    public void setSex(SexDto sex) {
        this.sex = sex;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<PerformedActivityDto> getPerformedActivities() {
        return performedActivities;
    }

    public void setPerformedActivities(List<PerformedActivityDto> performedActivities) {
        this.performedActivities = performedActivities;
    }

    public Long getCaloriesSum() {
        return caloriesSum;
    }

    public void setCaloriesSum(Long caloriesSum) {
        this.caloriesSum = caloriesSum;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.id);
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
        final SportsmanDto other = (SportsmanDto) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
