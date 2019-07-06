<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="resume" tagdir="/WEB-INF/tags" %>

<resume:edit-tab-header selected="languages"/>

<div class="panel panel-default">
	<div class="panel-body">
		<h4 class="data-header">Foreign language proficiency levels</h4>
		<hr />
		<resume:form-display-error-if-invalid formName="languageForm"/>
		<form:form action="/profile/edit/languages" method="post" commandName="languageForm">
			<security:csrfInput/>
			<div id="ui-block-container" class="edit-languages">
				<c:forEach var="language" items="${languageForm.items}" varStatus="status">
					<resume:edit-language-block index="${status.index}" language="${language}"/>
				</c:forEach>
			</div>
			<div class="row">
				<div class="col-xs-12">
					<a href="javascript:resume.ui.addBlock();">+ Add language</a>
				</div>
			</div>
			<hr />
			<div class="row">
				<div class="col-xs-12 text-center">
					<input type="submit" class="btn btn-primary"  value="Save"/>
				</div>
			</div>
		</form:form>
	</div>
</div>

<script id="ui-block-template" type="text/x-handlebars-template">
	<resume:edit-language-block index="{{blockIndex}}" />
</script>