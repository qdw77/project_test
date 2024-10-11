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
		fn_selectList(1);
		
		$("#searchBtn").on("click", function(){
			fn_selectList(1);
		});
		
	});
	
	function fn_selectList(pageIndex){
		$("#pageIndex").val(pageIndex);
		var frm = $("#searchFrm").serialize();
		$.ajax({
		    url: '/event/getEventInfoList.do',
		    method: 'post',
		    data : frm,
		    dataType : 'json',
		    success: function (data, status, xhr) {
		    	var innerHtml = '';
			       if(data.list.length > 0){
			    	   for(var i=0; i<data.list.length; i++){
			    		   innerHtml += '<tr onclick="javascript:fn_detail(\''+data.list[i].eventSeq+'\');">';
			    		   innerHtml += '<td>';
			    		   innerHtml += data.list[i].rnum;
			    		   innerHtml += '</td>';
			    		   innerHtml += '<td>';
			    		   innerHtml += data.list[i].eventTitle;
			    		   innerHtml += '</td>';
			    		   innerHtml += '<td>';
			    		   innerHtml += '<span>';
			    		   innerHtml += data.list[i].eventPostStartDate;
			    		   innerHtml += '</span> ~ ';
			    		   innerHtml += '<span>';
			    		   innerHtml += data.list[i].eventPostEndDate;
			    		   innerHtml += '</span>';
			    		   innerHtml += '</td>';
			    		   innerHtml += '<td>';
			    		   innerHtml += '<span>';
			    		   innerHtml += data.list[i].eventStartDate;
			    		   innerHtml += '</span> ~ ';
			    		   innerHtml += '<span>';
			    		   innerHtml += data.list[i].eventEndDate;
			    		   innerHtml += '</span>';
			    		   innerHtml += '</td>';
			    		   innerHtml += '<td>';
			    		   innerHtml += data.list[i].createId;
			    		   innerHtml += '</td>';
			    		   innerHtml += '<td>';
			    		   innerHtml += data.list[i].createDate;
			    		   innerHtml += '</td>';
			    		   innerHtml += '<td>';
			    		   innerHtml += data.list[i].eventViewCnt;
			    		   innerHtml += '</td>';
			    		   innerHtml += '<td>';
			    		   innerHtml += data.list[i].useYn;
			    		   innerHtml += '</td>';
			    		   innerHtml += '</tr>';
			    	   }
			       }else{
			    	   innerHtml += '<tr>';
			    	   innerHtml += '<td colspan="8">조회된 게시글이 없습니다.</td>';
			    	   innerHtml += '</tr>';
			       }
			       $("#eventList").html(innerHtml);
			       fn_paging(data.paginationInfo);
		    },
		    error: function (data, status, err) {
		    	console.log(data);
		    }
		});
	}
	
	function fn_detail(eventSeq){
		$("#eventSeq").val(eventSeq);
		var frm = $("#eventFrm");
		frm.attr("action", "/event/getEventInfo.do");
		frm.submit();
	}

	function fn_paging_move(pageIndex){
		$("#pageIndex").val(pageIndex);
		
		fn_selectList(pageIndex);
	}
	
	function fn_paging(paramMap){
	  var innerHtml = "";
	  if(paramMap.totalPageCount > 0) {
	     innerHtml +='<a href="javascript:fn_paging_move(1);"><img src="/images/egovframework/cmmn/btn_page_pre10.gif" alt="처음페이지로" /></a>&nbsp;'; //처음 페이지로
	     if(paramMap.currentPageNo == 1){
	        innerHtml +='<a href="javascript:fn_paging_move('+paramMap.currentPageNo+');"><img src="/images/egovframework/cmmn/btn_page_pre1.gif" alt="이전 페이지로" /></a>&nbsp;'; //이전 페이지로
	     }else if(paramMap.currentPage != 1){
	        innerHtml +='<a href="javascript:fn_paging_move('+(paramMap.currentPageNo-1)+');"><img src="/images/egovframework/cmmn/btn_page_pre1.gif" alt="이전 페이지로" /></a>&nbsp;'; //이전 페이지로
	     }
	     for(var i=paramMap.firstPageNoOnPageList; i<=paramMap.lastPageNoOnPageList; i++){ //한번에 보여주는 페이징처리 갯수 조정
	        if(paramMap.currentPageNo == i){
	           innerHtml += '<strong> '+i+' </strong>';
	        }else{
	           innerHtml += '<a href="javascript:fn_paging_move('+i+');" class="page_num"> '+ i +' </a>';
	        }
	     }
	     if(paramMap.currentPageNo == paramMap.totalPageCount){
	        innerHtml +='&nbsp;<a href="javascript:fn_paging_move('+paramMap.totalPageCount+');"><img src="/images/egovframework/cmmn/btn_page_next1.gif" alt="다음 페이지로" /></a>&nbsp;'; //다음 페이지로
	     }else if(paramMap.currentPageNo != paramMap.totalPageCount){
	        innerHtml +='&nbsp;<a href="javascript:fn_paging_move('+(paramMap.currentPageNo+1)+');"><img src="/images/egovframework/cmmn/btn_page_next1.gif" alt="다음 페이지로" /></a>&nbsp;'; //다음 페이지로
	     }
	  
	  
	     innerHtml +='<a href="javascript:fn_paging_move('+paramMap.totalPageCount+');"><img src="/images/egovframework/cmmn/btn_page_next10.gif" alt="마지막 페이지로" /></a>'; //마지막 페이지로
	  
	  }   
	  $("#paging").html(innerHtml);
	  
	  var innerHtmlCounter = "";
	  var totListCnt = "<strong>"+paramMap.totalRecordCount+"</strong>";
	  if(paramMap.listCnt > 0){
	     innerHtmlCounter += '<span>전체  '+totListCnt+'건</span><span>['+paramMap.currentPageNo+'/<strong>'+paramMap.totalPageCount+'</strong>] </span>'; //건
	  }else{
	     innerHtmlCounter += '<span>전체  '+totListCnt+'건</span>';
	  }
	  $("#total_counter").html(innerHtmlCounter);
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
	<div class="main-container">
    	<h2>진행 중인 이벤트</h2>
    	<form id="eventFrm" name="eventFrm" method="POST">
			<input type="hidden" id="eventSeq" name="eventSeq" value=""/>
		</form>
    	<div class="search-container">
    		<form id="searchFrm" name="searchFrm">
    			<input type="hidden" id="pageIndex" name="pageIndex" value="1"/>
    			<select class="search-select">
			        <option value="eventTitle">제목</option>
			    </select>
			    <input type="text" id="searchKeyword" name="searchKeyword" class="search-input" placeholder="검색어를 입력하세요" />
			    <a href="javascript:fn_selectList(1);" style="padding:5px;"><i class="fas fa-search"></i></a>
    		</form>
		</div>
	    <table>
      		<thead>
      			<tr>
		      		<th>번 호</th>
	      			<th>이벤트명</th>
	      			<th>게시기간</th>
	      			<th>신청기간</th>
	      			<th>등록자</th>
	      			<th>등록일자</th>
	      			<th>조회수</th>
	      			<th>사용여부</th>
   				</tr>
    		</thead>
      		<tbody id="eventList">
      			
   			</tbody>
      	</table>
      	<div class="pagination" id="paging">
		</div>
	</div>
	<footer>
		대전배재대ICT융합새일센터 | (34015) 대전광역시 유성구 테크노 1로 11-3(관평동 1337) 배재대학교 대덕밸리캠퍼스 C203호<br>
			대표. 김정현 | TEL. 042-520-5087 | FAX. 070-8240-7766 | Email. ictsaeil@pcu.ac.kr Copyright ⓒ <br>
			대전배재대ICT융합새일센터. All Rights reserved.
	</footer>
</body>
</html>