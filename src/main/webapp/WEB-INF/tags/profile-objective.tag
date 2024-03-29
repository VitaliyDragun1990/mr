<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="objective" required="true" type="java.lang.String"%>
<%@ attribute name="summary" required="true" type="java.lang.String"%>
<%@ attribute name="showEdit" required="false" type="java.lang.Boolean"%>

<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">
			<i class="fa fa-bullseye"></i> Objective
			<c:if test="${showEdit}">
				<a href='<c:url value="/profile/edit#inputObjective" />' class="edit-block">Edit</a>
			</c:if>
		</h3>
	</div>
	<div class="panel-body">
		<h4><c:out value="${objective}"/></h4>
		<p>
			<strong>Summary of Qualifications:</strong> <br />
			<c:out value="${summary}"/>
		</p>
	</div>
</div>