<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<security:authorize access="authenticated" var="authenticated"/>

<header>
<nav class="navbar navbar-default">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href='<c:url value="/" />'>My Resume</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">

      <ul class="nav navbar-nav navbar-right">
      	<c:choose>
      		<c:when test="${!authenticated}">
      			<li><a href='<c:url value="/sign-in" />'>Login</a></li>
        		<li><a href='<c:url value="/sign-up" />'>Register</a></li>
      		</c:when>
      		<c:otherwise>
      	 		<li class="dropdown">
          			<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><security:authentication property="principal.fullName"/> <span class="caret"></span></a>
         			<ul class="dropdown-menu">
           				<li><a href='<c:url value="/my-profile" />'><i class="fa fa-eye" aria-hidden="true"></i> My Profile</a></li>
           				<li><a href='<c:url value="/profile/edit" />'><i class="fa fa-pencil" aria-hidden="true"></i> Edit</a></li>
            			<li><a href="#"><i class="fa fa-unlock-alt" aria-hidden="true"></i> Password</a></li>
            			<li><a href="#"><i class="fa fa-trash-o" aria-hidden="true"></i> Remove</a></li>
            			<li role="separator" class="divider"></li>
            			<li><a href="javascript:resume.logout();"><i class="fa fa-sign-out" aria-hidden="true"></i> Logout</a></li>
          			</ul>
        		</li>
      		</c:otherwise>
      	</c:choose>
      </ul>
       <form action='<c:url value="/search" />' class="navbar-form navbar-right" role="search">
        <div class="form-group">
          <input type="text" name="query" class="form-control" placeholder="Search" value="${query}">
        </div>
        <button type="submit" class="btn btn-primary">Search</button>
      </form>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>
</header>