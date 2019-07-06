<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ attribute name="endDate" required="true" type="java.time.LocalDate"%>
<%@ attribute name="msg" required="true" type="java.lang.String"%>


<c:choose>
	<c:when test="${!empty endDate }">
		<fmt:parseDate value="${endDate}" pattern="yyyy-MM-dd" var="parsedDate" type="date" />
		<fmt:formatDate pattern="MMM yyyy" value="${parsedDate}" var="endDate" />
		${endDate}
	</c:when>
	<c:otherwise>
		<strong class="label label-danger">${msg}</strong>
	</c:otherwise>
</c:choose>

