package com.w;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class ResponseWrapper extends HttpServletResponseWrapper {
	HttpServletResponse response;
	
	public ResponseWrapper(HttpServletResponse response) {
		super(response);
		// TODO Auto-generated constructor stub
		this.response = response;
	}

	//쿠키를 조작하는 부분
	@Override
	public void addCookie(Cookie cookie) {
		// TODO Auto-generated method stub
		System.out.println("[응답래퍼]원데이터 " + cookie.getValue());
		cookie.setValue(cookie.getValue().toLowerCase());
		//super.addCookie(cookie);
		
		// 셋밸류가 되면 이것을 다시 돌려줘야 됨. 
		response.addCookie(cookie); //쿠키의 수정은 같은 키를 가지고 다시 애드하면 됨. 
	}
	
	//이것도 필터에서 래핑해줘야 함. 

}
