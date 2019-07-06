<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="resume" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tld/custom.tld" %>

<%@ attribute name="index" required="true" type="java.lang.Object" %>
<%@ attribute name="language" required="false" type="com.revenat.myresume.application.dto.LanguageDTO" %>

<div id="ui-item-${index}" class="panel panel-default">
	<input type="hidden" name="items[${index}].id" value="${language.id}" />
	<div class="panel-body ui-item">
		<div class="row">
			<div class="col-xs-12">
				<button type="button" class="close" onclick="$('#ui-item-${index}').remove();">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-12 col-md-3 form-group">
				<div class="row">
				
					<div class="col-xs-6 form-group" style="padding-right:0px;">
						<label for="items${index}type">Type</label>
						<select name="items[${index}].type" class="form-control">
							<c:forEach var="languageType" items="${languageTypes}">
							<option value="${languageType}" ${languageType == language.type ? 'selected="selected"' : ''}>
								<spring:message code="LanguageType.${languageType}"/>
							</option>
							</c:forEach>
						</select>
					</div>
					
					<c:if test="${language != null}"><resume:form-has-error path="items[${index}].name"/></c:if>
					<div class="col-xs-6 form-group ${hasError ? 'has-error has-feedback' : ''}" style="padding-right:8px;">
						<label class="control-label" for="items${index}name">Language</label>
						<input type="text" class="form-control" name="items[${index}].name" id="items${index }name"
							placeholder="Example: English" value="${language.name}" required="required"/>
						<c:if test="${language != null}">
							<resume:form-error path="items[${index}].name"/>
						</c:if>
					</div>
					
				</div>
			</div>
			<div class="col-xs-12 col-md-9">
				<label for="items${index}level">Level</label>
				<div style="padding: 0 30px;">
					<c:if test="${language != null}">
						<custom:lang-level-data varOrdinal="sliderIntValue" varCaption="caption" level="${language.level}"/>
					</c:if>
					<resume:form-input-slider index="${index}" value="${language != null ? sliderIntValue : 4}"/>
				</div>
			</div>
		</div>
	</div>
</div>
