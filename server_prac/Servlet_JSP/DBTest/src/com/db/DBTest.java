package com.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "DBConnect", description = "데이터베이스 접속 테스트", urlPatterns = { "/DBConnect" })
public class DBTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DBTest() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//DB 접속 확인
		String url = "jdbc:mysql://localhost/test";
		String id = "owner";
		String pw = "1234";
		//1단계: 드라이버 로드
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			//2단계: 연결하는 부분 
			conn = DriverManager.getConnection(url, id, pw); // DriverManager.getConnection을 통해서 나온 객체는 커넥션 객체
			System.out.println("연결완료");
			//3단계: 쿼리
			//4단계: 연결 종료
			conn.close();
			System.out.println("연결종료");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("연결실패");
		}
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
