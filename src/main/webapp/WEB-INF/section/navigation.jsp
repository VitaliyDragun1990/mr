<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<security:authentication property="principal" var="CURRENT_USER"/>

<nav class="navbar navbar-default resume-nav">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href='<c:url value="/" />'>My Resume</a>
    </div>

    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav navbar-right">
      	<security:authorize access="!isAuthenticated()">
      		<li><a href='<c:url value="/sign-in" />'>Login</a></li>
        	<li><a href='<c:url value="/sign-up" />'>Register</a></li>
      	</security:authorize>
      	<security:authorize access="hasAuthority('USER')">
      		<li class="dropdown">
          		<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">${CURRENT_USER.fullName} <span class="caret"></span></a>
         		<ul class="dropdown-menu profile-menu">
           			<li><a href='<c:url value="/my-profile" />'><i class="fa fa-eye" aria-hidden="true"></i> My Profile</a></li>
           			<li><a href='<c:url value="/profile/edit" />'><i class="fa fa-pencil" aria-hidden="true"></i> Edit</a></li>
            		<li><a href='<c:url value="/profile/edit/password/update" />'><i class="fa fa-unlock-alt" aria-hidden="true"></i> Password</a></li>
            		<li><a href='<c:url value="/profile/remove" />'><i class="fa fa-trash-o" aria-hidden="true"></i> Remove</a></li>
           		 	<li role="separator" class="divider"></li>
           			<li><a href="javascript:resume.logout();"><i class="fa fa-sign-out" aria-hidden="true"></i> Logout</a></li>
          		</ul>
        	</li>
      	</security:authorize>
      </ul>
       <form action='<c:url value="/search" />' class="navbar-form navbar-right" role="search">
        <div class="form-group">
          <input type="text" name="query" class="form-control" placeholder="Search" value="${query}">
        </div>
        <button type="submit" class="btn btn-primary">Search</button>
      </form>
    </div>
  </div>
</nav>
