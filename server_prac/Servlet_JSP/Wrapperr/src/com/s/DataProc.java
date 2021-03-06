package com.s;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DataProc
 */
@WebServlet(name = "proc", urlPatterns = { "/proc" })
public class DataProc extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DataProc() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//요청에 대한 인코딩 처리를 해줘야 함. 응답 쪽도 마찬가지 
		request.setCharacterEncoding("UTF-8");
		String msg = request.getParameter("msg");
		System.out.println("서블릿 전달 데이터 " + msg);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html"); //이 부분은 대부분 마임타입(text, html 등). utf-8로 맞쳐주면 문제됨. 
		response.getWriter().append("Served at: ").append(msg);
		
		//response.getWriter(): 출력 객체
		//서블릿 새로 추가하면 재가동하기 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
