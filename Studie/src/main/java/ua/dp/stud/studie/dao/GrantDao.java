/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.dp.stud.studie.dao;


import ua.dp.stud.studie.model.Grant;

import java.util.Collection;

/**
 * @author Lysenko Nikolai
 */

public interface GrantDao {

    Grant getGrantById(Integer id);

    Collection<Grant> getAllGrants();
    
    void addGrant(Grant grant);

    void deleteGrant(Integer id);

    void updateGrant(Grant grant);

    Boolean isDuplicateTopic(String name, Long id);

}
