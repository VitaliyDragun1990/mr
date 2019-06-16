<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ attribute name="endYear" required="true" type="java.lang.Integer"%>
<%@ attribute name="msg" required="true" type="java.lang.String"%>


<c:choose>
	<c:when test="${!empty endYear }">
	${endYear}
	</c:when>
	<c:otherwise>
		<strong class="label label-danger">${msg}</strong>
	</c:otherwise>
</c:choose>

