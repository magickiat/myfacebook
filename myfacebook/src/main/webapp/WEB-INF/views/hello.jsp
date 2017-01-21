<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
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
				<form:form servletRelativeAction="/post" method="post"
					modelAttribute="postForm">
					<form:hidden path="userId" />
					<div class="form-group">
						<form:textarea path="content" cssClass="form-control" rows="3"
							placeholder="คุณกำลังคิดอะไรอยู่?" />
					</div>
					<div class="text-right">
						<input type="submit" class="btn btn-primary" value="Post"></input>
					</div>
				</form:form>
			</div>
		</div>


		<c:if test="${ not empty posts }">
			<div class="row">
				<c:forEach items="${ posts }" var="post">
					<div class="col-md-4 col-md-offset-4  block">
						<div>
							<strong>${ post.createdUser.name }</strong>
							<p>
								<fmt:formatDate value="${ post.createdDate }"
									pattern="yyyy/MM/dd HH:mm" />
							</p>
						</div>
						<hr />
						<div>${ post.content }</div>

						<c:choose>
							<c:when test="${ not empty post.comments }">
								<hr />
								<div>
									<strong>${ post.comments.size() } Comments</strong>
								</div>
								<br />
								<c:forEach items="${ post.comments }" var="comment">
									<div>
										<strong>${ comment.createdUser.name }</strong>
										<fmt:formatDate value="${ comment.createdDate }"
											pattern="yyyy/MM/dd HH:mm" />
									</div>
									<div>${ comment.content }</div>
									<hr />
								</c:forEach>
							</c:when>
							<c:otherwise>
								<hr />
							</c:otherwise>
						</c:choose>



						<div>
							<form:form cssClass="form-inline"
								servletRelativeAction="/comment">
								<div class="form-group">
									<sec:authorize access="isAuthenticated()">
										<sec:authentication var="user" property="principal" />
										<input type="hidden" name="userId" value="${ user.id }" />
									</sec:authorize>

									<input type="hidden" name="postId" value="${ post.id }" />
									<textarea class="form-control" rows="1" name="content"></textarea>
									<input type="submit" class="btn btn-default" value="Post" />
								</div>
							</form:form>
						</div>
					</div>
				</c:forEach>
			</div>
		</c:if>


	</div>
	<%@include file="footer.jsp"%>
</body>
</html>