<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>기본페이지</title>
</head>
<body>
	<form action="/MyServlet/7/index2.jsp"> <!-- 폼으로 가니까 전체 경로 써줘야 됨. 그래서 /index2.jsp로만 하면 안됨. --> 
		<input type="text" name="age">
		<input type="submit" value="전송">
	</form>
	
	<form action="/MyServlet/7/index3.jsp"> <!-- 폼으로 가니까 전체 경로 써줘야 됨. 그래서 /index2.jsp로만 하면 안됨. --> 
		<input type="text" name="age">
		<input type="submit" value="전송2">
	</form>
</body>
</html>