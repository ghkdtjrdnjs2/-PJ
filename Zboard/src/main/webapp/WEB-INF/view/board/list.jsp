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
				<table class="table table-hover">
					<thead>
						<tr>
							<th>번호</th>
							<th>제목</th>
							<th>글쓴이</th>
							<th>날짜</th>
							<th>읽기</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${page.list}" var="b">
						<tr>
							<td>${b.bno}</td>
							<td>
								<a href="/board/read?bno=${b.bno}">${b.title}</a>
							</td>
							<td>${b.writer }</td>
							<td>${b.writeTime }</td>
							<td>${b.readCnt }</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
				<div id="pagination" style="display:flex; justify-content:center;">
					<ul class="pagination">
						<c:if test="${page.prev>0}">
							<li class="page-item">
								<a class="page-link" href="/?pageno=${page.prev}">이전으로	</a>
							</li>
						</c:if>
						<c:forEach begin="${page.start}" end="${page.end}" var="i">
							<c:if test="${page.pageno==i}">
								<li class="page-item active">
									<a class="page-link" href="/?pageno=${i}">${i}</a>
								</li>
							</c:if>
							<c:if test="${page.pageno!=i }">
								<li class="page-item">
									<a class="page-link" href="/?pageno=${i}">${i}</a>
								</li>
							</c:if>
						</c:forEach>						
						<c:if test="${page.next>0}">
							<li class="page-item">
								<a class="page-link" href="/?pageno=${page.next}">다음으로	</a>
							</li>
						</c:if>
					</ul>
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