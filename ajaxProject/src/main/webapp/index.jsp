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

	</script>
</body>
</html>