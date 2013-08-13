/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.dp.stud.studie.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dp.stud.StudPortalLib.model.ImageImpl;
import ua.dp.stud.studie.dao.StudieDao;
import ua.dp.stud.studie.model.Studie;
import ua.dp.stud.studie.service.StudieService;

import java.util.Collection;

/**
 * @author Ольга
 */
@Service("studieService")
@Transactional
public class StudieServiceImpl implements StudieService {

    @Autowired
    private StudieDao dao;

    public void setDao(StudieDao dao) {
        this.dao = dao;
    }

    @Override
    public Studie addStudie(Studie studie) {
        dao.save(studie);
        return studie;
    }

    @Transactional(readOnly = true)
    @Override
    public Studie getStudieById(Integer id) {
        return dao.getById(id);
    }

    @Override
    public Studie updateStudie(Studie studie) {
        return dao.update(studie);
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<Studie> getAllStudies() {
        return dao.getAll();
    }


    @Override
    public void deleteStudie(Studie studie) {
        dao.delete(studie.getId());
    }

    @Override
    public void addImage(ImageImpl image) {
        dao.addImage(image);
    }

    @Override
    public ImageImpl getImageById(Long id) {
        return dao.getImageById(id);
    }

    @Override
    public void deleteImage(ImageImpl image) {
        dao.deleteImage(image.getId());
    }
}
