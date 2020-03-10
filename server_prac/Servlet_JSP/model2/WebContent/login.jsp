<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<fieldset>
		<form action="/model2/loginProc"> <!-- mvc에 따르면 서블릿으로 가야됨.  -->
			<input type="text" name="uid">
			<input type="password" name="upw">
			<input type="submit" value="로그인">
		</form>
	</fieldset>
</body>
</html>
<!-- 이렇게 jsp에서 직관적으로 흐름에 맞춰서 짜는게 model1 방식 -->