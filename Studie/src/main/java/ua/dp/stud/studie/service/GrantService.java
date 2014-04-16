package ua.dp.stud.studie.service;

import ua.dp.stud.studie.model.Grant;

import java.util.Collection;

/**
 * @author Lysenko Nikolai
 */
public interface GrantService {

    Grant getGrantById(Integer id);

    Collection<Grant> getAllGrants();
    
    void addGrant(Grant grant);

    void deleteGrant(Integer id);

    void updateGrant(Grant grant);

    Boolean isDuplicateTopic(String name, Integer id);
}
