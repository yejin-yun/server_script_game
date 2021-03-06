package com.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter(description = "나의 필터", urlPatterns = { "/MyFilter" })
public class MyFilter implements Filter {


    public MyFilter() {
        // TODO Auto-generated constructor stub
    	System.out.println("필터 생성자");
    }

	public void destroy() {
		// TODO Auto-generated method stub
		System.out.println("필터 소멸");
	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		System.out.println("필터 구현부분(여기에 요청과 응답이 들어옴)");
		//세션이 없으면 MyServlet으로 갈 수 없다. 
		//특정 값이 포함된 요청이 아니면 해당 페이지로 갈 수 없다.
		if(request.getParameter("uid") == null || request.getParameter("uid").equals("")) { //서블릿 페이지를 요청했는데 uid를 보내지 않았을 수 있음. 
			//포워딩 --> 제어권을 돌림
			RequestDispatcher rd = request.getRequestDispatcher("ssError.jsp");
			rd.forward(request, response);
			//이제 서블릿을 요청할 때 uid를 보내지 않으면 걸러서 다시 메인으로 보내주겠다. 
		}
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("필터 초기화");
	}

}
