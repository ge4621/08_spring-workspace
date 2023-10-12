<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<jsp:include page="common/header.jsp"/>
	
	<div class="content">
		<br><br>
		<div class="innerOuter">
		
			<h4>게시글 Top5</h4>
			<br>
			
			<a href="list.bo" style="float:right">더보기</a>
			<br><br>
			
			<table id="boardList" class="table table-hover" align="center">
                <thead>
                  <tr>
                    <th>글번호</th>
                    <th>제목</th>
                    <th>작성자</th>
                    <th>조회수</th>
                    <th>작성일</th>
                    <th>첨부파일</th>
                  </tr>
                </thead>
                <!-- 현재 조회수가 가장 높은 상위 5개의 게시글 조회해서 뿌리기(ajax) -->
                <tbody>
                	
                </tbody>
            </table>	
		</div>
	</div>
	
	<script>
		$(function(){
			topBoardList();
			
			//setInterval(topBoardList,1000)
			
			/* => 이 방법으로는 동적으로 만들어지 요소에 이벤트 부여가 불가
			$("#boardList>tbody>tr").click(function(){
   			location.href="detail.bo?bno=" + $(this).children().eq(0).text();
   		})
   		*/
   		
   			//동적으로 만들어진 요소에 이벤트 부여 방법
   			//$(상위요소).on(이벤트명, 진짜요소, 실행할 함수) => 모든 태그의 가장 상위요소 : document
   			$(document).on("click","#boardList>tbody>tr",function(){
   				location.href="detail.bo?bno=" + $(this).children().eq(0).text();
   			})
    		
		})
		
		function topBoardList(){
			$.ajax({
				url:"topList.bo",
				success:function(data){
					console.log(data);
					
					let value ="";
					
					for(let i in data){
						value +="<tr>"
								+"<td>" + data[i].boardNo + "</td>"
								+"<td>" + data[i].boardTitle + "</td>"
								+"<td>" + data[i].boardWriter + "</td>"
								+"<td>" + data[i].count + "</td>"
								+"<td>" + data[i].createDate + "</td>"
								+"<td>"; 
								
							if(data[i].originName != null){ //첨부파일이 존재할 경우
								value += "★";
							}
								value += "</td></tr>";									
				
					}
					
					$("#boardList tbody").html(value);
					
					
					
				},error:function(){
					console.log("조회수 top5 게시글 조회용 ajax 통신 실패!!!");
				}
			})
		}
	
	</script>
	
	<jsp:include page="common/footer.jsp"/>
</body>
</html>