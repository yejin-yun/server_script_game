<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String nickname = request.getParameter("nickname");
	session.setAttribute("nickname", nickname);
	// 왜 계속 세션에 저장??
	// 요청하다가 다음 페이지로 오면 끝남. 즉 request.getParameter값이 유지가 되지 않음. 
	// 근데 페이지는 계속 이어져 가야만 하고, 이 정보는 계속 유지가 되어야 하니까 세션이라는 도구를 이용해서 계속해서 가지고 있는 것. 
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- 원래라면 db에 입력을 해서 정상처리 되고, 그다음에 결과를 보여줌.  -->
uid : <%= session.getAttribute("uid") %>
upw : <%= session.getAttribute("upw") %>
nickname : <%= session.getAttribute("nickname") %>

<%
	//세션을 모두 삭제한다. 왜? 이제 필요가 없기 때문에. 다 썼는데 삭제를 안하면 서버쪽에 많은 자원을 붙잡게 됨. 서버의 성능이 점차 저하되는 원인이 될 수 있음.
	// 필요한 것만 남겨주면 됨. 로그인 했다고 하면 로그인 정보만  가지고 있으면
	// 로그인 정보가 있으면 들어가고 없으면 안들어가게 처리할 수 있음. 
	session.invalidate();
%>

<!-- 위에서 최종적으로, db적으로 처리가 됬다고 하면  삭제를 해줘야 됨.  -->

<a href="main.jsp">메인화면 이동</a>
<!-- main.jsp -> 서블릿이 아니니까 바로 가도 되는 건가...? 내부여서 상관이 없나..? -->
</body>
</html>