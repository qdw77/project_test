package egovframework.com.admin.service.impl;

import java.util.HashMap;

import java.util.List;
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
	
	@Override
	public int selectIdChk(HashMap<String,Object> paramMap) {
		return adminDAO.selectIdChk(paramMap);
	}

	@Override
	public HashMap<String, Object> selectLoginInfo(HashMap<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return adminDAO.selectLoginInfo(paramMap);
	}

	@Override
	public int selectAdminCertification(HashMap<String, Object> paramMap) {
		// TODO Auto-generated method stub
		int chk=0;
		int adminSeq =0;
		chk = adminDAO.selectAdminCertificationChk(paramMap);
		if(chk>0) {
			adminSeq = adminDAO.selectAdminCertification(paramMap);
		}
		return adminSeq;
	}
	
	
	
	
}
