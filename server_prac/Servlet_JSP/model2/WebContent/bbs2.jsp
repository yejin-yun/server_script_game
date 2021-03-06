<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	${user.uid}님 반갑습니다.<br>
	<!-- 여기서 리스트를 보여주는 것은 또다른 요청을 통해서 만들어진다고 생각.  -->
	<!-- 게시물 내용 -> jsp에서 하는 건 아님
		할 수 있는 방법은 1. 페이지 삽입. 2. 로그인을 성공하면 loginProc에서 게시물을 가져다가 같이 던져는 방법 
	-->
	<table border="1">
		<thead>
			<tr>
				<td>글번호</td>
				<td>작성자</td>
				<td>제목</td>
				<td>내용</td>
				<td>등록일</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="board" items="${bbs}">
				<tr>
					<td>${board.bno}</td>
					<td>${board.writer}</td>
					<td>${board.title}</td>
					<td>${board.content}</td>
					<td>${board.regdate}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>