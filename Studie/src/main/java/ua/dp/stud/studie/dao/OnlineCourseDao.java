package ua.dp.stud.studie.dao;

import java.util.Collection;
import java.util.List;

import ua.dp.stud.StudPortalLib.model.News;
import ua.dp.stud.studie.model.OnlineCourse;
import ua.dp.stud.studie.model.OnlineCourseType;

public interface OnlineCourseDao
{
	OnlineCourse getOnlineCourseById(Integer id);

    void addOnlineCourse(OnlineCourse onlineCourse);

    void deleteOnlineCourse(Integer id);

    void updateOnlineCourse(OnlineCourse onlineCourse);

    List<OnlineCourse> getAll();

    OnlineCourseType getOnlineCourseTypeById(Long id);

    void addOnlineCourseType(OnlineCourseType onlineCourseType);

    void deleteOnlineCourseType(Long id);

    void updateOnlineCourseType(OnlineCourseType onlineCourseType);

    List<OnlineCourseType> getAllOnlineCourseType();

	List<OnlineCourse> getOnlineCourseByType(Long onlineCourseTypeId, Integer pageNumb, Integer courseByPage);

	void initializeCountOfCourses(OnlineCourseType onlineCourseType);

	List<OnlineCourse> getOnlineCourseByTitle(String title, Integer pageNumb, Integer courseByPage);

	Collection<OnlineCourse> getOnlineCoursesOnPage(Integer pageNumb, Integer courseByPage);

	Integer getPagesCount(Integer coursesByPage);

	
}