<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="resume" tagdir="/WEB-INF/tags" %>

<resume:edit-tab-header selected="education"/>

<div class="panel panel-default">
	<div class="panel-body">
		<h4 class="data-header">Education</h4>
		<h6 class="text-center help-block">(Descending order)</h6>
		<hr />
		<resume:form-display-error-if-invalid formName="educationForm"/>
		<form:form action="/profile/edit/education" method="post" commandName="educationForm">
			<security:csrfInput/>
			<div id="ui-block-container">
				<c:forEach var="education" items="${educationForm.items}" varStatus="status">
					<resume:edit-education-block index="${status.index}" education="${education}"/>
				</c:forEach>
			</div>
			<div class="row">
				<div class="col-xs-12">
					<a href="javascript:resume.ui.addBlock();">+ Add institution</a>
				</div>
			</div>
			<hr />
			<div class="row">
				<div class="col-xs-12 text-center">
					<input type="submit" class="btn btn-primary" value="Save"/>
				</div>
			</div>
		</form:form>
	</div>
</div>

<script id="ui-block-template" type="text/x-handlebars-template">
	<resume:edit-education-block index="{{blockIndex}}" />
</script>