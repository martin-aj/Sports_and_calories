/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author mato
 */
@Entity
public class SportActivity implements Serializable{
    //=============  Attributes  =====================================

    @Id
    @GeneratedValue()
    private Long id;
    @Column(nullable = false)
    private String name;
    @OneToMany(mappedBy = "sportActivity", fetch = FetchType.LAZY)
//    @JsonBackReference
//    @JsonIgnore
//    @JsonIgnoreProperties({"performedActivities"})
    private List<PerformedActivity> performedActivities;
    @OneToMany(mappedBy = "sportActivity", fetch = FetchType.LAZY)
//    @JsonBackReference
    private List<CaloricTableEntry> caloricTableEntries;

    //=============  Constructor  =====================================
    public SportActivity() {
        performedActivities = new ArrayList<>();
        caloricTableEntries = new ArrayList<>();
    }

    //=============  Setters/Getters =====================================
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

//    @JsonIgnore
    public List<CaloricTableEntry> getCaloricTableEntries() {
        return caloricTableEntries;
    }

//    @JsonIgnore
    public List<PerformedActivity> getPerformedActivities() {
        return performedActivities;
    }

//    @JsonProperty
    public void setPerformedActivities(List<PerformedActivity> performedActivities) {
        this.performedActivities = performedActivities;
    }

//    @JsonProperty
    public void setCaloricTableEntries(List<CaloricTableEntry> caloricTableEntries) {
        this.caloricTableEntries = caloricTableEntries;
    }

    //=============  Override, Implements  =====================================

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
        final SportActivity other = (SportActivity) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SportActivity{" + "id=" + id + ", name=" + name + "}";
    }
}
