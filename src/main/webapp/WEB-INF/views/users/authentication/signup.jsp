<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/layout/taglib.jsp"%>

<div class="page-header">
  <h2>Sign Up <small>to create a new account</small></h2>
</div>

<form:form commandName="user" cssClass="form-horizontal registrationForm">

	<c:if test="${success eq true}">
		<div class="alert alert-success">Registration Successful. Please check your email for a link to activate your account.</div>
	</c:if>

	<div class="form-group">
		<label for="inputName" class="col-sm-3 control-label">Username:</label>
		<div class="col-sm-9">
			<form:input path="userName" type="text" cssClass="form-control"
				id="inputName" placeholder="Username" />
			<form:errors path="userName" />
		</div>
	</div>
	<div class="form-group">
		<label for="inputEmail" class="col-sm-3 control-label">Email Address:</label>
		<div class="col-sm-9">
			<form:input path="userEmail" type="email" cssClass="form-control"
				id="inputEmail" placeholder="Email Address" />
			<form:errors path="userEmail" />
		</div>
	</div>
	<div class="form-group">
		<label for="inputPassword" class="col-sm-3 control-label">Password:</label>
		<div class="col-sm-9">
			<form:password path="userPassword" class="form-control"
				id="inputPassword" placeholder="Password" />
			<form:errors path="userPassword" />
		</div>
	</div>
	<div class="form-group">
		<label for="inputConfirmPassword" class="col-sm-3 control-label">Confirm Password:</label>
		<div class="col-sm-9">
			<input type="password" class="form-control" name="confirmPassword"
				id="inputConfirmPassword" placeholder="Confirm Password" />
		</div>
	</div>
	<div class="form-group">
		<div class="col-sm-offset-3 col-sm-9">
			<input type="submit" value="Sign Up" class="btn btn-primary" />
		</div>
	</div>
</form:form>

<script type="text/javascript">
$(document).ready(function() {
	$(".registrationForm").validate({
		rules : {
			userName : {
				required : true,
				minlength : 3,
				remote: {
					url: "<spring:url value='/signup/available-name.html' />",
					type: "get",
					data: {
						username: function() {
							return $("#inputName").val();
						}
					}
				}
			},
			userEmail : {
				required : true,
				email : true,
				remote: {
					url: "<spring:url value='/signup/email-exists.html' />",
					type: "get",
					data: {
						email: function() {
							return $("#inputEmail").val();
						}
					}
				}
			},
			userPassword : {
				required : true,
				minlength : 5
			},
			confirmPassword: {
				required: true,
				minlength: 5,
				equalTo: '#inputPassword'
			},
		},
		highlight: function(element) {
			$(element).closest('.form-group').removeClass('has-success').addClass('has-error');
		},
		unhighlight: function(element) {
			$(element).closest('.form-group').removeClass('has-error').addClass('has-success');
		},
		messages: {
			userName: {
				remote: "Username already exists."
			},
			userEmail: {
				remote: "Email address is already registered."
			}
		}
	});
});
</script>
