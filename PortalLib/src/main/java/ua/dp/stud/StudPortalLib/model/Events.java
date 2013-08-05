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
    private Date eventDateStart;
    private Date eventDateEnd;
    private Date eventTime;
    private String location;
    private String text;
    private EventsType type;
    private Boolean approved;
    private String comment;
    private String author;
    private Date publication;

    public Events() { }

    @Column
    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Column
    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Column
    public Boolean isApproved() {
        return this.approved;
    }

    public void setApproved(Boolean approve) {
        this.approved = approve;
    }

    @Column
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "eventDateStart")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getEventDateStart() {
        return this.eventDateStart;
    }

    public void setEventDateStart(Date date) {
        this.eventDateStart = date;
    }
    
     @Column(name = "eventDateEnd")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getEventDateEnd() {
        return this.eventDateEnd;
    }

    public void setEventDateEnd(Date date) {
        this.eventDateEnd = date;
    }

    @Column(name = "publication")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getPublication() {
        return this.publication;
    }

    public void setPublication(Date date) {
        this.publication = date;
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
