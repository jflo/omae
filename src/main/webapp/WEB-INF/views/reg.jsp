<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>'nother new omae</title>
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
					<strong>Papers, please</strong>
				</div>
				<div class="panel-body">
					<form class="form-horizontal" action='/omae/chummer/register'
						method="POST">
						<fieldset>
							<div id="legend">
								<legend class="">Register</legend>
							</div>
							<div class="control-group">
								<!-- Username -->
								<label class="control-label" for="username">Username</label>
								<div class="controls">
									<input type="text" id="username" name="username" placeholder=""
										class="input-xlarge">
									<p class="help-block">Username can contain any letters or
										numbers, without spaces</p>
								</div>
							</div>

							<div class="control-group">
								<!-- E-mail -->
								<label class="control-label" for="email">E-mail</label>
								<div class="controls">
									<input type="text" id="email" name="email" placeholder=""
										class="input-xlarge">
									<p class="help-block">Please provide your E-mail</p>
								</div>
							</div>

							<div class="control-group">
								<!-- Password-->
								<label class="control-label" for="password">Password</label>
								<div class="controls">
									<input type="password" id="password" name="password"
										placeholder="" class="input-xlarge">
									<p class="help-block">Password should be at least 4
										characters</p>
								</div>
							</div>

							<div class="control-group">
								<!-- Password -->
								<label class="control-label" for="password_confirm">Password
									(Confirm)</label>
								<div class="controls">
									<input type="password" id="password_confirm"
										name="password_confirm" placeholder="" class="input-xlarge">
									<p class="help-block">Please confirm password</p>
								</div>
							</div>

							<div class="control-group">
								<!-- Button -->
								<div class="controls">
									<button class="btn btn-success">Register</button>
								</div>
							</div>
						</fieldset>
					</form>
				</div>

			</div>
		</div>

	</div>

</body>
</html>