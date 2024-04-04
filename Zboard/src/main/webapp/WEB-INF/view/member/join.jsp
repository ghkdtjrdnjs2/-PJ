<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/css/main.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<script src="/script/join.js"></script>
<script>
	const msg = '${msg}';
	
	if(msg!=='') {
		alert(msg);
	}
</script>
<title>Insert title here</title>
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
				<h1>회원 가입</h1>
				<form id="join_form" method="post" action="/member/join" enctype="multipart/form-data">
					<div style="height: 240px">
						<img id="show-profile" height="240px">
					</div>
					<div class="mb-3 mt-3">
						<label for="profile">프로필 사진:</label> 
						<input type="file" id="profile" name="profile" accept=".jpg,.jpeg,.png" class="form-control">
					</div>
					<div class="mb-3 mt-3">
						<label for="username">아이디:</label> 
						<input type="text" name="username" id="username" class="form-control"> 
						<span class="message"></span>
					</div>
					<div class="mb-3 mt-3">
						<label for="password">비밀번호:</label>
						<input type="password" name="password" id="password" class="form-control"> 
						<span class="message"></span>
					</div>
					<div class="mb-3 mt-3">
						<label for="password2">비밀번호 확인:</label> 
						<input type="password" id="password2" class="form-control">
						<span class="message"></span>
					</div>
					<div class="mb-3 mt-3">
						<label for="email">이메일:</label> 
						<input type="text" name="email" id="email" class="form-control">
						<span class="message"></span>
					</div>
					<div class="mb-3 mt-3">
						<label for="birthday">생일(선택):</label> 
						<input type="date" name="birthday" id="birthday" class="form-control"> 
						<span class="message"></span>
					</div>
					<div class="mb-3 mt-3 d-grid">
						<button id="join" type="button" class="btn btn-primary btn-block">가입</button>
					</div>
					<!-- 보안은 개발이 끝내고 키는게 가장 좋다.  -->
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