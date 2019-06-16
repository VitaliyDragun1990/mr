<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="level" required="true" type="java.lang.String"%>

<c:choose>
	<c:when test="${level == 'beginner'}"> <c:out value="Beginner"/></c:when>
	<c:when test="${level == 'elementary'}"> <c:out value="Elementary"/></c:when>
	<c:when test="${level == 'pre_intermediate'}"> <c:out value="Pre-intermediate"/></c:when>
	<c:when test="${level == 'intermediate'}"> <c:out value="Intermediate"/></c:when>
	<c:when test="${level == 'upper_intermediate'}"> <c:out value="Upper-intermediate"/></c:when>
	<c:when test="${level == 'advanced'}"> <c:out value="Advanced"/></c:when>
	<c:when test="${level == 'proficiency'}"> <c:out value="Proficiency"/></c:when>
</c:choose>