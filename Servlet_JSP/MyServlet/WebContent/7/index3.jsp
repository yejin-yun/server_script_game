<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    errorPage="error01.jsp" %>
    <%-- 오류가 발생하면 바로  errorPage="error01.jsp로 감. 어떤 오류가 나든 여기로 가니까 이것을 쓰면 
    상세처리는 안됨. 이거는 한방에 잡을 때 쓰고 디스패쳐는 상세처리할 때 쓴다. 그리고 에러페이지는 오류가 나면 콘솔창에 로그가 다 뜬다.안뜨지않나??? --%>
<%
	String age = request.getParameter("age");
	//age를 int로 변환 

	int myage = Integer.parseInt(age);
	out.println("my age: " + myage);
%>