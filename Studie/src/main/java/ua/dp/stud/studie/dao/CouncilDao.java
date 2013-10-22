package ua.dp.stud.studie.dao;

import ua.dp.stud.studie.model.Council;

import java.util.Collection;
import java.util.List;

/**
 * @author Lysenko Nikolai
 */
public interface CouncilDao {


    void addCouncil(Council council);

    void deleteCouncil(Integer id);

    void updateCouncil(Council council);

    List<Council> getAll();

	Council getCouncilById(Integer id);
	
    }
