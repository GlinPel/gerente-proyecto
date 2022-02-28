<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>New book</title>
	<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
	<script src="/webjars/jquery/jquery.min.js"></script>
	<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
		<div class="row mt-5">
			<div class="col-6">
				<h1>Project <c:out value="${project.title}"/></h1>
				<h4>Project Lead: <c:out value="${project.user.userName}"/></h4>
			</div>
			<div class="col-6">
				<div class="col-12 d-flex flex-row-reverse">
					<a href="/dashboard" class="btn btn-primary">Dashboard</a><br>
				</div>
			</div>
		</div>
		<div class="row mt-3">
			<div class="col-6 p-5">
				<form action="/tasks/create" method="post" class="mt-4">
					<div class="mb-4 row">
						<div class="col-6">
							<h5>Description:</h5>
						</div>
						<div class="col-6">
							<textarea name="description" class="form-control"></textarea>
						</div>
						<c:if test="${description_error != null}">
							<div class="col-12">
								<p class="text-danger"><c:out value="${description_error}" /></p>
							</div>
						</c:if>
					</div>
					<input type="hidden" name="project_id" value="${project.id}"/>
					<div class="d-flex flex-row-reverse">
					  	<input type="submit" value="Submit" class="btn btn-primary m-2"/>
					</div>
				</form>
			</div>
		</div>
		<div class="row mt-5"> 
			<div class="col-6">
				<c:forEach var="task" items="${project.tasks}">
					<h4>Added by <c:out value="${task.user.userName}"/> at <fmt:formatDate type = "both" dateStyle = "short" timeStyle = "short" value="${task.date}"/></h4>
					<p><c:out value="${task.description}"/></p>
				</c:forEach>
			</div>
		</div>
	</div>
</body>
</html>