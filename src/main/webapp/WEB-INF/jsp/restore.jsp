<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="panel panel-info small-center-block">
	<div class="panel-heading">
		<h3 class="panel-title">
			<i class="fa fa-unlock"></i> Access restoration
		</h3>
	</div>
	<div class="panel-body">
		<form action='<c:url value="/restore" />' method="post">
			<security:csrfInput/>
			<div class="form-group">
				<label for="uid">Enter Your UID or Email or Phone</label>
				<input id="uid" name="uid" class="form-control" type="text" required="required" placeholder="UID or Email or Phone"/>
			</div>
			<button type="submit" class="btn btn-primary">Restore</button>
		</form>
	</div>
</div>