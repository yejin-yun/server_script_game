<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.sql.*, model1.*" %>
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

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	${user.uid}님 반갑습니다. <br>
	<table border="1">
		<thead>
			<tr>
				<td>글번호</td>
				<td>작성자</td>
				<td>제목</td>
				<td>내용</td>
				<td>등록일</td>
			</tr>
		</thead>
		<tbody>
<%
	//uid 유니크하게 줘야함...
	//아이디, 비번 이용하여 쿼리 수행
	String sql = "select * from tbl_board order by bno desc;";
	//쿼리를 수행하는 코드 
	Statement stmt = conn.createStatement();
	ResultSet rs = stmt.executeQuery(sql); //쿼리의 리턴 타입은 ResultSet
	//쿼리결과로 다음데이터가 존재하면 수행하라
	//담아서 뿌리는 방법이 있고, 여기서 직접 뿌리는 방법도 있고 다양함.  

	while(rs.next()) { //여기서 루프가 돎.
%>
		<tr>
			<td><%= rs.getInt("bno") %></td>
			<td><%= rs.getString("writer") %></td>
			<td><%= rs.getString("title") %></td>
			<td><%= rs.getString("content") %></td>
			<td><%= rs.getString("regdate") %></td>
		</tr>
<%
	}
	rs.close();
	stmt.close();
	
%>
			
		</tbody>
	</table>
</body>
</html>