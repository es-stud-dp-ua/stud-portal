package ua.dp.stud.studie.service;

import ua.dp.stud.studie.model.Course;

import java.util.Collection;

/**
 * @author Nazarenko Alexandra
 */
public interface CourseService {

    Course getCourseByID(Integer id);

    void addCourse(Course course);

    void deleteCourse(Integer id);

    void updateCourse(Course course);

    Collection<Course> getAll();

}
