<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String age = request.getParameter("age");
	//age를 int로 변환 
	try {
		int myage = Integer.parseInt(age);
		out.println("my age: " + myage);
	}catch (Exception e) {
		//요청을 그대로 이어가서 다른 페이지로 제어권을 넘기는 기술.
		//요청을 그대로 이어서 제어권을 error01.jsp로 넘기겠다는 거. 
		RequestDispatcher dispatcher = request.getRequestDispatcher("error01.jsp"); //특정 페이지를 지정해줌
		dispatcher.forward(request, response);
	}
%>