<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="resume" tagdir="/WEB-INF/tags"%>

<%@ attribute name="startDate" required="true" type="java.time.LocalDate"%>
<%@ attribute name="endDate" required="true" type="java.time.LocalDate"%>


<p>
	<fmt:parseDate value="${startDate}" pattern="yyyy-MM-dd" var="parsedDate" type="date" />
	<fmt:formatDate pattern="MMM yyyy" value="${parsedDate}" var="startDate" />
	<small class="dates"><i class="fa fa-calendar"></i> ${startDate} - <resume:end-date endDate="${endDate}" msg="Current"/></small>
</p>
