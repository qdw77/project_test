package egovframework.com.admin.service;

import java.util.HashMap;

import java.util.List;


public interface AdminService {
	
	
	public int insertAdmin(HashMap<String, Object> paramMap);

	public int selectIdChk(HashMap<String, Object> paramMap);
	
	public HashMap<String, Object> selectLoginInfo(HashMap<String, Object> paramMap);

	public int selectAdminCertification(HashMap<String, Object> paramMap);
	
	

}
