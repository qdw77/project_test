package egovframework.com.admin.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Controller;
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
	
	@RequestMapping("/admin/getFileDown.do")
	public void downloadFile(@RequestParam HashMap<String, Object> paramMap, HttpServletResponse response) throws Exception {

        try {
            // fileName 파라미터로 파일명을 가져온다.
            String fileName = paramMap.get("fileName").toString();

            // 파일이 실제 업로드 되어있는(파일이 존재하는) 경로를 지정한다.
            String filePath = paramMap.get("filePath").toString();
            String originalFileName = paramMap.get("originalFileName").toString();
            
            // 경로와 파일명으로 파일 객체를 생성한다.
            File dFile = new File(filePath, fileName);

            // 파일 길이를 가져온다.
            int fSize = (int) dFile.length();

            // 파일이 존재
            if (fSize > 0) {

                // 파일명을 URLEncoder 하여 attachment, Content-Disposition Header로 설정
                String encodedFilename = "attachment; filename*=" + "UTF-8" + "''" + URLEncoder.encode(originalFileName, "UTF-8").replaceAll("\\+", "\\ ");

                // ContentType 설정
                response.setContentType("application/octet-stream; charset=utf-8");

                // Header 설정
                response.setHeader("Content-Disposition", encodedFilename);

                // ContentLength 설정
                response.setContentLengthLong(fSize);

                BufferedInputStream in = null;
                BufferedOutputStream out = null;

                /* BufferedInputStream
                 * 
                java.io의 가장 기본 파일 입출력 클래스
                입력 스트림(통로)을 생성해줌
                사용법은 간단하지만, 버퍼를 사용하지 않기 때문에 느림
                속도 문제를 해결하기 위해 버퍼를 사용하는 다른 클래스와 같이 쓰는 경우가 많음
                */
                in = new BufferedInputStream(new FileInputStream(dFile));

                /* BufferedOutputStream
                 * 
                java.io의 가장 기본이 되는 파일 입출력 클래스
                출력 스트림(통로)을 생성해줌
                사용법은 간단하지만, 버퍼를 사용하지 않기 때문에 느림
                속도 문제를 해결하기 위해 버퍼를 사용하는 다른 클래스와 같이 쓰는 경우가 많음
                */
                out = new BufferedOutputStream(response.getOutputStream());

                try {
                    byte[] buffer = new byte[4096];
                    int bytesRead = 0;

                    /*
                    모두 현재 파일 포인터 위치를 기준으로 함 (파일 포인터 앞의 내용은 없는 것처럼 작동)
                    int read() : 1byte씩 내용을 읽어 정수로 반환
                    int read(byte[] b) : 파일 내용을 한번에 모두 읽어서 배열에 저장
                    int read(byte[] b. int off, int len) : 'len'길이만큼만 읽어서 배열의 'off'번째 위치부터 저장
                    */
                    while ((bytesRead = in .read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }

                    // 버퍼에 남은 내용이 있다면, 모두 파일에 출력
                    out.flush();
                } finally {
                    /*
                    현재 열려 in,out 스트림을 닫음
                    메모리 누수를 방지하고 다른 곳에서 리소스 사용이 가능하게 만듬
                    */
                    in.close();
                    out.close();
                }
            } else {
                throw new FileNotFoundException("파일이 없습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
	
	/*
	 * @RequestMapping("/admin/reRegisterEvent.do") public String
	 * reRegisterEvent(@RequestParam("eventSeq") String
	 * eventSeq, @RequestParam("memberId") String memberId) { HashMap<String,
	 * Object> paramMap = new HashMap<>(); paramMap.put("eventSeq", eventSeq);
	 * paramMap.put("memberId", memberId);
	 * 
	 * int result = adminService.reRegisterEvent(paramMap); if (result > 0) { return
	 * "redirect:/admin/eventMngList.do"; // 성공 시 목록으로 리디렉션 } else { // 실패 시 적절한 에러
	 * 처리 return "redirect:/admin/errorPage.do"; // 에러 페이지로 리디렉션 } }
	 */
	
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
	
	@RequestMapping("/admin/excelDown.do")
	public void excelDown(@RequestParam HashMap<String, Object> paramMap, HttpServletResponse response) throws Exception {
		Workbook workbook = new SXSSFWorkbook();
		Sheet sheet = workbook.createSheet("접수자 명단");
       		
		
		int rowIndex = 0;
		Row headerRow = sheet.createRow(rowIndex++);
		
//		headerRow 고정값
		
		Cell headerCell0 = headerRow.createCell(0);
		headerCell0.setCellValue("이름");
		
		Cell headerCell1 = headerRow.createCell(1);
		headerCell1.setCellValue("성별");

		Cell headerCell2 = headerRow.createCell(2);
		headerCell2.setCellValue("연락처");

		Cell headerCell3 = headerRow.createCell(3);
		headerCell3.setCellValue("이메일");
		
		Cell headerCell4 = headerRow.createCell(4);
		headerCell4.setCellValue("주소");
		
		Cell headerCell5 = headerRow.createCell(5);
		headerCell5.setCellValue("당첨여부");
		
		List<HashMap<String, Object>> excelList= adminService.selectEventApplyList(paramMap);
		for(int i=0; i<excelList.size(); i++) {
			
			Row bodyRow = sheet.createRow(rowIndex++);
//			bodyRow 변경 가능
			Cell bodyCell1 = bodyRow.createCell(0);
			bodyCell1.setCellValue(excelList.get(i).get("joinName").toString());
			Cell bodyCell2 = bodyRow.createCell(1);
			bodyCell2.setCellValue(excelList.get(i).get("joinSex").toString());
			Cell bodyCell3 = bodyRow.createCell(2);
			bodyCell3.setCellValue(excelList.get(i).get("joinPhone").toString());
			Cell bodyCell4 = bodyRow.createCell(3);
			bodyCell4.setCellValue(excelList.get(i).get("joinEmail").toString());
			Cell bodyCell5 = bodyRow.createCell(4);
			bodyCell5.setCellValue(excelList.get(i).get("joinAddr").toString());
			Cell bodyCell6 = bodyRow.createCell(5);
			bodyCell6.setCellValue(excelList.get(i).get("joinState").toString());
		}
		
		 // 응답 설정	
		response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attachment;filename=applyList.xls");
        
        workbook.write(response.getOutputStream());
        workbook.close();;
		
	}
	
	
	

}
