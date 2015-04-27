<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/layout/taglib.jsp"%>

<h2>Forgot Password</h2>

<c:choose>
	<c:when test="${notfound eq false}">
		<div class="alert alert-info" role="alert">An email message has
			been sent out with instructions on how to reset your password.</div>
	</c:when>
	<c:when test="${notfound eq true}">
		<div class="alert alert-danger" role="alert">
			Unfortunately, the email you entered could not be found in our
			system. <a href='<spring:url value="/signup.html" />'
				class="alert-link">Sign Up</a>.
		</div>
	</c:when>

	<c:otherwise>
		<p>We will send you the instructions to reset your password to
			your email address you registered with us.</p>
	</c:otherwise>
</c:choose>
<br>
<form:form commandName="user" cssClass="form-horizontal">
	<div class="form-group">
		<label for="inputEmail" class="col-sm-3 control-label">Email
			Address:</label>
		<div class="col-sm-9">
			<form:input path="userEmail" type="email" cssClass="form-control"
				id="inputEmail" placeholder="Email Address" />
		</div>
	</div>
	<form:input type="hidden" path="userId" value="${id}" />
	<div class="form-group">
		<div class="col-sm-offset-3 col-sm-9">
			<input type="submit" value="Request Reset" class="btn btn-primary" />
		</div>
	</div>
</form:form>
