<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%!
	public String getCookie(Cookie[] cookies, String key) {
		//쿠키 읽기. 쿠키는 요청을 통해서 읽음. 
		//Cookie[] cookies = request.getCookies(); --> 쿠키 내장객체 때문에 매개변수로 전달해주기로 함 
		if(cookies != null) {
			for(Cookie c : cookies) {
				//저장된 쿠키 중에 uid가 존재하면
				System.out.println(c.getName() + c.getValue() + " " + c.getName().equals("uid"));
				if(c.getName().equals("uid"));
				{
					return c.getValue();
				}
			}	
		}
		return null;
	}
%>
<%
	//쿠키 생성: 쿠키 생성에는 키, 값이 필요. 쿠키도 내장객체이기 때문에 별도의 import 없이 바로 쓸 수 있음. 
	Cookie cook = new Cookie("uid", "hong1111");
	//응답을 통해서 쿠키를 클라이언트의 브라우저에 전송. 즉 저장하는 거.
	response.addCookie(cook);
	//쿠키 읽기. 쿠키는 요청을 통해서 읽음. 
	out.println(getCookie(request.getCookies(), "uid"));

	
	//쿠키 수정
	//쿠키의 키 값이 일치하면 덮어쓰기
	Cookie c1 = new Cookie("uid", "123456789");
	//쿠키 기록
	response.addCookie(c1); //동일한 키 값이여서 덮어쓰기가 됨.
	
	//변경된 쿠키값
	out.println("변경된 쿠키값: " + getCookie(request.getCookies(), "uid"));

	
%>