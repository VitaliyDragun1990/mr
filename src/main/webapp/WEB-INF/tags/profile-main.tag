<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="panel panel-primary">
	<a href='<c:url value="/edit" />'>
		<img class="img-responsive photo" src='<c:url value="/media/avatar/027a8821-153e-4563-b79a-29032d4a90db.jpg" />' alt="photo" />
	</a>
	<h1 class="text-center">
		<a href='<c:url value="/edit" />' style="color:black;">${!empty name ? name : 'Sheldon Cooper'}</a>
	</h1>
	<h6 class="text-center">
		<strong>Odessa, Ukraine</strong>
	</h6>
	<h6 class="text-center">
		<strong>Age:</strong> 27, <strong>Birthday: </strong> Feb 26, 1989
	</h6>
	<div class="list-group contacts">
		<a class="list-group-item" href="tel:+380507525137"><i class="fa fa-phone"></i> +380507525137</a> 
		<a class="list-group-item" href="mailto:sheldon-cooper@gmail.com"><i class="fa fa-envelope"></i> sheldon-cooper@gmail.com</a> 
		<a class="list-group-item" href="javascript:void(0);"><i class="fa fa-skype"></i>sheldon-cooper</a> 
		<a target="_blank" class="list-group-item" href="https://vk.com/sheldon-cooper"><i class="fa fa-vk"></i> https://vk.com/sheldon-cooper</a> 
		<a target="_blank" class="list-group-item" href="https://facebook.com/sheldon-cooper"><i class="fa fa-facebook"></i> https://facebook.com/sheldon-cooper</a> 
		<a target="_blank" class="list-group-item" href="https://linkedin.com/sheldon-cooper"><i class="fa fa-linkedin"></i> https://linkedin.com/sheldon-cooper</a> 
		<a target="_blank" class="list-group-item" href="https://github.com/sheldon-cooper"><i class="fa fa-github"></i> https://github.com/sheldon-cooper</a> 
		<a target="_blank" class="list-group-item" href="https://stackoverflow.com/sheldon-cooper"><i class="fa fa-stack-overflow"></i> https://stackoverflow.com/sheldon-cooper</a>
	</div>
</div>