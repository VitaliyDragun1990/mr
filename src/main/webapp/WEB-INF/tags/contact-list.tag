<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="contacts" required="false" type="com.revenat.myresume.application.dto.ContactsDTO"%>


<c:if test="${!empty contacts.skype }">
	<a class="list-group-item" href="javascript:void(0);">
		<i class="fa fa-skype"></i> ${contacts.skype }
	</a>
</c:if>
<c:if test="${!empty contacts.vkontakte }">
	<a target="_blank" class="list-group-item" href="${contacts.vkontakte }">
		<i class="fa fa-vk"></i> ${contacts.vkontakte }
	</a>
</c:if>
<c:if test="${!empty contacts.facebook }">
	<a target="_blank" class="list-group-item" href="${contacts.facebook }">
		<i class="fa fa-facebook"></i> ${contacts.facebook }
	</a>
</c:if>
<c:if test="${!empty contacts.linkedin }">
	<a target="_blank" class="list-group-item" href="${contacts.linkedin }">
		<i class="fa fa-linkedin"></i> ${contacts.linkedin }
	</a>
</c:if>
<c:if test="${!empty contacts.github }">
	<a target="_blank" class="list-group-item" href="${contacts.github }">
		<i class="fa fa-github"></i> ${contacts.github }
	</a>
</c:if>
<c:if test="${!empty contacts.stackoverflow }">
	<a target="_blank" class="list-group-item" href="${contacts.stackoverflow }">
		<i class="fa fa-stack-overflow"></i> ${contacts.stackoverflow }
	</a>
</c:if>
