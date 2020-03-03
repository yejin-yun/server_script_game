<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	if (session.getAttribute("uid") == null){
		out.println("세션이 모두 삭제 되었다.");
	}
	else {
		out.println("세션이 존재한다.");
	}
		
%>
