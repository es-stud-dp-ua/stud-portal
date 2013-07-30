/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.dp.stud.StudPortalLib.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import ua.dp.stud.StudPortalLib.util.EventsType;

/**
 *
 * @author Ольга
 */
@NamedQueries(
        {
    @NamedQuery(
            name = "Events.getByApproved",
            query = "Select events From Events events  Where events.approved = :approved and events.comment is null ORDER BY events.eventDate desc")
})

@Entity
@Table(name = "events_table")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
public class Events extends BaseImagesSupport implements Serializable {

    private String title;
    private Date eventDate;
    private Date eventTime;
    private String location;
    private String text;
    private EventsType type;

    public Events() {
    }

    @Column
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "eventDate")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getEventDate() {
        return this.eventDate;
    }

    public void setEventDate(Date date) {
        this.eventDate = date;
    }

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    public Date getEventTime() {
        return this.eventTime;
    }

    public void setEventTime(Date time) {
        this.eventTime = time;
    }

    @Column
    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Column
    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Column
    public EventsType getType() {
        return this.type;
    }

    public void setType(EventsType type) {
        this.type = type;
    }
}
