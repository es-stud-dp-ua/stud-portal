package ua.dp.stud.StudPortalLib.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dp.stud.StudPortalLib.dao.OrganizationDao;
import ua.dp.stud.StudPortalLib.model.ImageImpl;
import ua.dp.stud.StudPortalLib.model.Organization;
import ua.dp.stud.StudPortalLib.service.OrganizationService;
import ua.dp.stud.StudPortalLib.util.OrganizationType;

import java.util.Collection;

import ua.dp.stud.StudPortalLib.dao.impl.BaseDaoImpl;

@Service("organizationService")
@Transactional
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationDao dao;


    public void setDao(OrganizationDao dao) {
        this.dao = dao;
    }

    public void setBaseDao(OrganizationDao dao) {
        this.dao = dao;
    }

    /**
     * persist organization in db
     *
     * @param organization
     * @return
     */
    @Override
    public Organization addOrganization(Organization organization) {
        dao.save(organization);
        return organization;
    }

    /**
     * @param id
     * @return organization by given ID
     */
    @Transactional(readOnly = true)
    @Override
    public Organization getOrganizationById(Integer id) {
        return dao.getOrganizationById(id);
    }

    /**
     * update organization
     *
     * @param organization
     * @return
     */
    @Override
    public Organization updateOrganization(Organization organization) {
        return dao.update(organization);
    }

    @Override
    public void incrementViews(Organization organization) {
        dao.incrementViews(organization);
    }

    /**
     * @return collection of organizations
     */
    @Transactional(readOnly = true)
    @Override
    public Collection<Organization> getAllOrganizations(Boolean approve) {
        return dao.getAllOrganizations(approve);
    }

    /**
     * return organizations by type (enum.toSting())
     *
     * @param type String representation of enum
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public Collection<Organization> getOrganizationsByType(String type) {
        try {
            OrganizationType orgType = OrganizationType.valueOf(type);
            return dao.getOrganizationsByType(orgType);
        } catch (Exception unused) {
            return null;
        }
    }

    /**
     * paginations support
     *
     * @param pageNumb
     * @param orgsPerPage
     * @param type
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public Collection<Organization> getOrganizationsOnPage(Integer pageNumb, Integer orgsPerPage, String type, Boolean approve) {
        try {
            OrganizationType orgType = OrganizationType.valueOf(type);
            return dao.getOrganizationsOnPage(pageNumb, orgsPerPage, orgType, approve);
        } catch (Exception unused) {
            return null;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<Organization> getOrganizationsOnPage2(Integer pageNumb, Integer orgsPerPage, Boolean approve) {
        return dao.getOrganizationsOnPage2(pageNumb, orgsPerPage, approve);
    }

    @Override
    public void deleteImage(ImageImpl image) {
        dao.deleteImage(image.getId());
    }

    /**
     * @return collection of strings that represent organization types
     */
    @Override
    public Collection<String> getOrganizationTypes() {
        return OrganizationType.allTypes();
    }

    @Override
    @Transactional(readOnly = true)
    public Integer getPagesCount(int orgsByPage) {
        return dao.calcPages(dao.getCount(), orgsByPage);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer getPagesCountOfType(int orgsByPage, OrganizationType type) {
        return dao.calcPages(dao.getCountOfType(type), orgsByPage);
    }

    /**
     * runs count(*) on table
     *
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public Integer getCount() {
        return dao.getCount();
    }

    /**
     * deletes organization
     *
     * @param organization
     * @return
     */
    @Override
    public void deleteOrganization(Organization organization) {
        dao.delete(organization.getId());
    }

    @Override
    public Collection<Organization> getAllOrganizationByAuthor(String author) {
        return dao.getAllOrganizationByAuthor(author);
    }

    @Override
    public Integer getPagesCountByAuthor(String author, Integer orgByPage) {
        return dao.calcPages(dao.getCountByAuthor(author), orgByPage);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Organization> getPagesOrganizationByAuthor(String author, Integer pageNumb, Integer organizationByPage) {
        return dao.getPagesObjectByAuthor(author, pageNumb, organizationByPage);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer getPagesCount(Boolean approved, int orgByPage) {
        return dao.calcPages(dao.getCount(approved), orgByPage);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Organization> getOrganizationsOnPage(Boolean approved, Integer pageNumb, Integer orgByPage) {
        return dao.getObjectOnPage(approved, pageNumb, orgByPage);
    }

    @Override
    @Transactional(readOnly = true)
    public ImageImpl getImageById(Long id) {
        return dao.getImageById(id);
    }

    /**
     * @param organization
     * @return true if organization with this name is present
     */
    @Override
    public Boolean isUnique(Organization organization) {
        return dao.getOrganizationByName(organization.getTitle()) != null;
    }
}
