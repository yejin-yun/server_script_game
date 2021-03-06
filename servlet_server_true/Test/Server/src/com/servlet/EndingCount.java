package com.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.google.gson.Gson;

import model.*;

//model(데이터베이스 처리) --> 나중에 내가 쓴거


@WebServlet(name = "ec", urlPatterns = { "/ec" })
public class EndingCount extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection conn = null;
	int cnt = -2;
	
    public EndingCount() {
        super();
    
    }
    
	public void init(ServletConfig config) throws ServletException {
		
	}

	public void destroy() {
		
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context)initCtx.lookup("java:comp/env"); 
			DataSource ds = (DataSource)envCtx.lookup("jdbc/java"); 
			conn = ds.getConnection();
			System.out.println("커넥션 풀로부터 커넥션 객체를 획득했다.");
			
			//요청의 body를 가져옴 --> post니까 데이터 정보는 body에 있음. 
			String body = "fjsnfjanfkdfmkdlendingfdkfm24394u3jkb4b43jn fss";//안드에서 post로 요청하는 방법을 찾을 때까지는 보류 getBody(request);
			
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
			System.out.println("응답완료");
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/plain;charset=utf-8");
			response.getWriter().append(data); 
		} catch (Exception e) {
			System.out.println("오류" + e.getMessage());
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doGet(request, response);
		/*try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context)initCtx.lookup("java:comp/env"); 
			DataSource ds = (DataSource)envCtx.lookup("jdbc/java"); 
			conn = ds.getConnection();
			System.out.println("커넥션 풀로부터 커넥션 객체를 획득했다.");
			
			//요청의 body를 가져옴 --> post니까 데이터 정보는 body에 있음. 
			String body = "fjsnfjanfkdfmkdlendingfdkfm24394u3jkb4b43jn fss";//안드에서 post로 요청하는 방법을 찾을 때까지는 보류 getBody(request);
			
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
			System.out.println("응답완료");
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/plain;charset=utf-8");
			response.getWriter().write(data); 
		} catch (Exception e) {
			System.out.println("오류" + e.getMessage());
		}*/
	}
	
	public String getBody(HttpServletRequest request) { //https://powere.tistory.com/35
		 
        String body = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
 
        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {
           System.out.println("IOException" + ex.getMessage());
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                	System.out.println("IOException.." + ex.getMessage());
                }
            }
        }
 
        body = stringBuilder.toString();
        return body;
    }

}

//문자열 포함 여부: https://fruitdev.tistory.com/72
