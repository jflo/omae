<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>runner upload</title>
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
		<div class="col-md-5">
      <div class="panel panel-default">
        <div class="panel-heading"><strong>Chumm5 Upload</strong> <small><i>...send in the runners</i></small></div>
        <div class="panel-body">
			<c:if test="${not empty error}">
    			<div class="alert alert-danger" role="alert">${error}</div>
			</c:if>
                   <c:url value="/runner/upload" var="post_url"/>
          <form action="${post_url}" method="post" enctype="multipart/form-data" id="js-upload-form">
            <div class="form-inline">
              <div class="form-group">
              <div class="input-group">
  				    <span class="input-group-addon">Runner name</span>
  					<input type="text" name="name">
			  </div>
              
              <div class="input-group">
                <span class="input-group-addon">.chum5 file</span>
                <input type="file" name="chumfile" id="js-upload-files" alt=".chum5 file from Chummer5"><br>
                </div>
                
                <div class="input-group">
                <span class="input-group-addon">.xml export file</span>
                
                <input type="file" name="renderfile" id="js-upload-files" alt="the xml export from the print menu of Chummer5 for the above .chum5 file">
                </div>
                
              </div>
              <button type="submit" class="btn btn-sm btn-primary" id="js-upload-submit">Upload runner</button>
            </div>
          </form>         
        </div>
      </div>
    </div>
    </div>
</body>
</html>