<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/layout/taglib.jsp"%>

<div class="page-header">
  <h2>Application Users <small>...</small></h2>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		$(".triggerRemove").click(function(e) {
			e.preventDefault();
			$("#removeModal .removeBtn").attr("href", $(this).attr("href"));
			$("#removeModal").modal();
		});
	});
</script>

<table
	class="table table-striped table-bordered table-hover table-condensed">
	<thead>
		<tr>
			<th>User Name</th>
			<th>Delete?</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${users}" var="user">
			<tr>
				<td><a href='<spring:url value="/users/${user.userId}.html" />'>
						<c:out value='${user.userName}' />
				</a></td>
				<td><a
					href='<spring:url value="/users/remove/${user.userId}.html" />'
					class="btn btn-danger triggerRemove">Delete</a></td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<!-- Modal -->
<div class="modal fade" id="removeModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">Delete User</h4>
			</div>
			<div class="modal-body">Are you sure you want to delete this user?</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
				<a href="" class="btn btn-danger removeBtn">Delete</a>
			</div>
		</div>
	</div>
</div>
