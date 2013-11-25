package ua.dp.stud.studie.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ua.dp.stud.StudPortalLib.model.BaseImagesSupport;

@Entity
@Table(name = "ONLINE_COURSE_TABLE")
public class OnlineCourse extends BaseImagesSupport
{
	@Id
	@Column(name="ONLINE_COURSE_NAME")
	private String onlineCourseName;
	
	@Column
	private OnlineCourseType onlineCourseType;
	
	@Column
	private String onlineCourseDescription;
	
	public OnlineCourse()
    {
    }
    
	public OnlineCourse(String onlineCourseName)
    {
    	this.onlineCourseName = onlineCourseName;
    }
	
	public String getOnlineCourseName()
	{
		return onlineCourseName;
	}
	
	public void setOnlineCourseName(String onlineCourseName)
	{
		this.onlineCourseName = onlineCourseName;
	}
	
	public String getOnlineCourseDescription()
	{
		return onlineCourseDescription;
	}
	
	public void setOnlineCourseDescription(String onlineCourseDescription)
	{
		this.onlineCourseDescription = onlineCourseDescription;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id", nullable = true)
    public OnlineCourseType getOnlineCourseType()
	{
        return onlineCourseType;
    }

    public void setOnlineCourseType(OnlineCourseType onlineCourseType)
    {
        this.onlineCourseType = onlineCourseType;
    }
    
    public OnlineCourse(String onlineCourseName, String onlineCourseDescription, OnlineCourseType onlineCourseType)
    {
        this.onlineCourseName = onlineCourseName;
        this.onlineCourseDescription = onlineCourseDescription;
        this.onlineCourseType = onlineCourseType;
    }
}