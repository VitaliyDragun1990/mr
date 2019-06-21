<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="resume" tagdir="/WEB-INF/tags"%>

<div class="panel panel-info small-center-block">
	<div class="panel-heading">
		<h3 class="panel-title">
			<i class="fa fa-sign-in"></i> Login
		</h3>
	</div>
	<div class="panel-body">
		<form action='<c:url value="/sign-in-handler" />' method="post">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			<c:if test="${!empty sessionScope.SPRING_SECURITY_LAST_EXCEPTION}">
				<div class="alert alert-danger" role="alert">
					<button type="button" class="close" data-dismiss="alert" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}
					<c:remove var="SPRING_SECURITY_LAST_EXCEPTION" scope="session"/>
				</div>
			</c:if>
			<div class="help-block">You can use your UID or Email or Phone as login</div>
			<div class="form-group">
				<label for="uid">Login</label>
				<input type="text" id="uid" name="uid" class="form-control" placeholder="UID or Email or Phone" required autofocus />
			</div>
			<div class="form-group">
				<label for="password">Password</label>
				<input type="password" id="password" name="password" class="form-control" placeholder="Password" required />
			</div>
			<div class="form-group">
				<label><input type="checkbox" name="remember-me" value="true" />Remember me</label>
			</div>
			<div class="form-group">
				<button type="submit" class="btn btn-primary pull-left">Login</button>
				<a href="#" class="pull-right">Restore access</a>
			</div>
		</form>
	</div>
</div>