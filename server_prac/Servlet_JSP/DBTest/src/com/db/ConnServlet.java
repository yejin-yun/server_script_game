package com.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "cs", description = "서블릿 디비 연결 폼", urlPatterns = { "/cs" })
public class ConnServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection conn = null;
	String url = "jdbc:mysql://localhost/test";
	String id = "owner";
	String pw = "1234";
       
    public ConnServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("디비 초기화");

		try {
			Class.forName("com.mysql.jdbc.Driver");
			//2단계: 연결하는 부분 
			conn = DriverManager.getConnection(url, id, pw); // DriverManager.getConnection을 통해서 나온 객체는 커넥션 객체
			System.out.println("연결완료");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("연결실패");
		}
	}

	public void destroy() {
		// TODO Auto-generated method stub
		try{
			if(conn != null)  {
				conn.close();
				System.out.println("연결 종료");
			}
		}
		catch (Exception e) {
			System.out.println("연결 종료 실패");
		}
		System.out.println("디비 해제");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//페이지 요청할 때 db를 매번 접속하는 지 확인
		System.out.println("디비 쿼리 수행 예정");
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
