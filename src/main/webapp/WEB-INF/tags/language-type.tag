<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="type" required="true" type="java.lang.String"%>

<c:choose>
	<c:when test="${type == 'spoken'}"><em><c:out value="(Spoken)"/></em></c:when>
	<c:when test="${type == 'written'}"><em><c:out value="(Written)"/></em></c:when>
</c:choose>