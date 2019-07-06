<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="resume" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tld/custom.tld" %>

<spring:eval expression="@properties.getProperty('profile.hobbies.max')" var="maxHobbies"/>
<resume:edit-tab-header selected="hobbies"/>

<div id="hobbyContainer" class="panel panel-default" data-csrf-value="${_csrf.token }">
	<div class="panel-body">
		<h4 class="data-header">Some employers pay attention to candidate's hobbies</h4>
		<h6 class="text-center help-block">You can choose at max ${maxHobbies} items!</h6>
		<hr />
		<div id="ui-block-container" class="row" data-max-hobbies="${maxHobbies}">
			<c:forEach var="hobby" items="${hobbies}">
				<div class="col-xs-6 col-sm-4 col-md-3 col-lg-2">
					<custom:hobby-class hobbyName="${hobby.name}" var="hobbyCssClassName"/>
					<button type="button" class="btn btn-block btn-default hobby-btn ${hobby.selected ? 'active' : ''}"
						data-hobby="${hobby.name}" data-toggle="button" aria-pressed="false">
						<i class="fa hobby ${hobbyCssClassName}"></i>${hobby.name}
					</button>
				</div>
			</c:forEach>
		</div>
		<hr />
		<div class="alert alert-danger" id="errorAlert" role="alert" style="display:none;">
			<button type="button" class="close" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			<strong>Error!</strong> Please choose at max ${maxHobbies} items!
		</div>
		<div class="row">
			<div class="col-xs-12 text-center">
				<a href="javascript:resume.hobbies.save();" class="btn btn-primary">Save</a>
			</div>
		</div>
	</div>
</div>