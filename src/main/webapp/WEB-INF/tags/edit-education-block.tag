<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="resume" tagdir="/WEB-INF/tags" %>

<%@ attribute name="index" required="true" type="java.lang.Object" %>
<%@ attribute name="education" required="false" type="com.revenat.myresume.application.dto.EducationDTO" %>

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
		
			<c:if test="${education != null}"><resume:form-has-error path="items[${index}].summary"/></c:if>
			<div class="col-xs-12 col-md-12 form-group ${hasError ? 'has-error has-feedback' : ''}">
				<label for="items${index}summary" class="control-label">Full specialization</label>
				<textarea name="items[${index}].summary" id="items${index}summary" class="form-control" style="margin-bottom: 10px;"
					required="required" rows="2">${education.summary}</textarea>
				<c:if test="${education != null}">
				<resume:form-error path="items[${index}].summary"/>
				</c:if>
			</div>
			
			<div class="col-xs-6 form-group">
				<label for="items${index}startDate">Enrollment year</label>
				<select name="items[${index}].startYear" class="form-control">
					<c:forEach var="year" items="${years}">
					<option value="${year}" ${year == education.startYear ? 'selected="selected"' : ''}>${year}</option>
					</c:forEach>
				</select>
			</div>
			
			<c:if test="${education != null}"><resume:form-has-error path="items[${index}].endYear"/></c:if>
			<div class="col-xs-6 form-group ${hasError ? 'has-error has-feedback' : ''}">
				<label for="items${index}endYear">Graduaction year</label>
				<select name="items[${index}].endYear" id="items${index}endYear" class="form-control">
					<option value="">Not finished yet</option>
					<c:forEach var="year" items="${years}">
					<option value="${year}" ${year == education.endYear ? 'selected="selected"' : ''}>${year}</option>
					</c:forEach>
				</select>
				<c:if test="${education != null}">
				<resume:form-error path="items[${index}].endYear" displayIcon="false" />
				</c:if>
			</div>
			
			<c:if test="${education != null}"><resume:form-has-error path="items[${index}].university"/></c:if>
			<div class="col-xs-12 col-md-6 form-group ${hasError ? 'has-error has-feedback' : ''}">
				<label for="items${index}university" class="control-label">University</label>
				<input type="text" class="form-control" name="items[${index}].university" id="items${index}university"
					value="${education.university}" required="required" />
				<c:if test="${education != null}">
				<resume:form-error path="items[${index}].university"/>
				</c:if>
			</div>
			
			<c:if test="${education != null}"><resume:form-has-error path="items[${index}].faculty"/></c:if>
			<div class="col-xs-12 col-md-6 form-group ${hasError ? 'has-error has-feedback' : ''}">
				<label for="items${index}faculty" class="control-label">Faculty</label>
				<input type="text" class="form-control" name="items[${index}].faculty" id="items${index}faculty"
					value="${education.faculty}" required="required" />
				<c:if test="${education != null}">
				<resume:form-error path="items[${index}].faculty"/>
				</c:if>
			</div>
		</div>
	</div>
</div>
