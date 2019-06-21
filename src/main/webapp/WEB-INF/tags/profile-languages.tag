<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="resume" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<%@ attribute name="languages" required="true" type="java.util.List"%>
<%@ attribute name="profileId" required="true" type="java.lang.Long"%>

<security:authorize access="authenticated" var="authenticated"/>

<c:if test="${!empty languages }">
<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">
			<i class="fa fa-language"></i> Foreign languages
			<c:if test="${authenticated}">
				<security:authentication property="principal.id" var="userId"/>
				<c:if test="${profileId == userId}" >
					<a href='<c:url value="/profile/edit/languages" />' class="edit-block">Edit</a>
				</c:if>
			</c:if>
		</h3>
	</div>
	<div class="panel-body">
		<c:forEach items="${languages }" var="lang">
			<strong>${lang.name}:</strong> <resume:language-level level="${lang.level}"/> <resume:language-type type="${lang.type }"/> <br />
		</c:forEach>		
	</div>
</div>
</c:if>