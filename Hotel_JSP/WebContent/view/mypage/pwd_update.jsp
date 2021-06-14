<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 변경</title>
</head>
<body>

	<link rel="stylesheet" href="css/MypageStyle.css">
	
	<jsp:include page="../../include/header.jsp" />

	<div class="myPage_header">
		<div>
			<h1 class="header_title">MY PAGE</h1>
			<p class="header_txt">멤버십 회원을 위한 다양한 혜택이 준비되어 있습니다.</p>
		</div>
	</div>
		
	<div class="myPage">
		<div class="myPage_inner">
			<div class="innerBox">
				<h3 class="inner_menu">예약확인</h3>
        		<ul class="menu_list">
            		<li class="munu_content"><a href="<%=request.getContextPath() %>/res_list.do" class="res">예약내역</a></li>
            		<li class="munu_content"><a href="<%=request.getContextPath() %>/res_change.do" class="res_change">예약변경</a></li>
        		</ul>
        
        		<h3 class="inner_menu">개인정보관리</h3>
        		<ul class="menu_list">
            		<li class="munu_content"><a href="<%=request.getContextPath() %>/info_update.do" class="login">회원 정보 수정</a></li>
            		<li class="munu_content"><a href="<%=request.getContextPath() %>/pwd_update.do" class="login">비밀번호 수정</a></li>
            		<li class="munu_content"><a href="<%=request.getContextPath() %>/info_del.do" class="join">회원탈퇴</a></li>
        		</ul>
        	</div>
        </div>
        
        <div class="myPage_box">
			<p class="headline">개인정보관리</p>
			<div>
				<ul class="myPage_container">
					<li class="resListChg_off"><a href="<%=request.getContextPath() %>/info_update.do">회원 정보 수정</a></li>
					<li class="resListChg_on"><a href="<%=request.getContextPath() %>/pwd_update.do">비밀번호 수정</a></li>
					<li class="resListChg_off"><a href="<%=request.getContextPath() %>/info_del.do">회원탈퇴</a></li>
				</ul>
			</div>
			
			<div>
				<form method="post" action="<%=request.getContextPath() %>/pwd_update_ok.do">
				<p>현재 비밀번호를 올바르게 입력해야만 비밀번호 변경이 가능합니다.</p>
				
				<div>
					<ul>
						<li>CURRENT PASSWORD</li>
						<li><input type="text" name="DbPwd" placeholder="현재 비밀번호를 입력해주세요."></li>
					</ul>
					<ul>
						<li>NEW PASSWORD</li>
						<li><input type="text" name="newPwd1" placeholder="영문/숫자 포함 15자 미만"></li>
					</ul>
					<ul>
						<li>CONFIRM PASSWORD</li>
						<li><input type="text" name="newPwd2" placeholder="동일한 비밀번호를 입력해주세요"></li>
					</ul>
					<ul>
						<li>
							<input type="submit" value="확인">
						</li>
					</ul>
				</div>
				</form>
			</div>
		</div>
	</div>
	

</body>
</html>