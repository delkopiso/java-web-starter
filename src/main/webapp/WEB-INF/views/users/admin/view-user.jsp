<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/layout/taglib.jsp"%>

<div class="page-header">
  <h2><c:out value='${user.userName}' />'s <small>Account</small></h2>
</div>

<div class="panel panel-primary">
	<div class="panel-heading">
		<h1 class="panel-title">Profile</h1>
	</div>
	<div class="panel-body">
		<form class="form-horizontal">
			<div class="form-group">
				<label class="col-sm-2 control-label">Username</label>
				<div class="col-sm-10">
					<p class="form-control-static">
						<c:out value="${user.userName}" />
					</p>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">Email</label>
				<div class="col-sm-10">
					<p class="form-control-static">
						<c:out value="${user.userEmail}" />
					</p>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">Password</label>
				<div class="col-sm-10">
					<p class="form-control-static">*****</p>
					<a class="btn btn-default"
						href='<spring:url value="/password/edit.html" />'>Change
						Password</a>
				</div>
			</div>
		</form>
	</div>
</div>
