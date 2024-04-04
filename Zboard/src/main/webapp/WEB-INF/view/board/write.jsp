<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/css/main.css">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.20/summernote-lite.min.css"
	rel="stylesheet">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.20/summernote-lite.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/lang/summernote-ko-KR.js"></script>

<title>Insert title here</title>
<script>
	$(document).ready(function() {
		const fontList = ['맑은 고딕','굴림','돋움','바탕','궁서','NotoSansKR'];
		// jQuery가 사용하는 $의 우선권을 양보한다.
		jQuery.noConflict();

		$('#summernote').summernote({
			height : 450,
			lang : 'ko-KR',
			fontNames : fontList
		//defaultFontName : '맑은 고딕'
		})
	})
</script>
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
				<form action="/board/write" method="post">
					<div class="mb-3 mt-3">
						<label for="title" class="form-label">제목</label> <input
							type="text" class="form-control" id="title"
							placeholder="제목 입력..." name="title">
					</div>


					<!-- summernote를 적용할 입력 요소 -->
					<textarea id="summernote" name="content"></textarea>

					<div class="mb-3 mt-3 d-grid">
						<button class="btn btn-outline-primary">작성</button>
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