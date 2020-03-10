package com.s;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 서블릿 예제: 초기화 파라미터  
 * @author yunyejin
 *
 */
@WebServlet(
		name = "i", 
		description = "초기화 파라미터를 가진 서블릿", 
		urlPatterns = { "/i" }, 
		initParams = { 
				//서블릿만 이용하는 파라미터. 전체적으로 쓸 수 있는 것은 web.xml에서 
				@WebInitParam(name = "age", value = "100", description = "나이"), 
				@WebInitParam(name = "name", value = "홍길동", description = "이름")
		})
public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
        
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InitServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		//본 서블릿만 사용하는 초기화 코드
		System.out.println(config.getInitParameter("age"));
		System.out.println(config.getInitParameter("name"));
		//전체 웹 페이지에서 사용하는 초기화코드
		System.out.println(config.getServletContext().getInitParameter("gAge"));
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
