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
	<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker.css" rel="stylesheet" type="text/css" />
	<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/js/bootstrap-datepicker.js"></script>
	<script>
	$(function () {
	  $("#datepicker").datepicker({ 
	        autoclose: true
	  }).datepicker();
	});
	</script>
</head>
<body>
	<div class="container">
		<div class="row mt-5">
			<div class="col-6">
				<h1>Project Details</h1>
			</div>
			<div class="col-6">
				<div class="col-12 d-flex flex-row-reverse">
					<a href="/dashboard" class="btn btn-primary">Dashboard</a><br>
				</div>
			</div>
		</div>
		<div class="row mt-3">
			<div class="col-6 p-5">
				<div class="mb-4 row">
					<div class="col-6">
						<h5>Title:</h5>
					</div>
					<div class="col-6">
						<h5><c:out value="${project.title}"></c:out></h5>
					</div>
				</div>
				<div class="mb-4 row">
					<div class="col-6">
						<h5>Description:</h5>
					</div>
					<div class="col-6">
						<h5><c:out value="${project.description}"></c:out></h5>
					</div>
				</div>
				<div class="mb-4 row">
					<div class="col-6">
						<h5>Date:</h5>
					</div>
					<div class="col-6">
						<h5><c:out value="${strDueDate}"></c:out></h5>
					</div>
				</div>
			</div>
		</div>
		<c:if test="${project.user.id == user.id}">
		<div class="row mt-5">
			<div class="col-12">
				<form action="/projects/delete">
					<input type="hidden" name="id" value="${project.id}"/>
					<button class="btn btn-danger" type="submit">Delete project</button>
				</form>
			</div>
		</div>
		</c:if>
	</div>
</body>
</html>