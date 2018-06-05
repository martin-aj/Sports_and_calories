package cz.muni.fi.pa165.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class Sportsman implements Serializable{

    @Id
    @GeneratedValue

    private Long id;
    @Column(nullable = false, unique = true)

    private String nickname;
    @Column
    private int weightKg;
    @Column
    private int age;
    @Column
    private int heightCm;
    @Column
    private Sex sex; // enum
    @OneToMany(mappedBy = "sportsman", fetch = FetchType.EAGER)
//    @JsonBackReference
//    @JsonIgnore
    private List<PerformedActivity> performedActivities;

    public Sportsman() {
        performedActivities = new ArrayList<PerformedActivity>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    public int getWeightKg() {
        return weightKg;
    }

    /**
     * @param weightKg the weightKg to set
     */
    public void setWeightKg(int weightKg) {
        this.weightKg = weightKg;
    }

    /**
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * @return the heightCm
     */
    public int getHeightCm() {
        return heightCm;
    }

    /**
     * @param heightCm the heightCm to set
     */
    public void setHeightCm(int heightCm) {
        this.heightCm = heightCm;
    }

    /**
     * @return the sex
     */
    public Sex getSex() {
        return sex;
    }

    /**
     * @param sex the sex to set
     */
    public void setSex(Sex sex) {
        this.sex = sex;
    }

    /**
     * @return the performedActivities
     */
    public List<PerformedActivity> getPerformedActivities() {
        return performedActivities;
    }

    /**
     * @param performedActivities the performedActivities to set
     */
    public void setPerformedActivities(List<PerformedActivity> performedActivities) {
        this.performedActivities = performedActivities;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.id);
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
        final Sportsman other = (Sportsman) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Sportsman [id=" + getId() + ", nickname=" + getNickname()
                + ", weight=" + getWeightKg() + ", age=" + getAge() + ", sex=" + getSex() + "]";
    }
}
