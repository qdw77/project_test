package egovframework.com.admin.service;

import java.util.HashMap;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;


public interface AdminService {


	HashMap<String, Object> selectAdminLoginInfo(HashMap<String, Object> paramMap);
	
	int saveEvent(HashMap<String, Object> paramMap, List<MultipartFile> multipartFile);
	
	List<HashMap<String, Object>> selectAdminEventList(HashMap<String, Object> paramMap);
	
	int selectAdminEventCnt(HashMap<String, Object> paramMap);
	
	HashMap<String, Object> getAdminEventInfo(HashMap<String, Object> paramMap);
	
	List<HashMap<String, Object>> selectFileList(HashMap<String, Object> paramMap);
	
	int deleteEventInfo(HashMap<String, Object> paramMap);
	
	List<HashMap<String, Object>> selectEventApplyList(HashMap<String, Object> paramMap);
	
	int setEventJoinCofirm(HashMap<String, Object> paramMap);

	/* int reRegisterEvent(HashMap<String, Object> paramMap); */

	
	

}
