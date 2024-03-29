<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="info" required="true" type="java.lang.String"%>
<%@ attribute name="showEdit" required="false" type="java.lang.Boolean"%>

<c:if test="${!empty info}">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title">
				<i class="fa fa-info-circle"></i> Additional info
				<c:if test="${showEdit}">
					<a href='<c:url value="/profile/edit/info" />' class="edit-block">Edit</a>
				</c:if>
			</h3>
		</div>
		<div class="panel-body text-justify">${info}</div>
	</div>
</c:if>