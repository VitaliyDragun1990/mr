<%@ tag language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="selected" required="true" type="java.lang.String" %>

<ul class="nav nav-tabs" role="tablist">
	<li role="presentation" ${selected == 'profile' ? 'class="active"' : ''}><a href='<c:url value="/profile/edit" />'>Main</a></li>
	<li role="presentation" ${selected == 'contacts' ? 'class="active"' : ''}><a href='<c:url value="/profile/edit/contacts" />'>Contacts</a></li>
	<li role="presentation" ${selected == 'skills' ? 'class="active"' : ''}><a href='<c:url value="/profile/edit/skills" />'>Technical skills</a></li>
	<li role="presentation" ${selected == 'experience' ? 'class="active"' : ''}><a href='<c:url value="/profile/edit/experience" />'>Practical experience</a></li>
	<li role="presentation" ${selected == 'certificates' ? 'class="active"' : ''}><a href='<c:url value="/profile/edit/certificates" />'>Certificates</a></li>
	<li role="presentation" ${selected == 'courses' ? 'class="active"' : ''}><a href='<c:url value="/profile/edit/courses" />'>Courses</a></li>
	<li role="presentation" ${selected == 'education' ? 'class="active"' : ''}><a href='<c:url value="/profile/edit/education" />'>Education</a></li>
	<li role="presentation" ${selected == 'languages' ? 'class="active"' : ''}><a href='<c:url value="/profile/edit/languages" />'>Languages</a></li>
	<li role="presentation" ${selected == 'hobbies' ? 'class="active"' : ''}><a href='<c:url value="/profile/edit/hobbies" />'>Hobbies</a></li>
	<li role="presentation" ${selected == 'info' ? 'class="active"' : ''}><a href='<c:url value="/profile/edit/info" />'>Extra</a></li>
</ul>
