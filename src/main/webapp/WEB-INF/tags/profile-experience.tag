<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="resume" tagdir="/WEB-INF/tags"%>

<%@ attribute name="experience" required="true" type="java.util.List"%>

<c:if test="${!empty experience}">
<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">
			<i class="fa fa-briefcase"></i> Practical Experience <a class="edit-block" href='<c:url value="/profile/edit/experience" />'>Edit</a>
		</h3>
	</div>
	<div class="panel-body">
		<ul class="timeline">
			<c:forEach items="${experience}" var="exp">
			<li>
				<div class="timeline-badge danger">
					<i class="fa fa-briefcase"></i>
				</div>
				<div class="timeline-panel">
					<div class="timeline-heading">
						<h4 class="timeline-title"><c:out value="${exp.position}"/> at <c:out value="${exp.company}"/></h4>
						<resume:experience-duration startDate="${exp.startDate}" endDate="${exp.endDate}"/>
					</div>
					<div class="timeline-body">
						<p>
							<strong>Responsibilities included:</strong> <c:out value="${exp.responsibilities}"></c:out>
						</p>
						<p>
							<strong>Demo: </strong><a href="${exp.demo}"><c:out value="${exp.demo}"/></a>
						</p>
						<p>
							<strong>Source code: </strong><a href="${exp.sourceCode}"><c:out value="${exp.sourceCode}"/></a> 
						</p>
					</div>
				</div>
			</li>
			</c:forEach>
		</ul>
	</div>
</div>
</c:if>