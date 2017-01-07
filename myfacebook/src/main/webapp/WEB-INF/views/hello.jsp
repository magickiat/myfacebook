<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	Hello
	<sec:authorize access="isAuthenticated()">
		<sec:authentication var="user" property="principal" />
		<c:out value="${ user.fullname }"></c:out>

		<form:form servletRelativeAction="/logout" method="post">
			<input type="submit" value="Logout" />
		</form:form>

	</sec:authorize>
</body>
</html>