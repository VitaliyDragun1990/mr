<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="resume" tagdir="/WEB-INF/tags" %>

<resume:edit-tab-header selected="profile"/>
<security:authentication property="principal.fullName" var="fullName"/>

<div class="panel panel-default edit-profile">
	<div class="panel-body">
		<h1 class="text-center">${fullName}</h1>
		<c:if test="${mainInfo.isCompleted}">
			<hr />
			<h4 class="data-header">In order to complete registration, fill in next form fields!</h4>
		</c:if>
		<resume:form-display-error-if-invalid formName="mainInfo"/>
		<form:form action="/profile/edit?${_csrf.parameterName}=${_csrf.token}" commandName="mainInfo" method="post"
			cssClass="form-horizontal data-form" enctype="multipart/form-data">
			<form:hidden path="largePhoto"/>
			
			<resume:form-has-error path="profilePhoto"/>
			<div class="form-group ${hasError ? 'has-error' : ''}">
				<label for="inputPhoto" class="col-sm-2 control-label">Photo*</label>
				<div class="col-sm-5">
					<img src="${mainInfo.profilePhoto}" alt="profile photo" id="currentPhoto" class="edit-photo" /> <br />
					<input type="file" name="profilePhoto" id="profilePhoto" />
					<resume:form-error path="profilePhoto"/>
				</div>
				<div class="col-sm-5 help-block">
					<blockquote>
						1. Photo can tell a lot about a candidate: from his aestatic qualities to his attitude to finding serious work. <br />
						2. Passport photo or photo of a candidate in a suit is not necessary, the main thing is the adequacy adn well-groomed, neat appearance <br />
						3. As an example you can look at photos of demonstrational resumes in this application. <br />
						4. Photo size should be no less than 400x400 <br />
						5. File must be in jpg format. <br />
					</blockquote>
				</div>
			</div>
			
			<resume:form-has-error path="birthDate"/>
			<div class="form-group ${hasError ? 'has-error' : ''}">
				<label for="inputBirthDate" class="col-sm-2 control-label">Birthday*</label>
				<div class="col-sm-5">
					<form:input path="birthDate" class="form-control datepicker" data-date-format="yyyy-mm-dd" id="inputBirthDate"
						placeholder="Example: 1990-12-31" required="required"/>
					<resume:form-error path="birthDate"/>
				</div>
				<div class="col-sm-5 help-block">
					<blockquote>Date format: yyyy-mm-dd (four-digit year - month code - birthday)</blockquote>
				</div>
			</div>
			
			<resume:form-has-error path="country"/>
			<div class="form-group ${hasError ? 'has-error' : ''}">
				<label for="inputCountry" class="col-sm-2 control-label">Country*</label>
				<div class="col-sm-5">
					<form:input path="country" cssClass="form-control" id="inputCountry" placeholder="Example: Ukraine" required="required"/>
					<resume:form-error path="country"/>
				</div>
				<div class="col-sm-5 help-block"></div>
			</div>
			
			<resume:form-has-error path="city"/>
			<div class="form-group ${hasError ? 'has-error' : ''}">
				<label for="inputCity" class="col-sm-2 control-label">City*</label>
				<div class="col-sm-5">
					<form:input path="city" cssClass="form-control" id="inputCity" placeholder="Example: Kharkiv" required="required"/>
					<resume:form-error path="city"/>
				</div>
				<div class="col-sm-5 help-block"></div>
			</div>
			
			<resume:form-has-error path="email"/>
			<div class="form-group ${hasError ? 'has-error' : ''}">
				<label for="inputEmail" class="col-sm-2 control-label">Email*</label>
				<div class="col-sm-5">
					<form:input path="email" cssClass="form-control" id="inputEmail" placeholder="Example: richard.hendricks@gmail.com" required="required"/>
					<resume:form-error path="email"/>
				</div>
				<div class="col-sm-5 help-block">
					<blockquote>
						1. It is desirable that the email contains your first and last name as indicated in your passport. If specified name is already occupied, abbreviations are possible. <br />
						2. It is not recommended to use fancy email addresses like TheBestDeveloper@gmail.com, lackomka@gmail.com, etc. <br />
						3. It is not recommended to use your current employer domain name. <br />
						4. For programmers is desirable to use @gmail.com domain name. <br />
					</blockquote>
				</div>
			</div>
			
			<resume:form-has-error path="phone"/>
			<div class="form-group ${hasError ? 'has-error' : ''}">
				<label for="inputPhone" class="col-sm-2 control-label">Phone*</label>
				<div class="col-sm-5">
					<form:input path="phone" cssClass="form-control" id="inputPhone" placeholder="Example: +380501234567" required="required"/>
					<resume:form-error path="phone"/>
				</div>
				<div class="col-sm-5 help-block">
					<blockquote>Phone number should be working one. It should be the number from which you will answer calls from unknown numbers.
					Phone number must be specified in a format <a href="https://en.wikipedia.org/wiki/E.164" target="_blank">E.164</a>, for example: +380501234567</blockquote>
				</div>
			</div>
			
			<resume:form-has-error path="objective"/>
			<div class="form-group ${hasError ? 'has-error' : ''}">
				<label for="inputObjective" class="col-sm-2 control-label">Objective*</label>
				<div class="col-sm-5">
					<form:input path="objective" cssClass="form-control" id="inputObjective" placeholder="Example: Junior java developer position" required="required"/>
					<resume:form-error path="objective"/>
				</div>
				<div class="col-sm-5 help-block">
					<blockquote>In this section you should specify career objective as briefly and clearly as possible</blockquote>
				</div>
			</div>
			
			<resume:form-has-error path="summary"/>
			<div class="form-group ${hasError ? 'has-error' : ''}">
				<label for="inputSummary" class="col-sm-2 control-label">Summary*</label>
				<div class="col-sm-5">
					<form:textarea path="summary" cssClass="form-control" id="inputSummary" rows="7" required="required"
						placeholder="Example: Two Java professional courses with developing two web applications: blog and resume (Links to demo are provided)" />
					<resume:form-error path="summary"/>
				</div>
				<div class="col-sm-5 help-block">
					<blockquote>
						1. In this section you should describe your experience (practical or theoretical) in the field you are looking for a job in. <br />
						2. If you worked as a photographer and decided to change your specialization, no need to describe your achivemens in photography when you are looking for a job as programmer. <br />
						3. If you don't have any experience in your career objective, you have a solid reason to take some related courses or to self study the fundamentals of your future profession.
					</blockquote>
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" class="btn btn-primary">Save</button>
				</div>
			</div>
		</form:form>
	</div>
</div>

<content tag="js-init">
<script>
$(document).ready(function() {
	resume.createDatePicker();
	resume.createPhotoUploader();
});
</script>
</content>