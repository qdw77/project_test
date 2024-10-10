package egovframework.com.event.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.com.event.service.EventService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("EventService")
public class EventServiceImpl extends EgovAbstractServiceImpl implements EventService{

	@Resource(name="EventDAO")
	private EventDAO eventDAO;
	
	@Override
	public List<HashMap<String, Object>> selectEventList(HashMap<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return eventDAO.selectEventList(paramMap);
	}

	@Override
	public int selectEventListCnt(HashMap<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return eventDAO.selectEventListCnt(paramMap);
	}

	@Override
	public int insertEventApply(HashMap<String, Object> paramMap) {
		// TODO Auto-generated method stub
		
		int resultChk = 0;
		int chk = 0;
		int eventSeq = Integer.parseInt(paramMap.get("eventSeq").toString());
		paramMap.replace("eventSeq", eventSeq);
		chk = eventDAO.selectEventApplyChk(paramMap);
		if(chk > 0) {
			resultChk = 2;
		}else {
			chk = eventDAO.insertEventApply(paramMap);
			if(chk > 0) {
				resultChk = 1;
			}else {
				resultChk = 0;
			}
		}
		return resultChk;
	}

	@Override
	public HashMap<String, Object> getEventInfo(HashMap<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return eventDAO.selectEventInfo(paramMap);
	}
}
