<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Book list</title>
	<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
	<script src="/webjars/jquery/jquery.min.js"></script>
	<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
		<div class="row mt-5">
			<div class="col-6">
				<h1>Welcome ${ user.userName }!</h1>
			</div>
			<div class="col-6">
				<div class="col-12 d-flex flex-row-reverse">
					<a href="/logout" class="btn btn-primary">Logout</a><br>
				</div>
				<div class="col-12 d-flex flex-row-reverse mt-3">
					<a href="/projects/new" class="btn btn-primary">+ New Project</a>
				</div>
			</div>
		</div>
		<div class="row mt-5">
			<div class="col-12">
				<h4>All projects</h4>
				<table class="table">
				  <thead>
				    <tr>
				      <th scope="col">Project</th>
				      <th scope="col">Team Lead</th>
				      <th scope="col">Due date</th>
				      <th scope="col">Actions</th>
				    </tr>
				  </thead>
				  <tbody>
				  	<c:forEach var="project" items="${noProjectByUser}">
					  	<tr>
					      <td>
					      	<a href="/projects/${ project.id }"><c:out value="${project.title}" /></a>
					      </td>
					      <td><c:out value="${project.user.userName}" /></td>
					      <td><fmt:formatDate pattern = "MM-dd-yyyy" value="${project.dueDate}"/></td>
					      <td>
				    		<form action="/projects/addprojectuser" method="POST">
					    		<input type="hidden" name="project_id" value="${project.id}" />
					    		<button class="btn btn-primary" type="submit">Join team</button>
					    	</form>
						  </td>
					    </tr>
				  	</c:forEach>
				  </tbody>
				</table>
			</div>
		</div>
		<div class="row mt-5">
			<div class="col-12">
				<h4>Your projects</h4>
				<table class="table">
				  <thead>
				    <tr>
				      <th scope="col">Project</th>
				      <th scope="col">Team Lead</th>
				      <th scope="col">Due date</th>
				      <th scope="col">Actions</th>
				    </tr>
				  </thead>
				  <tbody>
				  	<c:forEach var="project" items="${projectsByUser}">
					  	<tr>
					      <td>
					      	<a href="/projects/${ project.id }"><c:out value="${project.title}" /></a>
					      </td>
					      <td><c:out value="${project.user.userName}" /></td>
					      <td><fmt:formatDate pattern = "MM-dd-yyyy" value="${project.dueDate}"/></td>
					      <c:choose>
						    <c:when test="${ project.user.id == user.id }">
						    	<td class="d-flex">
							    	<a class="btn btn-primary" style="margin-right: 1rem;" href="/projects/edit/${project.id}/">Edit</a>
							    	<form action="/projects/delete" method="POST">
							    		<input type="hidden" name="id" value="${project.id}" />
							    		<button class="btn btn-danger" type="submit">Delete</button>
							    	</form>
							    </td>
						    </c:when>    
						    <c:otherwise>
						    	<td>
							      <form action="/projects/removeprojectuser" method="POST">
							    	<input type="hidden" name="project_id" value="${project.id}" />
							    	<button class="btn btn-primary" type="submit">Leave team</button>
							    </form>
							    </td>
						    </c:otherwise>
						</c:choose>
					    </tr>
				  	</c:forEach>
				  </tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>