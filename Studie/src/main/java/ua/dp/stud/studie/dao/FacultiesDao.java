package ua.dp.stud.studie.dao;

import ua.dp.stud.StudPortalLib.dao.BaseDao;
import ua.dp.stud.studie.model.Faculties;

import java.util.List;

/**
 * @author: Pikus Vladislav
 */
public interface FacultiesDao extends BaseDao<Faculties> {
    void saveList(List<Faculties> facList);

    void deleteList(List<Faculties> facList);

    Faculties getFacultyById(Integer id);
}
