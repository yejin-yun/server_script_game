<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="str" value="ABCD EFGH 1234"></c:set>

${fn:toLowerCase(str)}
<br>
${fn:indexOf(str, "EF")}
<br>
${fn:substring(str, 5, 11)} <!-- 5~10까지만 잘라짐 -->
<br>
${fn:replace(str, "ABCD", "^^")}
<br>
${str}