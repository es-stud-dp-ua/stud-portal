package ua.dp.stud.StudPortalLib.dao;

import ua.dp.stud.StudPortalLib.model.BaseImagesSupport;
import ua.dp.stud.StudPortalLib.model.ImageImpl;
import ua.dp.stud.StudPortalLib.model.News;
import ua.dp.stud.StudPortalLib.model.Organization;

import java.util.Collection;

/**
 * @author Pikus Vladislav
 */
public interface DaoForApprove<E extends BaseImagesSupport> extends BaseDao<E> {

    /**
     * Delete image
     *
     * @param id image id
     */
    void deleteImage(Long id);

    /**
     * Return image by id
     *
     * @param id image id
     * @return founded object
     */
    ImageImpl getImageById(Long id);

    /**
     * Save new image in DB
     *
     * @param image new image
     */
    void addImage(ImageImpl image);

    /**
     * Deleting current object by id
     *
     * @param id object id
     */
    void delete(Integer id);

    /**
     * Return object by id with initialization image list
     *
     * @param id object id
     * @return founded object
     */
    E getById(Integer id);

    /**
     * Calculate page
     *
     * @param count   page number
     * @param perPage object per page
     * @return calculated number
     */
    Integer calcPages(Integer count, Integer perPage);

    /**
     * Return object count by approve
     *
     * @param approved approve
     * @return object count
     */
    Integer getCount(Boolean approved);

    /**
     * Return object count by author
     *
     * @param author author of the object
     * @return object count
     */
    Integer getCountByAuthor(String author);

    /**
     * Return list of object ob current page by author
     *
     * @param author    author of the object
     * @param pageNumb  page number
     * @param objByPage object per page
     * @return list of object
     */
    Collection<E> getPagesObjectByAuthor(String author, Integer pageNumb, Integer objByPage);

    /**
     * Return list of object ob current page by approve
     *
     * @param approved  approve
     * @param pageNumb  page number
     * @param objByPage object per pag
     * @return list of object
     */
    Collection<E> getObjectOnPage(Boolean approved, Integer pageNumb, Integer objByPage);
}
