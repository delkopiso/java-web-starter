<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/layout/taglib.jsp"%>

<h2>Reset password</h2>

<c:choose>
	<c:when test="${invalid eq true}">
		<div class="alert alert-info" role="alert">Invalid account confirmation token.</div>
	</c:when>
	<c:when test="${expired eq true}">
		<div class="alert alert-danger" role="alert">Your registration token has expired. <a href='<spring:url value="/register.html" />'
				class="alert-link">Request reset again</a>.
		</div>
	</c:when>

	<c:otherwise>
		<form:form commandName="user" cssClass="form-horizontal resetForm">
			<div class="form-group">
				<label for="inputPassword" class="col-sm-3 control-label">New
					Password:</label>
				<div class="col-sm-9">
					<form:password path="userPassword" class="form-control"
						id="inputPassword" placeholder="New Password" />
					<form:errors path="userPassword" />
				</div>
			</div>
			<div class="form-group">
				<label for="inputConfirmPassword" class="col-sm-3 control-label">Confirm
					Password:</label>
				<div class="col-sm-9">
					<input type="password" class="form-control" name="confirmPassword"
						id="inputConfirmPassword" placeholder="Confirm Password" />
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-3 col-sm-9">
					<input type="submit" value="Reset Password" class="btn btn-primary" />
				</div>
			</div>
		</form:form>

		<script type="text/javascript">
			$(document).ready(
					function() {
						$(".resetForm").validate(
								{
									rules : {
										userPassword : {
											required : true,
											minlength : 5
										},
										confirmPassword : {
											required : true,
											minlength : 5,
											equalTo : '#inputPassword'
										},
									},
									highlight : function(element) {
										$(element).closest('.form-group')
												.removeClass('has-success')
												.addClass('has-error');
									},
									unhighlight : function(element) {
										$(element).closest('.form-group')
												.removeClass('has-error')
												.addClass('has-success');
									}
								});
					});
		</script>
	</c:otherwise>
</c:choose>
