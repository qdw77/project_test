package egovframework.com.event.web;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
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
	public ModelAndView getEventInfoList(@RequestParam HashMap<String, Object> paramMap){
		ModelAndView mv = new ModelAndView();
		
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(Integer.parseInt(paramMap.get("pageIndex").toString()));
		paginationInfo.setRecordCountPerPage(10);
		paginationInfo.setPageSize(10);
		
		paramMap.put("firstIndex", paginationInfo.getFirstRecordIndex());
		paramMap.put("lastIndex", paginationInfo.getLastRecordIndex());
		paramMap.put("recordCountPerPage", paginationInfo.getRecordCountPerPage());
		
		List<HashMap<String, Object>> list = eventService.selectEventList(paramMap);
		int totCnt = eventService.selectEventListCnt(paramMap);
		
		paginationInfo.setTotalRecordCount(totCnt);
		
		
		mv.addObject("totCnt", totCnt);
		mv.addObject("list", list);
		mv.addObject("paginationInfo", paginationInfo);
		
		mv.setViewName("jsonView");
		return mv;
	}
	
	@RequestMapping("/event/getApply.do")
	public String getApply(@RequestParam HashMap<String, Object> paramMap, ModelMap model) {
		model.addAttribute("eventSeq", paramMap.get("eventSeq"));
		return "/event/eventApply";
	}
	
	@RequestMapping("/event/getEventInfo.do")
	public String getEventInfo(@RequestParam HashMap<String, Object> paramMap, ModelMap model) {
		HashMap<String, Object> eventInfo = eventService.getEventInfo(paramMap);
		model.addAttribute("eventInfo", eventInfo);
		return "/event/eventDetail";
	}
	
	@RequestMapping("/event/apply.do")
	public ModelAndView apply(@RequestParam HashMap<String, Object> paramMap) {
		ModelAndView mv = new ModelAndView();
		
		int resultChk = 0;
		
		resultChk = eventService.insertEventApply(paramMap);
		
		mv.addObject("resultChk", resultChk);
		mv.setViewName("jsonView");
		return mv;
	}
	

}
