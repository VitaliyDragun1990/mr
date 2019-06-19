<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:forEach var="profile" items="${profiles}">
	<c:set var="info" value="${profile.mainInfo }"/>
	<div class="panel panel-default profile-item">
		<div class="media panel-body">
			<div class="media-left media-top">
				<a href='<c:url value="/profile/${profile.uid}" />'>
					<img src='<c:url value="${info.smallPhoto}" />' alt="${profile.fullName}" class="photo" />
				</a>
			</div>
			<div class="media-body search-result-item">
				<a href='<c:url value="/profile/${profile.uid}" />' class="btn btn-primary pull-right">
					Details
				</a>
				<h4 class="media-heading">
					<a href='<c:url value="/profile/${profile.uid}" />'>${profile.fullName}, ${profile.age}</a>
				</h4>
				<strong>${info.objective}</strong>
				<p>${info.city}, ${info.country}</p>
				<blockquote>
					<small>${info.summary}</small>
				</blockquote>
			</div>
		</div>
	</div>
</c:forEach>