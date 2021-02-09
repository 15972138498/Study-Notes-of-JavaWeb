<%--
  Created by IntelliJ IDEA.
  User: wzq
  Date: 2021/2/9
  Time: 20:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改书籍</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- 引入 Bootstrap -->
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">

    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="page-header">
                <h1>
                    <small>修改书籍</small>
                </h1>
            </div>
        </div>
    </div>
    <form action="${pageContext.request.contextPath}/book/updateBook" method="post">
        <input type="hidden" name="bookID" value="${book.bookID}">
        书籍名称：<input type="text" name="bookName" value="${book.bookName}"><br><br><br>
        书籍数量：<input type="text" name="bookCounts" value="${book.bookCounts}"><br><br><br>
        书籍详情：<input type="text" name="detail" value="${book.detail}"><br><br><br>
        <input type="submit" value="修改">
    </form>

</div>
</body>
</html>
