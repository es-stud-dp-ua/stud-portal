package ua.dp.stud.studie.model;
import ua.dp.stud.StudPortalLib.model.CommonType;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity
@DiscriminatorValue(value="onlineCourseType")
public class OnlineCourseType extends CommonType implements Serializable
{

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "onlineCourseType",cascade = CascadeType.ALL)
	private List<OnlineCourse> onlineCourse;
	
		
	public OnlineCourseType (String onlineCourseType){
       super(onlineCourseType);
    }

    public OnlineCourseType(){
    }
	
    public OnlineCourseType(String onlineCourseType,List<OnlineCourse> onlineCourse) {
    	super(onlineCourseType);
    	this.onlineCourse = onlineCourse;
    }
    
	public List<OnlineCourse> getOnlineCourse()
	{
	return onlineCourse;
	}
	
	public void setOnlineCourse (List<OnlineCourse> onlineCourse){
		this.onlineCourse = onlineCourse;
		for (OnlineCourse spec : this.onlineCourse) {
            if (spec.getOnlineCourseType() == null) {
                spec.setOnlineCourseType(this);
            }
        }
	}
	

}