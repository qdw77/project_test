<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/css/egovframework/main.css">
<link rel="stylesheet" href="/css/fontawesome/css/all.min.css">
<script src="https://code.jquery.com/jquery-3.7.1.js"
	integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4="
	crossorigin="anonymous"></script>
<title>Insert title here</title>
<!-- admin 등록 -->
<script type="text/javascript">
/* 파일 업로드 관련 변수 */
var fileCnt = 0;
var totalCnt = 20;
var fileNum = 0;
var content_files = new Array(); /* 실제 업로드 파일 */
var deleteFiles = new Array(); /* 삭제 업로드 파일 */
/* 파일 업로드 관련 변수 */

	$(document).ready(function(){
		var statusFlag = "${paramInfo.statusFlag}";
		if(statusFlag == 'U'){
			fn_detail("${paramInfo.eventSeq}");
		}
		
		$("#btn_save").on("click", function(){
			fn_save();
		});
		
		$("#btn_list").on("click", function(){
			location.href="/admin/eventMngList.do";
		});
		
		$("#fileUpload").on("change", function(e){
			var files = e.target.files;
			// 파일 배열 담기
			var filesArr = Array.prototype.slice.call(files);
			//파일 개수 확인 및 제한
			if(fileCnt + filesArr.length > totalCnt){
				alert("파일은 최대 "+totCnt+"개까지 업로드 할 수 있습니다.");
				return;
			}else{
				fileCnt = fileCnt+ filesArr.length;
			}
			
			// 각각의 파일 배열 담기 및 기타
			filesArr.forEach(function (f){
				var reader = new FileReader();
				reader.onload = function (e){
					content_files.push(f);
					$("#boardFileList").append(
								'<div id="file'+fileNum+'" style="float:left; width:100%; padding-left:100px;">'
								+'<font style="font-size:12px">' + f.name + '</font>'
								+'<a href="javascript:fileDelete(\'file'+fileNum+'\')"> X </a>'
								+'</div>'
					);
					fileNum++;
				};
				reader.readAsDataURL(f);
			});
			//초기화한다.
			$("#fileUpload").val("");
		});
	});
	
	function fileDelete(fileNum){
		var no = fileNum.replace(/[^0-9]/g, "");
		content_files[no].is_delete = true;
		$("#"+fileNum).remove();
		fileCnt--;
	}
	
	function fn_extFileDelete(fileNum){
		deleteFiles.push(fileNum);
		$("#extFile_"+fileNum).remove();
	}
	
	function fn_save(){
		var formData = new FormData($("#eventFrm")[0]);
		
		for(var x=0; x<content_files.length; x++){
			//삭제 안한 것만 담아준다.
			if(!content_files[x].is_delete){
				formData.append("fileList", content_files[x]); 
			}
		}
		formData.append("deleteFiles", deleteFiles);
		$.ajax({
		    url: '/admin/saveEvent.do',
		    method: 'post',
		    data : formData,
		    enctype : "multipart/form-data",
		    processData : false,
		    contentType : false,
		    dataType : 'json',
		    success: function (data, status, xhr) {
		    	if(data.resultChk > 0){
		    		alert("저장되었습니다.");
		    		location.href="/admin/eventMngList.do";
		    	}else{
		    		alert("저장에 실패하였습니다.");
		    	}
		    },
		    error: function (data, status, err) {
		    	console.log(err);
		    }
		});
	}
	
	function fn_detail(eventSeq){
		$.ajax({
		    url: '/admin/getEventInfoDetail.do',
		    method: 'post',
		    data : {
		    	"eventSeq" : eventSeq
		    },
		    dataType : 'json',
		    success: function (data, status, xhr) {
		    	$("#statusFlag").val("U");
		    	$("#eventSeq").val(data.eventInfo.eventSeq);
		    	$("#fileGroupSeq").val(data.eventInfo.eventFileGroupSeq);
		    	$("#eventTitle").val(data.eventInfo.eventTitle);
		    	$("#postStartDate").val(data.eventInfo.eventPostStartDate);
		    	$("#postStartHour").val(data.eventInfo.eventPostStartHour);
		    	$("#postStartMinute").val(data.eventInfo.eventPostStartMinute);
		    	$("#postEndDate").val(data.eventInfo.eventPostEndDate);
		    	$("#postEndHour").val(data.eventInfo.eventPostEndHour);
		    	$("#postEndMinute").val(data.eventInfo.eventPostEndMinute);
		    	$("#eventStartDate").val(data.eventInfo.eventStartDate);
		    	$("#eventStartHour").val(data.eventInfo.eventStartHour);
		    	$("#eventStartMinute").val(data.eventInfo.eventStartMinute);
		    	$("#eventEndDate").val(data.eventInfo.eventEndDate);
		    	$("#eventEndHour").val(data.eventInfo.eventEndHour);
		    	$("#eventEndMinute").val(data.eventInfo.eventEndMinute);
		    	$("#eventContent").val(data.eventInfo.eventContent);
		    	fn_getFileList(data.eventInfo.eventFileGroupSeq);
		    },
		    error: function (data, status, err) {
		    	console.log(data);
		    }
		});
	}
	
	function fn_getFileList(fileGroupSeq){
		
		$.ajax({
		    url: '/admin/getFileList.do',
		    method: 'post',
		    data : { 
		    	"fileGroupSeq" : fileGroupSeq
		    },
		    dataType : 'json',
		    success: function (data, status, xhr) {
		    	if(data.fileList.length > 0){
		    		for(var i=0; i<data.fileList.length; i++){
				    	$("#boardFileList").append(
								'<div id="extFile_'+data.fileList[i].fileSeq+'" style="float:left; width:100%; padding-left:100px;">'
								+'<font style="font-size:12px">' + data.fileList[i].fileOriginalName + '</font>'
								+'<a href="javascript:fn_extFileDelete(\''+data.fileList[i].fileSeq+'\');"> X </a>'
								+'</div>'
						);
						fileNum++;
		    		}	
		    	}
		    	
		    },
		    error: function (data, status, err) {
		    	console.log(status);
		    }
		});
	}
