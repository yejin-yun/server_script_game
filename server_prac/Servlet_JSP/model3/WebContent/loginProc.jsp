<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.sql.*, model1.*" %>
<!-- 로그인 정보를 가지고 빈에 담고, 디비에 연결하여 회원인지 체크하는 쿼리를 수행한다.-->
<%!
	//jsp life cycle을 이용한 디비 쿼리법이 효율적. 
	Connection conn = null;
	String url = "jdbc:mysql://localhost:3306/mydb";
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

<%
	//uid 유니크하게 줘야함...
	//아이디, 비번 이용하여 쿼리 수행
	String uid = request.getParameter("uid"); //실제로는 request.getParameter 쓰면 될 듯
	String upw = request.getParameter("upw");
	String sql = "select * from tbl_user where uid='"+ uid +"' and upw='" + upw + "';";
	//쿼리를 수행하는 코드 
	Statement stmt = conn.createStatement();
	ResultSet rs = stmt.executeQuery(sql); //쿼리의 리턴 타입은 ResultSet
	//쿼리결과로 다음데이터가 존재하면 수행하라
	User userBean = null;
	if(rs.next()) {
		userBean = new User();
		userBean.setIdx(rs.getInt("idx"));
		userBean.setUid(rs.getString("uid"));
		userBean.setUpw(rs.getString("upw"));
		userBean.setRegdate(rs.getString("regdate"));
	}
	
	rs.close();
	stmt.close();
	
	if(userBean == null) {
		RequestDispatcher rd = request.getRequestDispatcher("login.jsp"); //loginError.jsp여기서 포워딩 시키면 될 거 같다. 
		rd.forward(request, response);
	}
	else {
		//사용자 정보를 같이 보내고 싶다면 
		request.setAttribute("user", userBean);
		RequestDispatcher rd = request.getRequestDispatcher("bbs.jsp");
		rd.forward(request, response);
	}
%>

