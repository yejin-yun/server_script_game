<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- jstl의 core라이브러리 세팅 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="n1" value="${param.num1}" scope="request"></c:set>
<c:set var="n2" value="${param.num2}" scope="request"></c:set>
<!-- scope는 값이 유지되는 범위 request면 이 변수는 request 타고 다음 페이지까지 유지가 됨 -->
<jsp:forward page="result01.jsp"></jsp:forward>
<!-- forward는 전 페이지에서 받은 request값도 그대로 전달해줌. -->