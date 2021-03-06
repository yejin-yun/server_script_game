package com.s;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "proc", description = "래퍼 처리된 서블릿", urlPatterns = { "/proc" })
public class DataProc extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DataProc() {
        super();

    }

    //서블릿에서 최종 응답을 함. 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//요청에 대한 인코딩 처리를 해줘야 함. 응답 쪽도 마찬가지 
		request.setCharacterEncoding("utf-8");
		String msg = request.getParameter("msg");
		System.out.println("서블릿 전달 데이터 " + msg);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html"); //이 부분은 대부분 마임타입(text, html 등). utf-8로 맞쳐줘도 문제될 건 없음.
		//https://devjms.tistory.com/13 --> 쿠키 한글 에러
		response.addCookie(new Cookie("msg", URLEncoder.encode(msg, "utf-8")));
		//요청은 다 대문자로 바꾸는데 응답할 때 쿠키는 다 소문자로 감. 
		response.getWriter().append("Served at: ").append(msg);
		
		//response.getWriter(): 출력 객체
		//서블릿 새로 추가하면 재가동하기 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
