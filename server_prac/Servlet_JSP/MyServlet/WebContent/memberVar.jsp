<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%!
	int myCnt = 0;
	public void jspInit() {
		myCnt++;
	}
	
	public void jspDestroy() {
		//이 종료돠는 시점의 메소드에다가 오늘 카운트한 횟수를 db에 + 해줘야함. 
	}
%>
<%
	int count = 0; 
	count++;
	myCnt++;
%>
<%= count %> / <%= myCnt++ %> <!-- myCnt의 첫번째 페이지의 값은 2여야 함 -->