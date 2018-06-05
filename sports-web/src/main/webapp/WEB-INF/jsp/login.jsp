<%-- 
    Document   : login
    Created on : Jan 8, 2015, 9:38:36 PM
    Author     : Tomas Balogh, Michal Chromcak
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
    <head>
        <title><fmt:message key="login.title"/></title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/log.css"/> 
    </head>
    <body onload='document.loginForm.username.focus();'>
        <div id="login-box">
            <h3><fmt:message key="login.instructions"/></h3>

            <c:if test="${not empty error}">
                <div class="error"> <p>${error}</p></div>   
            </c:if>
            <c:if test="${not empty msg}">
                <div class="msg"><p>${msg}</p></div>
            </c:if>

            <form name='loginForm' action="<c:url value='/login' />" method='POST'>
                <p><input type='text' name='username' value='' placeholder="<fmt:message key='login.username' />"></p>
                <p><input type='password' name='password' placeholder="<fmt:message key='login.password' />"/></p>
                <p><input type='submit' class='loginButton' value='<fmt:message key='login.submit'/>' /></p>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            </form>
            <div class="footer">
                <p>Copyright &copy; <a href="#">A-Team</a> | <fmt:message key="copyright.rights"/></p>
                <p><fmt:message key="copyright.java"/></p>
            </div>
        </div>
    </body>
</html>