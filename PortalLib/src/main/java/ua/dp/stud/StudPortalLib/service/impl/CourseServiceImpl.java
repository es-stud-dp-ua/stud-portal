package ua.dp.stud.StudPortalLib.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.dp.stud.StudPortalLib.dao.CourseDao;
import ua.dp.stud.StudPortalLib.model.Course;
import ua.dp.stud.StudPortalLib.model.KindOfCourse;
import ua.dp.stud.StudPortalLib.service.CourseService;

import java.util.Collection;
import java.util.List;

/**
 * @author Nazarenko Alexandra
 */

@Service("courseService")
@Transactional
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseDao dao;

    public void setDao(CourseDao dao){
        this.dao=dao ;
    }

    @Override
    @Transactional(readOnly = true)
    public Course getCourseByID(Integer id) {
        return dao.getCourseById(id);
    }

    @Override
    @Transactional(readOnly = false)
    public void addCourse(Course course) {
        dao.addCourse(course);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteCourse(Integer id) {
        dao.deleteCourse(id);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateCourse(Course course) {
        dao.updateCourse(course);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Course> getAll() {
        return dao.getAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Course> getCoursesByKindAndType(String kindOfCourse, String coursesType) {
        return dao.getCoursesByKindAndType(kindOfCourse, coursesType);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Course> getCoursesByAuthor(String author){
        return dao.getAllCoursesByAuthor(author);
    }

    @Override
    public Integer getPagesCountByAuthor(String author, Integer courseByPage) {
        return dao.calcPages(dao.getCountByAuthor(author), courseByPage);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Course> getPagesCourseByAuthor(String author, Integer pageNumb, Integer coursesByPage) {
        return dao.getPagesObjectByAuthor(author, pageNumb, coursesByPage);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer getPagesCount(int courseByPage) {
        return dao.calcPages(dao.getCount(), courseByPage);
    }

    @Override
    @Transactional(readOnly = true)
    public KindOfCourse getKindOfCourseById(Long id) {
        return dao.getKindOfCourseById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer getPagesCount(Boolean approved, int courseByPage) {
        return dao.calcPages(dao.getCount(approved), courseByPage);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Course> getCoursesOnPage(Boolean approved, Integer pageNumb, Integer courseByPage) {
        return dao.getObjectOnPage(approved, pageNumb, courseByPage);
    }

    @Override
    @Transactional(readOnly = false)
    public void addKindOfCourse(KindOfCourse kindOfCourse) {
        dao.addKindOfCourse(kindOfCourse);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteKindOfCourse(Long id){
        dao.deleteKindOfCourse(id);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateKindOfCourse(KindOfCourse kindOfCourse) {
        dao.updateKindOfCourse(kindOfCourse);
    }

    @Override
    @Transactional(readOnly = true)
    public  List<KindOfCourse> getAllKindOfCourse() {
        return dao.getAllKindOfCourse();
    }

    @Override
    @Transactional(readOnly = true)
    public  List<KindOfCourse> getAllKindOfCourseWithCount() {
        List<KindOfCourse> kindOfCourses = dao.getAllKindOfCourse();
        for (KindOfCourse c:kindOfCourses) {
            initializeCountOfCourses(c);
        }
        return kindOfCourses;
    }

    @Override
    @Transactional(readOnly = true)
    public void initializeCountOfCourses(KindOfCourse kindOfCourse) {
        dao.initializeCountOfCourses(kindOfCourse);
    }
}