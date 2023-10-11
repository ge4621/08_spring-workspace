<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<!-- jQuery 라이브러리 -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
</head>
<body>

	<h1>Spring에서의 AJAX 사용방법</h1>
	
	<h3>1. 요청시 값 전달, 응답 결과 받아보기</h3>
	이름 : <input type="text" name="" id="name"> <br>
	나이 : <input type="number" name="" id="age">
	<!-- <button id="btn">전송</button> -->
	<button onclick="test1();">전송</button>
	
	<div id="result1"></div>

	<script>
	/*	
		$("#btn").click(function(){

		})
	*/
	/*data => 넘길값 (키:벨류 형식으로) 키=> 넘길때 이름, 벨류=> jsp에서 사용한 id값(.val() =>벨류값을 넘겨야 할때 여기서는 이름, 나이의 입력값을 넘겨야 하기 때문에 .val()사용  )
	* 자바스크립트는 자료형 구분을 안해도 되기 때문에 매개변수 쓸때 자료형 신경 안쓰고 쓸수 있다.
	*/
	
	/*
		function test1(){
			$.ajax({
				url:"ajax1.do",
				data:{
					name:$("#name").val(),
					age:$("#age").val()
				},
				success:function(result){
					console.log(result);
					$("#result1").text(result);
				},
				error:function(){
					console.log("ajax 통신 실패!!");
				}
			})
		}
*/

			function test1(){
				$.ajax({
					url:"ajax1.do",
					data:{
						name:$("#name").val(),
						age:$("#age").val()
					},
					success:function(result){
						console.log(result);
						
						//응답데이터가 배열의 형태일 경우 => 인덱스에 접근 가능 =>  [인덱스]를 사용 
						/*
						let value = "이름 : " + result[0] + "<br> 나이 : " + result[1];
						$("#result1").html(value);
						*/
						
						//응답데이터가 단순객체의 형태일 경우 => 속성에 접근해야 한다. .속성명 => 속성명은 controller에서 사용한 키값
						let value = "이름 : " + result.name + "<br> 나이 : " + result.age;
						$("#result1").html(value);
					},
					error:function(){
						console.log("ajax 통신 실패!!");
					}
				})
			}
	</script>
	
	<h3>2. 조회 요청 후 조회된 한 회원 객체를 응답 받아서 출력해보기</h3>
	조회 할 회원번호 : <input type="number" id="userNo">
	<button id="btn">조회</button>
	
	<div id="result2"></div>
	
	<script>
		$(function(){
			$("#btn").click(function(){
				$.ajax({
					url:"ajax2.do",
					data:{num:$("#userNo").val()},
					success:function(obj){
						console.log(obj);
						
						/*객체 접근시 속성을 사용해야 하기 때문에 .을 사용해야 한다.*/
						let value = "<ul>"
									+ "<li>이름 : " + obj.userName + "</li>"
									+ "<li>아이디 : " + obj.userId + "</li>"
									+ "<li>나이 : " + obj.age + "</li>"
									+"</ul>";
					
						$("#result2").html(value);
						
					},error:function(){
						console.log("ajax 통신 실패");
					}
				})
			})
		})
	</script>
	
	<h3>3. 조회요청 후 조회된 회원 리스트 응답받아서 출력해보기</h3>
	<button onclick="test3();">회원 전체 조회</button>
	<br><br>
	
	<table border="1" id="result3">
		<thead>
			<tr>
				<th>아이디</th>
				<th>이름</th>
				<th>나이</th>
				<th>전화번호</th>
			</tr>
		</thead>
		<tbody>
			
		</tbody>
	</table>
	
	<script>
		function test3(){
			$.ajax({
				url:"ajax3.do",
				success:function(list){
					
					console.log(list); //전부 조회
					console.log(list[0]); //0번 인덱스에 해당되는 것 조회(차은우정보 조회)
					console.log(list[0].userName); //0번째 인덱스에 있는 사람의 name속성 조회(차은우)
					
					let value = "";
					for(let i in list){
						value += "<tr>"
								+"<td>" + list[i].userId + "</td>"
								+"<td>" + list[i].userName + "</td>"
								+"<td>" + list[i].age + "</td>"
								+"<td>" + list[i].phone + "</td>"
								+"</tr>";
					}
					
					$("#result3 tbody").html(value);
					
				},error:function(){
					console.log("ajax통신 실패");
				}
			})
		}
	</script>
	
	
	
	
</body>
</html>