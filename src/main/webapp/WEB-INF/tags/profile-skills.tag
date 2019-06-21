<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<%@ attribute name="skills" required="true" type="java.util.List"%>
<%@ attribute name="profileId" required="true" type="java.lang.Long"%>

<security:authorize access="authenticated" var="authenticated"/>

<c:if test="${!empty skills}">
<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">
			<i class="fa fa-code"></i> Technical Skills
			<c:if test="${authenticated}">
				<security:authentication property="principal.id" var="userId"/>
				<c:if test="${profileId == userId}" >
					<a href='<c:url value="/profile/edit/skills" />' class="edit-block">Edit</a>
				</c:if>
			</c:if>
		</h3>
	</div>
	<div class="panel-body">
		<table class="table table-striped table-bordered">
			<tbody>
				<tr>
					<th style="width: 140px">Category</th>
					<th>Frameworks and technologies</th>
				</tr>
				<c:forEach items="${skills}" var="skill">
					<tr>
						<td>${skill.category}</td>
						<td>${skill.value}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
</c:if>