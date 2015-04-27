<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/layout/taglib.jsp"%>

<div class="page-header">
  <h2>Change Password <small>...</small></h2>
</div>

<c:if test="${invalid eq true}">
	<div class="alert alert-danger" role="alert">Incorrect Old
		Password.</div>
</c:if>
<c:if test="${success eq true}">
	<div class="alert alert-success" role="alert">
		<strong>Success!</strong> Your password has been updated.
	</div>
</c:if>

<form:form commandName="passwordChange" cssClass="changeForm">
	<div class="form-group">
		<label for="oldPassword">Old Password:</label>
		<form:password path="oldPassword" class="form-control"
			id="oldPassword" placeholder="Old Password" />
	</div>
	<div class="form-group">
		<label for="newPassword">New Password:</label>
		<form:password path="newPassword" class="form-control"
			id="newPassword" placeholder="New Password" />
		<form:errors path="newPassword" />
	</div>
	<div class="form-group">
		<label for="confirmNewPassword">Confirm Password:</label> <input
			type="password" class="form-control" name="confirmNewPassword"
			id="confirmNewPassword" placeholder="Confirm New Password" />
	</div>
	<form:input type="hidden" path="userName"
		value="${pageContext.request.userPrincipal.name}" />
	<div class="form-group">
		<input type="submit" value="Change Password" class="btn btn-primary" />
	</div>
</form:form>

<script type="text/javascript">
	$(document).ready(
			function() {
				$(".changeForm").validate(
						{
							rules : {
								newPassword : {
									required : true,
									minlength : 5
								},
								confirmNewPassword : {
									required : true,
									minlength : 5,
									equalTo : '#newPassword'
								},
							},
							highlight : function(element) {
								$(element).closest('.form-group').removeClass(
										'has-success').addClass('has-error');
							},
							unhighlight : function(element) {
								$(element).closest('.form-group').removeClass(
										'has-error').addClass('has-success');
							}
						});
			});
</script>
