<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<%@ attribute name="hobbies" required="true" type="java.util.List"%>
<%@ attribute name="profileId" required="true" type="java.lang.Long"%>

<security:authorize access="authenticated" var="authenticated"/>

<c:if test="${!empty hobbies }">
<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">
			<i class="fa fa-heart"></i> Hobby
			<c:if test="${authenticated}">
				<security:authentication property="principal.id" var="userId"/>
				<c:if test="${profileId == userId}" >
					<a href='<c:url value="/profile/edit/hobby" />' class="edit-block">Edit</a>
				</c:if>
			</c:if>
		</h3>
	</div>
	<div class="panel-body">
		<div class="hobbies">
			<table class="table table-bordered">
				<tbody>
					<c:forEach items="${hobbies }" var="hobby">
						<tr>
							<td><i class="fa fa-heart"></i></td>
							<td>${hobby.name}</td>
						</tr>					
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>
</c:if>