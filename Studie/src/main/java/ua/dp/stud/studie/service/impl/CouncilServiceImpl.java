package ua.dp.stud.studie.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.dp.stud.studie.dao.CouncilDao;
import ua.dp.stud.studie.model.Council;
import ua.dp.stud.studie.model.CouncilMembers;
import ua.dp.stud.studie.service.CouncilService;

import java.util.Collections;
import java.util.List;

/**
 * @author Lysenko Nikolai
 */

@Service("councilService")
@Transactional
public class CouncilServiceImpl implements CouncilService {

    @Autowired
    private CouncilDao dao;

    public void setDao(CouncilDao dao){
        this.dao=dao ;
    }

    @Override
    @Transactional(readOnly = true)
    public Council getCouncilById(Integer id) {
        return dao.getCouncilById(id);
    }

    @Override
    @Transactional(readOnly = false)
    public void addCouncil(Council course) {
         dao.addCouncil(course);
    }

    @Override
    @Transactional(readOnly = false)
     public void deleteCouncil(Integer id) {
        dao.deleteCouncil(id);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateCouncil(Council council) {
        dao.updateCouncil(council);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Council> getAll() {
        return dao.getAll();
    }
    
    //------------------------------------------------------------
    
    @Override
    @Transactional(readOnly = true)
    public CouncilMembers getCouncilMembersById(Integer id) {
        return dao.getCouncilMembersById(id);
    }

    @Override
    @Transactional(readOnly = false)
    public void addCouncilMembers(CouncilMembers councilMembers) {
        dao.addCouncilMembers(councilMembers);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteCouncilMembers(Integer id){
        dao.deleteCouncilMembers(id);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateCouncilMembers(CouncilMembers councilMembers) {
        dao.updateCouncilMembers(councilMembers);
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean isDuplicateUniversity(Integer id, Integer idCouncil){
        return dao.isDuplicateTopic(id,idCouncil);
    }

    @Override
    @Transactional(readOnly = true)
    public  List<CouncilMembers> getAllCouncilMembers() {
        return dao.getAllCouncilMembers();
    }


}
