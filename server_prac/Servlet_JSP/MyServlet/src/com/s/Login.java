package com.s;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "login", description = "로그인하는 서블릿", urlPatterns = { "/login" })
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	//생성자 - 여기서 서블릿이 완성됨
    public Login() { 
        super();
        System.out.println("생성자 Login()");
    }

    // 여기서 서블릿이 초기화 
	public void init(ServletConfig config) throws ServletException {
        System.out.println("초기화 init()");
	}

	//reload 될때 호출 됨. 
	public void destroy() {
        System.out.println("소멸 destroy()");
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("페이지가 요청되는데 get방식 호출 시.. doGet()");
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("페이지가 요청되는데 post방식 호출 시.. doPost()");
		
		doGet(request, response);
	}

}
