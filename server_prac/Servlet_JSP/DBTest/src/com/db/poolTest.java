package com.db;

import java.io.IOException;
import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.mysql.jdbc.PreparedStatement;


@WebServlet(name = "pt", description = "커넥션 풀링 테스트", urlPatterns = { "/pt" })
public class poolTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection conn = null;
	
    public poolTest() {
        super();
        // TODO Auto-generated constructor stub
    }

	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	public void destroy() {
		// TODO Auto-generated method stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context)initCtx.lookup("java:comp/env"); //이 자체가 또하나의 컨텍스트가 됨. 컨텍스트 캐스팅해서 envCtx로 받음
			//환경변수까지 들어왔고 여기서 이제 lookup을 또 함. 
			DataSource ds = (DataSource)envCtx.lookup("jdbc/java"); //이름을 찾아서 뽑는 것은 데이터 소스로 캐스팅..
			//최종적으로 ds로 부터 커넥션을 구하면 됨. 
			conn = ds.getConnection();
			System.out.println("커넥션 풀로부터 커넥션 객체를 획득했다.");
			
			//매번 요청하면 윗부분이 작동하는데 객체를 매번 만드는게 아니라 풀링에서 가져옴. 
			
			//쿼리수행
			
			//반납
			conn.close();
			System.out.println("커넥션 풀로부터 커넥션 객체를 반납했다.");
		} catch (Exception e) {
			System.out.println("커넥션 객체 획득 오류." + e.getMessage());
		}
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
