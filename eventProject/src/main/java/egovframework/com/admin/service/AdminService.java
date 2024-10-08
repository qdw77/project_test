package egovframework.com.admin.service;

import java.util.HashMap;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;


public interface AdminService {


	public HashMap<String, Object> selectAdminLoginInfo(HashMap<String, Object> paramMap);

	public HashMap<String, Object> getAdminEventInfo(HashMap<String, Object> paramMap);

	public List<HashMap<String, Object>> selectFileList(HashMap<String, Object> paramMap);

	public int selectAdminEventCnt(HashMap<String, Object> paramMap);

	public List<HashMap<String, Object>> selectAdminEventList(HashMap<String, Object> paramMap);

	public int saveEvent(HashMap<String, Object> paramMap, List<MultipartFile> multipartFile);

	public int setEventJoinCofirm(HashMap<String, Object> paramMap);

	public List<HashMap<String, Object>> selectEventApplyList(HashMap<String, Object> paramMap);

	public int deleteEventInfo(HashMap<String, Object> paramMap);
	
	

}
