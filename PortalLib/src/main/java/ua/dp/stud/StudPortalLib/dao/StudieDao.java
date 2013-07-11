/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.dp.stud.StudPortalLib.dao;


import java.util.Collection;

import ua.dp.stud.StudPortalLib.model.ImageImpl;
import ua.dp.stud.StudPortalLib.model.Studie;

/**
 * @author Ольга
 */
public interface StudieDao {

    Studie addStudie(Studie studie);

    Studie getStudieById(Integer id);

    Studie updateStudie(Studie studie);

    Collection<Studie> getAllStudies();

    void deleteStudie(Integer id);

    void addImage(ImageImpl image);

    ImageImpl getImageById(Long id);

    void deleteImage(Long id);

    Integer calcPages(Integer count, Integer perPage);

}
