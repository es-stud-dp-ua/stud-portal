package ua.dp.stud.studie.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public OnlineCourse getOnlineCourseById(Integer id)
    {
        return dao.getOnlineCourseById(id);
    }

    @Override
    @Transactional(readOnly = false)
    public void addOnlineCourse(OnlineCourse onlineCourse)
    {
        dao.addOnlineCourse(onlineCourse);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteOnlineCourse(Integer id)
    {
        dao.deleteOnlineCourse(id);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateOnlineCourse(OnlineCourse onlineCourse)
    {
        dao.updateOnlineCourse(onlineCourse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OnlineCourse> getAll()
    {
        return dao.getAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OnlineCourse> getOnlineCourseByType(Integer onlineCourseTypeId)
    {
        return dao.getOnlineCourseByType(onlineCourseTypeId);
    }

    @Override
    @Transactional(readOnly = true)
    public OnlineCourseType getOnlineCourseTypeById(Integer id)
    {
        return dao.getOnlineCourseTypeById(id);
    }

    @Override
    @Transactional(readOnly = false)
    public void addOnlineCourseType(OnlineCourseType onlineCourseType)
    {
        dao.addOnlineCourseType(onlineCourseType);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteOnlineCourseType(Integer id)
    {
        dao.deleteOnlineCourseType(id);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateOnlineCourseType(OnlineCourseType onlineCourseType)
    {
        dao.updateOnlineCourseType(onlineCourseType);
    }

    @Override
    @Transactional(readOnly = true)
    public  List<OnlineCourseType> getAllOnlineCourseType()
    {
        return dao.getAllOnlineCourseType();
    }
}	