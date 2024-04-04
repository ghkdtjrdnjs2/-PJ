<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/css/main.css">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<!-- <script>
	const msg = '${msg}';
	if(msg!=='')
		alert(msg);

	$(document).ready(function() {
		//$('#login-form').submit();
		
		$('#login').on('click', function() {
			$('#login-form').submit();
		});
	})
</script>-->


<script>
	$(function() {
		if (location.search === '?error') {
			$('#alert').css('display', 'block');
		}
	})
</script>
<title>로그인</title>
</head>
<body>
	<div id="page">
		<header>
			<jsp:include page="/WEB-INF/view/include/header.jsp" />
		</header>
		<nav>
			<jsp:include page="/WEB-INF/view/include/nav.jsp" />
		</nav>
		<main>
			<aside>
				<jsp:include page="/WEB-INF/view/include/aside.jsp" />
			</aside>
			<section>
				<div class="alert alert-danger" id="alert" style="display: none;">
					<strong>로그인에 실패했습니다.</strong>
				</div>
				<form action="/member/login" id="login-form" method="post">
					<div class="mb-3 mt-3">
						<label for="username" class="form-label">아이디:</label> <input
							type="text" class="form-control" id="username" name="username"
							maxlength="10"> <span class="message"></span>
					</div>
					<div class="mb-3 mt-3">
						<label for="password" class="form-label">비밀번호:</label> <input
							type="password" class="form-control" id="password"
							name="password" maxlength="20"> <span class="message"></span>
					</div>
					<div class="mb-3 mt-3">
						<button class="btn btn-primary" id="login">로그인</button>
					</div>
					<input type="hidden" name="_csrf" value="${_csrf.token}">
				</form>
			</section>
			<aside>
				<jsp:include page="/WEB-INF/view/include/aside.jsp" />
			</aside>
		</main>
		<footer>
			<jsp:include page="/WEB-INF/view/include/footer.jsp" />
		</footer>
	</div>
</body>
</html>