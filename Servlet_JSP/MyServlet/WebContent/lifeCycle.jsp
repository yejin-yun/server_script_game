<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- jsp의 라이프 사이클 --%>

<%!
//이렇게 정의해놓으면 멤버로써 구성이 된다. 
	//처음 구동시 얘가 호출됨.
	public void jspInit() {
		System.out.println("jsp 초기화 코드 호출 jspInit()"); //1번만 수행되는 코드 
	}
	//리로드가 되면 여기가 호출됨. 
	public void jspDestroy() {
		System.out.println("jsp 종료 코드 호출 jspDestroy() ");
	}
%>

<%
	out.println("helloWorld1<br>"); // 기본적인 요청에서 얘가 응답함.
	out.println("helloWorld2<br>");
%>
