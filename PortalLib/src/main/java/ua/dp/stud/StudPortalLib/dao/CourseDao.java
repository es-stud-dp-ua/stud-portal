package ua.dp.stud.StudPortalLib.dao;

import ua.dp.stud.StudPortalLib.model.Course;
import ua.dp.stud.StudPortalLib.model.KindOfCourse;

import java.util.List;

/**
 * @author Nazarenko K.V.
 * @author Nazarenko Alexandra
 */
public interface CourseDao {

    Course getCourseById(Integer id);

    void addCourse(Course course);

    void deleteCourse(Integer id);

    void updateCourse(Course course);

    List<Course> getAll();

    List<Course> getCoursesByKindAndType(String kindOfCourse, String coursesType);

    KindOfCourse getKindOfCourseById(Integer id);

    void addKindOfCourse(KindOfCourse kindOfCourse);

    void deleteKindOfCourse(Integer id);

    void updateKindOfCourse(KindOfCourse kindOfCourse);

    List<KindOfCourse> getAllKindOfCourse();

    void initializeCountOfCourses(KindOfCourse kindOfCourse);
}