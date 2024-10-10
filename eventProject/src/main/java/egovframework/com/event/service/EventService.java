package egovframework.com.event.service;

import java.util.HashMap;

import java.util.List;

public interface EventService {

	List<HashMap<String, Object>> selectEventList(HashMap<String, Object> paramMap);
	
	int selectEventListCnt(HashMap<String, Object> paramMap);
	
	int insertEventApply(HashMap<String, Object> paramMap);
	
	HashMap<String, Object> getEventInfo(HashMap<String, Object> paramMap);
}
