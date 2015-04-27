<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/layout/taglib.jsp"%>
<c:choose>
	<c:when test="${resent eq true}">
		<div class="alert alert-info" role="alert">A new token has been sent to you! Follow the instructions in your email to reverify.</div>
	</c:when>

	<c:otherwise>
		<div class="panel panel-danger">
			<div class="panel-heading">Account verification failed</div>
			<div class="panel-body">
				<c:if test="${invalid eq true}">
					Invalid verification token. <a
						href='<spring:url value="/signup.html" />'>Create New Account</a>.
				</c:if>
				<c:if test="${expired eq true}">
					Unfortunately, this verification token is expired. <a
								href='<spring:url value="/reverify/${token}.html" />'>Request
								a new token</a>.
				</c:if>
			</div>
		</div>
	</c:otherwise>
</c:choose>
