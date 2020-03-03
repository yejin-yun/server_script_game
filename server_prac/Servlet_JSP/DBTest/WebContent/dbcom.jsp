<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.sql.*"%>
<%!

	Connection conn = null;
	String url = "jdbc:mysql://localhost/test";
	String id = "owner";
	String pw = "1234";

	//초기화는 jsp가 한번 올라올 때 그 때 호출되는 단 한번의 메소드. 여기에 멤버변수 초기화를 함.
	public void jspInit() {
		System.out.println("멤버변수 초기화 : DB연결");
		//1단계: 드라이버 로드
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

	public void jspDestroy() { //소멸할 때는 멤버변수 해제
		try{
			if(conn != null)  {
				conn.close();
				System.out.println("연결 종료");
			}
		}
		catch (Exception e) {
			System.out.println("연결 종료 실패");
		}
		System.out.println("멤버변수 해제 : DB연결 종료");
	}
%>

디비연결테스트(요청하는 화면)
