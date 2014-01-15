package ua.dp.stud.studie.model;


import java.io.Serializable;

import javax.persistence.*;

import ua.dp.stud.StudPortalLib.model.BaseImagesSupport;
/**
 * Author: Lysenko Nikolai
 * Date: 15.01.14
 *
 */

@Entity
@Table(name = "grants")
@PrimaryKeyJoinColumn(name = "grantId", referencedColumnName = "id")
public class Grant extends BaseImagesSupport implements Serializable{
	
	private String university;
    private String city;
    private String country;
    private String speciality;
    private String qualification;
    private String educationPeriod;
    private String educationLanguage;
    private String receiptOfDocuments;
    private String description;
    private String documents;
    
	public Grant(){}

	@Column
	public String getUniversity() {
		return university;
	}

	public void setUniversity(String university) {
		this.university = university;
	}

	@Column
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Column
	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	@Column
	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	@Column
	public String getEducationPeriod() {
		return educationPeriod;
	}

	public void setEducationPeriod(String educationPeriod) {
		this.educationPeriod = educationPeriod;
	}

	@Column
	public String getEducationLanguage() {
		return educationLanguage;
	}

	public void setEducationLanguage(String educationLanguage) {
		this.educationLanguage = educationLanguage;
	}
	
	@Column
	public String getReceiptOfDocuments() {
		return receiptOfDocuments;
	}

	public void setReceiptOfDocuments(String receiptOfDocuments) {
		this.receiptOfDocuments = receiptOfDocuments;
	}
	
	@Column
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column
	public String getDocuments() {
		return documents;
	}

	public void setDocuments(String documents) {
		this.documents = documents;
	}
	
	public Grant(String university,
     String city,
     String country,
     String speciality,
     String qualification,
     String educationPeriod,
     String educationLanguage,
     String receiptOfDocuments,
     String description,
     String documents){
		
		this.university=university;
		this.city=city;
		this.country=country;
		this.speciality=speciality;
		this.qualification=qualification;
		this.educationPeriod=educationPeriod;
		this.educationLanguage=educationLanguage;
		this.receiptOfDocuments=receiptOfDocuments;
		this.description=description;
	    this.documents=documents;
	}
	
	
	@Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (university != null ? university.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (speciality != null ? speciality.hashCode() : 0);
        result = 31 * result + (qualification != null ? qualification.hashCode() : 0);
        result = 31 * result + (educationPeriod != null ? educationPeriod.hashCode() : 0);
        result = 31 * result + (educationLanguage != null ? educationLanguage.hashCode() : 0);
        result = 31 * result + (receiptOfDocuments != null ? receiptOfDocuments.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (documents != null ? documents.hashCode() : 0);
        return result;
    }
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Grant grant = (Grant) o;

        
        if (university != null ? !university.equals(grant.university) : grant.university != null){ 
        	return false;
        }
        if (city != null ? !city.equals(grant.city) : grant.city != null){ 
        	return false;
        }
        if (country != null ? !country.equals(grant.country) : grant.country != null){ 
        	return false;
        }
        if (speciality != null ? !speciality.equals(grant.speciality) : grant.speciality != null){ 
        	return false;
        }
        if (qualification != null ? !qualification.equals(grant.qualification) : grant.qualification != null){ 
        	return false;
        }
        if (educationPeriod != null ? !educationPeriod.equals(grant.educationPeriod) : grant.educationPeriod != null){ 
        	return false;
        }
        if (educationLanguage != null ? !educationLanguage.equals(grant.educationLanguage) : grant.educationLanguage != null){ 
        	return false;
        }
        if (receiptOfDocuments != null ? !receiptOfDocuments.equals(grant.receiptOfDocuments) : grant.receiptOfDocuments != null){ 
        	return false;
        }
        if (description != null ? !description.equals(grant.description) : grant.description != null){ 
        	return false;
        }

        if (documents != null ? !documents.equals(grant.documents) : grant.documents != null){ 
        	return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return new StringBuffer().append("Grants[").append("university=").append(university)
                .append(", city=").append(city).append(", country=").append(country)
                .append(", speciality=").append(speciality).append(", qualification=").append(qualification).
                append(", educationPeriod=").append(educationPeriod).append(", educationLanguage=").append(educationLanguage).
                append(", receiptOfDocuments=").append(receiptOfDocuments).append(", description=").append(description).
                append(", documents=").append(documents).append(']').toString();
    }
}