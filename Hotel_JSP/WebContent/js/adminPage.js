const room_click = document.querySelector('#room_click');
const res_click = document.querySelector('#res_click');
const standard = document.querySelector('#standard');
const contArea2 = document.querySelector('#contArea2');
const use = document.querySelector('.use');

/* 현재 날짜를 불러오는 함수 */
function getToday() {
	let date = new Date();
	return date.getFullYear() + "-" + ("0"+(date.getMonth()+1)).slice(-2) + "-" + ("0"+date.getDate()).slice(-2);
}

function roomNo() {
	/*let today = getToday();  // 오늘 날짜를 YYYY-MM-DD로 저장
	console.log(today);*/
	let today = '2021-06-14';
	let standard = $("#standard_name").text();
	let deluxe = $("#deluxe_name").text();
	let suite = $("#suite_name").text();
	let prestige = $("#prestige_name").text();
	
	// standard ajax
	$.ajax({
		type : "post",
		url : "./view/admin/standard_main.jsp",
		data : {
			"today" : today,
			"room_name" : standard
		},
		success : function(data) {
			//alert('성공');
			let table = "";
			$(data).find("roomnum").each(function(){
				let number = $(this).find("number").text();
				table += number +"<br>";
				
			});
			let table2="";
			$(data).find("roomnum").each(function(){
				let number = $(this).find("number").text();
				
				if(number != null){
					table2 += "O <br>";
				}
			});
			$("#standard_td").append(table);
			$("#standard_O").append(table2);
			

		},
		error : function() {
			alert('오류');
		}
	});
	
	// deluxe ajax
	$.ajax({
		type : "post",
		url : "./view/admin/deluxe_main.jsp",
		data : {
			"today" : today,
			"room_name" : deluxe
		},
		success : function(data) {
			//alert('성공');
			let table = "";
			$(data).find("roomnum").each(function(){
				let number = $(this).find("number").text();
				table += number +"<br>";
				
			});
			let table2="";
			$(data).find("roomnum").each(function(){
				let number = $(this).find("number").text();
				
				if(number != null){
					table2 += "O <br>";
				}
			});
			$("#deluxe_td").append(table);
			$("#deluxe_O").append(table2);
			

		},
		error : function() {
			alert('오류');
		}
	});
	// suite ajax
	$.ajax({
		type : "post",
		url : "./view/admin/suite_main.jsp",
		data : {
			"today" : today,
			"room_name" : suite
		},
		success : function(data) {
			//alert('성공');
			let table = "";
			$(data).find("roomnum").each(function(){
				let number = $(this).find("number").text();
				table += number +"<br>";
				
			});
			let table2="";
			$(data).find("roomnum").each(function(){
				let number = $(this).find("number").text();
				
				if(number != null){
					table2 += "O <br>";
				}
			});
			$("#suite_td").append(table);
			$("#suite_O").append(table2);
			

		},
		error : function() {
			alert('오류');
		}
	});
	
	// prestige ajax
	$.ajax({
		type : "post",
		url : "./view/admin/prestige_main.jsp",
		data : {
			"today" : today,
			"room_name" : prestige
		},
		success : function(data) {
			//alert('성공');
			let table = "";
			$(data).find("roomnum").each(function(){
				let number = $(this).find("number").text();
				table += number +"<br>";
				
			});
			let table2="";
			$(data).find("roomnum").each(function(){
				let number = $(this).find("number").text();
				
				if(number != null){
					table2 += "O <br>";
				}
			});
			$("#prestige_td").append(table);
			$("#prestige_O").append(table2);
			

		},
		error : function() {
			alert('오류');
		}
	});
};
roomNo();

/* check 클릭시 상세내역 오픈*/
const btn = document.querySelector('#check');
const list = document.querySelector('.content_info');

btn.addEventListener('click', function() {
	let d_twin = $("#D_TWIN").text();
	console.log(d_twin);
	
	// 상세내역 오픈될 때만 룸번호 보여주는 ajax 실행
	if (list.style.display === 'none') {
		list.style.display = 'block';
		
		$.ajax({
			type : "post",
			url : "./view/admin/adminPageCheck.jsp",
			data : {
				"D_TWIN" : d_twin
			},
			success : function(data) {
				//alert('성공');
				let table = "";
				console.log($(data).find("number"));
				$(data).find("number").each(function(){
					
					table += "<tr>";
					table += "<td>"+$(this).find("name").text()+"</td>"
					table += "<td>"+$(this).find("num").text()+"</td>"
					table += "</tr>";	
				});
				
				$(".content_info").append(table);

			},
			error : function() {
				alert('오류');
			}
		});
	} else {
		list.style.display = 'none';
	}
	
});

/* 객실검색 버튼 눌렀을 때*/

$(function() {
	$("#btn").click(function() {
		standard.classList.replace('use', 'off');
		let room = $("#roomName").val();
		let checkIn = $("#checkIn").val();
		let checkOut = $("#checkOut").val();
		console.log(room);
		console.log(checkIn);
		console.log(checkOut);

		$.ajax({
			type : "post",
			url : "./view/admin/adminPage1.jsp",
			data : {
				"roomName" : room,
				"checkIn" : checkIn,
				"checkOut" : checkOut
			},
			async: false,
			datatype : "xml",
			success : function(data) {
				alert('page1 성공');
				console.log(data);
				let table = "";
				console.log($(data).find("room"));
				$(data).find("room").each(function(){
					
					table += "<tr>";
					table += "<td rowspan='3'><img alt='상품' src='image/"+$(this).find("name").text()+".jpg'></td>"
					table += "<td colspan='2'>"+$(this).find("name").text()+"</td>"
					table += "</tr>";
					table += "<tr>";
					table += "<td colspan='2' align='left'>"+$(this).find("content").text()+" | Size : "+$(this).find("size").text()+" m² </td>"
					table += "</tr>";
					table += "<tr>";
					table += "<td><h2>"+$(this).find("price").text()+"</h2></td>"
					table += "</tr>";
					
				});
				
				$("#standard_search").append(table);

			},
			error : function() {
				alert("오류");
			}

		});
	$.ajax({
			type : "post",
			url : "./view/admin/adminPage2.jsp",
			data : {
				"roomName" : room,
				"checkIn" : checkIn,
				"checkOut" : checkOut
			},
			async: false,
			datatype : "xml",
			success : function(data) {
				alert('page2 성공');
				console.log(data);
				let table = "";
				console.log($(data).find("reserve"));
				$(data).find("reserve").each(function(){
					
					table += "<tr>";
					table += "<td>"+$(this).find("name").text()+"</td>"
					table += "<td>"+$(this).find("resin").text()+"</td>"
					table += "<td>"+$(this).find("resout").text()+"</td>"
					table += "</tr>";
					
				});
				
				$("#D_KING_search").append(table);
				
				$("#twin").after(table);

			},
			error : function() {
				alert("오류");
			}

		}); 
	});
	
	

});



/* 객실관리, 예약관리 클릭시 페이시 숨김 or 보임*/




room_click.addEventListener('click', function() {
	standard.classList.replace('off', 'on');
	contArea2.classList.replace('use','off');
	if (contArea2.classList == 'on') {
		contArea2.classList.replace('on', 'off');
	}
	console.log(contArea1);
});

res_click.addEventListener('click', function() {
	standard.classList.replace('use', 'on');
	contArea2.classList.replace('off', 'on');
	if (standard.classList == 'on') {
		standard.classList.replace('on', 'off');
	}
	console.log(contArea2);
});
