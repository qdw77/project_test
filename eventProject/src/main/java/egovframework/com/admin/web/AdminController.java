package egovframework.com.admin.web;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import egovframework.com.admin.service.AdminService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;


@Controller
public class AdminController {
	
	@Resource(name="AdminService")
	private AdminService adminService;
	
	//login 화면
	@RequestMapping("/login.do")
	public String login() {
		//jsp위치 admin(파일위치)/adminLogin(jsp이름)
		return "admin/adminLogin";
	}
	
	@RequestMapping("/admin/loginAction.do")
	public ModelAndView loginAction(HttpSession session, @RequestParam HashMap<String, Object> paramMap) {
		ModelAndView mv = new ModelAndView();
		
		HashMap<String, Object> loginInfo = null;
		loginInfo = adminService.selectLoginInfo(paramMap);
		if(loginInfo != null) {
			session.setAttribute("loginInfo", loginInfo);
			mv.addObject("resultChk", true);
		}else {
			mv.addObject("resultChk", false);
		}
		
		mv.setViewName("jsonView");
		return mv;
	}
	
	@RequestMapping("/join.do")
	public String join() {
		return "/";
	}
	
	@RequestMapping("/logout.do")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("loginInfo", null);
		session.removeAttribute("loginInfo");
		session.invalidate();
		return "redirect:/";
	}

	@RequestMapping("/admin/idChk.do")
	public ModelAndView idChk(@RequestParam HashMap<String, Object> paramMap) {
		ModelAndView mv = new ModelAndView();
		
		int idChk = 0;
		idChk= adminService.selectIdChk(paramMap);
		mv.addObject("idChk", idChk);
		mv.setViewName("jsonView");
		return mv;
	}
	
	@RequestMapping("/certification.do")
	public ModelAndView certification(@RequestParam HashMap<String, Object> paramMap) {
		ModelAndView mv = new ModelAndView();
		
		int adminSeq = 0;
		
		adminSeq = adminService.selectAdminCertification(paramMap);
		System.out.println(adminSeq);
		
		mv.addObject("adminSeq", adminSeq);
		mv.setViewName("jsonView");
		return mv;
	}
	
	
	@RequestMapping("/admin/insertAdmin.do")
	public ModelAndView insertAdmin(@RequestParam HashMap<String, Object> paramMap){
		ModelAndView mv = new ModelAndView();
		int resultChk = 0;
		resultChk = adminService.insertAdmin(paramMap);
		
		
		mv.addObject("resultChk", resultChk);
		mv.setViewName("jsonView");
		return mv;
	}
	
	
	
	@RequestMapping("/admin/updateAdmin.do")
	public ModelAndView updateAdmin(@RequestParam HashMap<String, Object> paramMap) {
		ModelAndView mv = new ModelAndView();
		
		
		
		return mv;
	}
	
	

}
