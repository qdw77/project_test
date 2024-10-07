package egovframework.com.admin.service.impl;

import java.util.HashMap;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository("AdminDAO")
public class AdminDAO extends EgovAbstractMapper{

	public int insertAdmin(HashMap<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return insert("insertAdmin", paramMap);
	}
	
	public int selectIdChk(HashMap<String, Object> paramMap) {
		return selectOne("selectIdChk", paramMap);
	}
	
	public HashMap<String, Object> selectLoginInfo(HashMap<String, Object> paramMap){
		return selectOne("selectLoginInfo", paramMap);
	}
	
	public int updateAdmin(HashMap<String, Object> paramMap) {
		return update("updateAdmin", paramMap);
	}

	public int selectAdminCertification(HashMap<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return selectOne("selectAdminCertification", paramMap);
	}
	
	public int selectAdminCertificationChk(HashMap<String, Object> paramMap) {
		return selectOne("selectMemberCertificationChk", paramMap);
	}
	
	

}
