<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<!-- 주소 우편번호 서비스 스크립트 -->
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
    // 우편번호 찾기 찾기 화면을 넣을 element
    var element_wrap = document.getElementById('wrap');

    function foldDaumPostcode() {
        // iframe을 넣은 element를 안보이게 한다.
        element_wrap.style.display = 'none';
    }

    function sample3_execDaumPostcode() {
        // 현재 scroll 위치를 저장해놓는다.
        var currentScroll = Math.max(document.body.scrollTop, document.documentElement.scrollTop);
        new daum.Postcode({
            oncomplete: function(data) {
                // 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                if(data.userSelectedType === 'R'){
                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    if(data.buildingName !== '' && data.apartment === 'Y'){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                    if(extraAddr !== ''){
                        extraAddr = ' (' + extraAddr + ')';
                    }
                    // 조합된 참고항목을 해당 필드에 넣는다.
                    document.getElementById("sample3_extraAddress").value = extraAddr;
                
                } else {
                    document.getElementById("sample3_extraAddress").value = '';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('sample3_postcode').value = data.zonecode;
                document.getElementById("sample3_address").value = addr;
                // 커서를 상세주소 필드로 이동한다.
                document.getElementById("sample3_detailAddress").focus();

                // iframe을 넣은 element를 안보이게 한다.
                // (autoClose:false 기능을 이용한다면, 아래 코드를 제거해야 화면에서 사라지지 않는다.)
                element_wrap.style.display = 'none';

                // 우편번호 찾기 화면이 보이기 이전으로 scroll 위치를 되돌린다.
                document.body.scrollTop = currentScroll;
            },
            // 우편번호 찾기 화면 크기가 조정되었을때 실행할 코드를 작성하는 부분. iframe을 넣은 element의 높이값을 조정한다.
            onresize : function(size) {
                element_wrap.style.height = size.height+'px';
            },
            width : '100%',
            height : '100%'
        }).embed(element_wrap);

        // iframe을 넣은 element를 보이게 한다.
        element_wrap.style.display = 'block';
    }
</script>

</head>
<body>
	
	<jsp:include page="../../include/header.jsp" />
	
	<div>
		<h1 class="sign_title">JOIN NOW</h1>
	</div>
	<br>
	
	<div>
		<form method="post" action="<%=request.getContextPath() %>/user_join_ok.do">
			<input type="hidden" name="userDate" value="sysdate">	<!-- 가입일자 hidden으로 넘겨주기 -->
			
			<ul>
				<li><font size="2" color="red">*필수정보 입력</font></li>
				<br>
				<li>NAME* <input type="text" name="userName" required></li>
				<li>ID* <input type="text" name="userId"> <input type="button" name="userId_check" value="중복확인" required></li>
				<li>PASSWORD* <input type="password" name="userPwd" min="6" size="15" placeholder="영문/숫자/특수문자 포함 6자 이상" required></li>
				<li>CONFIRM PASSWORD* <input type="password" name="userPwd_check" min="6" size="15" placeholder="영문/숫자/특수문자 포함 6자 이상" required></li>
				<li>GENDER <input type="radio" name="userGen" value="남성">남성
					  	   <input type="radio" name="userGen" value="여성">여성
				</li>
				<li>PHONE  <!-- <select>
								<option value="010">010</option>
								<option value="02">02</option>
								<option value="032">032</option>
								<option value="031">031</option>
							</select> -->
							<input type="tel" name="userPhone" placeholder="'-'제외하고 입력해주세요.">
				</li>
				<li>ADDRESS<br>
					<input type="text" id="sample3_postcode" placeholder="우편번호">
					<input type="button" onclick="sample3_execDaumPostcode()" value="우편번호 찾기"><br>
					<input type="text" id="sample3_address" placeholder="주소"><br>
					<input type="text" id="sample3_detailAddress" placeholder="상세주소">
					<input type="text" id="sample3_extraAddress" placeholder="참고항목">

					<div id="wrap" style="display:none;border:1px solid;width:500px;height:300px;margin:5px 0;position:relative">
					<img src="//t1.daumcdn.net/postcode/resource/images/close.png" id="btnFoldWrap" style="cursor:pointer;position:absolute;right:0px;top:-1px;z-index:1" onclick="foldDaumPostcode()" alt="접기 버튼">
					</div>
				</li>
				<li>E-MAIL <input type="text" name="userEmail_1" placeholder="이메일 입력해주세요.">		
						   <span>@</span>
						   <select name="userEmail_2">
						  		<option value="" disabled selected>::E-Mail 선택::</option>
						  		<option value="naver">naver.com</option>
						  		<option value="daum">daum.net</option>
						  		<option value="gmial">gmail.com</option>
						  		<option value="hamail">hanmail.net</option>
						  		<option value="nate">nate.com</option>
						   </select>
				</li>
				<li>POINT <input type="text" name="userPoint">p</li>   <!-- ★String으로 받아오는지? -->
				<br>
				<li> <input type="submit" value="회원가입"> &nbsp;
					 <input type="reset" value="취소">
				</li>
			</ul>
		</form>
	</div>
	
	<jsp:include page="../../include/footer.jsp" />
	
</body>
</html>