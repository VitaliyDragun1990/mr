<%@ tag language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="resume" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<%@ attribute name="courses" required="true" type="java.util.List"%>
<%@ attribute name="profileId" required="true" type="java.lang.Long"%>

<security:authorize access="authenticated" var="authenticated"/>

<c:if test="${!empty courses}">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title">
				<i class="fa fa-book"></i> Courses
				<c:if test="${authenticated}">
					<security:authentication property="principal.id" var="userId"/>
					<c:if test="${profileId == userId}" >
						<a href='<c:url value="/profile/edit/courses" />' class="edit-block">Edit</a>
					</c:if>
				</c:if>
			</h3>
		</div>
		<div class="panel-body">
			<ul class="timeline">
				<c:forEach items="${courses}" var="course">
					<li>
						<div class="timeline-badge success">
							<i class="fa fa-book"></i>
						</div>
						<div class="timeline-panel">
							<div class="timeline-heading">
								<h4 class="timeline-title"><c:out value="${course.name}"/> at ${course.school}</h4>
								<p>
									<small class="dates"> <i class="fa fa-calendar"></i>
										<strong>Finish Date:</strong> <resume:end-date endDate="${course.endDate }" msg="Not finished yet"/>
									</small>
								</p>
							</div>
						</div>
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>
</c:if>