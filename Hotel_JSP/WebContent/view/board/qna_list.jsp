<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>QNA 게시판</title>
<link rel="stylesheet" href="css/qnaStyle.css" />
<jsp:include page="../../include/header.jsp" />
	
	<div class="topArea">
		<div class="topInner">
			<h2 class="titDep1">Customer Service</h2>
			<p class="pageGuide">
				호텔 이용과 관련된 궁금한 사항을 남겨주시면 신속하게 답변 드리겠습니다.
				<br/>
				항상 고객의 소리에 귀 기울이는 호텔앤리조트가 되겠습니다.
			</p>
		</div>
	</div>
	
	<div class="inner">
		<div class="myContents">
			<h3 class="titDep2">FAQ</h3>
			
			<form method="post"
			action="<%=request.getContextPath() %>/qna_search.do">
			<div class="searchBox">
				<div class="searchOp">
					<div class="selectWrap">
					<select name="search_field" class="select_ct">
						<option value="title">글제목</option>
						<option value="content">글내용</option>
						<option value="title_content">글제목+글내용</option>
						<option value="writer">작성자</option>
					</select>
					</div>
					<div class="intWord">
					<span class="intArea">
						<input type="text" name="search_content" placeholder="검색어를 입력해주세요." class="search_txt">
					</span>		
				</div>
				</div>		
				<div class="btnWrap">
					<input type="submit" value="검색" class="search_button">
				</div>
			</div>
			</form>
		</div>
		
		<ul class="qnaType">
			<li id="type0">번호</li>
			<li id="type1">제목</li>
			<li id="type2">작성자</li>
			<li id="type3">조회수</li>
			<li id="type4">작성일자</li>
		</ul>
		
		<div id="qnaTab" class="qnaTab">
			
			
			<div class="qnaTabType">
				<c:set var="list" value="${List }" />
					<c:if test="${!empty list }">
						<c:forEach items="${list }" var="dto">
				<ul class="qnaList">
					<li id="type0">
						<strong class="listTit"> 
							${dto.getQnaNo() }
						</strong>
					</li>
					<c:forEach begin="1" end="${dto.getQnaStep() }"></c:forEach>
					<li id="type1"><a href="<%=request.getContextPath() %>/qna_cont.do?no=${dto.getQnaNo() }">${dto.getQnaTitle() }</a></span>
					<li id="type2">${dto.getUserId() }</li>
					<li id="type3">${dto.getQnaHit() }</li>
					<li id="type4">${dto.getQnaDate().substring(0,10) }</li>
					

				</ul>
						</c:forEach>
					</c:if>
			</div>	
			
			
			<c:if test="${empty list }">
				<tr>
					<td colspan="5" align="center">
						<h3>검색된 게시물이 없습니다.</h3>
					</td>
				</tr>
			</c:if>

			
		</div>
	</div>
	
	<div align="center" style="margin-top: 200px;">
		<hr width="50%" color="red">
		<h3>QNA 게시판 전체 리스트</h3>
		<hr width="50%" color="red">
		<br> <br>

		<table border="1" cellspacing="0" width="600">
			<tr>
				<th>글번호</th>
				<th>글제목</th>
				<th>작성자</th>
				<th>조회수</th>
				<th>작성일자</th>
			</tr>

			<c:set var="list" value="${List }" />
			<c:if test="${!empty list }">
				<c:forEach items="${list }" var="dto">
					<tr>
						<td>${dto.getQnaNo() }</td>
						<td>
							<c:forEach begin="1" end="${dto.getQnaStep() }">
	                       		&nbsp;&nbsp;
	                    	</c:forEach>
	                    	<a href="<%=request.getContextPath() %>/qna_cont.do?no=${dto.getQnaNo() }">${dto.getQnaTitle() }</a>
	                    </td>
						<td>${dto.getUserId() }</td>
						<td>${dto.getQnaHit() }</td>
						<td>${dto.getQnaDate().substring(0,10) }</td>
					</tr>
				</c:forEach>
			</c:if>

			<c:if test="${empty list }">
				<tr>
					<td colspan="5" align="center">
						<h3>검색된 게시물이 없습니다.</h3>
					</td>
				</tr>
			</c:if>

			<tr>
				<td colspan="5" align="right">
				<input type="button" value="글쓰기" onclick="location.href='qna_write.do'">	<!-- 세션정보 추가 필요 -->
				</td>
			</tr>
		</table>
		<br>

		<c:if test="${page > block }">
			<a href="qna_list.do?page=1">[맨처음]</a>
			<a href="qna_list.do?page=${startBlock - 1 }">◀</a>
		</c:if>

		<c:forEach begin="${startBlock }" end="${endBlock }" var="i">
			<c:if test="${i == page }">
				<b><a href="qna_list.do?page=${i }">[${i }]</a></b>
			</c:if>

			<c:if test="${i != page }">
				<a href="qna_list.do?page=${i }">[${i }]</a>
			</c:if>
		</c:forEach>

		<c:if test="${endBlock < allPage }">
			<a href="qna_list.do?page=${endBlock + 1 }">▶</a>
			<a href="qna_list.do?page=${allPage }">[마지막]</a>
		</c:if>
		<br> <br>

		<form method="post"
			action="<%=request.getContextPath() %>/qna_search.do">

			<select name="search_field">
				<option value="title">글제목</option>
				<option value="content">글내용</option>
				<option value="title_content">글제목+글내용</option>
				<option value="writer">작성자</option>
			</select>
			&nbsp;
			<input type="text" name="search_content">
			&nbsp; 
			<input type="submit" value="검색">

		</form>

	</div>
	<jsp:include page="../../include/footer.jsp" />
</body>
</html>