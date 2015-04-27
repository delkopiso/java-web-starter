<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>

<%@ include file="/WEB-INF/layout/taglib.jsp"%>

<form class="form-signin" role="form" name="f"
	action="<spring:url value='/login' />" method="POST">
	<h2 class="form-signin-heading">Please sign in</h2>
	<label for="inputName" class="sr-only">Username</label>
	<input type="text" name="username" id="inputName" class="form-control" placeholder="Username" required autofocus />
	<!-- 	<label for="inputEmail" class="sr-only">Email</label> -->
	<!-- 	<input type="email" name="email" id="inputEmail" class="form-control" placeholder="Email Address" required autofocus /> -->
	<label for="inputPassword" class="sr-only">Password</label>
	<input type="password" name="password" id="inputPassword" class="form-control" placeholder="Password" required />
	<!-- 	<div class="checkbox"> -->
	<!-- 		<label> <input type="checkbox" value="remember-me" /> -->
	<!-- 			Remember me -->
	<!-- 		</label> -->
	<!-- 	</div> -->
	<input name="submit" type="submit" value="Sign in" class="btn btn-lg btn-primary btn-block" />
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	<br>
	<p class="text-center"><a href='<spring:url value="/forgot.html" />'>Forgot Password?</a></p>
</form>
