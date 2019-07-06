<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<security:authentication property="principal" var="CURRENT_USER"/>

<div class="panel panel-info">
	<div class="panel-heading">
		<h3 class="panel-title">
			<i class="fa fa-thumbs-o-up"></i> Successful sign up
		</h3>
	</div>
	<div class="panel-body">
		<p>After the sign up has been completed you can access your profile via link: <a href='<c:url value="/profile/${CURRENT_USER.username}" />'>
			${applicationHost}/profile/${AUTH_USER.username}
		</a></p>
		<p>Your UID: <strong>${CURRENT_USER.username}</strong>. Use this UID to enter Your personal area</p>
		<hr />
		<div class="row">
			<div class="col-xs-12 text-center">
				<a href='<c:url value="/profile/edit" />' class="btn btn-primary">Complete sign up</a>
			</div>
		</div>
	</div>
</div>