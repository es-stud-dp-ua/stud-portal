package ua.dp.stud.studie.model;


import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import ua.dp.stud.StudPortalLib.model.BaseImagesSupport;
/**
 * Author: Lysenko Nikolai
 * Date: 21.10.13
 *
 */

@Entity
@Table(name = "council_table")
//@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
public class Council implements Serializable{

    private Integer id;
    private Studie studie;
    @NotEmpty
    @Size(min = 100, max = 3000)
    private String councilContact;
    @NotEmpty
    @Size(min = 100, max = 3000)
    private String councilDescription;
    private List<CouncilMembers> councilMembers;
    
    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy = "nameOfCouncil", fetch = FetchType.LAZY)    
    public List<CouncilMembers> getCouncilMembers() {
        if (councilMembers!=null)
           Collections.sort(councilMembers);
    	return councilMembers;
    }

    public void setCouncilMembers(List<CouncilMembers> councilMembers) {
        this.councilMembers = councilMembers;
        for (CouncilMembers spec : this.councilMembers) {
            if (spec.getNameOfCouncil() == null) {
                spec.setNameOfCouncil(this);
            }
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Council()
    {
    }


    public Council(Studie studie){
    	this.setStudie(studie);
    }

    public Council(Studie studie, String councilContact, String councilDescription) {
        this.studie = studie;
        this.councilContact = councilContact;
        this.councilDescription = councilDescription;
        
    }
    
    public Council(Studie studie, String councilContact, String councilDescription,List<CouncilMembers> councilMembers) {
        this.studie = studie;
        this.councilContact = councilContact;
        this.councilDescription = councilDescription;
        this.councilMembers=councilMembers;
        
    }

    @OneToOne
    @JoinColumn(name = "studie_id")
    public Studie getStudie()
    {
        return studie;
    }

    public void setStudie(Studie studie) {
        this.studie = studie;
    }

    @Column(name = "councilContact")
    public String getCouncilContact() {
        return councilContact;
    }

    public void setCouncilContact(String councilContact) {
        this.councilContact = councilContact;
    }

    @Column(name = "councilDescription")
    public String getCouncilDescription() {
        return councilDescription;
    }

    public void setCouncilDescription(String councilDescription) {
        this.councilDescription = councilDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Council)) return false;

        Council council = (Council) o;

        if (councilContact != null ? !councilContact.equals(council.councilContact) : council.councilContact != null)
            return false;
        if (councilDescription != null ? !councilDescription.equals(council.councilDescription) : council.councilDescription != null)
            return false;
        if (councilMembers != null ? !councilMembers.equals(council.councilMembers) : council.councilMembers != null)
            return false;
        if (!id.equals(council.id)) return false;
        if (studie != null ? !studie.equals(council.studie) : council.studie != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (studie != null ? studie.hashCode() : 0);
        result = 31 * result + (councilContact != null ? councilContact.hashCode() : 0);
        result = 31 * result + (councilDescription != null ? councilDescription.hashCode() : 0);
        result = 31 * result + (councilMembers != null ? councilMembers.hashCode() : 0);
        return result;
    }
}