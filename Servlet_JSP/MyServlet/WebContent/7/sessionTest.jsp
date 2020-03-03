<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	session.setAttribute("upw", "1234");
%>

<!-- 서버측의 세션이라는 객체에 upw라는 이름으로 저장이 되는데 이거는 개별적으로 
브라우저별로 설정이 되는 부분이니까 여러사람이 쓴다고 해서 값은 값이 저장되지는 않음.  -->
<%= session.getAttribute("upw") %>