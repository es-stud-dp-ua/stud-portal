package ua.dp.stud.studie.dao;

import ua.dp.stud.studie.model.Course;
import ua.dp.stud.studie.model.KindOfCourse;

import java.util.Collection;

/**
 * @author Nazarenko K.V.
 * @author Nazarenko Alexandra
 */
public interface CourseDao {

    Course getCourseById(Integer id);

    void addCourse(Course course);

    void deleteCourse(Integer id);

    void updateCourse(Course course);

    Collection<Course> getAll();

    KindOfCourse getKindOfCourseById(Integer id);

    void addKindOfCourse(KindOfCourse kindOfCourse);

    void deleteKindOfCourse(Integer id);

    void updateKindOfCourse(KindOfCourse kindOfCourse);

    Collection<KindOfCourse> getAllKindOfCourse();


}
