package ua.dp.stud.studie.model;


import javax.persistence.*;

import ua.dp.stud.StudPortalLib.model.BaseImagesSupport;
/**
 * Author: Lysenko Nikolai
 * Date: 21.10.13
 *
 */

@Entity
@Table(name = "council_table")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
public class Council extends BaseImagesSupport{

    @Column
    private String councilName;

    @Column
    private String councilContact;

    @Column
    private String councilDescription;


    public Council()
    {
    }

    public Council(String councilName){
    	this.setCouncilName(councilName);
    }

    public String getCouncilName() {
        return councilName;
    }

    public void setCouncilName(String councilName) {
        this.councilName = councilName;
    }

    public String getCouncilContact() {
        return councilContact;
    }

    public void setCouncilContact(String councilContact) {
        this.councilContact = councilContact;
    }


    public String getCouncilDescription() {
        return councilDescription;
    }

    public void setCouncilDescription(String councilDescription) {
        this.councilDescription = councilDescription;
    }



    
        public Council(String councilName, String authorslogin, String councilContact, String councilDescription) {
        this.councilName = councilName;
        this.councilContact = councilContact;
        this.councilDescription = councilDescription;
        
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Council council = (Council) o;

        
        if (councilName != null ? !councilName.equals(council.councilName) : council.councilName != null) return false;
        if (councilContact != null ? !councilContact.equals(council.councilContact) : council.councilContact != null)
            return false;
        if (councilDescription != null ? !councilDescription.equals(council.councilDescription) : council.councilDescription != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (councilName != null ? councilName.hashCode() : 0);
        result = 31 * result + (councilContact != null ? councilContact.hashCode() : 0);
        result = 31 * result + (councilDescription != null ? councilDescription.hashCode() : 0);
        return result;
    }
}