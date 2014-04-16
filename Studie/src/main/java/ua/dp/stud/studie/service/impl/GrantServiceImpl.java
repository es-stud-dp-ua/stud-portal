package ua.dp.stud.studie.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.dp.stud.studie.dao.GrantDao;
import ua.dp.stud.studie.model.Grant;
import ua.dp.stud.studie.service.GrantService;

import java.util.Collection;

/**
 * @author Lysenko Nikolai
 */

@Service("grantService")
@Transactional
public class GrantServiceImpl implements GrantService {

    @Autowired
    private GrantDao dao;

    public void setDao(GrantDao dao){
        this.dao=dao ;
    }

	@Override
	@Transactional(readOnly = true)
	public Grant getGrantById(Integer id) {
		return dao.getGrantById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<Grant> getAllGrants() {
		return dao.getAllGrants();
	}

	@Override
	@Transactional(readOnly = false)
	public void addGrant(Grant grant) {
		dao.addGrant(grant);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteGrant(Integer id) {
		dao.deleteGrant(id);
	}

	@Override
	@Transactional(readOnly = false)
	public void updateGrant(Grant grant) {
		dao.updateGrant(grant);
	}

    @Override
    public Boolean isDuplicateTopic(String name, Integer id){
        return dao.isDuplicateTopic(name,id);
    }

}
