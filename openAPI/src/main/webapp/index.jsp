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

	<h2>지진겸용 임시주거시설(xml)</h2>
	
	<button id="btn1">확인</button>
	<div id="result"></div>
	
	<script>
		$(function(){
			
			$("#btn1").click(()=>{
				$.ajax({
					url:"check.do",
					success:data => {
						
						let $table = $("<table border='1'></table>");
						let $thead = $("<thead></thead>");
						let headTr = "<tr>"
									+"<th>지역코드</th>"
									+"<th>시도명</th>"
									+"<th>시군구명</th>"
									+"<th>시설명</th>"
									+"<th>상세주소</th>"
									+"</tr>;"
									
						$thead.html(headTr);
									
						let $tbody = $("<tbody></tbody>");
						let bodyTr = "";
						
						$(data).find("row").each((i,row)=>{
							bodyTr += "<tr>"
									+"<td>" + $(row).find("arcd").text() +"</td>"
									+"<td>" + $(row).find("ctprvn_nm").text() +"</td>"
									+"<td>" + $(row).find("sgg_nm").text() +"</td>"
									+"<td>" + $(row).find("vt_acmdfclty_nm").text() +"</td>"
									+"<td>" + $(row).find("dtl_adres").text() +"</td>"
									+"</tr>"
						})
						
						$tbody.html(bodyTr);
						$table.append($thead,$tbody).appendTo("#result");
						
					},error:data =>{
						console.log("ajax통신에 실패");
					}
				})
				
			})
		})
	</script>
	
	<h2>지진겸용 임시주거시설(json)</h2>
	
	<button id="btn2">확인</button>
	
	<table id="result2" border="1">
		<thead>
			<tr>
				<th>지역코드</th>
				<th>시도명</th>
				<th>시군구명</th>
				<th>시설명</th>
				<th>상세주소</th>
				<th>관리부서</th>
			</tr>
		</thead>
		<tbody>
		
		</tbody>
	</table>
	
	<script>
		$(function(){
			$("#btn2").click(()=>{
				$.ajax({
					url:"ajax.bo",
					success:list =>{
						//console.log(list);
						//console.log(list.EarthquakeIndoors[1].row);
						
						const listArr=list.EarthquakeIndoors[1].row;
						
						let value = "";
						for(let i in listArr){
							//console.log(listArr[i]);
							
							let list = listArr[i];
							value += "<tr>"
									+"<td>" + list.arcd + "</td>"
									+"<td>" + list.ctprvn_nm + "</td>"
									+"<td>" + list.sgg_nm + "</td>"
									+"<td>" + list.vt_acmdfclty_nm + "</td>"
									+"<td>" + list.dtl_adres + "</td>"
									+"<td>" + list.mngps_nm + "</td>"
									+"</td>"
						}
						
						$("#result2 tbody").html(value);
						
					},error:list=>{
						console.log("ajax통신 실패");
					}
				})
			})
		})
	</script>
	

</body>
</html>