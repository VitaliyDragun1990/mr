<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ attribute name="formName" required="true" type="java.lang.String"%>

<spring:hasBindErrors name="${formName}">
	<div class="alert alert-danger">Please, correct errors before submitting data!</div>
</spring:hasBindErrors>
