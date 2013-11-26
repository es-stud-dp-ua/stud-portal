package ua.dp.stud.studie.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="online_courses_type_table")
public class OnlineCourseType implements Serializable 
{
	@Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "onlineCourseType")
	private List<OnlineCourse> onlineCourse;
	
	@Column
	private String onlineCourseType;
		
	public OnlineCourseType (String onlineCourseType)
    {
        this.onlineCourseType = onlineCourseType;
    }

    public OnlineCourseType()
    {
    }
	
	public List<OnlineCourse> getOnlineCourse()
	{
		return onlineCourse;
	}
	
	public void setOnlineCourse (List<OnlineCourse> onlineCourse)
	{
		this.onlineCourse = onlineCourse;
	}
	
	public Long getId()
	{
		return id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public String getOnlineCourseType()
	{
		return onlineCourseType;
	}
	
	public void setOnlineCourseType (String onlineCourseType)
	{
		this.onlineCourseType = onlineCourseType;
	}
}