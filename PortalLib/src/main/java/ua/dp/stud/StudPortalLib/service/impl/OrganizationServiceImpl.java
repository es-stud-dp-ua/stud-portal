package ua.dp.stud.StudPortalLib.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dp.stud.StudPortalLib.dao.OrganizationDao;
import ua.dp.stud.StudPortalLib.model.ImageImpl;
import ua.dp.stud.StudPortalLib.model.Organization;
import ua.dp.stud.StudPortalLib.model.OrganizationType;
import ua.dp.stud.StudPortalLib.service.OrganizationService;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationDao dao;
    //todo: you shouldn't use non final variables in service
    private String type;
    //todo: delete this
    @Override
   public String getType()
    {
    return type;
    }
    //todo: and this
    @Override
   public void setType(String type)
    {
        this.type=type;
    }
    //todo: and this
    public OrganizationServiceImpl()
    {
  
    }
    

    
    private static final List<String> ORGANIZATION_TYPES;
    //todo: use Arrays.asList(OrganizationType.values())
    static {
        ORGANIZATION_TYPES = new ArrayList<String>();

        for (OrganizationType t:OrganizationType.values()){
            ORGANIZATION_TYPES.add(t.toString());
        }
    }

    public void setDao(OrganizationDao dao) {
        this.dao = dao;
    }

    /**
     * persist organization in db
     * @param organization
     * @return
     */
    @Override
    public Organization addOrganization(Organization organization) {
        dao.addOrganization(organization);
        return organization;
    }

    /**
     *
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
     * @param organization
     * @return
     */
    @Override
    public Organization updateOrganization(Organization organization) {
        return dao.updateOrganization(organization);
    }

    /**
     *
     * @return collection of organizations
     */
    @Transactional(readOnly = true)
    @Override
    public Collection<Organization> getAllOrganizations(Boolean approve) {
        return dao.getAllOrganizations(approve);
    }

    /**
     * return organizations by type  (enum.toSting())
     * @param type String representation of enum
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public Collection<Organization> getOrganizationsByType(String type) {
        Collection<Organization> result;
        //todo: remove try-block
        try {
            OrganizationType orgType = OrganizationType.valueOf(type);
            result = dao.getOrganizationsByType(orgType);
        } catch (Exception ex){
            result = null;
        }

        return result;
    }

    /**
     * paginations support
     * @param pageNumb
     * @param orgsPerPage
     * @param type
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public Collection<Organization> getOrganizationsOnPage(Integer pageNumb, Integer orgsPerPage, String type, Boolean approve) 
    {
        Collection<Organization> result;
        //todo: remove try-block
        try {
            OrganizationType orgType = OrganizationType.valueOf(type);
            result = dao.getOrganizationsOnPage(pageNumb, orgsPerPage, orgType,approve);
        } catch (Exception ex){
            result = null;
        }

        return result;
    }
    
    @Transactional(readOnly = true)
    @Override
    public Collection<Organization> getOrganizationsOnPage2(Integer pageNumb, Integer orgsPerPage, Boolean approve)
    {
        Collection<Organization> result;
        //todo: remove try-block
        try {
             result = dao.getOrganizationsOnPage2(pageNumb, orgsPerPage,approve);
        } catch (Exception ex){
            result = null;
        }

        return result;
    }
     
    @Override
    public void deleteImage(ImageImpl image)
	{
        dao.deleteImage(image.getId());
    }      
            /**
     *
     * @return collection of strings that represent organization types
     */
    @Override
    public Collection<String> getOrganizationTypes() {
        return ORGANIZATION_TYPES;
    }
    @Override
    //todo: @Transactional readonly = true
    public int getPagesCount(Integer orgsByPage) {
        int orgsCount = dao.getCount();
        return (orgsCount>0)? ((orgsCount-1)/orgsByPage + 1) : 0;
    }
    @Override
    //todo: @Transactional readonly = true
    public int getPagesCountOfType(Integer orgsByPage, OrganizationType type)
    {
    int orgsCount = dao.getCountOfType(type);
    return (orgsCount>0)? ((orgsCount-1)/orgsByPage + 1) : 0;
    }
    /**
     * runs count(*) on table
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public int getCount() {
        return dao.getCount();
    }

    /**
     * deletes organization
     * @param organization
     * @return
     */
    @Override
    public void deleteOrganization(Organization organization) {
        dao.deleteOrganization(organization.getId());
    }

    @Override
    public Collection<Organization> getAllOrganizationByAuthor(String author)
    {
        return dao.getAllOrganizationByAuthor(author);
    }

    @Override
    public int getPagesCountByAuthor(String author, Integer orgByPage)
    {
        int newsCount = dao.getCountByAuthor(author);
        return (newsCount > 0) ? ((newsCount - 1) / orgByPage + 1) : 0;
    }

    @Override
    //todo: @Transactional readonly = true
    public Collection<Organization> getPagesOrganizationByAuthor(String author, Integer pageNumb, Integer organizationByPage)
    {
        return dao.getPagesOrganizationByAuthor(author, pageNumb, organizationByPage);
    }

    @Override
    //todo: @Transactional readonly = true
    public int getPagesCount(Boolean approved, Integer orgByPage)
    {
        int newsCount = dao.getCount(approved);
        return (newsCount > 0) ? ((newsCount - 1) / orgByPage + 1) : 0;
    }

    @Override
    //todo: @Transactional readonly = true
    public Collection<Organization> getOrganizationsOnPage(Boolean approved, Integer pageNumb, Integer orgByPage)
    {
        return dao.getOrganizationsOnPage(approved, pageNumb, orgByPage);
    }
   	
	@Override
    //todo: @Transactional readonly = true
    public ImageImpl getImageById(Long id)
	{
        ImageImpl image = dao.getImageById(id);
        if (image != null) {
            return image;
        }
        return null;
    }
}

