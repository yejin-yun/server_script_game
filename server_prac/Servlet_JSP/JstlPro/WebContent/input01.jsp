<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String names[] = {"ABC", "123", "가나다"};
	request.setAttribute("names", names);
	//이렇게 저장한 request를 전달하기 위해서는 forward가 적절함. 
%>
<!-- 화면이 나오기도 전에  포워딩이 됨. 데이터만 가지고 넘어옴 -->
<jsp:forward page="for.jsp"></jsp:forward>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="send2.jsp">
		<input type="text" name="num1">
		<input type="text" name="num2">
		<input type="submit" value="전송">
	</form>
	<form action="choose.jsp">
		<input type="text" name="num1">
		<input type="text" name="num2">
		<input type="submit" value="전송">
	</form>
</body>
</html>