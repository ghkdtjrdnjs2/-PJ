<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/css/main.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script>
$(document).ready(function() {
	$("#find_id").on("click", function() {
		$.ajax({
			url: "/member/find-username?email="+$('#email').val(),
			success:function(result) {
				Swal.fire("아이디 찾기 성공", "당신의 아이디 : " + result, "success")
			}, error:function(){
				Swal.fire("아이디 찾기 실패", "사용자를 찾지 못했습니다", "error")
			}
		});
	});
	
	$("#find_password").on("click", function() {
		const params = {
			username : $("#username").val(),
			// _를 사용하는 이유는 이름이 겹치지 않게 하기 위해서
			_csrf : '${_csrf.token}'
		};
		$.ajax({
			url: "/member/reset-password",
			method : 'post',
			data: params,
			success:function(result) {
				Swal.fire("비밀번호 찾기 성공", "임시비밀번호를 이메일로 보냈습니다", "success")
			}, error:function(){
				Swal.fire("비밀번호 찾기 실패", "사용자를 찾지 못했습니다", "error")
			}
		});
	});
});
</script>
<title>찾기</title>
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
				<div class="tab-content">
						<div id="find_id_div" class="mb-3 mt-3">
							<h2>아이디 찾기</h2>
							<div class="form-group">
								<label for="email1">이메일:</label>
								<input type="text" class="form-control" id="email" placeholder="이메일 입력...">
								<span class="helper-text" id="email_msg1"></span>
							</div>
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
							<button type="button" class="btn btn-primary" id="find_id">아이디 찾기</button>
						</div>
						<hr>
						<div id="find_password_div" class="mb-3 mt-3">
							<h2>비밀번호 찾기</h2>
							<div class="form-group">
								<label for="username">아이디:</label>
								<input type="text" class="form-control" id="username" placeholder="아이디 입력..." name="username">
								<span class="helper-text" id="username_msg"></span>
							</div>
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
							<button type="button" class="btn btn-primary" id="find_password">비밀번호 찾기</button>
						</div>
					</div>
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