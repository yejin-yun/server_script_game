<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%!
	public void jspInit() {
		ServletConfig config = getServletConfig(); //서블릿에서 init할 때 컨피그가 넘어옴. 자동으로 넘어온다고 보면 됨.
		if(config.getInitParameter("onlyApp") != null) {
			ServletContext ctx = getServletContext();
			String value = config.getInitParameter("onlyApp");
			ctx.setAttribute("onlyApp", value);	
		}
		
		//config 객테를 구하고 널이 아니면 ctx를 구하고 값을 여기에 세팅함. 
	}
%>
<%
	//servletContext와 servletConfig
	//https://blog.naver.com/PostView.nhn?blogId=youngchanmm&logNo=221227744788&parentCategoryNo=&categoryNo=45&viewDate=&isShowPopularPosts=false&from=postView
	//전체 페이지에서 적용되는 파라미터 --> 서블릿에서는 servletContext로 가져오고 jsp파일에서는 application으로 가져옴. https://sumin172.tistory.com/148
	out.println("=>" + application.getInitParameter("jspApp")); //내장객체 중에서 전체를 아우르는게 application
	//config -> 서블릿에서 초기화에서 구했었음. jsp에서도 초기화에서 구함. 
	out.println("->" + getServletContext().getAttribute("onlyApp")); //Context객체 쪽에 Attribute로 저장했으니까 이렇게 꺼내는 듯. 
%>