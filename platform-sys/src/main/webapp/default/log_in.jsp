<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%
    /*IUserService userService = (IUserService) PlatFormContext.getSpringContext().getBean("userService");
    HashMap hashMap = new HashMap();
    hashMap.put("userName","admin");
    hashMap.put("password","test");
    userService.createUser(hashMap);*/
%>
<head>
    <title>登录</title>
</head>
<body>

<div class="error">${requestScope.error}</div>
<form action="/pt/securi/login" method="post">
    用户名：<input type="text" name="userName"><br/>
    密码：<input type="password" name="password"><br/>
    <input type="submit" value="登录">
</form>

</body>
</html>
