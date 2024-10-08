<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
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
		$("#btn_list").on("click", function(){
			location.href = "/event/eventList.do";
		});
				
		$("#btn_apply").on("click", function(){
			fn_apply();
		});
	});
	
	function fn_apply(){
		var frm = $("#joinFrm").serialize();
		$.ajax({
		    url: '/event/apply.do',
		    method: 'post',
		    data : frm,
		    dataType : 'json',
		    success: function (data, status, xhr) {
		    	if(data.resultChk > 0){
		    		if(data.resultChk == 1){
			    		alert("접수되었습니다.");
			    		location.href = "/event/eventList.do";
			    	}else if(data.resultChk == 2){
			    		alert("이미 접수된 사용자 정보입니다.");
			    		return;
			    	}
		    	}else{
		    		alert("접수에 실패하였습니다.");
		    		return;
		    	}
		    	
		    },
		    error: function (data, status, err) {
		    	console.log(status);
		    }
		});
	}
</script>
<header>
	<div class="container">
        <h1>EVENT</h1>
        <nav>
            <ul>
                <li><a href="/event/eventList.do">Home</a></li>
                <li><a href="javascript:alert('준비중입니다.');">About</a></li>
                <li><a href="javascript:alert('준비중입니다.');">Services</a></li>
                <li><a href="javascript:alert('준비중입니다.');">Contact</a></li>
            </ul>
        </nav>
    </div>

</header>
</head>
<body>
	<div class="event-form-container">
        <h2 class="event-form-title">접수</h2>
        <form class="event-form" method="POST" id="joinFrm" name="joinFrm">
        	<input type="hidden" id="eventSeq" name="eventSeq" value="${eventSeq}"/>
            <div class="event-input-group">
                <label for="joinTitle">이름</label>
                <input type="text" id="joinName" name="joinName">
            </div>
            <div class="event-input-group">
                <label for="joinTitle">연락처</label>
                <input type="text" id="joinPhone" name="joinPhone">
            </div>
            <div class="event-input-group">
                <label for="joinTitle">이메일</label>
                <input type="text" id="joinEmail" name="joinEmail">
            </div>
            <div class="event-input-group">
                <label for="joinTitle">성별</label>
                <input type="radio" id="joinSex_m" name="joinSex" value="m" checked style="flex:0; margin:10px;"/>남자
                <input type="radio" id="joinSex_f" name="joinSex" value="f" style="flex:0; margin:10px;"/>여자
            </div>
            <div class="event-input-group">
                <label for="joinTitle">생년월일</label>
                <input type="text" id="joinBirth" name="joinBirth">
            </div>
            <div class="event-input-group">
                <label for="joinTitle">주소</label>
                <input type="text" id="joinAddr" name="joinAddr">
            </div>
            <div class="rightBtn">
            	<button type="button" id="btn_apply" name="btn_apply" style="margin-right: 2px;">접수</button>
            	<button type="button" id="btn_list" name="btn_list">목록으로</button>
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