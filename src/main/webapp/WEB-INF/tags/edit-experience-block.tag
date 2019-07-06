<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="resume" tagdir="/WEB-INF/tags" %>

<%@ attribute name="index" required="true" type="java.lang.Object" %>
<%@ attribute name="experience" required="false" type="com.revenat.myresume.application.dto.PracticalExperienceDTO" %>

<div id="ui-item-${index}" class="panel panel-default">
	<input type="hidden" name="items[${index}].id" value="${experience.id}" />
	<div class="panel-body ui-item">
		<div class="row">
			<div class="col-xs-12">
				<button type="button" class="close" onclick="$('#ui-item-${index}').remove();">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
		</div>
		<div class="row">
			<c:if test="${experience != null}"><resume:form-has-error path="items[${index}].position"/></c:if>
			<div class="col-xs-12 col-md-6 form-group ${hasError ? 'has-error has-feedback' : ''}">
				<label for="items${index}position" class="control-label">Position</label>
				<input type="text" class="form-control" name="items[${index}].position" id="items${index}position"
					placeholder="Example: Java trainee" value="${experience.position}" required="required" />
					<c:if test="${experience != null}">
						<resume:form-error path="items[${index}].position"/>
					</c:if>
			</div>
			
			<c:if test="${course != null}"><resume:form-has-error path="items[${index}].company"/></c:if>
			<div class="col-xs-12 col-md-6 form-group ${hasError ? 'has-error has-feedback' : ''}">
				<label for="items${index}company" class="control-label">Company or organization</label>
				<input type="text" class="form-control" name="items[${index}].company" id="items${index}company"
					placeholder="Example: Google" value="${experience.company}" required="required" />
					<c:if test="${experience != null}">
						<resume:form-error path="items[${index}].company"/>
					</c:if>
			</div>
			
			<div class="col-xs-12 col-md-6 form-group">
				<label class="control-label" for="items${index}startDate">Start date</label>
				<div class="row">
					<div class="col-xs-6">
						<select name="items[${index}].startDateMonth" id="items${index}startDateMonth" class="form-control">
						<c:forEach var="month" items="${months}">
						<option value="${month.key}" ${month.key == experience.startDateMonth ? 'selected="selected"' : '' }>${month.value}</option>
						</c:forEach>
						</select>
					</div>
					<div class="col-xs-6">
						<select name="items[${index}].startDateYear" id="items${index}startDateYear" class="form-control">
						<c:forEach var="year" items="${years}">
						<option value="${year}" ${year == experience.startDateYear ? 'selected="selected"' : '' }>${year}</option>
						</c:forEach>
						</select>
					</div>
				</div>
			</div>
			
			<c:if test="${experience != null}"><resume:form-has-error path="items[${index}].endDate"/></c:if>
			<div class="col-xs-12 col-md-6 form-group ${hasError ? 'has-error has-feedback' : ''}">
				<label class="control-label" for="items${index}endDate">End date</label>
				<div class="row">
					<div class="col-xs-6">
						<select name="items[${index}].endDateMonth" id="items${index}endDateMonth" class="form-control"
							onchange="resume.ui.updateSelect($(this))" data-ref-select="items${index}endDateYear">
						<option value="">Not finished yet</option>
						<c:forEach var="month" items="${months}">
						<option value="${month.key}" ${month.key == experience.endDateMonth ? 'selected="selected"' : '' }>${month.value}</option>
						</c:forEach>
						</select>
					</div>
					<div class="col-xs-6">
						<select name="items[${index}].endDateYear" id="items${index}endDateYear" class="form-control" 
							onchange="resume.ui.updateSelect($(this))" data-ref-select="items${index}endDateMonth">
						<c:forEach var="year" items="${years}">
						<option value="${year}" ${year == experience.endDateYear ? 'selected="selected"' : '' }>${year}</option>
						</c:forEach>
						</select>
					</div>
				</div>
				<c:if test="${experience != null}">
					<resume:form-error path="items[${index}].endDate" displayIcon="false"/>
				</c:if>
			</div>
			
			<c:if test="${experience != null}"><resume:form-has-error path="items[${index}].responsibilities"/></c:if>
			<div class="col-xs-12 col-md-6 form-group ${hasError ? 'has-error has-feedback' : ''}">
				<label for="items${index}responsibilities" class="control-label">Responsibilities/Achievements</label>
				<textarea class="form-control" name="items[${index}].responsibilities" id="items${index}responsibilities"
					style="margin-bottom: 10px;" rows="2" required="required" >${experience.responsibilities}</textarea>
				<c:if test="${experience != null}">
					<resume:form-error path="items[${index}].responsibilities"/>
				</c:if>
			</div>
			
			<c:if test="${experience != null}"><resume:form-has-error path="items[${index}].demo"/></c:if>
			<div class="col-xs-12 col-md-6 form-group ${hasError ? 'has-error has-feedback' : ''}">
				<label for="items${index}demo" class="control-label">Link to demo</label>
				<input class="form-control" name="items[${index}].demo" id="items${index}demo"
					placeholder="Example: http://google.com" value="${experience.demo}" />
				<c:if test="${experience != null}">
					<resume:form-error path="items[${index}].demo"/>
				</c:if>
			</div>
			
			<c:if test="${experience != null}"><resume:form-has-error path="items[${index}].sourceCode"/></c:if>
			<div class="col-xs-12 col-md-6 form-group ${hasError ? 'has-error has-feedback' : ''}">
				<label for="items${index}sourceCode" class="control-label">Source code</label>
				<input class="form-control" name="items[${index}].sourceCode" id="items${index}sourceCode"
					placeholder="Example: http://github.com/some-name/some-project" value="${experience.sourceCode}" />
				<c:if test="${experience != null}">
					<resume:form-error path="items[${index}].sourceCode"/>
				</c:if>
			</div>
		</div>
	</div>
</div>
