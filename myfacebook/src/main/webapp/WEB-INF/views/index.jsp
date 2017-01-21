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

<title>MyFacebook</title>
<link rel="stylesheet" type="text/css"
	href='<c:url value="/css/bootstrap.min.css" />' />
</head>
<body>

	<%@include file="nav.jsp"%>


	<div class="container">
		<div class="row">
			<div class="col-md-5 text-center">
				<h2>สวัสดีชาวโลก</h2>
				<img src='<c:url value="/img/accepted.jpg"  />' />
			</div>
			<div class="col-md-5">
				<form:form cssClass="form" action="/register" method="POST"
					modelAttribute="registerForm">

					<div class="form-group">
						<form:label path="name">Name <form:errors
								cssClass="text-danger" path="name"></form:errors>
						</form:label>
						<form:input cssClass="form-control" path="name" />
					</div>

					<div class="form-group">
						<form:label path="email">Email <form:errors
								cssClass="text-danger" path="email"></form:errors>
						</form:label>
						<form:input cssClass="form-control" path="email" />
					</div>

					<div class="form-group">
						<form:label path="password">Password <form:errors
								cssClass="text-danger" path="password"></form:errors>
						</form:label>
						<form:password cssClass="form-control" path="password" />
					</div>

					<div class="form-group">
						<form:label path="password">Re-Password  <form:errors
								cssClass="text-danger" path="rePassword"></form:errors>
						</form:label>
						<form:password cssClass="form-control" path="rePassword" />
					</div>

					<div class="text-center">
						<input type="submit" class="btn btn-success" value="Register"
							style="width: 80px;" />
						<input type="reset" class="btn btn-default" value="Clear"
							style="width: 80px;" />
					</div>
				</form:form>
			</div>
		</div>
	</div>

	<%@include file="footer.jsp"%>
</body>
</html>