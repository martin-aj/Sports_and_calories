<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<fmt:message var="title" key="index.title"/>
<my:layout title="${title}">
    <jsp:attribute name="body">
     <h2><fmt:message key="home.top10"/></h2>

        <table class="basic">
            <tr>
                <th><fmt:message key="sportsman.calories"/></th>
                <th class="width-stretch"><fmt:message key="sportsman.nickname"/></th>
                <th><fmt:message key="sportsman.weightKg"/></th>
                <th><fmt:message key="sportsman.heightCm"/></th>
                <th><fmt:message key="sportsman.age"/></th>
                <th><fmt:message key="sportsman.sex"/></th>                
            </tr>
            
            <c:forEach items="${sportsmen}" var="sportsman">
                <tr>
                    <td><c:out value="${sportsman.caloriesSum}"/></td>
                    <td><c:out value="${sportsman.nickname}"/></td>
                    <td><c:out value="${sportsman.weightKg}"/></td>
                    <td><c:out value="${sportsman.heightCm}"/></td>
                    <td><c:out value="${sportsman.age}"/></td>
                    <td><fmt:message key="sportsman.sex.${sportsman.sex}"/></td>                    
                </tr>
            </c:forEach>
        </table>
    </jsp:attribute>
</my:layout>
