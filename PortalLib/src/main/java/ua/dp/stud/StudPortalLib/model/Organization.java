package ua.dp.stud.StudPortalLib.model;


import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "organizations_table")
@PrimaryKeyJoinColumn(name="id", referencedColumnName="id")
public class Organization extends BaseImagesSupport implements Serializable {

    /**
     * Constructor by default
     */
    public Organization(){
        approved=false;
    }

    //Fields//
    private static final int TEXT_LENGTH = 10000;
    private String author;
    private String title;
    private String text;
    private OrganizationType organizationType;
    private List<News> newsList;
    private Boolean approved;
    private String comment;

    @Column
    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }
    //todo: use lazy fetch type
    @OneToMany(mappedBy = "baseOrg", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    public List<News> getNewsList()
    {
        return this.newsList;
    }
    
    public void setNewsList(List<News> newsList)
    {
        this.newsList = newsList;
    }

    @Column
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Column
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(length = TEXT_LENGTH)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Column
    @Enumerated(EnumType.STRING)
    public OrganizationType getOrganizationType() {
        return organizationType;
    }

    public void setOrganizationType(OrganizationType organizationType) {
        this.organizationType = organizationType;
    }

    @Column()
    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }
}
