package ua.dp.stud.studie.service;

import java.util.Collection;
import java.util.List;

import ua.dp.stud.StudPortalLib.model.Course;
import ua.dp.stud.studie.model.OnlineCourse;
import ua.dp.stud.studie.model.OnlineCourseType;

public interface OnlineCourseService{
	OnlineCourse getOnlineCourseById (Integer id);
	
	void addOnlineCourse (OnlineCourse onlineCourse);
	
	void deleteOnlineCourse (Integer Id);
	
	void updateOnlineCourse (OnlineCourse onlineCourse);
	
	List<OnlineCourse> getAll();
	
	OnlineCourseType getOnlineCourseTypeById (Long id);
	
	void addOnlineCourseType (OnlineCourseType onlineCourseType);

    void deleteOnlineCourseType (Long id);

    void updateOnlineCourseType (OnlineCourseType onlineCourseType);

    List<OnlineCourseType> getAllOnlineCourseType();

	List<OnlineCourse> getOnlineCourseByType(Long onlineCourseTypeId, Integer pageNumb, Integer coursesByPage);

    List<OnlineCourseType> getAllKindOfCourseWithCount();

    void initializeCountOfCourses(OnlineCourseType onlineCourseType);

    String[] getAutocomplete();
    
    List<OnlineCourse> getOnlineCourseByTitle(String title, Integer pageNumb, Integer coursesByPage);
    
    Collection<OnlineCourse> getOnlineCoursesOnPage(Integer pageNumb, Integer coursesByPage);
    
    Integer getPagesCount(Integer coursesByPage);
}