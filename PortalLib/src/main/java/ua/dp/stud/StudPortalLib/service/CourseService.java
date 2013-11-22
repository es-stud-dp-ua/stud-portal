package ua.dp.stud.StudPortalLib.service;

import ua.dp.stud.StudPortalLib.model.Course;
import ua.dp.stud.StudPortalLib.model.KindOfCourse;

import java.util.Collection;
import java.util.List;

/**
 * @author Nazarenko Alexandra
 */
public interface CourseService {

    Course getCourseByID(Integer id);

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

    List<KindOfCourse> getAllKindOfCourseWithCount();

    public Collection<Course> getCoursesByAuthor(String author);

    public Integer getPagesCountByAuthor(String author, Integer orgByPage);

    public Collection<Course> getPagesCourseByAuthor(String author, Integer pageNumb, Integer organizationByPage);

    void initializeCountOfCourses(KindOfCourse kindOfCourse);

}