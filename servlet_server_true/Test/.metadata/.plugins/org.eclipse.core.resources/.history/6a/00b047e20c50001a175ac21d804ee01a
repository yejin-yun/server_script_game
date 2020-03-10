<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 해야할 것 -->
1. DB하고 연결하는 구조 작성(mysql.jar) 설정 --> maven project로 convert
2. 빈2개 --> 로그인용, 게시물 용(빈은 테이블보고 만들면 됨. )
3. jsp 1개 --> 이 친구가 다 처리함. 
경우에 따라서는 jsp 2개 가져도 됨. --> 로그인 처리, 게시물 용 --> 우리는 이것을 할 거. 
구성: login.jsp --> loginProc(로그인 되면 됨) --> bbs(게시물 뿌려줌)

create table tbl_board(
	bno int not null auto_increment,
	title varchar(200) not null,
	content text null,
	writer varchar(50) not null,
	regdate timestamp not null default now(),
	viewcnt int default 0,
	replycnt int default 0,
	primary key(bno)
);
create table tbl_user(
	idx int(11) not null auto_increment,
	uid varchar(50) not null, 
	upw varchar(50) not null, 
	regdate timestamp not null default now(),
	primary key(idx)
);
-- 전체 게시물 가져오기
select * from tbl_board order by bno desc;

select * from tbl_user;

-- 로그인
select * from tbl_user where uid='guest' and upw='1234';