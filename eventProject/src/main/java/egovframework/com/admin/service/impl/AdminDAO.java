package egovframework.com.admin.service.impl;

import java.util.HashMap;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository("AdminDAO")
public class AdminDAO extends EgovAbstractMapper{

	public HashMap<String, Object> selectAdminLoginInfo(HashMap<String, Object> paramMap){
		return selectOne("selectAdminLoginInfo", paramMap);
	}
	
	public int insertEvent(HashMap<String, Object> paramMap) {
		return insert("insertEvent", paramMap);
	}
	
	public int updateEvent(HashMap<String, Object> paramMap) {
		return update("updateEvent", paramMap);
	}
	
	public int getFileGroupMaxSeq() {
		return selectOne("getFileGroupMaxSeq");
	}
	
	public int getFileGroupSeq(HashMap<String, Object> paramMap) {
		return selectOne("getFileGroupSeq", paramMap);
	}
	
	public int insertFileAttr(HashMap<String, Object> paramMap) {
		return insert("insertFileAttr", paramMap);
	}
	
	public List<HashMap<String, Object>> selectAdminEventList(HashMap<String, Object> paramMap){
		return selectList("selectAdminEventList", paramMap);
	}
	
	public int selectAdminEventCnt(HashMap<String, Object> paramMap) {
		return selectOne("selectAdminEventCnt", paramMap);
	}
	
	public HashMap<String, Object> getAdminEventInfo(HashMap<String, Object> paramMap){
		return selectOne("selectAdminEventInfo", paramMap);
	}
	
	public List<HashMap<String, Object>> selectFileList(HashMap<String, Object> paramMap){
		return selectList("selectFileList", paramMap);
	}
	
	public int deleteFileInfo(HashMap<String, Object> paramMap) {
		return update("deleteFileInfo", paramMap);
	}
	
	public int deleteEventInfo(HashMap<String, Object> paramMap) {
		return update("deleteEventInfo", paramMap);
	}
	
	public List<HashMap<String, Object>> selectEventApplyList(HashMap<String, Object> paramMap){
		return selectList("selectEventApplyList", paramMap);
	}
	
	public int setEventJoinCofirm(HashMap<String, Object> paramMap) {
		return update("setEventJoinCofirm", paramMap);
	}

	
	

}
