<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="resume" tagdir="/WEB-INF/tags" %>

<resume:edit-tab-header selected="certificates"/>

<div class="panel panel-default certificates">
	<div class="panel-body">
		<h4 class="data-header">Certificates</h4>
		<hr />
		<resume:form-display-error-if-invalid formName="certificateForm"/>
		<form:errors path="*"/>
		<form:form action="/profile/edit/certificates" method="post" commandName="certificateForm">
			<security:csrfInput/>
			<div id="ui-block-container">
				<c:forEach var="certificate" items="${certificateForm.items}" varStatus="status">
					<resume:edit-certificate-block index="${status.index}" id="${certificate.id}" largeUrl="${certificate.largeUrl}"
						smallUrl="${certificate.smallUrl}" name="${certificate.name}"/>
				</c:forEach>
			</div>
			<hr />
			<div class="row">
				<div class="col-xs-12">
					<a href="javascript:resume.certificates.showUploadDialog();">+ Add certificate</a>
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

<script id="ui-block-template" type="text/x-handlebars-template">
	<resume:edit-certificate-block index="{{blockIndex}}" largeUrl="{{largeUrl}}" smallUrl="{{smallUrl}}" name="{{name}}"/>
</script>
<resume:certificate-viewer/>
<resume:certificate-uploader/>

<content tag="js-init">
<script>
$(document).ready(function() {
	resume.initCertificateViewer();
	resume.createCertificateUploader('${_csrf.token}');
});
</script>
</content>