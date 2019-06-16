<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="resume" tagdir="/WEB-INF/tags"%>

<%@ attribute name="info" required="true" type="com.revenat.myresume.application.dto.MainInfoDTO" %>
<%@ attribute name="fullName" required="true" type="java.lang.String" %>
<%@ attribute name="contacts" required="false" type="com.revenat.myresume.application.dto.ContactsDTO" %>

<div class="panel panel-primary">
	<a href='<c:url value="/profile/edit" />'>
		<img class="img-responsive photo" src='<c:url value="${info.largePhoto}" />' alt="photo" />
	</a>
	<h1 class="text-center">
		<a href='<c:url value="/profile/edit" />' style="color:black;">${fullName}</a>
	</h1>
	<h6 class="text-center">
		<strong>${info.city}, ${info.country}</strong>
	</h6>
	<h6 class="text-center">
		<fmt:parseDate value="${info.birthDate}" pattern="yyyy-MM-dd" var="parsedDate" type="date" />
		<fmt:formatDate pattern="EEE MMM d, yyyy" value="${parsedDate}" var="birthDate" />
		<strong>Age:</strong> ${info.age}, <strong>Birthday: </strong> ${birthDate }
	</h6>
	<div class="list-group contacts">
		<a class="list-group-item" href="tel:${info.phone}"><i class="fa fa-phone"></i> ${info.phone}</a> 
		<a class="list-group-item" href="mailto:${info.email}"><i class="fa fa-envelope"></i> ${info.email}</a> 
		<resume:contact-list contacts="${contacts}"/>
	</div>
</div>