<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="resume" tagdir="/WEB-INF/tags" %>

<%@ attribute name="index" required="true" type="java.lang.Object" %>
<%@ attribute name="course" required="false" type="com.revenat.myresume.application.dto.CourseDTO" %>

<div id="ui-item-${index}" class="panel panel-default">
	<div class="panel-body ui-item">
		<div class="row">
			<div class="col-xs-12">
				<button type="button" class="close" onclick="$('#ui-item-${index}').remove();">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
		</div>
		<div class="row">
			<c:if test="${course != null}"><resume:form-has-error path="items[${index}].name"/></c:if>
			<div class="col-xs-6 col-md-4 form-group ${hasError ? 'has-error has-feedback' : ''}">
				<label for="items${index}name" class="control-label">Course name*</label>
				<input type="text" class="form-control" name="items[${index}].name" id="items${index}name"
					placeholder="Example: Java Advanced" value="${course.name}" required="required" />
					<c:if test="${course != null}">
						<resume:form-error path="items[${index}].name"/>
					</c:if>
			</div>
			<c:if test="${course != null}"><resume:form-has-error path="items[${index}].school"/></c:if>
			<div class="col-xs-6 col-md-4 form-group ${hasError ? 'has-error has-feedback' : ''}">
				<label for="items${index}school" class="control-label">School/platform name*</label>
				<input type="text" class="form-control" name="items[${index}].school" id="items${index}school"
					placeholder="Example: SourceIt" value="${course.school}" required="required" />
					<c:if test="${course != null}">
						<resume:form-error path="items[${index}].school"/>
					</c:if>
			</div>
			<div class="col-xs-12 col-md-4 form-group">
				<label for="items${index}endDate">Graduation date</label>
				<div class="row">
					<div class="col-xs-6">
						<select name="items[${index}].endDateMonth" id="items${index}endDateMonth" class="form-control" 
							onchange="resume.ui.updateSelect($(this))" data-ref-select="items${index}endDateYear">
						<option value="">Not finished yet</option>
						<c:forEach var="month" items="${months}">
						<option value="${month.key}" ${month.key == course.endDateMonth ? 'selected="selected"' : '' }>${month.value}</option>
						</c:forEach>
						</select>
					</div>
					<div class="col-xs-6">
						<select name="items[${index}].endDateYear" id="items${index}endDateYear" class="form-control" 
							onchange="resume.ui.updateSelect($(this))" data-ref-select="items${index}endDateMonth">
						<option value="">Not finished yet</option>
						<c:forEach var="year" items="${years}">
						<option value="${year}" ${year == course.endDateYear ? 'selected="selected"' : '' }>${year}</option>
						</c:forEach>
						</select>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
