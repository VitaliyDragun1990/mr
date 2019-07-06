<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="resume" tagdir="/WEB-INF/tags"%>

<%@ attribute name="languages" required="true" type="java.util.List"%>
<%@ attribute name="showEdit" required="false" type="java.lang.Boolean"%>

<c:if test="${!empty languages }">
<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">
			<i class="fa fa-language"></i> Foreign languages
			<c:if test="${showEdit}">
				<a href='<c:url value="/profile/edit/languages" />' class="edit-block">Edit</a>
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