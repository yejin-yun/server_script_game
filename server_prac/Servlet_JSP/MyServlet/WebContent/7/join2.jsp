<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String uid = request.getParameter("uid"); 
	//키는 input 태그의 name이라는 속성을 보면 됨. 
	session.setAttribute("uid", uid);
	//join3에서 join2로 간다면 앞에서 뒤로 오는 거니까 upw를 삭제해줘야 함.
	session.removeAttribute("upw");
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>second input</title>
</head>
<body>
	<h3>Password</h3>
	<fieldset>
		<form action="/MyServlet/7(session)/join3.jsp">
			<input type="password" name="upw" placeholder="패스워드">
			<input type="submit" value="2단계 완료">
		</form>
	</fieldset>
</body>
</html>