</script>
</head>
<body>
	<div class="event-form-container">
        <h2 class="event-form-title">이벤트</h2>
        <form class="event-form" method="POST" id="eventFrm" name="eventFrm">
        	<input type="hidden" id="statusFlag" name="statusFlag" value="I"/>
        	<input type="hidden" id="eventSeq" name="eventSeq" />
        	<input type="hidden" id="fileGroupSeq" name="fileGroupSeq"/>
            <div class="event-input-group">
                <label for="eventSub">이벤트명</label>
                <input type="text" id="eventTitle" name="eventTitle" required>
            </div>
            <div class="event-input-group">
                <label for="postStartDate">게시 시작일</label>
                <input type="date" id="postStartDate" name="postStartDate" class="date-input" required>
                <input type="text" id="postStartHour" name="postStartHour" style="width:10px;"/> :
                <input type="text" id="postStartMinute" name="postStartMinute" style="width:10px;"/>
                <label for="postEndDate">게시 종료일</label>
                <input type="date" id="postEndDate" name="postEndDate" class="date-input" required>
                <input type="text" id="postEndHour" name="postEndHour" style="width:10px;"/> :
                <input type="text" id="postEndMinute" name="postEndMinute" style="width:10px;"/>
            </div>
            <div class="event-input-group">
                <label for="eventStartDate">이벤트 시작일</label>
                <input type="date" id="eventStartDate" name="eventStartDate" class="date-input" required>
                <input type="text" id="eventStartHour" name="eventStartHour" style="width:10px;"/> :
                <input type="text" id="eventStartMinute" name="eventStartMinute" style="width:10px;"/>
                <label for="endDate">게시 종료일</label>
                <input type="date" id="eventEndDate" name="eventEndDate" class="date-input" required>
                <input type="text" id="eventEndHour" name="eventEndHour" style="width:10px;"/> :
                <input type="text" id="eventEndMinute" name="eventEndMinute" style="width:10px;"/>
            </div>
            <div class="event-input-group">
			    <label for="fileUpload">파일 업로드</label>
			    <input type="file" class="file-upload" id="fileUpload" name="fileUpload" multiple>
			    
			</div>
			<div class="event-input-group" id="boardFileList" style="flex-direction:column;"></div>
            <div class="event-input-group">
                <label for="eventContent">이벤트 내용</label>
                <textarea id="eventContent" name="eventContent" rows="15" required></textarea>
            </div>
            <div class="rightBtn">
            	<button type="button" id="btn_save" name="btn_save" style="margin-right: 2px;">저장</button>
            	<button type="button" id="btn_list" name="btn_list">목록으로</button>
            </div>
        </form>
    </div>
</body>
</html>