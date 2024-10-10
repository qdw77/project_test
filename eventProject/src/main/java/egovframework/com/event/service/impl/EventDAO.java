package egovframework.com.event.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository("EventDAO")
public class EventDAO extends EgovAbstractMapper{

	public List<HashMap<String, Object>> selectEventList(HashMap<String, Object> paramMap){
		return selectList("selectEventList", paramMap);
	}
	
	public int selectEventListCnt(HashMap<String, Object> paramMap) {
		return selectOne("selectEventListCnt", paramMap);
	}
	
	public int selectEventApplyChk(HashMap<String, Object> paramMap) {
		return selectOne("selectEventApplyChk", paramMap);
	}
	
	public int insertEventApply(HashMap<String, Object> paramMap) {
		return insert("insertEventApply", paramMap);
	}
	
	public HashMap<String, Object> selectEventInfo(HashMap<String, Object> paramMap){
		return selectOne("selectEventInfo", paramMap);
	}
	
}