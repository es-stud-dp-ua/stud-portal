package ua.dp.stud.StudPortalLib.dao.impl;

import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ua.dp.stud.StudPortalLib.dao.OrganizationDao;
import ua.dp.stud.StudPortalLib.model.ImageImpl;
import ua.dp.stud.StudPortalLib.model.News;
import ua.dp.stud.StudPortalLib.model.Organization;
import ua.dp.stud.StudPortalLib.util.OrganizationType;

import java.util.Collection;

@Repository("organizationDao")
public class OrganizationDaoImpl extends BaseDaoImpl<Organization> implements OrganizationDao {

    /**
     * collection for organizations news by id
     *
     * @param id         organizations id
     * @param pageNumb   page number
     * @param newsByPage news by page
     * @return collection of News for organization
     */
    @Override
    public Collection<News> getOrganizationsNewsByIdOnPage(Integer id, Integer pageNumb, Integer newsByPage, Boolean approve) {
        int firstResult = (pageNumb - 1) * newsByPage;
        return getSession().getNamedQuery("Organization.getNewsByOrgId")
                .setParameter("id", id).setParameter("orgApproved", approve)
                .setFirstResult(firstResult)
                .setMaxResults(newsByPage)
                .list();
    }

    @Override
    public Integer calcPages(Integer count, Integer perPage) {
        return (count > 0) ? ((count - 1) / perPage + 1) : 0;
    }

    /**
     * collection for all organizations news by id
     *
     * @param id of organization
     * @return set of News
     */
    @Override
    public Collection<News> getAllOrganizationsNewsById(Integer id, Boolean approve) {
        return getSession().getNamedQuery("Organization.getNewsByOrgId")
                .setParameter("id", id).setParameter("orgApproved", approve).list();
    }

    /**
     * @param id id of organization
     * @return
     */
    @Override
    public Organization getOrganizationById(Integer id) {
        Organization org = (Organization) getSession().get(Organization.class, id);
        Hibernate.initialize(org.getAdditionalImages());
        Hibernate.initialize(org.getNewsList());
        return org;

    }

    /**
     * @param type Enumeration type of orgs
     * @return all orgs by specified type
     */
    @Override
    public Collection<Organization> getOrganizationsByType(OrganizationType type) {
        return getSession().createCriteria(Organization.class)
                .add(Restrictions.eq("organizationType", type))
                .addOrder(Order.desc("id")).list();
    }

    @Override
    public Integer getCountByAuthor(String author) {
        return ((Long) getSession().createQuery("Select Count(*) From Organization organization  Where organization.author = :author")
                .setParameter("author", author).uniqueResult()).intValue();
    }

    @Override
    public Integer getCount() {
        return ((Long) getSession().createQuery("Select Count(*) From Organization WHERE approved= true")
                .uniqueResult()).intValue();
    }

    @Override
    public Collection<Organization> getAllOrganizations(Boolean approve) {
        return getSession().createCriteria(Organization.class).add(Restrictions.eq("approved", approve)).addOrder(Order.desc("id")).list();
    }

    /**
     * @param pageNumb    number of requested page
     * @param orgsPerPage number of organizations per page
     * @param type        Enumeration type of org
     * @return
     */
    @Override
    public Collection<Organization> getOrganizationsOnPage(Integer pageNumb, Integer orgsPerPage, OrganizationType type, Boolean approve) {
        int firstResult = (pageNumb - 1) * orgsPerPage;
        return (Collection<Organization>) getSession().createQuery("From Organization organization WHERE organization.organizationType= :type and organization.approved=:approve_ and organization.comment is null ORDER BY organization.id desc").setParameter("type", type).setParameter("approve_", approve).setFirstResult(firstResult).setMaxResults(orgsPerPage).list();


    }

    @Override
    public Collection<Organization> getOrganizationsOnPage2(Integer pageNumb, Integer orgsPerPage, Boolean approve) {
        int firstResult = (pageNumb - 1) * orgsPerPage;
        return (Collection<Organization>) getSession().createQuery("From Organization a WHERE a.approved=:approve_ ORDER BY a.id desc").setParameter("approve_", approve).setFirstResult(firstResult).setMaxResults(orgsPerPage).list();

    }

    @Override
    public Collection<Organization> getOrganizationsOnPage(Boolean approved, Integer pageNumb, Integer orgByPage) {
        int firstResult = (pageNumb - 1) * orgByPage;
        return getSession().createQuery("Select organization From Organization organization  Where organization.approved = :approved")
                .setParameter("approved", approved).setFirstResult(firstResult).setMaxResults(orgByPage).list();
    }

    @Override
    public Integer getCountOfType(OrganizationType type) {
        return ((Long) getSession().createQuery("Select Count(*) From Organization WHERE organizationType= :type and approved=true")
                .setParameter("type", type).uniqueResult()).intValue();
    }

    @Override
    public Collection<Organization> getAllOrganizationByAuthor(String author) {
        return getSession().getNamedQuery("Organization.getByAuthor")
                .setParameter("author", author).list();
    }

    @Override
    public Collection<Organization> getPagesOrganizationByAuthor(String author, Integer pageNumb, Integer organizationByPage) {
        int firstResult = (pageNumb - 1) * organizationByPage;
        return getSession().getNamedQuery("Organization.getByAuthor")
                .setParameter("author", author).setFirstResult(firstResult).setMaxResults(organizationByPage).list();
    }

    @Override
    public Integer getCountByApprove(Boolean approved) {
        return ((Long) getSession().createQuery("Select Count(*) From Organization organization  Where organization.approved = :approved and organization.comment is null")
                .setParameter("approved", approved).uniqueResult()).intValue();
    }

    @Override
    //todo: rename method
    public void incViews(Organization organization) {
        organization.setViews(organization.getViews() + 1);
        getSession().update(organization);

    }

}
