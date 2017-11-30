<html>
<body>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<h2>Hello World!</h2>
springMvc
<form name="form1" enctype="multipart/form-data" method="post" action="/user/user_avatar_upload.do">
    <input type="file" name="upload_file"/>
    <input type="submit" value="springMvc upload"/>
</form>
product
<form name="form1" enctype="multipart/form-data" method="post" action="/manage/product/upload.do">
    <input type="file" name="upload_file"/>
    <input type="submit" value="springMvc上传文件"/>
</form>
富文本上传文件
<form name="form1" enctype="multipart/form-data" method="post" action="/manage/product/richtext_img_upload.do">
    <input type="file" name="upload_file"/>
    <input type="submit" value="richtext上传文件"/>
</form>
</body>
</html>
