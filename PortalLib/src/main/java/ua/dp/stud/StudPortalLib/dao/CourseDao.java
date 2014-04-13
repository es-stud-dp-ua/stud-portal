package ua.dp.stud.StudPortalLib.dao;

import ua.dp.stud.StudPortalLib.model.Course;
import ua.dp.stud.StudPortalLib.model.KindOfCourse;

import java.util.Collection;
import java.util.List;

/**
 * @author Nazarenko K.V.
 * @author Nazarenko Alexandra
 */
public interface CourseDao extends DaoForApprove<Course>{

    Course getCourseById(Integer id);

    void addCourse(Course course);

    void deleteCourse(Integer id);

    void updateCourse(Course course);

    List<Course> getAll();

    List<Course> getCoursesByKindAndType(String kindOfCourse, String coursesType);

    KindOfCourse getKindOfCourseById(Long id);

    void addKindOfCourse(KindOfCourse kindOfCourse);

    void deleteKindOfCourse(Long id);

    void updateKindOfCourse(KindOfCourse kindOfCourse);

    List<KindOfCourse> getAllKindOfCourse();

    public Collection<Course> getAllCoursesByAuthor(String author);

    void initializeCountOfCourses(KindOfCourse kindOfCourse);


    Boolean isDuplicateTopic(String name, Long id);
}