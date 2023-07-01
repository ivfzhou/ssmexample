<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>商品列表</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/static/css/bootstrap.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/static/css/3.css"/>
</head>
<body>
<div class="page-header">
    <h1>商品列表</h1>
    <h1 id="itemInfo" style="color: red;"></h1>
</div>
<%--查询条件--%>
<form id="form" action="${pageContext.request.contextPath}/item/list" method="GET">
    <input type="hidden" name="page"/>
    <table class="table table-hover">
        <tr>
            <th class="active">
                <span style="font-size: 26px;">商品名称:</span>
            </th>
            <td class="warning">
                <input type="text" id="name" name="name" value="${name}" class="form-control" placeholder="商品名称"/>
            </td>
            <td class="info">
                <button type="submit" class="btn btn-success">查询</button>
            </td>
            <td class="danger">
                <button type="button" id="addBtn" class="btn btn-info">添加商品</button>
            </td>
        </tr>
    </table>
</form>

<%--商品列表--%>
<table class="table">
    <tr class="info">
        <td colspan="7">商品列表:</td>
    </tr>
    <tr class="active">
        <th>商品名称</th>
        <th>商品价格</th>
        <th>生产日期</th>
        <th>商品描述</th>
        <th>展示图片</th>
        <th>操作</th>
    </tr>
    <c:forEach items="${pageInfo.list}" var="item">
        <tr class="success">
            <td style="width:20%;">
                    ${item.name }
            </td>
            <td>
                    ${item.price }
            </td>
            <td>
                <fmt:formatDate value="${item.productionDate }" pattern="yyyy-MM-dd"/>
            </td>
            <td style="width:40%;">${item.description }</td>
            <td>
                <img src="${item.pic }" style="width:80px;height:80px;"/>
            </td>
            <td>
                <button type="button" class="btn btn-warning">修改</button>
                <button type="button" class="btn btn-warning" onclick="del(this,${item.id})">删除</button>
            </td>
        </tr>
    </c:forEach>
</table>

<%--分页信息和分页按钮--%>
<div id="page">
    <span style="font-size: 20px;">当前第 ${pageInfo.pageNum} 页 / 共 ${pageInfo.pages} 页  (${pageInfo.total}条)</span>
    <div style="float: right;">
        <%--		显示按钮的条件--%>
        <c:if test="${pageInfo.hasPreviousPage}">
            <button class="btn btn-warning" onclick="page(1)">首页</button>
            <button class="btn btn-warning" onclick="page(${pageInfo.pageNum - 1})">上一页</button>
        </c:if>
        <%--			<c:forEach begin="${pageInfo.navigateFirstPage}" end="${pageInfo.navigateLastPage}" varStatus="i">--%>
        <%--				${i.index}--%>
        <%--			</c:forEach>--%>
        <%--		显示按钮的条件--%>
        <c:if test="${pageInfo.hasNextPage}">
            <button class="btn btn-warning" onclick="page(${pageInfo.pageNum + 1})">下一页</button>
            <button class="btn btn-warning" onclick="page(${pageInfo.pages})">尾页</button>
        </c:if>

    </div>
</div>

</body>
<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/jquery.min.js"></script>
<script type="text/javascript">
    $(function () {
        $("#addBtn").click(function () {
            location.href = "${pageContext.request.contextPath}/item/add-ui";
        });
    });

    // 删除
    function del(obj, id) {
        //1. 发送ajax请求.
        $.ajax({
            url: "${pageContext.request.contextPath}/item/del/" + id,
            data: null,
            type: "DELETE",
            dataType: "json",
            success: function (result) {
                if (result.code == 0) {
                    // 3.1 如果删除成功: 动态删除当前行数据.
                    $(obj).parent().parent().remove();
                } else {
                    // 3.2 如果删除失败: 给用户一个提示.
                    $("#itemInfo").html(result.msg);
                }
            },
            error: function () {
                alert("服务器爆炸!!");
            }
        });
    }

    // 分页
    function page(page) {
        $("input[name = 'page']").val(page);

        $("#form").submit();
    }
</script>
</html>