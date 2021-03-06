package com.w;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class RequestWrapper extends HttpServletRequestWrapper {
	HttpServletRequest request;
	
	public RequestWrapper(HttpServletRequest request) {
		super(request);
		// TODO Auto-generated constructor stub//생
		//생성자에서는 request 객체를 전역으로 받아달라
		this.request = request;
	}

	@Override
	public String getParameter(String name) {
		// TODO Auto-generated method stub
		//return super.getParameter(name);
		//데이터 조작을 할 것
		// 우리가 하는 조작은 소문자를 대문자로 바꾸는 조작
		
		String value = request.getParameter(name);
		System.out.println("[래퍼]원데이터" + value);
		//어떤 요청이 들어와도 대문자로 바꿔서 간다.
		// 얘를 필터로 감싸서 넣어줘야 함. 
		return value.toUpperCase();
	}
	
	//이제 getParameter로 데이터 조작을 하면 됨. 

}
