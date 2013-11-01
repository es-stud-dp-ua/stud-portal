package ua.dp.stud.studie.service;

import ua.dp.stud.studie.model.Council;

import java.util.List;

/**
 * @author Lysenko Nikolai
 */
public interface CouncilService {

		void addCouncil(Council council);

	    void deleteCouncil(Integer id);

	    void updateCouncil(Council council);

	    List<Council> getAll();

		Council getCouncilById(Integer id);
}
