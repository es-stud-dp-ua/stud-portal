package ua.dp.stud.aboutTeam.service;

import ua.dp.stud.aboutTeam.model.Human;

import java.util.Collection;

/**
 * @author Vladislav Pikus
 */
public interface HumanService {
    Human getHumanById(Integer id);

    void addHuman(Human human);

    void deleteHuman(Human human);

    void updateHuman(Human human);

    Collection<Human> getAll();

    Human getByUrl(String url);
}
