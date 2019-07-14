<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="resume" tagdir="/WEB-INF/tags"%>

<security:authorize access="isAuthenticated()">
	<security:authentication property="principal" var="CURRENT_USER"/>
	<c:set var="showEdit" value="${CURRENT_USER.id eq profile.id}"/>
</security:authorize>

	<div id="proifle-${profile.id}" class="row profile">
		<div class="col-xs-12 col-sm-6 col-md-4">
			<resume:profile-main fullName="${profile.fullName}" info="${profile.mainInfo}" contacts="${profile.contacts}" showEdit="${showEdit}" />
			<div class="hidden-xs">
				<resume:profile-languages languages="${profile.languages}" showEdit="${showEdit}"/>
				<resume:profile-hobbies hobbies="${profile.hobbies }" showEdit="${showEdit}" />
				<resume:profile-info info="${profile.info}" showEdit="${showEdit}" />
			</div>
		</div>
		<div class="col-xs-12 col-sm-6 col-md-8">
			<resume:profile-objective objective="${profile.mainInfo.objective}" summary="${profile.mainInfo.summary}" showEdit="${showEdit}"  />
			<resume:profile-skills skills="${profile.skills}" showEdit="${showEdit}" />
			<resume:profile-experience experience="${profile.experience}" showEdit="${showEdit}" />
			<resume:profile-certificates certificates="${profile.certificates}" showEdit="${showEdit}" />
			<resume:profile-courses courses="${profile.courses}" showEdit="${showEdit}" />
			<resume:profile-education educations="${profile.educations}" showEdit="${showEdit}" />
		</div>
		<div class="col-xs-12 visible-xs">
			<resume:profile-languages languages="${profile.languages}" showEdit="${showEdit}"  />
			<resume:profile-hobbies hobbies="${profile.hobbies }" showEdit="${showEdit}"  />
			<resume:profile-info info="${profile.info}" showEdit="${showEdit}"  />
		</div>
	</div>
	
	<c:if test="${fn:length(profile.certificates) > 0}">
		<content tag="js-init">
		<script>
			$(document).ready(function() {
				resume.initCertificateViewer();
			});
		</script>
		</content>
	</c:if>