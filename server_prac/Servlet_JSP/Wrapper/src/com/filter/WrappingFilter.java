package com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.w.RequestWrapper;
import com.w.ResponseWrapper;


@WebFilter("/WrappingFilter")
public class WrappingFilter implements Filter {

    public WrappingFilter() {
        // TODO Auto-generated constructor stub
    }


	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		//요청이 들어오면 요청객체를 래퍼 객체로 감싸서 이 래퍼객체를 서블릿으로 전달한다.
		String str = request.getParameter("msg");
		System.out.println("[필터]원데이터 " + str);
		
		//요청이 들어오면 래퍼로 쌈. 
		RequestWrapper re = new RequestWrapper((HttpServletRequest)request);
		//응답 객체도 응답 래퍼 객체로 감싸서 서블릿으로 전송한다. 
		ResponseWrapper res = new ResponseWrapper((HttpServletResponse)response);
		// pass the request along the filter chain
		chain.doFilter(re, res); //이걸로 개별 서블릿에 전달이 됨. request를 re로 대체하면 래핑이 마무리 됨. 
	}
	//서블릿에 요청이 들어오면 필터에서 래핑을 해서 보내줌. 래핑을 해서 보내주면 서블릿에서 겟파라미터하면 래퍼에서 요청을 받으면 무조건 대문자로 돌려주게 함. 
	//그러니까 서블릿에서 겟파라미터하면 대문자로 튀어나옴. 
	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
