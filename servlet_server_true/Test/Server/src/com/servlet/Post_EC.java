package com.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.google.gson.Gson;

import model.Ending;

//controller

@WebServlet(name = "postEc", urlPatterns = { "/postEc" })
public class Post_EC extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection conn = null;
	int cnt = -2;
	
    public Post_EC() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);

		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context)initCtx.lookup("java:comp/env"); 
			DataSource ds = (DataSource)envCtx.lookup("jdbc/java"); 
			conn = ds.getConnection();
			System.out.println("커넥션 풀로부터 커넥션 객체를 획득했다.");
			
			//요청의 body를 가져옴 --> post니까 데이터 정보는 body에 있음. 
			String body = request.getParameter("title");
			
			//쿼리수행
			String sql = "select * from endingcnt;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			Ending end = new Ending();
			while(rs.next()) {
				end.setId(rs.getInt("id"));
				end.setTitle(rs.getString("title"));
				
				String title = end.getTitle();
				int id = end.getId();
				
				if(body.contains(title) == true) {
					cnt = rs.getInt("cnt"); //변경되기 전 cnt
					String updateSql = "update endingcnt set cnt = cnt + 1 where id=?;";
					PreparedStatement updatePs = conn.prepareStatement(updateSql);
					updatePs.setInt(1, id);
					int rslt = updatePs.executeUpdate();
					if(rslt == 0) {
						System.out.println("카운트 증가 오류");
					}
					break;
				}
			}
					
			rs.close();
			ps.close();
			//반납
			conn.close();
			System.out.println("커넥션 풀로부터 커넥션 객체를 반납했다.");
			
			//Gson으로 파싱
			//응답데이터는 end
			end.setCnt(cnt + 1);//원래 cnt에 내 몫 1을 더해줌.
			Gson gson = new Gson();
			String data = gson.toJson(end);
			
			//응답
			//System.out.println("응답완료");
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/plain;charset=utf-8");
			response.getWriter().append(data); 
		} catch (Exception e) {
			System.out.println("오류" + e.getMessage());
		}
	}

}
