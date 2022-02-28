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
				<h1>Edit project</h1>
			</div>
			<div class="col-6">
				<div class="col-12 d-flex flex-row-reverse">
					<a href="/dashboard" class="btn btn-primary">Dashboard</a><br>
				</div>
			</div>
		</div>
		<div class="row mt-3">
			<div class="col-6 p-5">
				<form:form action="/projects/edit" method="post" class="mt-4">
					<div class="mb-4 row">
						<div class="col-6">
							<h5>Title:</h5>
						</div>
						<div class="col-6">
							<input name="title" class="form-control" value="${project.title}"/>
						</div>
						<c:if test="${title_error != null}">
							<div class="col-12">
								<p class="text-danger"><c:out value="${title_error}" /></p>
							</div>
						</c:if>
					</div>
					<div class="mb-4 row">
						<div class="col-6">
							<h5>Description:</h5>
						</div>
						<div class="col-6">
							<textarea name="description" class="form-control"><c:out value="${project.description}"/></textarea>
						</div>
						<c:if test="${description_error != null}">
							<div class="col-12">
								<p class="text-danger"><c:out value="${description_error}" /></p>
							</div>
						</c:if>
					</div>
					<div class="mb-4 row">
						<div class="col-6">
							<h5>Date:</h5>
						</div>
						<div class="col-6">
							<div id="datepicker" class="input-group date" data-date-format="mm-dd-yyyy">
							    <input class="form-control" readonly name="dueDate" value="${strDueDate}"/>
							    <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
							</div>
						</div>
						<c:if test="${dueDate_error != null}">
							<div class="col-12">
								<p class="text-danger"><c:out value="${dueDate_error}" /></p>
							</div>
						</c:if>
					</div>
					<input type="hidden" value="${project.id}" name="id"/>
					<div class="d-flex flex-row-reverse">
					  	<input type="submit" value="Submit" class="btn btn-primary m-2"/>
					  	<a class="btn btn-primary m-2" href="/dashboard">Cancel</a>
					</div>
				</form:form>    
			</div>
		</div>
	</div>
</body>
</html>