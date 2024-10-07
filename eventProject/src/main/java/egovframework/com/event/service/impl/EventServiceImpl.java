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
	public List<HashMap<String, Object>> getEventInfoList(HashMap<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return null;
	}
}
