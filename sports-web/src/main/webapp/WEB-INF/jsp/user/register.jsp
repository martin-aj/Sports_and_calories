<%-- 
    Document   : register
    Created on : Jan 14.1, 2015, 16:13:36 PM
    Author     : Peter Karlubík
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="true"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<fmt:message var="title" key="registration.title"/>
<my:layout title="${title}">
    <jsp:attribute name="body">
        
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/log.css"/> 


        <div id="login-box">
            <h3><fmt:message key="registration.instructions"/></h3>

            <c:if test="${not empty error}">
                <div class="error"> <p>${error}</p></div>   
            </c:if>
            <c:if test="${not empty msg}">
                <div class="msg"><p>${msg}</p></div>
            </c:if>
                
            <form name='registrationForm' action="${pageContext.request.contextPath}/user/update" method='POST' modelAttribute="userRegDto">
                <fieldset id="newUser">
                <table>
                    <tr><p style="margin-bottom: 45px !important"><input type='text' name='username' value='' placeholder="<fmt:message key='registration.username' />"></p></tr>
                <tr><p style="margin-bottom: 45px !important"><input type='password' name='password' placeholder="<fmt:message key='registration.password' />"/></p></tr>
                <tr><p style="margin-bottom: 45px !important">
                    <select path="roleForUser" name='roleForUser' placeholder="<fmt:message key="registration.roleForUser"/>"> 
                        <c:forEach items="${roleTypes}" var="a">
                            <option value="${a}">${a.roleTypes}</option>
                        </c:forEach>
                    </select>    
                </p></tr>
                <tr><p><input type='submit' class='loginButton' value='<fmt:message key='registration.submit'/>' /></p></tr>
                </table>
                </fieldset>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                
            </form>

        </div>
            
    </jsp:attribute>
</my:layout>