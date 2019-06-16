<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="resume" tagdir="/WEB-INF/tags"%>

<div class="container">
	<div class="row">
		<div class="col-md-4 col-sm-6">
			<resume:profile-main fullName="${profile.fullName}" info="${profile.mainInfo}" contacts="${profile.contacts}" />
			<div class="hidden-xs">
				<resume:profile-languages languages="${profile.languages}" />
				<resume:profile-hobbies hobbies="${profile.hobbies }" />
				<resume:profile-info info="${profile.info}" />
			</div>
		</div>
		<div class="col-md-8 col-sm-6">
			<resume:profile-objective objective="${profile.mainInfo.objective}" summary="${profile.mainInfo.summary}" />
			<resume:profile-skills skills="${profile.skills}" />
			<resume:profile-experience experience="${profile.experience}" />
			<resume:profile-certificates certificates="${profile.certificates}" />
			<resume:profile-courses courses="${profile.courses}" />
			<resume:profile-education educations="${profile.educations}" />
		</div>
		<div class="col-xs-12 visible-xs-block">
			<resume:profile-languages languages="${profile.languages}" />
			<resume:profile-hobbies hobbies="${profile.hobbies }" />
			<resume:profile-info info="${profile.info}" />
		</div>
	</div>
</div>