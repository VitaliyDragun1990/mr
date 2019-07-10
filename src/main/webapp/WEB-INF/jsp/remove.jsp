<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<security:authentication property="principal" var="CURRENT_USER"/>

<div class="panel panel-warning">
	<div class="panel-heading">
		<h3 class="panel-title">
			<i class="fa fa-exclamation-circle"></i> Account removal - permanent operation!
		</h3>
	</div>
	<div class="panel-body">
		<div class="row">
			<div class="col-xs-12 text-center">
				<h4>Account removal: <strong>CAN'T BE CANCELED</strong>. Please, confirm removal: <strong>${CURRENT_USER.username}</strong>!</h4>
			<hr />
			<form action='<c:url value="/profile/remove" />' method="post">
				<security:csrfInput/>
				<input type="submit" value="Confirm removal" class="btn btn-danger" />
			</form>
			</div>
		</div>
	</div>
</div>