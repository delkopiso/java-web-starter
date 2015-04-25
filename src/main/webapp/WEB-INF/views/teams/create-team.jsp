<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>

<%@ include file="/WEB-INF/layout/taglib.jsp"%>

<form:form commandName="league" cssClass="form-horizontal createLeagueForm">
	<div class="form-group">
		<label for="inputName" class="col-sm-2 control-label">League Name:</label>
		<div class="col-sm-10">
			<form:input path="leagueName" type="text" cssClass="form-control"
				id="inputName" placeholder="League Name" />
			<form:errors path="leagueName" />
		</div>
	</div>
	<div class="form-group">
		<div class="col-sm-offset-2 col-sm-10">
			<input type="submit" value="Create" class="btn btn-default" />
		</div>
	</div>
</form:form>

<script type="text/javascript">
$(document).ready(function() {
	$(".createLeagueForm").validate({
		rules : {
			leagueName : {
				required : true,
				minlength : 3,
				remote: {
					url: "<spring:url value='/leagues/create/available.html' />",
					type: "get",
					data: {
						teamname: function() {
							return $("#inputName").val();
						}
					}
				}
			},
		},
		highlight: function(element) {
			$(element).closest('.form-group').removeClass('has-success').addClass('has-error');
		},
		unhighlight: function(element) {
			$(element).closest('.form-group').removeClass('has-error').addClass('has-success');
		},
		messages: {
			leagueName: {
				remote: "League name already exists."
			}
		}
	});
});
</script>
