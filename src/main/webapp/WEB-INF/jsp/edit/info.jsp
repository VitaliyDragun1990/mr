<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="resume" tagdir="/WEB-INF/tags" %>

<resume:edit-tab-header selected="info"/>

<div class="panel panel-default edit-profile">
	<div class="panel-body">
		<h1 class="text-center">About yourself</h1>
		<hr />
		<h4 class="data-header">Write a few worlds about yourself which can give you some advantage over other candidates.</h4>
		<resume:form-display-error-if-invalid formName="infoForm"/>
		<form:form commandName="infoForm" action="/profile/edit/info" method="post" cssClass="form-horizontal data-form">
			<security:csrfInput/>
			<resume:form-has-error path="info"/>
			<div class="form-group row">
				<div class="col-sm-12">
					<form:textarea path="info" cssClass="form-control" id="inputSummary" rows="7"
						placeholder="Example: An open visa in the US, married, 2 childer, have research experience in the field of solid state physics"/>
					<resume:form-error path="info"/>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-12 help-block">
					<blockquote>
						Provide additional information that can be useful for employer. Example: <br />
						<i>An open visa in the foreign country, marital status, real experience in the field that can be your domain aria
						during software engineering, possible research experience in the research institution, etc</i> <br />
						No need to specify your personal qualities, almost all candidates are responsible, sociable, respectable))) <br />
						
					</blockquote>
				</div>
			</div>
			<div class="form-group">
				<div class="row">
					<div class="col-xs-12 text-center">
						<button type="submit" class="btn btn-primary">Save</button>
					</div>
				</div>
			</div>
		</form:form>
	</div>
</div>