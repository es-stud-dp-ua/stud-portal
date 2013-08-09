package ua.dp.stud.StudPortalLib.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;

import ua.dp.stud.StudPortalLib.util.OrganizationType;

@NamedQueries(
        {
                @NamedQuery(
                        name = "Organization.getByAuthor",
                        query = "Select organization From Organization organization  Where organization.author = :author ORDER BY organization.id desc"),
                @NamedQuery(
                        name = "Organization.getNewsByOrgId",
                        query = "select news from News news where news.baseOrg.id = :id and news.orgApproved= :orgApproved order by news.id")
        })

@Entity
@Table(name = "organizations_table")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
public class Organization extends BaseImagesSupport implements Serializable {

    /**
     * Constructor by default
     */
    public Organization() {
        approved = false;
    }

    //Fields//
    private static final int TEXT_LENGTH = 10000;
    private String author;
    @Size(min=5, max=100)  @NotNull @NotBlank
    private String title;
    @Size(min=500, max=10000) @NotNull @NotBlank
    private String text;
    private OrganizationType organizationType;
    private List<News> newsList;
    private Boolean approved;
    private String comment;
    @NotNull @Min(0) 
    private int views;
    private Date publication;
    private String contacts;
    
    @Column
    public String getContacts(){
        return contacts;
    }
    
    public void setContacts(String contacts){
        this.contacts=contacts;
    }

    @Column
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @OneToMany(mappedBy = "baseOrg", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    public List<News> getNewsList() {

        return this.newsList;
    }

    public void setNewsList(List<News> newsList) {
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
    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    @Column(name = "publication")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getPublication() {
        return publication;
    }

    /**
     * Set Date when news was create in full format
     *
     * @param date
     */
    public void setPublication(Date date) {
        this.publication = date;
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

    private static final int START_HASH = 3;
    private static final int MULT_HASH = 89;

    @Override
    public int hashCode() {
        return new HashCodeBuilder(START_HASH, MULT_HASH).append(this.author).append(this.text).append(this.title)
                .append(this.organizationType).append(this.approved).append(this.comment).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Organization)) {
            return false;
        }
        final Organization other = (Organization) obj;
        return new EqualsBuilder().append(other.author, author).append(other.text, text).append(other.title, title)
                .append(other.organizationType, organizationType).append(other.approved, approved)
                .append(other.comment, comment).append(other.newsList, newsList).isEquals();
    }

    @Override
    public String toString() {
        return new StringBuffer().append("Organization[").append("title=").append(title)
                .append(", text=").append(text).append(", author=").append(author)
                .append(", organizationType=").append(organizationType).append(']').toString();
    }
}
