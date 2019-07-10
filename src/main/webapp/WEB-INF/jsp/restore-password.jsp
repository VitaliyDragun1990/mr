<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="resume" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="panel panel-info small-center-block">
	<div class="panel-heading">
		<h3 class="panel-title">
			<i class="fa fa-unlock-alt"></i> New password for account
		</h3>
	</div>
	<div class="panel-body edit-password">
		<resume:form-display-error-if-invalid formName="passwordForm"/>
		<form:form action="/profile/edit/password/restore" commandName="passwordForm" method="post" >
			<form:hidden path="token" value="${token}"/>
			<div class="help-block">Enter new password and confirm it</div>
			<resume:form-has-error path="password"/>
			<div class="form-group ${hasError ? 'has-error has-feedback' : ''}">
				<label for="password" class="control-label">New password</label>
				<form:password path="password" id="password" cssClass="form-control" required="required"/>
				<resume:form-error path="password"/>
			</div>
			
			<resume:form-has-error path="confirmPassword"/>
			<div class="form-group ${hasError ? 'has-error has-feedback' : ''}">
				<label for="confirmPassword" class="control-label">Confirm password</label>
				<form:password path="confirmPassword" id="confirmPassword" cssClass="form-control" required="required"/>
				<resume:form-error path="confirmPassword"/>
			</div>
			<button type="submit" class="btn btn-primary">Change password</button>
		</form:form>
	</div>
</div>