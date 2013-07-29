package ua.dp.stud.StudPortalLib.dao;

import ua.dp.stud.StudPortalLib.model.ImageImpl;
import ua.dp.stud.StudPortalLib.model.News;
import ua.dp.stud.StudPortalLib.model.Organization;
import ua.dp.stud.StudPortalLib.util.OrganizationType;

import java.util.Collection;

public interface OrganizationDao<E> extends Base<E> {

    /**
     * collection for organizations news by id
     *
     * @param id         organizations id
     * @param pageNumb   page number
     * @param newsByPage news by page
     * @return collection of News for organization
     */
    Collection<News> getOrganizationsNewsByIdOnPage(Integer id, Integer pageNumb, Integer newsByPage, Boolean approve);

    /**
     * collection for all organizations news by id
     *
     * @param id of organization
     * @return set of News
     */
    Collection<News> getAllOrganizationsNewsById(Integer id, Boolean approve);

    /**
     * persists organization
     *
     * @param organization
     * @return
     */
   // Organization addOrganization(Organization organization);

    /**
     * @param id
     * @return organization by id
     */
    Organization getOrganizationById(Integer id);

    /**
     * updates organization
     *
     * @param organization
     * @return
     */
    //Organization updateOrganization(Organization organization);

    /**
     * return all orgs, approved or not
     *
     * @param approve
     * @return collection of all organizations
     */
  //  Collection<Organization> getAllOrganizations(Boolean approve);

    /**
     * increment of views for current organization
     *
     * @param organization
     */
    void incViews(Organization organization);

    /**
     * return all organization with specified enum type
     *
     * @param type
     * @return
     */
    Collection<Organization> getOrganizationsByType(OrganizationType type);

    /**
     * pagination support for organizations
     *
     * @param pageNumb
     * @param orgsPerPage
     * @param type
     * @return
     */
    Collection<Organization> getOrganizationsOnPage(Integer pageNumb, Integer orgsPerPage, OrganizationType type, Boolean approve);

    Collection<Organization> getOrganizationsOnPage2(Integer pageNumb, Integer orgsPerPage, Boolean approve);

    /**
     * cout all organizations
     *
     * @return
     */
    //Integer getCount();

    Integer getCountOfType(OrganizationType type);

    /**
     * delete given organization from db
     *
     * @return
     */
    void deleteOrganization(Integer id);

  //  void deleteImage(Long id);

   // ImageImpl getImageById(Long id);

    Collection<Organization> getAllOrganizationByAuthor(String author);

    //Integer getCountByAuthor(String author);

    Collection<Organization> getPagesOrganizationByAuthor(String author, Integer pageNumb, Integer organizationByPage);

    Integer getCountByApprove(Boolean approved);

   // Collection<Organization> getOrganizationsOnPage(Boolean approved, Integer pageNumb, Integer orgByPage);

    Integer calcPages(Integer count, Integer perPage);
}
