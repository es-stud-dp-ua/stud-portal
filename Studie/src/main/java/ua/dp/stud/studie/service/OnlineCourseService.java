package ua.dp.stud.studie.service;

import java.util.List;

import ua.dp.stud.studie.model.OnlineCourse;
import ua.dp.stud.studie.model.OnlineCourseType;

public interface OnlineCourseService
{
	OnlineCourse getOnlineCourseById (Integer id);
	
	void addOnlineCourse (OnlineCourse onlineCourse);
	
	void deleteOnlineCourse (Integer Id);
	
	void updateOnlineCourse (OnlineCourse onlineCourse);
	
	List<OnlineCourse> getAll();
	
	OnlineCourseType getOnlineCourseTypeById (Integer id);
	
	void addOnlineCourseType (OnlineCourseType onlineCourseType);

    void deleteOnlineCourseType (Integer id);

    void updateOnlineCourseType (OnlineCourseType onlineCourseType);

    List<OnlineCourseType> getAllOnlineCourseType();

	List<OnlineCourse> getOnlineCourseByType(Integer onlineCourseTypeId);
}