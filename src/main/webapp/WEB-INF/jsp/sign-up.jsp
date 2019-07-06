<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="resume" tagdir="/WEB-INF/tags" %>

<div class="panel panel-info small-center-block">
	<div class="panel-heading">
		<h3 class="panel-title">
			<i class="fa fa-user-plus"></i> Enter your personal data
		</h3>
	</div>
	<div class="panel-body">
		<resume:form-display-error-if-invalid formName="profileForm"/>
		<form:form action="/sign-up" commandName="profileForm" method="post">
			<security:csrfInput/>
			<div class="help-block text-justify">
				Keep in mind that provided first and last name can't be changed in the future! Please, provide real first and last name!
			</div>
			
			<resume:form-has-error path="firstName"/>
			<div class="form-group ${hasError ? 'has-error has-feedback' : ''}">
				<label for="firstName">First name</label>
				<form:input path="firstName" id="firstName" cssClass="form-control" placeholder="Example: Richard" required="required"/>
				<resume:form-error path="firstName"/>
			</div>
			
			<resume:form-has-error path="lastName"/>
			<div class="form-group ${hasError ? 'has-error has-feedback' : ''}">
				<label for="lastName">Last name</label>
				<form:input path="lastName" id="lastName" cssClass="form-control" placeholder="Example: Hendricks" required="required"/>
				<resume:form-error path="lastName"/>
			</div>
			
			<resume:form-has-error path="password"/>
			<div class="form-group ${hasError ? 'has-error has-feedback' : ''}">
				<label for="password">Password</label>
				<form:password path="password" id="password" cssClass="form-control" required="required"/>
				<resume:form-error path="password"/>
			</div>
			
			<resume:form-has-error path="confirmPassword"/>
			<div class="form-group ${hasError ? 'has-error has-feedback' : ''}">
				<label for="confirmPassword">Confirm password</label>
				<form:password path="confirmPassword" id="confirmPassword" cssClass="form-control" required="required"/>
				<resume:form-error path="confirmPassword"/>
			</div>
			
			<button type="submit" class="btn btn-success">Sign up</button>
			<a href='<c:url value="/fbLogin" />' class="btn btn-primary pull-right"><i class="fa fa-facebook"></i>&nbsp;&nbsp;Sign up with Facebook</a>
		</form:form>
	</div>
</div>