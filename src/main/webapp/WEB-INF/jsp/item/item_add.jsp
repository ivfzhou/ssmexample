<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>添加商品</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/static/css/bootstrap.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/static/css/2.css"/>
</head>
<body>
<div class="page-header">
    <h1>添加商品</h1>
    <h1 id="addInfo" style="color:red;"></h1>
</div>
<form action="" method="">
    <table class="table table-hover">
        <tr class="active">
            <td>
                <label for="name">商品名称:</label></td>
            <td>
                <input type="text" class="form-control" name="name" id="name" placeholder="请输入商品名称">
            </td>
        </tr>
        <tr class="success">
            <td><label for="price">商品价格:</label></td>
            <td>
                <input type="text" class="form-control" name="price" id="price" placeholder="请输入商品价格">
            </td>
        </tr>
        <tr class="success">
            <td><label for="productionDate">生产日期:</label></td>
            <td>
                <input type="text" class="form-control" name="productionDate" id="productionDate"
                       data-options="{'type':'YYYY-MM-DD','beginYear':2010,'endYear':2088}" placeholder="请输入生产日期"/>
            </td>
        </tr>
        <tr class="warning">
            <td><label for="descr">商品描述:</label></td>
            <td>
                <div class="form-group">
                    <textarea id="descr" style="resize: none" name="description" class="form-control" rows="3"
                              placeholder="请输入商品描述"></textarea>
                </div>
            </td>
        </tr>
        <tr class="info">
            <td><label for="picFile">上传图片:</label></td>
            <td>
                <div id="preview">
                    <div class="img">
                        <img src="${pageContext.request.contextPath}/static/img/uploadPicture.jpg"/>
                    </div>
                </div>
                <div class="form-group">
                    <input type="file" onchange="preview(this)" name="picFile" id="picFile">
                </div>
            </td>
        </tr>
        <tr class="danger">
            <td colspan="2">
                <input type="button" class="btn btn-danger" onclick="submitForm()" value="增加商品"/>
            </td>
        </tr>
    </table>
</form>
</body>
<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/jquery.date.js"></script>
<script>

    // 提交当前form表单
    function submitForm() {
        // 2. 封装数据.  layUI
        var formData = new FormData();
        formData.append("picFile", $("#picFile")[0].files[0]);
        formData.append("name", $("#name").val());
        formData.append("price", $("#price").val());
        formData.append("productionDate", $("#productionDate").val());
        formData.append("description", $("#descr").val());
        // 3. 发送ajax请求,并携带封装好的数据.
        $.ajax({
            url: "${pageContext.request.contextPath}/item/add",
            data: formData,
            type: "post",
            dataType: "json",
            success: function (result) {
                // 4.1 如果添加成功 -> 跳转到/item/list
                if (result.code == 0) {
                    location.href = "${pageContext.request.contextPath}/item/list";
                } else {
                    // 4.2 如果添加失败 -> 给用户提示.
                    $("#addInfo").html(result.msg);
                }
            },
            error: function () {
                alert("服务器爆炸!");
            },
            contentType: false,
            processData: false
        });

    }


    $.date('#productionDate');

    function preview(file) {
        var prevDiv = document.getElementById('preview');
        if (file.files && file.files[0]) {
            reader = new FileReader();
            reader.onload = function (evt) {
                prevDiv.innerHTML = '<img src="' + evt.target.result + '" />';
            }
            reader.readAsDataURL(file.files[0]);
        } else {
            prevDiv.innerHTML = '<div class="img" style="filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src=\'' + file.value + '\'"></div>';
        }
    }
</script>
</html>