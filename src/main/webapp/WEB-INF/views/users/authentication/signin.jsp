<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/layout/taglib.jsp"%>

<c:if test="${not empty reset}">
	<c:choose>
		<c:when test="${reset eq 'success'}">
			<div class="alert alert-success alert-dismissible" role="alert">
				<button type="button" class="close" data-dismiss="alert"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				Your password reset was successful! Please sign in.
			</div>
		</c:when>
		<c:otherwise>
			<div class="alert alert-danger alert-dismissible" role="alert">
				<button type="button" class="close" data-dismiss="alert"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				Your password reset failed!
			</div>
		</c:otherwise>
	</c:choose>
</c:if>

<form class="form-signin" role="form" name="f"
	action="<spring:url value='/login' />" method="POST">
	<h2 class="form-signin-heading">Please sign in</h2>
	<c:if test="${param.error eq true}">
		<div class="alert alert-danger" role="alert">Incorrect
			username/password combination.</div>
	</c:if>
	<label for="inputName" class="sr-only">Username</label> 
	<input type="text" name="username" id="inputName" class="form-control"
		placeholder="Username" required autofocus /> 
	<label for="inputPassword" class="sr-only">Password</label> 
	<input type="password" name="password" id="inputPassword"
		class="form-control" placeholder="Password" required />
	<div class="checkbox">
		<label><input type="checkbox" value="remember-me" />
			Remember me
		</label>
	</div>
	<input name="submit" type="submit" value="Sign in"
		class="btn btn-lg btn-primary btn-block" /> <input type="hidden"
		name="${_csrf.parameterName}" value="${_csrf.token}" /> <br>
	<p class="text-center">
		<a href='<spring:url value="/forgot.html" />'>Forgot Password?</a>
	</p>
</form>
