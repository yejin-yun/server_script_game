package com.s;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import model1.*;



@WebServlet(name = "bbs", description = "게시물 데이터를 가지고 오는 서블릿", urlPatterns = { "/bbs" })
public class bbsProc extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection conn = null;
	
    public bbsProc() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		//게시물을 읽어와서 전달해주면 됨. (페이징해야 하지만 전체 읽어와서 전달)
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
			String sql = "select * from tbl_board;";
			PreparedStatement ps = conn.prepareStatement(sql); //연결할 때 쿼리
			ResultSet rs = ps.executeQuery();
			
			//User가 model
			ArrayList<Board> boards = new ArrayList<Board>();
			Board boardBean = null;
			while(rs.next()) {
				boardBean = new Board();	
				boardBean.setBno(rs.getInt("bno"));
				boardBean.setTitle(rs.getString("title"));
				boardBean.setContent(rs.getString("content"));
				boardBean.setWriter(rs.getString("writer"));
				boardBean.setRegdate(rs.getString("regdate"));
				boards.add(boardBean);
				//이 것들을 컬렉션에 담아서 컬렉션에 담긴 내용을 전달해주면 됨. 
			}
			
			rs.close();
			ps.close();
		
			//반납 --> 반납을 하면 풀로 디비커넥션은 다 처리가 된 거. 
			conn.close();
			System.out.println("커넥션 풀로부터 커넥션 객체를 반납했다.");
			
			request.setAttribute("bbs", boards);
			RequestDispatcher rd = request.getRequestDispatcher("bbs2.jsp"); //로그인에 대한 처리를 다하고 결과를 bbs2로 던짐.
			//bbsProc가 게시물을 읽어와가지고 bbs2.jsp로 최종 던지는 거. 
			rd.forward(request, response);	
			//proc에서 쿼리쳐서 내용을 다 담아서 bbs로 던진 거. 
			
		} catch (Exception e) {
			System.out.println("커넥션 객체 획득 오류." + e.getMessage());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
