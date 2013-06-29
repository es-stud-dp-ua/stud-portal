package ua.dp.stud.StudPortalLib.service;

import ua.dp.stud.StudPortalLib.model.ImageImpl;
import ua.dp.stud.StudPortalLib.model.Organization;
import ua.dp.stud.StudPortalLib.model.OrganizationType;

import java.util.Collection;

public interface OrganizationService {
  
     
    /**
     * persist organization in db
     * @param organization
     * @return
     */
    Organization addOrganization(Organization organization);


    
   
    /**
     *
     * @param id
     * @return organization by given ID
     */
    Organization getOrganizationById(Integer id);

    /**
     * update organization
     * @param organization
     * @return
     */
    Organization updateOrganization(Organization organization);

    /**
     *
     * @return collection of organizations
     */
    Collection<Organization> getAllOrganizations(Boolean approve);

    /**
     * return organizations by type  (enum.toSting())
     * @param type String representation of enum
     * @return
     */
    Collection<Organization> getOrganizationsByType(String type);

    /**
     * paginations support
     * @param pageNumb
     * @param orgsPerPage
     * @param type
     * @return
     */
    Collection<Organization> getOrganizationsOnPage(Integer pageNumb, Integer orgsPerPage, String type, Boolean approve);
    Collection<Organization> getOrganizationsOnPage2(Integer pageNumb, Integer orgsPerPage, Boolean approve);
    /**
     * runs count(*) on table
     * @return
     */
    int getCount();
    
    
   int getPagesCount(Integer orgsByPage);
   int getPagesCountOfType(Integer orgsByPage, OrganizationType type);
    /**
     * deletes organization
     * @param organization
     * @return
     */
    void deleteOrganization(Organization organization);
    void deleteImage(ImageImpl image);
	ImageImpl getImageById(Long id);

    Collection<Organization> getAllOrganizationByAuthor(String author);
    int getPagesCountByAuthor(String author, Integer orgByPage);
    Collection<Organization> getPagesOrganizationByAuthor(String author, Integer pageNumb, Integer organizationByPage);
    int getPagesCount(Boolean approved, Integer orgByPage);
    Collection<Organization> getOrganizationsOnPage(Boolean approved, Integer pageNumb, Integer orgByPage);
}
