<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<body>
<h2>시각</h2>
<c:set var="date" value="<%= new java.util.Date() %>"></c:set>
<fmt:formatDate value="${date }" type="both" dateStyle="short"  
	timeStyle="short"/><br> <!-- value에는 현재시간을 쓰면 됨. -->
<fmt:formatDate value="${date }" type="both" dateStyle="medium"  
	timeStyle="medium"/><br> 
<fmt:formatDate value="${date }" type="both" dateStyle="long"  
	timeStyle="long"/><br> 
<fmt:formatDate value="${date }" type="both" dateStyle="full"  
	timeStyle="full"/><br> 
<br> 
<br> 
------------------------------
<br> 
<h2>숫자포맷</h2>
<fmt:formatNumber value="100000000000000" type="currency" currencySymbol="$" />
<br>
<fmt:formatNumber value="0.99" type="percent" />
<br>
---------------------
<br>
<h2>국가별 포맷</h2>
<!-- 국가별로 지정하려면 지역을 지정하면 됨 로케일을 지정하고 뭔가 데이터를 쓰면 해당 내용이 바뀜
	로케일만 지정해주면 간단히 포맷이 바뀜. 
-->
<fmt:setLocale value="ko_kr" />
<fmt:formatNumber value="100000000000000" type="currency" /> <!-- 이번엔 심볼 줄 필요 없음 -->
<br> 
<fmt:formatDate value="${date }" type="both" dateStyle="full"  
	timeStyle="full"/>
<br>
<fmt:setLocale value="en_us" />
<fmt:formatNumber value="100000000000000" type="currency" /> <!-- 이번엔 심볼 줄 필요 없음 -->
<br> 
<fmt:formatDate value="${date }" type="both" dateStyle="full"  
	timeStyle="full"/>
<br>
<fmt:setLocale value="ja_jp" />
<fmt:formatNumber value="100000000000000" type="currency" /> <!-- 이번엔 심볼 줄 필요 없음 -->
<br> 
<fmt:formatDate value="${date }" type="both" dateStyle="full"  
	timeStyle="full"/>
<br>
----------------------
<br>
<h2>세계별 시간대</h2>
<!-- timeZone하면 시간대를 지정할 수 있음 -->
<fmt:timeZone value="Asia/Hong_kong">
	홍콩: <fmt:formatDate value="${date }" type="both" />
</fmt:timeZone>
</body>
</html>