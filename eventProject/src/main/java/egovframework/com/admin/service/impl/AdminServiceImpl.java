package egovframework.com.admin.service.impl;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.com.admin.service.AdminService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("AdminService")
public class AdminServiceImpl extends EgovAbstractServiceImpl implements AdminService{
	@Resource(name="AdminDAO")
	private AdminDAO adminDAO;
	
	@Override
	public int insertAdmin(HashMap<String, Object> paramMap) {
		
		
		
		return adminDAO.insertAdmin(paramMap);
		
	}
	
	
	
	
}
