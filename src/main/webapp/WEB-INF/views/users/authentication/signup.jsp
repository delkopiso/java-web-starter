<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>

<%@ include file="/WEB-INF/layout/taglib.jsp"%>

<form:form commandName="user" cssClass="form-horizontal registrationForm">

	<c:if test="${success eq true}">
		<div class="alert alert-success">Registration Successful</div>
	</c:if>

	<div class="form-group">
		<label for="inputName" class="col-sm-2 control-label">Name:</label>
		<div class="col-sm-10">
			<form:input path="userName" type="text" cssClass="form-control"
				id="inputName" placeholder="Full Name" />
			<form:errors path="userName" />
		</div>
	</div>
	<div class="form-group">
		<label for="inputEmail" class="col-sm-2 control-label">Email:</label>
		<div class="col-sm-10">
			<form:input path="userEmail" type="email" cssClass="form-control"
				id="inputEmail" placeholder="Email Address" />
			<form:errors path="userEmail" />
		</div>
	</div>
	<div class="form-group">
		<label for="inputPassword" class="col-sm-2 control-label">Password:</label>
		<div class="col-sm-10">
			<form:password path="userPassword" class="form-control"
				id="inputPassword" placeholder="Password" />
			<form:errors path="userPassword" />
		</div>
	</div>
	<div class="form-group">
		<label for="inputConfirmPassword" class="col-sm-2 control-label">Password again:</label>
		<div class="col-sm-10">
			<input type="password" class="form-control" name="confirmPassword"
				id="inputConfirmPassword" placeholder="Confirm Password" />
		</div>
	</div>
	<!--   <div class="form-group"> -->
	<!--     <div class="col-sm-offset-2 col-sm-10"> -->
	<!--       <div class="checkbox"> -->
	<!--         <label> -->
	<!--           <input type="checkbox"> Remember me -->
	<!--         </label> -->
	<!--       </div> -->
	<!--     </div> -->
	<!--   </div> -->
	<div class="form-group">
		<div class="col-sm-offset-2 col-sm-10">
			<input type="submit" value="Save" class="btn btn-default" />
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
					url: "<spring:url value='/register/available.html' />",
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
				email : true
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
			}
		}
	});
});
</script>
