<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="resume" tagdir="/WEB-INF/tags" %>

<%@ attribute name="index" required="true" type="java.lang.Object" %>
<%@ attribute name="id" required="false" type="java.lang.Object" %>
<%@ attribute name="smallUrl" required="false" type="java.lang.String" %>
<%@ attribute name="largeUrl" required="false" type="java.lang.String" %>
<%@ attribute name="name" required="false" type="java.lang.String" %>

<div id="ui-item-${index}" class="thumbnail ui-item">
	<input type="hidden" name="items[${index}].id" value="${id}" />
	<input type="hidden" name="items[${index}].smallUrl" value="${smallUrl}" />
	<input type="hidden" name="items[${index}].largeUrl" value="${largeUrl}" />
	<input type="hidden" name="items[${index}].name" value="${name}" />
	
	<button type="button" class="close" onclick="$('#ui-item-${index}').remove();">
		<span aria-hidden="true">&times;</span>
	</button>
	<a href="#" class="certificate-link" data-title="${name}" data-url="${largeUrl}">
		<img src="${largeUrl}" alt="${name}" class="img-responsive" >
		<span>${name}</span>
	</a>
</div>
