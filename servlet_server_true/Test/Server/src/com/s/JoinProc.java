package com.s;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import model1.User;

@WebServlet(name = "joinProc", description = "회원가입", urlPatterns = { "/joinProc" })
public class JoinProc extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection conn = null;
	
    public JoinProc() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	//join.html에서 post로 데이터 전달을 하므로 doPost에서 작성. 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
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
			String uid = request.getParameter("uid"); //실제로는 request.getParameter 쓰면 될 듯
			String upw = request.getParameter("upw");
			String sql = "insert into tbl_user(uid, upw) values(?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql); //연결할 때 쿼리
			ps.setString(1, uid);
			ps.setString(2, upw);
			
			int count = ps.executeUpdate();
			//이 상태에서 결과를 돌려 주면 됨 
			if(count > 0) { //반영이 된 거. 
				request.setAttribute("code", "1"); //결과는 리퀘스트 통해서 던져줄 때 값만 간단하게 보여줄 것. 성공하면 1, 실패하면 0
				
			}
			else {
				request.setAttribute("code", "0");
			}


			ps.close();
			//반납 --> 반납을 하면 풀로 디비커넥션은 다 처리가 된 거. 
			conn.close();
			System.out.println("커넥션 풀로부터 커넥션 객체를 반납했다.");
			
	
			RequestDispatcher rd = request.getRequestDispatcher("joinOk.jsp"); //loginError.jsp여기서 포워딩 시키면 될 거 같다. 
			rd.forward(request, response);
			
			
		} catch (Exception e) {
			System.out.println("커넥션 객체 획득 오류." + e.getMessage());
		}

	}

}
