<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>My Facebook</title>
<link rel="stylesheet" type="text/css"
	href='<c:url value="/css/bootstrap.min.css" />' />
<link rel="stylesheet" type="text/css"
	href='<c:url value="/css/main.css" />' />
</head>
<body>
	<%@include file="nav.jsp"%>


	<div class="container">

		<div class="row">
			<div class="col-md-4 col-md-offset-4 block">
				<form:form servletRelativeAction="/friend/search" method="get"
					modelAttribute="friendForm" cssClass="form-inline">
					<form:hidden path="userId" />
					<div class="form-group">
						<form:input cssClass="form-control" path="keyword"
							placeholder="Name or Email" />
						<input type="submit" class="btn btn-primary" value="Search" />
					</div>

				</form:form>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4 col-md-offset-4 block">
				<c:choose>
					<c:when test="${ not empty users }">
						<div class="row">
							<div class="col-sm-12">Found ${ users.size() }</div>
						</div>
						<hr />
						<div class="row">
							<c:forEach items="${ users }" var="user">
								<div class="col-sm-8">
									<div class="form-group">
										<a href="#">${user.name }</a>
									</div>
								</div>
								<div class="col-sm-4">
									<c:choose>
										<c:when test="${ user.friend }">
											<form action='<c:url value="/friend/remove" />' method="post" >
												<input type="hidden" name="userId" value="${ user.userId }" />
												<input type='hidden' name='${_csrf.parameterName}'
													value='${_csrf.token}' />
												<input type="submit" class="btn btn-danger" value="Unfriend" />
											</form>
										</c:when>
										<c:otherwise>
											<form action='<c:url value="/friend/add" />' method="post">
												<input type="hidden" name="userId" value="${ user.userId }" />
												<input type='hidden' name='${_csrf.parameterName}'
													value='${_csrf.token}' />
												<input type="submit" class="btn btn-success"
													value="Add friend" />
											</form>
										</c:otherwise>
									</c:choose>
								</div>
							</c:forEach>
						</div>
					</c:when>
					<c:otherwise>Not found a user</c:otherwise>
				</c:choose>
			</div>
		</div>


	</div>
	<%@include file="footer.jsp"%>
</body>
</html>