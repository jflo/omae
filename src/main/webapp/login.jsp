<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login, omae</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">

<!-- Optional theme -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css">

<!-- Latest compiled and minified JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container" style="margin-top: 30px">
		<div class="col-md-4">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">
					<c:if test="${not empty param.login_error}">
    					<div class="alert alert-danger" role="alert">Overwatch Initiated, retry?</div>
					</c:if>
					<c:if test="${not empty param.welcome}">
    					<div class="alert alert-success" role="alert">Welcome aboard. Now login.</div>
					</c:if>			
					</h3>
				</div>
				<div class="panel-body">
					<c:url var="post_url"  value="/j_spring_security_check" />
					<form role="form" action="<c:url value="${post_url}"/>"
						method="post">
						<div class="form-group">
							<input type="username" name="j_username" class="form-control"
								id="exampleInputUsername1" placeholder="Enter username">
						</div>
						<div class="form-group">
							<input type="password" name="j_password" class="form-control"
								id="exampleInputPassword1" placeholder="Password">
						</div>
						<button type="submit" class="btn btn-sm btn-default">Sign
							in</button>
					</form>
				</div>
				<div class="panel-footer">
					<a href="<c:url value="/reg"/>">new here, omae?</a>
				</div>
			</div>
		</div>

	</div>

</body>
</html>