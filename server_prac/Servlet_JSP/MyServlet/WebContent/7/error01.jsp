<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isErrorPage="true" %>
<%
	System.out.println(exception.getMessage());
%>    

<script>
	alert("입력 오류입니다.");
	history.back();
	//index2에서 error01한테 제어권을 줬기 때문에 즉 index2에서 error01로 페이지가 이동을 한게 아니라 
	// 제어권만 준 거라서 둘은 서로 같은 페이지로 취급이 되는 거지
	// 그래서 뒤로 돌아가면 index2가 아닌 index로 돌아가게 된다.  
</script>