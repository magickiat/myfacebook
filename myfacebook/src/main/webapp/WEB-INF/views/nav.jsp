<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>My Facebook</title>
</head>
<body>
	<nav class="navbar navbar-default navbar-static-top">
		<div class="container-fluid">

			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
					aria-expanded="false">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">My Facebook</a>
			</div>


			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<li class="active">
						<a href='<c:url value="/" />'>Home <span class="sr-only">(current)</span></a>
					</li>
					<sec:authorize access="isAnonymous()">
						<li>
							<a href='<c:url value="/login" />'>Login</a>
						</li>
					</sec:authorize>
					<sec:authorize access="isAuthenticated()">
						<li>
							<a href='<c:url value="/friend" />'> <span
									class="glyphicon glyphicon glyphicon-user" aria-hidden="true">
									<c:if test="${ not empty friendsWaitForApprove }">${ friendsWaitForApprove.size() }</c:if>
								</span>
							</a>
						</li>
						<li>
							<a href="#"> <sec:authorize access="isAuthenticated()">
									<sec:authentication var="user" property="principal" />
									<c:out value="${ user.fullname }"></c:out>

								</sec:authorize>
							</a>
						</li>

						<li>

							<form:form cssClass="navbar-form" servletRelativeAction="/logout"
								method="post">
								<input type="submit" class="btn btn-danger" value="Logout" />
							</form:form>
						</li>
					</sec:authorize>
				</ul>
			</div>

		</div>
	</nav>
</body>
</html>