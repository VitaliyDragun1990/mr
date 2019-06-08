<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html>
<head>
<title>Search Result</title>
</head>
<body>
	<h2>Your name: ${name}</h2>
	<a href='<c:url value="/search" />'>Try again</a>
</body>
</html>