<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html>
<head>
<title>Search Form</title>
</head>
<body>
	<h2>Input name</h2>
	<c:if test="${invalid}">
		<h5 style="color:red;">Please input correct value!!!</h5>
	</c:if>
	<form action='<c:url value="/search" />' method="post">
		<input name="query" />
		<input type="submit" value="Search" />	
	</form>
</body>
</html>