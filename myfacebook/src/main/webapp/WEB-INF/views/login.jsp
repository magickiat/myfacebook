<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>Login</title>
<link rel="stylesheet" type="text/css"
	href='<c:url value="/css/bootstrap.min.css" />' />
</head>
<body>

	<%@include file="nav.jsp"%>

	<div class="container">



		<div class="row">

			<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
				<div class="col-md-6 col-md-offset-3 text-danger">
					<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
				</div>
			</c:if>

			<div class="col-md-6 col-md-offset-3">
				<form:form cssClass="form" action="/login" method="POST">
					<div class="form-group">
						<label for="username">Username</label>
						<input type="text" class="form-control" id="username"
							name="username" />
					</div>

					<div class="form-group">
						<label for="password">Password</label>
						<input type="password" class="form-control" id="password"
							name="password" />
					</div>

					<div>
						<input type="submit" class="btn btn-success" value="Login" />
					</div>
				</form:form>
			</div>
		</div>
	</div>

</body>
</html>