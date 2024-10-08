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
<title>관리자 로그인</title>
<script type="text/javascript">
	$(document).ready(function(){

		
	});
	
	
	
</script>
</head>

<body>
	<div class="login-container">
        <div class="login-box">
            <h2>로그인</h2>
            <form method="POST" action="/admin/loginAction.do" id="frm" name="frm">
                <div class="input-group">
                    <label for="id">아이디</label>
                    <input type="text" id="id" name="id" required>
                </div>
                <div class="input-group">
                    <label for="password">비밀번호</label>
                    <input type="password" id="pwd" name="pwd" required autocomplete="off">
                </div>
                <button type="submit">로그인</button>
            </form>
        </div>
    </div>
</body>
</html>