package ua.dp.stud.studie.model;


import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import ua.dp.stud.StudPortalLib.model.BaseImagesSupport;

@Entity
@Table(name = "ONLINE_COURSE_TABLE")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
public class OnlineCourse extends BaseImagesSupport
{
	private String onlineCourseName;
	private OnlineCourseType onlineCourseType;
	private String onlineCourseDescription;
	
	public OnlineCourse()
    {
    }
    
	public OnlineCourse(String onlineCourseName)
    {
    	this.onlineCourseName = onlineCourseName;
    }
	@Column
	public String getOnlineCourseName()
	{
		return onlineCourseName;
	}
	
	public void setOnlineCourseName(String onlineCourseName)
	{
		this.onlineCourseName = onlineCourseName;
	}
	@Column
	public String getOnlineCourseDescription()
	{
		return onlineCourseDescription;
	}
	
	public void setOnlineCourseDescription(String onlineCourseDescription)
	{
		this.onlineCourseDescription = onlineCourseDescription;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="online_course_type_id", nullable = true)
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
    
    public OnlineCourse(String onlineCourseName, String onlineCourseDescription)
    {
        this.onlineCourseName = onlineCourseName;
        this.onlineCourseDescription = onlineCourseDescription;
    }
    
}