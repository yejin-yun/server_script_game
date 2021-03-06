<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- jquery 설정이 된 거. 즉 사용할 수 있게 된 거. 사용하려면 스크립트를 열기  -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){ /* 이 함수는 문서가  다 로딩이 되면 호출된다. == html이 dom으로 올라오면 호출된다. */
	//제이쿼리는 기본적인 객체를 $()로 표현함. 문서 객체가 들어오게 되고 혹은 특정 요소들을 찾을 수 있고 그렇게 찾는 방법을 select라고 함. 
	//alert("hi");
	//submit() 이벤트를 잡아서 ajax로 통신한다. 
	
	//1. 일단 폼태그를 찾아야 됨. 하나밖에 없으니까 form으로 바로 써줌.
	//2. submit() 함수를 호출해줌.
	//3. 이때 일어나는 이벤트를 비동기로 잡아서 익명함수 집어넣어주면 여기서 잡힘.
	//4. 우리는 전송되는 데이터를 잡아서 아작스로 가야됨. 아작스는 현재화면이 바뀌지 않고 바로 통신만 하고 오는 방식.  
	$("form").submit(function(e){
		//통신 --> 아작스 통신을 할 것.
		$.ajax({ //이 안에 인자값을 주면 됨. 
			url:"/m/loginProc", //어디로 갈것인가.
			data:$("form").serialize(), //$(form).serialize(): uid, upw 값이 uid=guest&upw=1234으로 가게 되어있음//data는 파라미터 --> 파라미터는 폼태그를 가지고 하면 됨.
			dataType:"json", //응답 타입이 무엇인가. 데이터 타입은 json으로 가게 될 것. 
			type:"get", //get or post default가 get
			success:function(json) { //성공을 하면 제이슨으로 오고
				//성공하면 여기서 성공 결과를 보고 화면 처리를 하면 됨.
				console.log(json);
			},
			error:function(err) { //에러나면 실패
				//로그인실패
				alert("로그인 실패");
				//console.log(err);
			}
		});
		
		//데이터 전송을 막는 방법: 이벤트를 매개변수로 받아서 preventDefault()를 치면 데이터가 가지 않음. 이를 이용해 이벤트를 중단 시킬 수 있음. 
		e.preventDefault(); //이벤트 중단
		return false; //return false를 통해서도 이벤트 중단 가능 둘 중에 하나 쓰면 됨. 
	});
});
/* 문서 객체가 들어오게 됨. 혹은 특정 요소들을 찾을 수 있게 됨. 그렇게 찾는 방법을 설렉트라고 함. 제이쿼리의 설렉트는 css의 설렉트와 동일 */
</script>
</head>
<body>
	<fieldset>
		<form name="frm1" action="/m/loginProc"> <!-- mvc에 따르면 서블릿으로 가야됨.  -->
			<input type="text" name="uid">
			<input type="password" name="upw">
			<input type="submit" value="로그인">
		</form>
	</fieldset>
</body>
</html>
<!-- 이렇게 jsp에서 직관적으로 흐름에 맞춰서 짜는게 model1 방식 -->