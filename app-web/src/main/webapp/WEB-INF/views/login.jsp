<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ru" lang="ru" dir="ltr">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <!--
        <link rel="shortcut icon" href="/sites/default/files/garland_favicon.gif" type="image/x-icon"/>
    -->

    <link href="<c:url value="/resources/css/bootstrap.css" />" rel="stylesheet"  type="text/css" />

    <style type="text/css">
        .well {
            position:absolute;
            width:370px;
            height:220px;
            top: 50%;
            left: 50%;
            margin-top:-150px; /* negative number 1/2 height */
            margin-left:-180px; /* negative number 1/2 width */
            outline:1px solid #CCC;
        }

    </style>

</head>
<body>
   <div class="container">
       <div class="row">
        <div class="span4 offset4 well pagination-centered" style="text-align: center ">
            <legend>Пожалуйста, авторизуйтесь</legend>
            <c:if test="${not empty param.error}">
                <div class="alert alert-error">
                    Неверное имя пользователя или пароль!
                </div>
            </c:if>
            <form method="POST" action="<c:url context="${target_url}" value="/j_spring_security_check" />" accept-charset="UTF-8">
                <input type="text" id="username" class="span4" name="j_username" placeholder="Имя пользователя"><br/>
                <input type="password" id="password" class="span4" name="j_password" placeholder="Пароль"><br/>
                <button type="submit" name="submit" class="btn ">Войти</button>
            </form>

        </div>
    </div>
</body>
</html>