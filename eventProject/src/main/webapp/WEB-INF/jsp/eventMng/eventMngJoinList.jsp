<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/css/egovframework/main.css"/>
<script src="https://code.jquery.com/jquery-3.7.1.js"
	integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4="
	crossorigin="anonymous"></script>
<title>Insert title here</title>
<script type="text/javascript">
	$(document).ready(function(){
		fn_selectList();
		
		$("#searchBtn").on("click", function(){
			fn_selectList();
		});
		
		$("#btn_confirm").on("click", function(){
			fn_chkConfirm();
		});
		
		$("#excelDown").on("click", function(){
			fn_excelDown();
		});
		
	});
	
	function fn_selectList(){
		
		var frm = $("#searchFrm").serialize();
		$.ajax({
		    url: '/admin/getEventApplyList.do',
		    method: 'post',
		    data : frm,
		    dataType : 'json',
		    success: function (data, status, xhr) {
		    	var innerHtml = '';
			       if(data.list.length > 0){
			    	   for(var i=0; i<data.list.length; i++){
			    		   innerHtml += '<tr>';
			    		   innerHtml += '<td>';
			    		   innerHtml += '  	<input type="checkbox" id="chk_'+data.list[i].eventJoinSeq+'" name="chk_seq" value="'+data.list[i].eventJoinSeq+'"/>';
			    		   innerHtml += '</td>';
			    		   innerHtml += '<td>';
			    		   innerHtml += data.list[i].eventTitle;
			    		   innerHtml += ' </td>';
			    		   innerHtml += '<td>';
			    		   innerHtml += data.list[i].joinName;
			    		   innerHtml += ' </td>';
			    		   innerHtml += '<td>';
			    		   innerHtml += data.list[i].joinSex;
			    		   innerHtml += '</td>';
			    		   innerHtml += '<td>';
			    		   innerHtml += data.list[i].joinPhone;
			    		   innerHtml += '</td>';
			    		   innerHtml += '<td>';
			    		   innerHtml += data.list[i].joinEmail;
			    		   innerHtml += '</td>';
			    		   innerHtml += '<td>';
			    		   innerHtml += data.list[i].joinAddr;
			    		   innerHtml += '</td>';
			    		   innerHtml += '<td>';
			    		   if(data.list[i].joinState == 'A'){
			    			   innerHtml += '접수';
			    		   }else if(data.list[i].joinState == 'W'){
			    			   innerHtml += '당첨';
			    		   }
			    		   innerHtml += '</td>';
			    		   innerHtml += '</tr>';
			    	   }
			       }else{
			    	   innerHtml += '<tr>';
			    	   innerHtml += '<td colspan="8">접수 내역이 없습니다.</td>';
			    	   innerHtml += '</tr>';
			       }
			       $("#eventList").html(innerHtml);
		    },
		    error: function (data, status, err) {
		    	console.log(data);
		    }
		});
	}
	
	function fn_chkConfirm(){
		var chkArray = '';
		$('input:checkbox[name=chk_seq]').each(function (index) {
			if($(this).is(":checked")==true){
		    	chkArray+=$(this).val();
		    	chkArray+=',';
		    }
		});
		chkArray = chkArray.slice(0, -1);
		var eventSeq = $("#eventSeq").val();
		$.ajax({
		    url: '/admin/setEventApplyConfirm.do',
		    method: 'post',
		    data : {
		    	"eventSeq" : eventSeq,
		    	"chkArray" : chkArray
		    },
		    dataType : 'json',
		    success: function (data, status, xhr) {
		    	if(data.resultChk > 0){
		    		alert("일괄 당첨처리되었습니다.");
		    		fn_selectList();
		    	}else{
		    		alert("일괄 당첨처리에 실패하였습니다.");
		    		return;
		    	}
		    },
		    error: function (data, status, err) {
		    	console.log(data);
		    }
		});
		
	}
	
	function fn_excelDown(){
		var frm = $("#searchFrm");
		frm.attr("action", "/admin/excelDown.do");
		frm.submit();
	}

</script>
</head>
<body>
	<div class="main-container">
    	<h2>${paramMap.eventTitle}</h2>
    	<form id="eventFrm" name="eventFrm" method="POST">
			
		</form>
    	<div class="search-container">
    		<form id="searchFrm" name="searchFrm">
    			<input type="hidden" id="eventSeq" name="eventSeq" value="${paramMap.eventSeq }"/>
    			<select class="search-select">
			        <option value="eventJoinName">이름</option>
			    </select>
			    <input type="text" id="searchKeyword" name="searchKeyword" class="search-input" placeholder="검색어를 입력하세요" />
			    <a href="javascript:fn_selectList(1);" style="padding:5px;"><i class="fas fa-search"></i></a>
			    <input type="button" id="excelDown" name="excelDown" class="search-button" value="엑셀다운로드"/>
    		</form>
		</div>
	    <table>
      		<thead>
      			<tr>
		      		<th></th>
	      			<th>이벤트명</th>
	      			<th>이름</th>
	      			<th>성별</th>
	      			<th>연락처</th>
	      			<th>이메일</th>
	      			<th>주소</th>
	      			<th>상태</th>
   				</tr>
    		</thead>
      		<tbody id="eventList">
   			</tbody>
      	</table>
      	<div class="rightBtn">
           	<button type="button" id="btn_confirm" name="btn_confirm" style="margin-right: 2px;">일괄 당첨</button>
           	<button type="button" id="btn_list" name="btn_list">목록으로</button>
       </div>
	</div>
</body>
</html>