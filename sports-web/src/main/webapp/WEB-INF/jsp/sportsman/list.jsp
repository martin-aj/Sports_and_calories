<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<fmt:message var="title" key="sportsman.list.title"/>
<my:layout title="${title}">
 <jsp:attribute name="body">

        <h2><fmt:message key="sportsman.list.allSportsmen"/></h2>

        <table class="basic">
            <tr>
                <th style = "display:none">id</th>
                <th class="width-stretch"><fmt:message key="sportsman.nickname"/></th>
                <th><fmt:message key="sportsman.weightKg"/></th>
                <th><fmt:message key="sportsman.heightCm"/></th>
                <th><fmt:message key="sportsman.age"/></th>
                <th><fmt:message key="sportsman.sex"/></th>
                <th><fmt:message key='table.edit'/></th>
                <th><fmt:message key='table.delete'/></th>

            </tr>
            
            <c:forEach items="${sportsmen}" var="sportsman">
                <tr>
                    <td style = "display:none" >${sportsman.id}</td>
                    <td><c:out value="${sportsman.nickname}"/></td>
                    <td><c:out value="${sportsman.weightKg}"/></td>
                    <td><c:out value="${sportsman.heightCm}"/></td>
                    <td><c:out value="${sportsman.age}"/></td>
                    <td><fmt:message key="sportsman.sex.${sportsman.sex}"/></td> 

                    <td>
                        <form method="get" action="${pageContext.request.contextPath}/sportsman/update/${sportsman.id}">
                            <input class="editButton" type="submit" value="">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        </form>
                    </td>
                    <td>
                        <form method="post" action="${pageContext.request.contextPath}/sportsman/delete/${sportsman.id}">
                            <input class="deleteButton" type="submit" value="">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        </form>
                    </td>

                </tr>
            </c:forEach>
        </table>

<form:form method="post" action="${pageContext.request.contextPath}/sportsman/update" modelAttribute="sportsman">
    <fieldset id="newSportsman"><h3><fmt:message key="sportsman.list.newSportsman"/></h3>
    <%@include file="form.jsp"%>
    <input class="createButton" type="submit" value="<fmt:message key='sportsman.list.createSportsman'/>">
    </fieldset>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form:form>
</jsp:attribute>
</my:layout>