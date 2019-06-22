<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="resume" tagdir="/WEB-INF/tags" %>

<div class="container">
<div class="panel panel-default">
	<div class="panel-body">
		<h4 class="data-header">Technical skills in frameworks and technologies</h4>
		<hr />
		<div class="row" style="margin-bottom: 20px;">
			<div class="col-xs-5 col-sm-4 col-md-2 text-center"><strong>Category</strong></div>
			<div class="col-xs-7 col-sm-8 col-md-10 text-center"><strong>Framework/Technology</strong></div>
		</div>
		<c:url value="/profile/edit/skills" var="action" />
		<form:form action="${action}" method="post" commandName="skillForm">
			<div class="ui-block-container">
				<c:forEach var="skill" items="${skillForm.items}" varStatus="status">
					<resume:edit-skill-block index="${status.index}" skill="${skill}" />
				</c:forEach>
			</div>
			<div class="row">
				<div class="col-xs-12">
					<a href="javascript:void(0);">+ Add skill</a>
				</div>
			</div>
			<hr />
			<div class="row">
				<div class="col-xs-12 text-center">
					<input type="submit" class="btn btn-primary" value="Save" />
				</div>
			</div>
		</form:form>
	</div>
</div>
</div>