package egovframework.com.admin.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import java.util.List;
import javax.annotation.Resource;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import egovframework.com.admin.service.AdminService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import egovframework.com.admin.service.AdminService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("AdminService")
public class AdminServiceImpl extends EgovAbstractServiceImpl implements AdminService{
	
	@Resource(name="AdminDAO")
	private AdminDAO adminDAO;

	@Override
	public HashMap<String, Object> selectAdminLoginInfo(HashMap<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return adminDAO.selectAdminLoginInfo(paramMap);
	}

	@Override
	public int saveEvent(HashMap<String, Object> paramMap, List<MultipartFile> multipartFile) {
		// TODO Auto-generated method stub
		int resultChk = 0;
		
		String flag = paramMap.get("statusFlag").toString();
		int fileGroupSeq = 0;
		if("I".equals(flag)) {
			resultChk = adminDAO.insertEvent(paramMap);
			fileGroupSeq = adminDAO.getFileGroupMaxSeq();
		}else if("U".equals(flag)) {
			resultChk = adminDAO.updateEvent(paramMap);
			fileGroupSeq = adminDAO.getFileGroupSeq(paramMap);
		}
		paramMap.put("fileGroupSeq", fileGroupSeq);
		//파일 업로드
		int chk = 0;
		String filePath = "/ictsaeil/event/";
		String deleteFiles = (String)paramMap.get("deleteFiles");
		if(deleteFiles.length() >0) {
			// 파일 테이블 데이터 삭제
			adminDAO.deleteFileInfo(paramMap);
		}
		
		if(multipartFile.size() > 0 && !multipartFile.get(0).getOriginalFilename().equals("")) {
			int index = 0;
			for(MultipartFile file : multipartFile) {
				HashMap<String, Object> uploadFile = new HashMap<String, Object>();
				SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHms");
				Calendar cal = Calendar.getInstance();
				String today = date.format(cal.getTime());
				try {
					File fileFolder = new File(filePath);
					if(!fileFolder.exists()) {
						if(fileFolder.mkdirs()) {
							System.out.println("[file.mkdirs] : Success");
						}
					}
					String fileExt = FilenameUtils.getExtension(file.getOriginalFilename());
					double fileSize = file.getSize();
					File saveFile = new File(filePath, "file_"+today+"_"+index+"."+fileExt);
					uploadFile.put("fileGroupSeq", fileGroupSeq);
					uploadFile.put("originalFileName", file.getOriginalFilename());
					uploadFile.put("saveFileName", "file_"+today+"_"+index+"."+fileExt); 
					uploadFile.put("saveFilePath", filePath);
					uploadFile.put("fileSize", fileSize);
					uploadFile.put("fileExt", fileExt);
					uploadFile.put("memberId", paramMap.get("memberId"));
					adminDAO.insertFileAttr(uploadFile);
					
					file.transferTo(saveFile);
					index++;
				}catch(Exception e) {
					e.printStackTrace();
					return 0;
				}
			}
		}
		
		return resultChk;
	}

	@Override
	public List<HashMap<String, Object>> selectAdminEventList(HashMap<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return adminDAO.selectAdminEventList(paramMap);
	}

	@Override
	public int selectAdminEventCnt(HashMap<String, Object> paramMap) {
		// TODO Auto-generated method stubs
		return adminDAO.selectAdminEventCnt(paramMap);
	}

	@Override
	public HashMap<String, Object> getAdminEventInfo(HashMap<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return adminDAO.getAdminEventInfo(paramMap);
	}

	@Override
	public List<HashMap<String, Object>> selectFileList(HashMap<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return adminDAO.selectFileList(paramMap);
	}

	@Override
	public int deleteEventInfo(HashMap<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return adminDAO.deleteEventInfo(paramMap);
	}
	
	/*
	 * @Override public int reRegisterEvent(HashMap<String, Object> paramMap) {
	 * return adminDAO.reRegisterEventInfo(paramMap); }
	 */


	@Override
	public List<HashMap<String, Object>> selectEventApplyList(HashMap<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return adminDAO.selectEventApplyList(paramMap);
	}

	@Override
	public int setEventJoinCofirm(HashMap<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return adminDAO.setEventJoinCofirm(paramMap);
	}
	
	
}
