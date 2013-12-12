package ua.dp.stud.studie.dao.impl;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.dp.stud.StudPortalLib.dao.impl.BaseDaoImpl;
import ua.dp.stud.studie.dao.FacultiesDao;
import ua.dp.stud.studie.model.Faculties;

import java.util.List;

/**
 * @author: Pikus Vladislav
 */
@Repository("facultiesDao")
public class FacultiesDaoImpl extends BaseDaoImpl<Faculties> implements FacultiesDao {



    @Override
    public void saveList(List<Faculties> facList) {
        for(Faculties faculties : facList) {
            getSession().saveOrUpdate(faculties);
        }
    }

    @Override
    public void deleteList(List<Faculties> facList) {
        for(Faculties faculties : facList) {
            getSession().delete(faculties);
        }
    }

    @Override
    public Faculties getFacultyById(Integer id) {
        Faculties faculty = (Faculties) getSession().get(Faculties.class, id);
        Hibernate.initialize(faculty.getListOfSchedules());
        Hibernate.initialize(faculty.getSpecialties());
        return faculty;
    }
}
