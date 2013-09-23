package ua.dp.stud.aboutTeam.dao;

import ua.dp.stud.aboutTeam.model.Human;

import java.util.Collection;

/**
 * @author Vladislav Pikus
 */
public interface HumanDao {
    Human getHumanById(Integer id);

    void addHuman(Human human);

    void deleteHuman(Integer id);

    void updateHuman(Human human);

    Collection<Human> getAll();

    Human getByUrl(String url);
}
