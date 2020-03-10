package com.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "h", description = "나의 첫번째 서블릿", urlPatterns = { "/h" })
public class Myservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Myservlet() {
        super();
    }
    
    //get방식으로 데이터를 전송하면 호출된다. 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getParameter("uid"));
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
