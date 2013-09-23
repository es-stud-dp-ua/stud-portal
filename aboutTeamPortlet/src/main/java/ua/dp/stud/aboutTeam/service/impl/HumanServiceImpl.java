package ua.dp.stud.aboutTeam.service.impl;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ua.dp.stud.aboutTeam.dao.HumanDao;
import ua.dp.stud.aboutTeam.model.Human;
import ua.dp.stud.aboutTeam.service.HumanService;

import java.util.Collection;

/**
 * @author Vladislav Pikus
 */
@Service("humanService")
public class HumanServiceImpl implements HumanService {
    @Autowired
    @Qualifier(value = "humanDao")
    private HumanDao humanDao;

    @Transactional(readOnly = false)
    public void setHumanDao(HumanDao humanDao) {
        this.humanDao = humanDao;
    }
    //todo: remove all @Override
    @Override
    @Transactional(readOnly = true)
    public Human getHumanById(Integer id) {
        return humanDao.getHumanById(id);
    }

    @Override
    @Transactional(readOnly = false)
    public void addHuman(Human human) {
        humanDao.addHuman(human);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteHuman(Human human) {
        humanDao.deleteHuman(human.getId());
    }

    @Override
    @Transactional(readOnly = false)
    public void updateHuman(Human human) {
        humanDao.updateHuman(human);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Human> getAll() {
        return humanDao.getAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Human getByUrl(String url) {
        return humanDao.getByUrl(url);
    }
}
