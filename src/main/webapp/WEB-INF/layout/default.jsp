<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<title><tiles:getAsString name="title" /></title>

<link rel="icon" href='<spring:url value="/favicon.ico" />'>

<link rel="stylesheet" type="text/css" href='<spring:url value="/resources/css/bootstrap.min.css" />'>
<link rel="stylesheet" type="text/css" href='<spring:url value="/resources/css/styles.css" />'>

<script type="text/javascript" src='<spring:url value="/resources/js/jquery.min.js" />'></script>
<script type="text/javascript" src='<spring:url value="/resources/js/jquery.validate.min.js" />'></script>
<script type="text/javascript" src='<spring:url value="/resources/js/bootstrap.min.js" />'></script>
<!--[if lt IE 9]>
	<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
	<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
<![endif]-->
</head>
<body>

	<tilesx:useAttribute name="current" />

	<div class="container">
		<nav class="navbar navbar-default navbar-fixed-top">
			<div class="container-fluid">
				<!-- Brand and toggle get grouped for better mobile display -->
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed"
						data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href='<spring:url value="/" />'> <img
						alt="Brand" width="30" height="30"
						src='<spring:url value="/resources/images/logo.svg" />'>
					</a>
				</div>

				<!-- Collect the nav links, forms, and other content for toggling -->
				<div class="collapse navbar-collapse"
					id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav">
						<li class="${current == 'index' ? 'active' : ''}"><a
							href='<spring:url value="/" />'>Home</a></li>
						<security:authorize access="hasRole('ROLE_ADMIN')">
							<li class="${current == 'users' ? 'active' : ''}"><a
								href='<spring:url value="/users.html" />'>Users</a></li>
						</security:authorize>
					</ul>
					<ul class="nav navbar-nav navbar-right">
						<security:authorize access="isAnonymous()">
							<li class="${current == 'signup' ? 'active' : ''}"><a
								href='<spring:url value="/signup.html" />'>Sign Up</a></li>
							<li class="${current == 'signin' ? 'active' : ''}"><a
								href='<spring:url value="/signin.html" />'>Sign In</a></li>
						</security:authorize>
						<security:authorize access="isAuthenticated()">
							<li class="dropdown ${current == 'settings' ? 'active' : ''}"><a
								href="#" class="dropdown-toggle" data-toggle="dropdown"
								role="button" aria-expanded="false">${pageContext.request.userPrincipal.name}
									<span class="caret"></span>
							</a>
								<ul class="dropdown-menu" role="menu">
									<li><a href='<spring:url value="/account.html" />'>Account</a></li>
									<li><a href='<spring:url value="/password/edit.html" />'>Change
											Password</a></li>
									<li class="divider"></li>
									<li><c:url var="logoutUrl" value="/logout" />
										<form name="f" action="${logoutUrl}" method="post">
											<input type="hidden" name="${_csrf.parameterName}"
												value="${_csrf.token}" />
										</form> <a href="#" onclick="document.f.submit()">Sign out</a></li>
								</ul></li>
						</security:authorize>
					</ul>
				</div>
				<!-- /.navbar-collapse -->
			</div>
			<!-- /.container-fluid -->
		</nav>

		<tiles:insertAttribute name="body" />
	</div>

	<tiles:insertAttribute name="footer" />
</body>
</html>
