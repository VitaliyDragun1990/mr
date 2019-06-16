<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="skills" required="true" type="java.util.List"%>

<c:if test="${!empty skills}">
<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">
			<i class="fa fa-code"></i> Technical Skills <a href='<c:url value="/profile/edit/skills" />' class="edit-block">Edit</a>
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