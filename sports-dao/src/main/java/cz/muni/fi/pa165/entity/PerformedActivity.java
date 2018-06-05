package cz.muni.fi.pa165.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Entity representing performance of activity by sportsman.
 *
 * @author mato
 */
@Entity
public class PerformedActivity implements Serializable{

    //=============  Attributes  ===============================================
    @Id
    @GeneratedValue
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="dd/MM/yyyy")
    private Date startOfActivity;
    @Column
    private Long durationInSeconds;
    @Column
    private Long distanceInMeters;
    @ManyToOne
//    @JsonManagedReference
    private SportActivity sportActivity = null;
    @ManyToOne
//    @JsonManagedReference
    private Sportsman sportsman = null;

    //=============  Getter/Setters  ===========================================
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

//    @JsonIgnore
    public Sportsman getSportsman() {
        return sportsman;
    }

//    @JsonProperty
    public void setSportsman(Sportsman sportsman) {
        this.sportsman = sportsman;
    }

//    @JsonIgnore
    public SportActivity getSportActivity() {
        return sportActivity;
    }

//    @JsonProperty
    public void setSportActivity(SportActivity sportActivity) {
        this.sportActivity = sportActivity;
    }

    //=============  Override, Implements  =====================================

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.id);
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
        final PerformedActivity other = (PerformedActivity) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "PerformedActivity{" + "id=" + id + ", startOfActivity=" + startOfActivity + ", durationInSeconds="
                + durationInSeconds + ", distanceInMeters=" + distanceInMeters + ", sportActivity=" + sportActivity
                + ", sportsman=" + sportsman + '}';
    }

}
