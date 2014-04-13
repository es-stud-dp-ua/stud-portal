package ua.dp.stud.studie.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.dp.stud.StudPortalLib.model.News;
import ua.dp.stud.studie.dao.OnlineCourseDao;
import ua.dp.stud.studie.model.OnlineCourse;
import ua.dp.stud.studie.model.OnlineCourseType;
import ua.dp.stud.studie.service.OnlineCourseService;

@Service("onlineCourseService")
@Transactional
public class OnlineCourseServiceImpl implements OnlineCourseService
{

    @Autowired
    private OnlineCourseDao dao;

    public void setDao(OnlineCourseDao dao)
    {
        this.dao=dao ;
    }

    @Override
    @Transactional(readOnly = true)
    public OnlineCourse getOnlineCourseById(Integer id) {
        return dao.getOnlineCourseById(id);
    }

    @Override
    @Transactional(readOnly = false)
    public void addOnlineCourse(OnlineCourse onlineCourse) {
        dao.addOnlineCourse(onlineCourse);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteOnlineCourse(Integer id){
        dao.deleteOnlineCourse(id);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateOnlineCourse(OnlineCourse onlineCourse){
        dao.updateOnlineCourse(onlineCourse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OnlineCourse> getAll() {
        return dao.getAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OnlineCourse> getOnlineCourseByType(Long onlineCourseTypeId, Integer pageNumb, Integer coursesByPage){
        return dao.getOnlineCourseByType(onlineCourseTypeId, pageNumb, coursesByPage);
    }

    @Override
    @Transactional(readOnly = true)
    public OnlineCourseType getOnlineCourseTypeById(Long id) {
        return dao.getOnlineCourseTypeById(id);
    }

    @Override
    @Transactional(readOnly = false)
    public void addOnlineCourseType(OnlineCourseType onlineCourseType) {
        dao.addOnlineCourseType(onlineCourseType);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteOnlineCourseType(Long id) {
        dao.deleteOnlineCourseType(id);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateOnlineCourseType(OnlineCourseType onlineCourseType) {
        dao.updateOnlineCourseType(onlineCourseType);
    }

    @Override
    @Transactional(readOnly = true)
    public  List<OnlineCourseType> getAllOnlineCourseType(){
        return dao.getAllOnlineCourseType();
    }
    @Override
    @Transactional(readOnly = true)
    public List<OnlineCourseType> getAllKindOfCourseWithCount(){
        List<OnlineCourseType> onlineCourseTypes = dao.getAllOnlineCourseType();
        for (OnlineCourseType c:onlineCourseTypes) {
            initializeCountOfCourses(c);
        }
        return onlineCourseTypes;
    }

    @Override
    @Transactional(readOnly = true)
    public void initializeCountOfCourses(OnlineCourseType onlineCourseType){
        dao.initializeCountOfCourses(onlineCourseType);
    }

    @Override
	public String[] getAutocomplete() {
	    String[] list;
	    List<OnlineCourse> courses = getAll();
	    list = new String[courses.size()];
	    for(int i=0;i<list.length;i++){
	    	list[i]=courses.get(i).getOnlineCourseName();
	    }
	    return list;
	}
	 
    @Override
    @Transactional(readOnly = true)
    public List<OnlineCourse> getOnlineCourseByTitle(String title, Integer pageNumb, Integer coursesByPage){
    	return dao.getOnlineCourseByTitle(title,pageNumb,coursesByPage);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<OnlineCourse> getOnlineCoursesOnPage(Integer pageNumb, Integer coursesByPage) {
        return dao.getOnlineCoursesOnPage(pageNumb, coursesByPage);
    }


    @Override
    @Transactional(readOnly = true)
    public Integer getPagesCount(Integer coursesByPage) {
        return dao.getPagesCount(coursesByPage);
    }

    @Override
    public Boolean isDuplicateTopic(String name, Long id){
        return dao.isDuplicateTopic(name,id);
    }

}	