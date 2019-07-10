<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="custom" uri="/WEB-INF/tld/custom.tld" %>

<%@ attribute name="hobbies" required="true" type="java.util.List"%>
<%@ attribute name="showEdit" required="false" type="java.lang.Boolean"%>

<c:if test="${!empty hobbies }">
<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">
			<i class="fa fa-heart"></i> Hobby
			<c:if test="${showEdit}">
				<a href='<c:url value="/profile/edit/hobbies" />' class="edit-block">Edit</a>
			</c:if>
		</h3>
	</div>
	<div class="panel-body">
		<div class="hobbies">
			<table class="table table-bordered">
				<tbody>
					<c:forEach items="${hobbies }" var="hobby">
						<custom:hobby-class hobbyName="${hobby.name}" var="hobbyCssClassName"/>
						<tr>
							<td><i class="fa hobby ${hobbyCssClassName}"></i></td>
							<td>${hobby.name}</td>
						</tr>					
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>
</c:if>