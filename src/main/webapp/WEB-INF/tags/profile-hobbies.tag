<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="hobbies" required="true" type="java.util.List"%>

<c:if test="${!empty hobbies }">
<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">
			<i class="fa fa-heart"></i> Hobby <a href='<c:url value="/profile/edit/hobby" />' class="edit-block">Edit</a>
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