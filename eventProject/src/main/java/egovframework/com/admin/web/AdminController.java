package egovframework.com.admin.web;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.List;


import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import egovframework.com.admin.service.AdminService;
import egovframework.com.util.SHA256;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;


@Controller
public class AdminController {
	 SHA256 sha256 = new SHA256();
	
	@Resource(name="AdminService")
	private AdminService adminService;
	
	//login 화면
	@RequestMapping("/admin.do")
	public String admin() {
		//jsp위치 admin(파일위치)/adminLogin(jsp이름)
		return "admin/adminLogin";
	}
	
	@RequestMapping("/admin/loginAction.do")
	public String loginAction(HttpSession session, @RequestParam HashMap<String, Object> paramMap) {
		ModelAndView mv = new ModelAndView();
		// 입력받은 패스워드
		String pwd = paramMap.get("pwd").toString();
		// 암호화된 패스워드
		String encryptPwd = null;
		try {
			encryptPwd = sha256.encrypt(pwd).toString();
			paramMap.replace("pwd", encryptPwd);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HashMap<String, Object> loginInfo = null;
		loginInfo = adminService.selectAdminLoginInfo(paramMap);
		if(loginInfo != null) {
			session.setAttribute("loginInfo", loginInfo);
			return "redirect:/admin/eventMngList.do";
		}else {
			return "/admin/adminLogin";
		}
	}
	
	@RequestMapping("/admin/eventMngList.do")
	public String eventMngList() {
		return "eventMng/eventMngList";
	}
	
	@RequestMapping("/admin/eventRegist.do")
	public String evetnRegist(@RequestParam HashMap<String, Object> paramMap, ModelMap model, HttpSession session) {
		HashMap<String, Object> loginInfo = null;
		loginInfo = (HashMap<String, Object>) session.getAttribute("loginInfo");
		if(loginInfo != null) {
			model.addAttribute("loginInfo", loginInfo);
			model.addAttribute("paramInfo", paramMap);
			return "eventMng/eventMngRegist";
		}else {
			return "redirect:/admin.do";
		}
	}
	
	@RequestMapping("/admin/saveEvent.do")
	public ModelAndView saveEvent(@RequestParam HashMap<String, Object> paramMap, @RequestParam(name="fileList") List<MultipartFile> multipartFile
			, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		
		int resultChk = 0;
		
		HashMap<String, Object> sessionInfo = (HashMap<String, Object>) session.getAttribute("loginInfo");
		paramMap.put("memberId", sessionInfo.get("id").toString());
		
		resultChk = adminService.saveEvent(paramMap, multipartFile);
		mv.addObject("resultChk", resultChk);
		mv.setViewName("jsonView");
		return mv;
	}
	
	@RequestMapping("/admin/getEventList.do")
	public ModelAndView getEventList(@RequestParam HashMap<String, Object> paramMap) {
		ModelAndView mv = new ModelAndView();
		
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(Integer.parseInt(paramMap.get("pageIndex").toString()));
		paginationInfo.setRecordCountPerPage(10);
		paginationInfo.setPageSize(10);
		
		paramMap.put("firstIndex", paginationInfo.getFirstRecordIndex());
		paramMap.put("lastIndex", paginationInfo.getLastRecordIndex());
		paramMap.put("recordCountPerPage", paginationInfo.getRecordCountPerPage());
		
		List<HashMap<String, Object>> list = adminService.selectAdminEventList(paramMap);
		int totCnt = adminService.selectAdminEventCnt(paramMap);
		
		paginationInfo.setTotalRecordCount(totCnt);
		
		
		mv.addObject("totCnt", totCnt);
		mv.addObject("list", list);
		mv.addObject("paginationInfo", paginationInfo);
		mv.setViewName("jsonView");
		return mv;
	}
	
	@RequestMapping("/admin/getEventInfo.do")
	public String getEventInfo(@RequestParam HashMap<String, Object> paramMap, ModelMap model, HttpSession session) {
		HashMap<String, Object> loginInfo = null;
		loginInfo = (HashMap<String, Object>) session.getAttribute("loginInfo");
		if(loginInfo != null) {
			model.addAttribute("loginInfo", loginInfo);
			HashMap<String, Object> eventInfo = adminService.getAdminEventInfo(paramMap);
			model.addAttribute("eventInfo", eventInfo);
			return "eventMng/eventMngDetail";
		}else {
			return "redirect:/admin.do";
		}
	}
	
	@RequestMapping("/admin/getEventInfoDetail.do")
	public ModelAndView getEventInfoDetail(@RequestParam HashMap<String, Object> paramMap, ModelMap model, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		
		HashMap<String, Object> eventInfo = adminService.getAdminEventInfo(paramMap);
		
		mv.addObject("eventInfo", eventInfo);
		mv.setViewName("jsonView");
		return mv;
	}
	
	@RequestMapping("/admin/getFileList.do")
	public ModelAndView getFileList(@RequestParam HashMap<String, Object> paramMap) {
		ModelAndView mv = new ModelAndView();
		
		List<HashMap<String, Object>> fileList = adminService.selectFileList(paramMap);
		
		mv.addObject("fileList", fileList);
		mv.setViewName("jsonView");
		return mv;
	}
	
	@RequestMapping("/admin/deleteEventInfo.do")
	public ModelAndView deleteEventInfo(@RequestParam HashMap<String, Object> paramMap, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		
		int resultChk = 0;
		HashMap<String, Object> sessionInfo = (HashMap<String, Object>) session.getAttribute("loginInfo");
		paramMap.put("memberId", sessionInfo.get("id").toString());
		resultChk = adminService.deleteEventInfo(paramMap);
		mv.addObject("resultChk", resultChk);
		mv.setViewName("jsonView");
		return mv;
	}
	
	@RequestMapping("/admin/getApplyList.do")
	public String getApplyList(@RequestParam HashMap<String, Object> paramMap, ModelMap model, HttpSession session) {
		HashMap<String, Object> loginInfo = null;
		loginInfo = (HashMap<String, Object>) session.getAttribute("loginInfo");
		if(loginInfo != null) {
			model.addAttribute("loginInfo", loginInfo);
			model.addAttribute("paramMap", paramMap);
			
			return "eventMng/eventMngJoinList";
		}else {
			return "redirect:/admin.do";
		}
	}
	
	@RequestMapping("/admin/getEventApplyList.do")
	public ModelAndView getEventApplyList(@RequestParam HashMap<String, Object> paramMap) {
		ModelAndView mv = new ModelAndView();
		
		List<HashMap<String, Object>> list = adminService.selectEventApplyList(paramMap);
		mv.addObject("list", list);
		mv.setViewName("jsonView");
		return mv;
	}
	
	@RequestMapping("/admin/setEventApplyConfirm.do")
	public ModelAndView setEventApplyConfirm(@RequestParam HashMap<String, Object> paramMap, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		
		HashMap<String, Object> sessionInfo = (HashMap<String, Object>) session.getAttribute("loginInfo");
		paramMap.put("memberId", sessionInfo.get("id").toString());
		
		int resultChk = 0;
		
		resultChk = adminService.setEventJoinCofirm(paramMap);
		
		mv.addObject("resultChk", resultChk);
		mv.setViewName("jsonView");
		return mv;
	}
	
	
	
	

}
