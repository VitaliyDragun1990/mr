<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="resume" tagdir="/WEB-INF/tags"%>

<div class="container">
	<div class="row">
		<div class="col-md-4 col-sm-6">
			<resume:profile-main fullName="${profile.fullName}" info="${profile.mainInfo}" contacts="${profile.contacts}" />
			<div class="hidden-xs">
				<resume:profile-languages languages="${profile.languages}" profileId="${profile.id}" />
				<resume:profile-hobbies hobbies="${profile.hobbies }" profileId="${profile.id}" />
				<resume:profile-info info="${profile.info}" profileId="${profile.id}" />
			</div>
		</div>
		<div class="col-md-8 col-sm-6">
			<resume:profile-objective objective="${profile.mainInfo.objective}" summary="${profile.mainInfo.summary}" profileId="${profile.id}"  />
			<resume:profile-skills skills="${profile.skills}" profileId="${profile.id}" />
			<resume:profile-experience experience="${profile.experience}" profileId="${profile.id}" />
			<resume:profile-certificates certificates="${profile.certificates}" profileId="${profile.id}" />
			<resume:profile-courses courses="${profile.courses}" profileId="${profile.id}" />
			<resume:profile-education educations="${profile.educations}" profileId="${profile.id}" />
		</div>
		<div class="col-xs-12 visible-xs-block">
			<resume:profile-languages languages="${profile.languages}" profileId="${profile.id}"  />
			<resume:profile-hobbies hobbies="${profile.hobbies }" profileId="${profile.id}"  />
			<resume:profile-info info="${profile.info}" profileId="${profile.id}"  />
		</div>
	</div>
</div>