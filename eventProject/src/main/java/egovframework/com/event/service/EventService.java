package egovframework.com.event.service;

import java.util.HashMap;

import java.util.List;

public interface EventService {

	public List<HashMap<String, Object>> getEventInfoList(HashMap<String, Object> paramMap);
	public int getEventInfoListCnt(HashMap<String, Object> paramMap);
}
