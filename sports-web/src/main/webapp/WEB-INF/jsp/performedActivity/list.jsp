<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<fmt:message var="title" key="performedActivity.list.title"/>
<my:layout title="${title}">
    <jsp:attribute name="body">

        <h2><fmt:message key="performedActivity.list.allPerformedActivities"/></h2>

        <table class="basic">
            <tr>
                <th style="display:none">id</th>
                <th><fmt:message key="performedActivity.sportsman"/></th>                
                <th><fmt:message key="performedActivity.sportActivity"/></th>
                <th><fmt:message key="performedActivity.startOfActivity"/></th>                
                <th><fmt:message key="performedActivity.durationInSeconds"/></th>
                <th><fmt:message key="performedActivity.distanceInMeters"/></th>                
                <th><fmt:message key="performedActivity.calories"/></th>
                <th><fmt:message key='table.edit'/></th>
                <th><fmt:message key='table.delete'/></th>
            </tr>

            <c:forEach items="${performedActivities}" var="performedActivity">
                <tr>
                    <td style="display:none">${performedActivity.id}</td>
                    <td><c:out value="${performedActivity.sportsman.nickname}"/></td>
                    <td><c:out value="${performedActivity.sportActivity.name}"/></td>
                    <td><fmt:formatDate value="${performedActivity.startOfActivity}" pattern="MM/dd/yyyy" /></td>
                    <td><c:out value="${performedActivity.durationInSeconds}"/></td>
                    <td><c:out value="${performedActivity.distanceInMeters}"/></td>
                    <td><c:out value="${performedActivity.calories}"/></td>
                    <td>
                        <form method="get" action="${pageContext.request.contextPath}/performedActivity/update/${performedActivity.id}">
                            <input class="editButton" type="submit" value="">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        </form>
                    </td>
                    <td>
                        <form method="post" action="${pageContext.request.contextPath}/performedActivity/delete/${performedActivity.id}">
                            <input class="deleteButton" type="submit" value="">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <form:form method="post" action="${pageContext.request.contextPath}/performedActivity/update" modelAttribute="performedActivity">
            <fieldset id="newPerformedActivity"><h3><fmt:message key="performedActivity.list.newPerformedActivity"/></h3>
                    <%@include file="form.jsp"%>
                <input class="createButton" type="submit" value="<fmt:message key='performedActivity.list.createPerformedActivity'/>">
            </fieldset>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form:form>
    </jsp:attribute>
</my:layout>