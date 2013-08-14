/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.dp.stud.studie.dao;


import ua.dp.stud.StudPortalLib.dao.DaoForApprove;
import ua.dp.stud.studie.model.Studie;

import java.util.Collection;

/**
 * @author Ольга
 */
public interface StudieDao extends DaoForApprove<Studie> {

    Studie getById(Integer id);

    Collection<Studie> getAll();

}
