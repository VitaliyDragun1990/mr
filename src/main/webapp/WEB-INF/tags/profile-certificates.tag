<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<%@ attribute name="certificates" required="true" type="java.util.List"%>
<%@ attribute name="profileId" required="true" type="java.lang.Long"%>

<security:authorize access="authenticated" var="authenticated"/>

<c:if test="${!empty certificates}">
<div class="panel panel-primary certificates">
	<div class="panel-heading">
		<h3 class="panel-title">
			<i class="fa fa-certificate"></i> Certificates
			<c:if test="${authenticated}">
				<security:authentication property="principal.id" var="userId"/>
				<c:if test="${profileId == userId}" >
					<a href='<c:url value='/profile/edit/certificates' />' class="edit-block">Edit</a>
				</c:if>
			</c:if>
		</h3>
	</div>
	<div class="panel-body">
		<c:forEach items="${certificates }" var="certificate">
			<a class="thumbnail certificate-link" href="#" data-url='<c:url value="${certificate.largeUrl}" />' data-title="${certificate.name}">
				<img src='<c:url value="${certificate.smallUrl}" />' alt="${certificate.name}" class="img-responsive" />
				<span><c:out value="${certificate.name}" /></span>
			</a>
		</c:forEach>
	</div>
</div>
</c:if>