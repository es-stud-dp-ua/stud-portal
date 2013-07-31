package ua.dp.stud.StudPortalLib.dao;

import ua.dp.stud.StudPortalLib.model.BaseImagesSupport;
import ua.dp.stud.StudPortalLib.model.ImageImpl;
import ua.dp.stud.StudPortalLib.model.News;
import ua.dp.stud.StudPortalLib.model.Organization;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: VladB
 * Date: 31.07.13
 * Time: 23:15
 * To change this template use File | Settings | File Templates.
 */
public interface DaoForApprove<E extends BaseImagesSupport> extends BaseDao<E> {

    void deleteImage(Long id);

    ImageImpl getImageById(Long id);

    void addImage(ImageImpl image);

    void delete(Integer id);

    E getById(Integer id);

    Integer calcPages(Integer count, Integer perPage);

    Integer getCount(Boolean approved);

    Integer getCountByAuthor(String author);

    Collection<E> getPagesObjectByAuthor(String author, Integer pageNumb, Integer objByPage);

    Collection<E> getObjectOnPage(Boolean approved, Integer pageNumb, Integer objByPage);
}
