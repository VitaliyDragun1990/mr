<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>

<!DOCTYPE html>
<html lang="eng">
<head>
<meta name="viewpoint" content="with=device-width, initial-scale=1">
<jsp:include page="../section/css.jsp"/>
</head>
<body class="resume">
	<jsp:include page="../section/header.jsp"/>
	<jsp:include page="../section/nav.jsp"/>
	<section class="main">
		<sitemesh:write property='body'/>
	</section>
	<jsp:include page="../section/footer.jsp"/>
	<jsp:include page="../section/js.jsp"/>
</body>
</html>