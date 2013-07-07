/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.dp.stud.StudPortalLib.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dp.stud.StudPortalLib.dao.StudieDao;
import ua.dp.stud.StudPortalLib.model.Studie;
import ua.dp.stud.StudPortalLib.service.StudieService;
import java.util.*;
import ua.dp.stud.StudPortalLib.model.ImageImpl;
/**
 *
 * @author Ольга
 */
@Service("studieService")
@Transactional
public class StudieServiceImpl implements StudieService{
    
    @Autowired
    private StudieDao dao;
    
    public void setDao(StudieDao dao) {
        this.dao = dao;
    }

    @Override
    public Studie addStudie(Studie studie) {
       dao.addStudie(studie);
        return studie;
    }

   @Transactional(readOnly = true)
    @Override
    public Studie getStudieById(Integer id) {
        return dao.getStudieById(id);
    }

    @Override
    public Studie updateStudie(Studie studie) {
        return dao.updateStudie(studie);
    }
    @Transactional(readOnly = true)
    @Override
    public Collection<Studie> getAllStudies() {
       return  dao.getAllStudies();
    }

    
    @Override
    public void deleteStudie(Studie studie) {
       dao.deleteStudie(studie.getId());
    }
    
     @Override
    public void addImage(ImageImpl image) {
       dao.addImage(image);
    }
   @Override
    public ImageImpl getImageById(Long id)
	{
        return dao.getImageById(id);
    } 
       @Override
    public void deleteImage(ImageImpl image)
	{
        dao.deleteImage(image.getId());
    } 
}
