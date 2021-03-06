package com.s;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.google.gson.Gson;

import model1.*;

@WebServlet(description = "게시물 리스트를 json으로 응답.", urlPatterns = { "/bbsJson" })
public class bbsJson extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection conn = null;
	
    public bbsJson() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
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
			
			//응답데이터
			ResBoard resData = new ResBoard();
			resData.setCode(1);
			resData.setBoards(boards);
			
			
			//GSon으로 파싱하여서 응답한다.
			// 지선을 구성하기 위해서는 제이슨으로 바꿀 수 있게끔 클래스 구조가 만들어 져야 함. 
			//현재 데이터가 ArrayList의 boards라는 이름으로 들어와 있음. 이것만 가지고는 답이 없음.
			//그래서 모델에서 클래스 하나를 더 만듦. 
			Gson gson = new Gson();
			//Object를 string으로 뽑아내는 작업
			String data = gson.toJson(resData); //객체를 스트링으로 뽑아야지 보낼 수 있음.응답하는 거니까.
			//응답
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/plain;charset=utf-8");
			response.getWriter().append(data); //append대신에 print로 해도 됨. 
			//이렇게 하면 서블릿은 view 필요 없이 바로 여기서 응답데이터를 제이슨으로 만들어서 보냄. 
			
		} catch (Exception e) {
			System.out.println("커넥션 객체 획득 오류." + e.getMessage());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
