<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="resume" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<%@ attribute name="educations" required="true" type="java.util.List" %>
<%@ attribute name="profileId" required="true" type="java.lang.Long"%>

<security:authorize access="authenticated" var="authenticated"/>

<c:if test="${!empty educations }">
<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">
			<i class="fa fa-graduation-cap"></i> Education
			<c:if test="${authenticated}">
				<security:authentication property="principal.id" var="userId"/>
				<c:if test="${profileId == userId}" >
					<a href='<c:url value="/profile/edit/education" />' class="edit-block">Edit</a>
				</c:if>
			</c:if>
		</h3>
	</div>
	<div class="panel-body">
		<ul class="timeline">
			<c:forEach items="${educations}" var="education">
			<li>
				<div class="timeline-badge warning">
					<i class="fa fa-graduation-cap"></i>
				</div>
				<div class="timeline-panel">
					<div class="timeline-heading">
						<h4 class="timeline-title"><c:out value="${education.summary}"/></h4>
						<p>
							<small class="dates"><i class="fa fa-calendar"></i> ${education.startYear} - <resume:end-year msg="Current" endYear="${education.endYear}"/></small>
						</p>
					</div>
					<div class="timeline-body">
						<p><c:out value="${education.faculty}"/>, <c:out value="${education.university}"/></p>
					</div>
				</div>
			</li>
			</c:forEach>
		</ul>
	</div>
</div>
</c:if>