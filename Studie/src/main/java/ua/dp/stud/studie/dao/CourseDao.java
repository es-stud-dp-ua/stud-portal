package ua.dp.stud.studie.dao;

import ua.dp.stud.studie.model.Course;

import java.util.Collection;

/**
 * 1
 */
public interface CourseDao {

    Course getCourseById(Integer id);

    void addCourse(Course course);

    void deleteCourse(Integer id);

    void updateCourse(Course course);

    Collection<Course> getAll();

}
