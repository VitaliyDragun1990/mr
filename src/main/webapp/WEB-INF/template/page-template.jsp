<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="eng">
<head>
<title>My Resume</title>
<meta charset="utf-8" >
<meta http-equiv="x-ua-compatible" content="ie=edge" >
<meta name="viewpoint" content="with=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<security:csrfMetaTags/>
<jsp:include page="../section/css.jsp"/>
</head>
<body class="resume">
	<jsp:include page="../section/navigation.jsp"/>
	<section class="container">
		<sitemesh:write property='body'/>
	</section>
	<jsp:include page="../section/footer.jsp"/>
	<jsp:include page="../section/js.jsp"/>
	<sitemesh:write property='page.js-init' />
</body>
</html>