package ua.dp.stud.StudPortalLib.dao;

import ua.dp.stud.StudPortalLib.model.News;
import ua.dp.stud.StudPortalLib.model.Organization;
import ua.dp.stud.StudPortalLib.util.OrganizationType;

import java.util.Collection;

public interface OrganizationDao extends DaoForApprove<Organization> {

    Integer getCount();

    Collection<Organization> getAllOrganizations(Boolean approve);

    Organization getOrganizationByName(String title);
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
     * @param id
     * @return organization by id
     */
    Organization getOrganizationById(Integer id);

    /**
     * increment of views for current organization
     *
     * @param organization
     */
    void incrementViews(Organization organization);

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

    Integer getCountOfType(OrganizationType type);

    Collection<Organization> getAllOrganizationByAuthor(String author);
}
