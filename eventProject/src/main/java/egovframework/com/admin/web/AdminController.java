package egovframework.com.admin.web;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import egovframework.com.admin.service.AdminService;


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
	
	@RequestMapping("/join.do")
	public String join() {
		return "admin/adminJoin";
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
	
	


}
