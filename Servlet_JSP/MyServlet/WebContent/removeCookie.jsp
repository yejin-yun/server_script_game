<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//쿠키 삭제 
	//setMaxAge(0); //바로 삭제
	//setMaxAge(-1); //브라우저를 닫으면 삭제
	//setMaxAge 값은 세션으로 되어있음..? 영상 18:48(6강)
	
	//쿠키를 찾아서
	Cookie[] cookies = request.getCookies();
	if (cookies != null) {
		for (Cookie c : cookies) {
			//저장된 쿠키 중에 uid가 존재하면
			System.out.println(c.getName() + c.getValue() + " " + c.getName().equals("uid"));
			if (c.getName().equals("uid")){
				c.setMaxAge(0);
				//쿠키가 삭제가 안됨. 왜냐면 변경된 값이 브라우저로 전송되지 않았기 때문. 
				// 즉 변경한 쿠키를 브라우저에게 전송해야지만 반영이 된다. 
				response.addCookie(c); //이러면 같은 이름의 쿠키가 수정이 되면서 들어가는데 maxAge가 0가 되서 사라지는 거. 
				// 항상 변경을 하면 addCookie를 하기 위해 반영을 해야함. 
				break;
			}
		}
	}
%>