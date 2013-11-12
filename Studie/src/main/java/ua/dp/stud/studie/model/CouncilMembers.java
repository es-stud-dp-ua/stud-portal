package ua.dp.stud.studie.model;


import java.io.Serializable;

import javax.persistence.*;

import ua.dp.stud.StudPortalLib.model.BaseImagesSupport;
/**
 * Author: Lysenko Nikolai
 * Date: 06.11.13
 *
 */

@Entity
@Table(name = "council_members")
public class CouncilMembers implements Serializable {
		
    	private Integer memberId;
		
		private String memberName;
	    private String memberContact;
	    private String memberPosition;
	    
	    private Council nameOfCouncil;


	    public CouncilMembers()
	    {
	    }

	    public CouncilMembers(String memberName, String memberContact, String memberPosition)
	    {
	    	this.memberPosition=memberPosition;
	    	this.memberName=memberName;
	    	this.memberContact=memberContact;
	    }
	    public CouncilMembers(String memberName, String memberContact, String memberPosition,Council nameOfCouncil)
	    {
	    	this.memberPosition=memberPosition;
	    	this.memberName=memberName;
	    	this.memberContact=memberContact;
	    	this.nameOfCouncil=nameOfCouncil;
	    }
	    

	    @Id
    	@GeneratedValue(strategy = GenerationType.AUTO)
	    public Integer getMemberId() {
	        return memberId;
	    }

	    public void setMemberId(Integer memberId) {
	        this.memberId = memberId;
	    }
	    
	    public void setNameOfCouncil(Council nameOfCouncil) {
	        this.nameOfCouncil = nameOfCouncil;
	    }


	    @ManyToOne(fetch = FetchType.EAGER)
	    @JoinColumn(name = "council_id")
	    public Council getNameOfCouncil() {
	        return nameOfCouncil;
	    }
	    
	    public void setMemberName(String memberName){
	    	this.memberName=memberName;
	    }

	    @Column(name = "memberName")
	    public String getMemberName(){
	    	return memberName;
	    }

	    public void setMemberPosition(String memberPosition){
	    	this.memberPosition=memberPosition;
	    }

	    @Column(name = "memberPosition")
	    public String getMemberPosition(){
	    	return memberPosition;
	    }
	    
	    public void setMemberContact(String memberContact){
	    	this.memberContact=memberContact;
	    }

	    @Column(name = "memberContact")
	    public String getMemberContact(){
	    	return memberContact;
	    }

	    @Override
	    public int hashCode() {
	        int result = super.hashCode();
	        result = 31 * result + (memberName != null ? memberName.hashCode() : 0);
	        result = 31 * result + (memberContact != null ? memberContact.hashCode() : 0);
	        result = 31 * result + (memberPosition != null ? memberPosition.hashCode() : 0);
	        return result;
	    }

	    @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	        if (!super.equals(o)) return false;

	        CouncilMembers council = (CouncilMembers) o;

	        
	        if (memberName != null ? !memberName.equals(council.memberName) : council.memberName != null) return false;
	        if (memberContact != null ? !memberContact.equals(council.memberContact) : council.memberContact != null)
	            return false;
	        if (memberPosition != null ? !memberPosition.equals(council.memberPosition) : council.memberPosition != null)
	            return false;

	        return true;
	    }

}