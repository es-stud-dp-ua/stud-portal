package ua.dp.stud.aboutTeam.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Date;

/**
 * Author: Tradunsky V.V.
 * Date: 24.04.13
 */
@Entity
@Table(name = "team_table")
public class Human {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String status;
    @Column
    private String url;
    @Column
    private String photoUrl;
    @Column
    @Temporal(TemporalType.DATE)
    private Date date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Human() {
    }

    public Human(String firstName, String lastName, String status, String photoUrl, String url) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
        this.url = url;
        this.photoUrl = photoUrl;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    private static final int START_HASH = 37;
    private static final int MULT_HASH = 77;

    @Override
    public int hashCode() {
        return new HashCodeBuilder(START_HASH, MULT_HASH).append(this.id).
                append(this.firstName).append(this.lastName)
                .append(status).append(url).append(photoUrl).append(date)
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Human)) {
            return false;
        }
        final Human other = (Human) obj;
        return new EqualsBuilder().append(other.id, id).append(other.firstName, firstName)
                .append(other.lastName, lastName).append(other.status, status)
                .append(other.url, url).append(other.photoUrl, photoUrl).append(other.date, date).isEquals();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Human[").append("firstName=").append(firstName)
                .append("lastName=").append(lastName)
                .append("status=").append(status)
                .append("url=").append(url)
                .append("photoUrl=").append(photoUrl).append("]");
        return sb.toString();
    }
}
