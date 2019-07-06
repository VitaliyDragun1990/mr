<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="resume" tagdir="/WEB-INF/tags" %>

<resume:edit-tab-header selected="contacts"/>

<div class="panel panel-default">
	<div class="panel-body">
		<h4 class="data-header">Additional contacts: skype, links to social network profiles, etc.</h4>
		<resume:form-display-error-if-invalid formName="contactsForm"/>
		<form:form commandName="contactsForm" action="/profile/edit/contacts" method="post" cssClass="form-horizontal data-form">
			<security:csrfInput/>
			
			<div class="row">
				<div class="col-sm-7">
					<resume:form-has-error path="skype"/>
					<div class="form-group ${hasError ? 'hasError' : ''}">
						<label for="inputSkype" class="col-sm-4 control-label">Skype</label>
						<div class="col-sm-8">
							<form:input path="skype" cssClass="form-control" id="inputSkype" placeholder="Example: richard-hendriks"/>
							<resume:form-error path="skyp"/>
						</div>
					</div>
				</div>
				<div class="col-sm-5 help-block">
					<blockquote>
						1. It is desirable that skype contains your first and last name as they are spelled in your international passport. If specified name is already occupied, abbreviations are possible. <br />
						2. It is not recommended to use fancy skype addressses like TheBestDeveloper, lackomka, etc <br />
						3. If you don't have reasonable skype-name yet, it's time to create one. <br />
					</blockquote>
				</div>
			</div>
			
			<div class="row">
				<div class="col-sm-7">
					<resume:form-has-error path="vkontakte"/>
					<div class="form-group ${hasError ? 'has-error' : ''}">
						<label for="inputVkontakte" class="col-sm-4 control-label">Vkontakte</label>
						<div class="col-sm-8">
							<form:input path="vkontakte" cssClass="form-control" id="inputVkontakte" placeholder="Example: https://vk.com/richard-hendricks"/>
							<resume:form-error path="vkontakte"/>
						</div>
					</div>
					<resume:form-has-error path="facebook"/>
					<div class="form-group ${hasError ? 'has-error' : ''}">
						<label for="inputFacebook" class="col-sm-4 control-label">Facebook</label>
						<div class="col-sm-8">
							<form:input path="facebook" cssClass="form-control" id="inputFacebook" placeholder="Example: https://facebook.com/richard-hendricks"/>
							<resume:form-error path="facebook"/>
						</div>
					</div>
					<resume:form-has-error path="linkedin"/>
					<div class="form-group ${hasError ? 'has-error' : ''}">
						<label for="inputLinkedin" class="col-sm-4 control-label">Linkedin</label>
						<div class="col-sm-8">
							<form:input path="linkedin" cssClass="form-control" id="inputLinkedin" placeholder="Example: https://linkedin.com/richard-hendricks"/>
							<resume:form-error path="linkedin"/>
						</div>
					</div>
					<resume:form-has-error path="github"/>
					<div class="form-group ${hasError ? 'has-error' : ''}">
						<label for="inputGithub" class="col-sm-4 control-label">Github</label>
						<div class="col-sm-8">
							<form:input path="github" cssClass="form-control" id="inputGithub" placeholder="Example: https://github.com/richard-hendricks"/>
							<resume:form-error path="github"/>
						</div>
					</div>
					<resume:form-has-error path="stackoverflow"/>
					<div class="form-group ${hasError ? 'has-error' : ''}">
						<label for="inputStackoverflow" class="col-sm-4 control-label">Stackoverflow</label>
						<div class="col-sm-8">
							<form:input path="stackoverflow" cssClass="form-control" id="inputStackoverflow" placeholder="Example:  https://stackoverflow.com/richard-hendricks"/>
							<resume:form-error path="stackoverflow"/>
						</div>
					</div>
				</div>
				<div class="col-sm-5 help-block">
					<blockquote>
						1. In order to obtain full information on candidate HR-manager can search for their profile at social network site. In order to facilitate to this search,
						provide links to your social network profiles in your additional contacts section. <br />
						2. When you provide links to some of yours social network profiles, make sure thay don't contain contradictions to information you specify in your resume.
						Such contradictions can be spotted quite easy. <br />
						3. Look at your profile again and make sure there are no indecent photos from your student life - there is no need is such information
						when you're looking for a job. <br />
						4. If you take part in some doubtful groups which can compromise you in any way, its time to leave such groups. <br />
						5. In almost all moder social networks there is an ability to create a named link to your profile, insted of one such as id12345678,
						so it makes sence to create such named link and use it when you fill your contacts page. <br />
					</blockquote>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-7">
					<div class="form-group">
						<div class="col-sm-offset-3 col-sm-9">
							<button type="submit" class="btn btn-primary">Save</button>
						</div>
					</div>
				</div>
				<div class="col-sm-5"></div>
			</div>
		</form:form>
	</div>
</div>