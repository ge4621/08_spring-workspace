<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<!-- jQuery 라이브러리 -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
</head>
<body>
	<h2>실시간 대기오염 정보</h2>
	
	지역 : 
	<select id="location">
		<option>서울</option>
		<option>부산</option>
		<option>대전</option>
	</select>
	
	<button id="btn1">해당 지역 대기오염 정보</button>
	
	<table id="result1" border="1">
		<thead>
			<tr>
				<th>측정소 명</th>
				<th>측정 일시</th>
				<th>통합 대기 환경 수치</th>
				<th>미세먼지 농도</th>
				<th>아황산가스 농도</th>
				<th>일산화탄소 농도</th>
				<th>이산화질소 농도</th>
				<th>오존 농도</th>
			</tr>
		</thead>
		<tbody>
		
		</tbody>
	</table>
	
	<script>
		$(function(){
			$("#btn1").click(function(){
				
				
				
				/* json형식으로 응답받을때의 예시
				$.ajax({
					url:"air.do",
					data:{location:$("#location").val()},
					success:function(data){
						//console.log(data);
						//console.log(data.response.body.items);	
						
						const itemArr= data.response.body.items;
						
						let value = "";
						for(let i in itemArr){
							//console.log(itemArr[i]);
							
							let item = itemArr[i];
							value += "<tr>"
									+"<td>" + item.stationName  + "</td>"
									+"<td>" + item.dataTime + "</td>"
									+"<td>" + item.khaiValue + "</td>"
									+"<td>" + item.pm10Value + "</td>"
									+"<td>" + item.so2Value + "</td>"
									+"<td>" + item.coValue + "</td>"
									+"<td>" + item.no2Value + "</td>"
									+"<td>" + item.o3Value + "</td>"
									+"</tr>"	
						}
						
						$("#result1 tbody").html(value);
						
					},error:function(){
						console.log("ajax 통신 실패");
					}
				})
				*/
				
				//xml형식으로 응답 데이터를 받을 때
				$.ajax({
					url:"air.do",
					data:{location:$("#location").val()},
					success:function(data){
						console.log(data);
						//jQuery 에서의 find 메소드 : 기준이 되는 요소의 하위 요소들 중 특정 요소를 찾을 때 사용(html, xml)
						//console.log($(data).find("item"));  //find메소드는 제이쿼리 메소드 임!!!그렇기 때문에 그냥 사용하면 안됨
						
						//xml 형식으로 응답데이터를 받았을 때
						//1. 응답데이터 안에 실제 데이터가 담겨 있는 요소 선택
						let itemArr = $(data).find("item");
						
						//2. 반복문을 통해 실제 데이터가 담긴 요소들에 접근해서 동적으로 요소 만들기
						let value = "";
						itemArr.each(function(i,item){
							//console.log(item);
							//console.log($(item).find("stationName").text()); //<stationName>강남구</stationName> => .text가 없을 경우 , .text가 있을 경우 => 강남구
							
							value += "<tr>"
									+"<td>" + $(item).find("stationName").text() + "</td>"
									+"<td>" + $(item).find("dataTime").text() + "</td>"
									+"<td>" + $(item).find("khaiValue").text() + "</td>"
									+"<td>" + $(item).find("pm10Value").text() + "</td>"
									+"<td>" + $(item).find("so2Value").text() + "</td>"
									+"<td>" + $(item).find("coValue").text() + "</td>"
									+"<td>" + $(item).find("no2Value").text() + "</td>"
									+"<td>" + $(item).find("o3Value").text() + "</td>"
									+"</tr>"
						})
						
						//3. 동적으로 만들어낸 요소를 화면에 출력
						$("#result1 tbody").html(value);
						
						
					},error:function(){
						console.log("ajax 통신 실패");
					}
				})
				

			})
		})
	</script>
	
	<hr>
	
	<h2>지진 해일 대피소 정보</h2>
	
	<input type="button" value="실행" id="btn2">
	
	<div id="result2"></div>	
	
	<script>
		$(function(){
			
			//화살표 함수로 바꾼 버전
			$("#btn2").click(()=>{
				$.ajax({
					url:"disaster.do",
					success:data => {
						
						let $table = $("<table border='1'></table>");
						let $thead = $("<thead></thead>");
						let headTr = "<tr>"
									+	"<th>일련번호</th>"
									+	"<th>시도명</th>"
									+	"<th>시군구명</th>"
									+	"<th>대피장소명</th>"
									+	"<th>주소</th>"
									+	"<th>수용가능인원(명)</th>"
									+	"<th>해변으로부터의 거리</th>"
									+	"<th>대피소 분류명</th>"
									+"</tr>";
						
						$thead.html(headTr);
						
						let $tbody = $("<tbody></tbody>");
						let bodyTr = "";
						
						$(data).find("row").each((i,row) => {
							
							bodyTr += "<tr>"
								+ "<td>" + $(row).find("id").text() +"</td>"
								+ "<td>" + $(row).find("sido_name").text() +"</td>"
								+ "<td>" + $(row).find("sigungu_name").text() +"</td>"
								+ "<td>" + $(row).find("shel_nm").text() +"</td>"
								+ "<td>" + $(row).find("address").text() +"</td>"
								+ "<td>" + $(row).find("shel_av").text() +"</td>"
								+ "<td>" + $(row).find("lenth").text() +"</td>"
								+ "<td>" + $(row).find("shel_div_type").text() +"</td>"
								+ "</tr>"
							
						})
						
						$tbody.html(bodyTr);
						
						$table.append($thead,$tbody).appendTo("#result2");
						
					},error:data => {
						console.log("ajax통신에 실패");
					}
				})
			})
			
			
			/*
			$("#btn2").click(function(){
				$.ajax({
					url:"disaster.do",
					success:function(data){
						
						//console.log(data);
						//console.log($(data).find("row"));
						
						let $table = $("<table border='1'></table>");
						let $thead = $("<thead></thead>");
						let headTr = "<tr>"
									+	"<th>일련번호</th>"
									+	"<th>시도명</th>"
									+	"<th>시군구명</th>"
									+	"<th>대피장소명</th>"
									+	"<th>주소</th>"
									+	"<th>수용가능인원(명)</th>"
									+	"<th>해변으로부터의 거리</th>"
									+	"<th>대피소 분류명</th>"
									+"</tr>";
						
						$thead.html(headTr);
						
						let $tbody = $("<tbody></tbody>");
						let bodyTr = "";
						$(data).find("row").each(function(i,row){
							//console.log($(row).find("shel_nm").text());
							
							bodyTr += "<tr>"
									+ "<td>" + $(row).find("id").text() +"</td>"
									+ "<td>" + $(row).find("sido_name").text() +"</td>"
									+ "<td>" + $(row).find("sigungu_name").text() +"</td>"
									+ "<td>" + $(row).find("shel_nm").text() +"</td>"
									+ "<td>" + $(row).find("address").text() +"</td>"
									+ "<td>" + $(row).find("shel_av").text() +"</td>"
									+ "<td>" + $(row).find("lenth").text() +"</td>"
									+ "<td>" + $(row).find("shel_div_type").text() +"</td>"
									+ "</tr>"
						})
						
						$tbody.html(bodyTr);
						
						
						//$table.append($thead, $tbody);  //테이블에 붙이기
						//$table.appendTo("#result2");	//만들어지 테이블을 해당 영역에 넣기
						
						$table.append($thead, $tbody).appendTo("#result2");
						
						
					},error:function(){
						console.log("ajax 통신 실패");
					}
				})
				
			})
			*/
			
			
			
			
			
			/*
			
				**화살표 함수***
				익명 함수들을 화살표 함수로 작성 할 수 있다.
				
				"function(){ }"를 "()=>{ }" 이런 형식으로 작성 가능
				
				"function(data){  }"를 "data => { }" 이런식으로 작성 가능
				
				"function(a,b){  }"를 "(a,b) => {}"이런 식으로 작성 가능
				
				"function(){ return 10; }"를 "() => 10"이런 식으로 작성 가능
				
			*/
			
			
			
		})
	</script>
	
	
	
</body>
</html>