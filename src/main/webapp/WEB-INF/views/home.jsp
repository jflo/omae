<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>sup, omae?</title>
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
				<!-- Default panel contents -->
				<div class="panel-heading">Runners owned by ${user.userName}</div>
				<div class="panel-body">
					<p>
						<a href="<c:url value="/runner/uploadForm"/>">+ runner</a>
					</p>
				</div>

				<!-- Table -->
				<table class="table">
					<c:forEach items="${runners}" var="runner" varStatus="status">
						<tr>
							<td>${runner.name}</td>
							<td><a href="<c:url value="/runner/download/${runner.name}.chum5"/>"> download .chum5</a> </td>
							<td><a href="<c:url value="/runner/play/${runner.name}"/>">play</a> </td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
