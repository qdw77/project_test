package egovframework.com.event.web;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import egovframework.com.admin.service.AdminService;
import egovframework.com.event.service.EventService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
public class EventController {
	
	@Resource(name="EventService")
	private EventService eventService;
	
	@Resource(name="AdminService")
	private AdminService adminService;
	
	@RequestMapping("/event/eventList.do")
	public String eventList() {
		return "/event/eventList";
	}
	
	@RequestMapping("/event/getEventInfoList.do")
	public ModelAndView getEventInfoList(@RequestParam HashMap<String, Object> paramMap) {
		ModelAndView mv = new ModelAndView();
		
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(Integer.parseInt(paramMap.get("pageIndex").toString()));
		paginationInfo.setRecordCountPerPage(10);
		paginationInfo.setPageSize(10);
		
		paramMap.put("firstIndex", paginationInfo.getFirstRecordIndex());
		paramMap.put("lastrIndex", paginationInfo.getLastRecordIndex());
		paramMap.put("recordCountPerPage", paginationInfo.getRecordCountPerPage());
		
		List<HashMap<String, Object>> list = eventService.getEventInfoList(paramMap);
		int totCnt = eventService.getEventInfoListCnt(paramMap);
		paginationInfo.setTotalRecordCount(totCnt);
		return mv;
	}


}
