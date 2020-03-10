<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String upw = request.getParameter("upw");
	session.setAttribute("upw", upw);
	session.removeAttribute("nickname");
	//사실 삭제안하고 새로운 값으로 덮어도 상관 없음. 
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>third input</title>
</head>
<body>
	<h3>Nickname</h3>
	<fieldset>
		<form action="/MyServlet/7(session)/result.jsp">
			<input type="text" name="nickname" placeholder="닉네임">
			<input type="submit" value="최종 완료">
		</form>
	</fieldset>
</body>
</html>