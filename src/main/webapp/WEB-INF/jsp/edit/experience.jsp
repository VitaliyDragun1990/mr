<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="resume" tagdir="/WEB-INF/tags" %>

<resume:edit-tab-header selected="experience"/>

<div class="panel panel-default">
	<div class="panel-body">
		<h4 class="data-header">Practical experience</h4>
		<h6 class="text-center help-block">(Descending order)</h6>
		<hr />
		<resume:form-display-error-if-invalid formName="experienceForm"/>
		<form:form action="/profile/edit/experience" method="post" commandName="experienceForm">
			<security:csrfInput/>
			<div id="ui-block-container">
				<c:forEach var="experience" items="${experienceForm.items}" varStatus="status">
					<resume:edit-experience-block index="${status.index}" experience="${experience}"/>
				</c:forEach>
			</div>
			<div class="row">
				<div class="col-xs-12">
					<a href="javascript:resume.ui.addBlock();">+ Add</a>
				</div>
			</div>
			<hr />
			<div class="row">
				<div class="col-xs-12 text-center">
					<input type="search" class="btn btn-primary" value="Save" />
				</div>
			</div>
		</form:form>
	</div>
</div>

<script id="ui-block-template" type="text/x-handlebars-template">
	<resume:edit-experience-block index="{{blockIndex}}"/>
</script>