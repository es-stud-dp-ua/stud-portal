package ua.dp.stud.studie.service;

import ua.dp.stud.studie.model.Course;
import ua.dp.stud.studie.model.KindOfCourse;

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

    KindOfCourse getKindOfCourseById(Integer id);

    void addKindOfCourse(KindOfCourse kindOfCourse);

    void deleteKindOfCourse(Integer id);

    void updateKindOfCourse(KindOfCourse kindOfCourse);

    Collection<KindOfCourse> getAllKindOfCourse();

    Collection<KindOfCourse> getAllKindOfCourseWithCount();

    void initializeCountOfCoursesInKindOfCourse(KindOfCourse kindOfCourse);

}
