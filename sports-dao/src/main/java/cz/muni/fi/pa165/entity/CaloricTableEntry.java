/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author mato
 */
@Entity
public class CaloricTableEntry implements Serializable{

    @Id
    @GeneratedValue()
    private Long id;
    @Column(nullable = false)
    private Integer weightFrom;
    @Column(nullable = false)
    private Integer weightTo;
    @Column(nullable = false)
    private Integer calValue;
    @ManyToOne
//    @JsonManagedReference
    private SportActivity sportActivity = null;

    public CaloricTableEntry() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public SportActivity getSportActivity() {
        return sportActivity;
    }

    public void setSportActivity(SportActivity sportsActivity) {
        this.sportActivity = sportsActivity;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.id);
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
        final CaloricTableEntry other = (CaloricTableEntry) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CaloricTableEntry{" + "id=" + id + ", weightFrom=" + weightFrom + ", weightTo=" + weightTo + ", caloricValue=" + calValue + '}';
    }
}
