package ua.dp.stud.studie.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dp.stud.studie.dao.FacultiesDao;
import ua.dp.stud.studie.model.Faculties;
import ua.dp.stud.studie.service.FacultiesService;

import java.util.List;
/**
 * @author: Pikus Vladislav
 */
@Service("facultiesService")
@Transactional
public class FacultiesServiceImpl implements FacultiesService {

    @Autowired
    private FacultiesDao dao;

    public void saveList(List<Faculties> facList) {
        dao.saveList(facList);
    }

    public void deleteList(List<Faculties> facList) {
        dao.deleteList(facList);
    }
}
