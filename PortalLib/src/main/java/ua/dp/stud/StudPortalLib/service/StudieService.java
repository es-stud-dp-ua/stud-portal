/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.dp.stud.StudPortalLib.service;


import java.util.Collection;
import ua.dp.stud.StudPortalLib.model.ImageImpl;
import ua.dp.stud.StudPortalLib.model.Studie;
/**
 *
 * @author Ольга
 */
public interface StudieService {
    
Studie addStudie(Studie studie);
Studie getStudieById(Integer id);
Studie updateStudie(Studie studie);
Collection<Studie> getAllStudies();
void deleteStudie(Studie studie);
void addImage(ImageImpl image);
ImageImpl getImageById(Long id);
 void deleteImage(ImageImpl image);
}
