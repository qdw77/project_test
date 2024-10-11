<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/css/egovframework/main.css"/>
<link rel="stylesheet" href="/css/fontawesome/css/all.min.css">
<script src="https://code.jquery.com/jquery-3.7.1.js"
	integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4="
	crossorigin="anonymous"></script>
<title>Insert title here</title>
<script type="text/javascript">


	$(document).ready(function(){
		fn_getFileList();
		$("#btn_list").on("click", function(){
			location.href = "/event/eventList.do";
		});
				
		$("#btn_apply").on("click", function(){
			fn_apply();
		});
	});
	
	function fn_getFileList(){
		// /board/getFileList.do
			var fileGroupSeq = "${eventInfo.eventFileGroupSeq}";
		$.ajax({
		    url: '/admin/getFileList.do',
		    method: 'post',
		    data : { 
		    	"fileGroupSeq" : fileGroupSeq
		    },
		    dataType : 'json',
		    success: function (data, status, xhr) {
		    	var innerHtml = '';
		    	for(var i=0; i<data.fileList.length; i++){
		    		innerHtml += '<span>';
		    		innerHtml += '<a href="javascript:fn_down(\''+data.fileList[i].saveFilePath+'\',\''+data.fileList[i].saveFileName+'\', \''+data.fileList[i].originalFileName+'\');">';
			    	innerHtml += data.fileList[i].originalFileName;
			    	innerHtml += '</a></span><br>';	
		    	}
		    	$("#boardFileList").html(innerHtml);
		    },
		    error: function (data, status, err) {
		    	console.log(status);
		    }
		});
	}
	
	function fn_down(filePath, fileName, originalFileName){
		$("#fileName").val(fileName);
		$("#filePath").val(filePath);
		$("#originalFileName").val(originalFileName);
		var frm = $("#fileFrm");
		frm.attr("action", "/admin/getFileDown.do");
		frm.submit();
	}
	
	function fn_apply(){
		var frm = $("#eventFrm");
		frm.attr("action", "/event/getApply.do");
		frm.submit();
	}
	
</script>
<header>
	<div class="container">
        <h1>EVENT</h1>
        <nav>
            <ul>
                <li><a href="/event/eventList.do">Home</a></li>
                <li><a href="javascript:alert('준비중입니다.');">About</a></li>
                <!-- About <접수된 이벤트 -->
                <li><a href="javascript:alert('준비중입니다.');">Services</a></li>
                <!-- service 개인정보 수정 / 로그 아웃  / 개인 페이지  -->
                <li><a href="javascript:alert('준비중입니다.');">Contact</a></li>
                <!-- 회사에 대한 문의/ 오류 상담 문의/ 이벤트 문의 -->
            </ul>
        </nav>
    </div>

</header>
</head>
<body>
	<div class="event-form-container">
        <h2 class="event-form-title">이벤트</h2>
        <form id="fileFrm" name="fileFrm" method="POST">
        	<input type="hidden" id="fileName" name="fileName" />
        	<input type="hidden" id="filePath" name="filePath" />
        	<input type="hidden" id="originalFileName" name="originalFileName" />
        </form>
        <form class="event-form" method="POST" id="eventFrm" name="eventFrm">
        	<input type="hidden" id="eventSeq" name="eventSeq" value="${eventInfo.eventSeq }"/>
        	<input type="hidden" id="statusFlag" name="statusFlag" value=""/>
            <div class="event-input-group">
                <label for="eventSub">이벤트명</label>
                <input type="text" id="eventTitle" name="eventTitle" value="${eventInfo.eventTitle}" readonly>
            </div>
            <div class="event-input-group">
                <label for="postStartDate">게시 시작일</label>
                <input type="date" id="postStartDate" name="postStartDate" class="date-input" value="${eventInfo.eventPostStartDate}" readonly >
                <input type="text" id="postStartHour" name="postStartHour" style="width:10px;" value="${eventInfo.eventPostStartHour}" readonly/> :
                <input type="text" id="postStartMinute" name="postStartMinute" style="width:10px;" value="${eventInfo.eventPostStartMinute}" readonly/>
                <label for="postEndDate">게시 종료일</label>
                <input type="date" id="postEndDate" name="postEndDate" class="date-input" value="${eventInfo.eventPostEndDate}" readonly>
                <input type="text" id="postEndHour" name="postEndHour" style="width:10px;" value="${eventInfo.eventPostEndHour}" readonly/> :
                <input type="text" id="postEndMinute" name="postEndMinute" style="width:10px;" value="${eventInfo.eventPostEndMinute}" readonly/>
            </div>
            <div class="event-input-group">
                <label for="eventStartDate">이벤트 시작일</label>
                <input type="date" id="eventStartDate" name="eventStartDate" class="date-input" value="${eventInfo.eventStartDate}" readonly>
                <input type="text" id="eventStartHour" name="eventStartHour" style="width:10px;" value="${eventInfo.eventStartHour}" readonly/> :
                <input type="text" id="eventStartMinute" name="eventStartMinute" style="width:10px;" value="${eventInfo.eventStartMinute}" readonly/>
                <label for="endDate">게시 종료일</label>
                <input type="date" id="eventEndDate" name="eventEndDate" class="date-input" value="${eventInfo.eventEndDate}" readonly>
                <input type="text" id="eventEndHour" name="eventEndHour" style="width:10px;" value="${eventInfo.eventEndHour}" readonly/> :
                <input type="text" id="eventEndMinute" name="eventEndMinute" style="width:10px;" value="${eventInfo.eventEndMinute}" readonly/>
            </div>
            <div class="event-input-group">
			    <label for="fileUpload">첨부파일</label>
			    <div id="boardFileList"></div>
			</div>
			<div class="event-input-group" id="boardFileList" style="flex-direction:column;"></div>
            <div class="event-input-group">
                <label for="eventContent">이벤트 내용</label>
                <textarea id="eventContent" name="eventContent" rows="15" readonly>${eventInfo.eventContent}</textarea>
            </div>
            <div class="event-input-group">
                <label for="eventContent">작성자</label>
                <input type="text" id="createId" name="createId" value="${eventInfo.createId}" />
            </div>
            <div class="event-input-group">
                <label for="eventContent">작성일자</label>
                <input type="text" id="createDate" name="createDate" value="${eventInfo.createDate}" />
            </div>
            <div class="event-input-group">
                <label for="eventContent">수정자</label>
                <input type="text" id="updateId" name="updateId" value="${eventInfo.updateId}" />
            </div>
            <div class="event-input-group">
                <label for="eventContent">수정일자</label>
                <input type="text" id="updateDate" name="updateDate" value="${eventInfo.updateDate}" />
            </div>
            <div class="rightBtn">
            	<c:if test="${eventInfo.applyState == 'P'}">
            		<button type="button" id="btn_apply" name="btn_apply" style="margin-right: 2px;">접수</button>
            	</c:if>
            	<button type="button" id="btn_list" name="btn_list">목록으로</button>
            	<!-- 목록으로 돌아가기 추가 -->
            </div>
        </form>
    </div>
    <footer>
		대전배재대ICT융합새일센터 | (34015) 대전광역시 유성구 테크노 1로 11-3(관평동 1337) 배재대학교 대덕밸리캠퍼스 C203호<br>
			대표. 김정현 | TEL. 042-520-5087 | FAX. 070-8240-7766 | Email. ictsaeil@pcu.ac.kr Copyright ⓒ <br>
			대전배재대ICT융합새일센터. All Rights reserved.
	</footer>
</body>
</html>