<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<fmt:message var="title" key="sportActivity.list.title"/>
<my:layout title="${title}">
    <jsp:attribute name="body">

        <h2><fmt:message key="sportActivity.list.allSportActivities"/></h2>

        <table class="basic">
            <tr>
                <th style="display:none"><fmt:message key="sportActivity.id"/></th>
                <th class="width-stretch"><fmt:message key="sportActivity.name"/></th>
                <th><fmt:message key='table.edit'/></th>
                <th><fmt:message key='table.delete'/></th>

            </tr>

            <c:forEach items="${sportActivities}" var="sportActivity">
                 <tr>
                     <td style="display:none">${sportActivity.id}</td>
                     <td><c:out value="${sportActivity.name}"/></td>
                     
                     
                     <td>
                         <form method="get" action="${pageContext.request.contextPath}/sportActivity/update/${sportActivity.id}">
                             <input class="editButton" type="submit" value="">
                             <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                         </form>
                     </td>
                     <td>
                         <form method="post" action="${pageContext.request.contextPath}/sportActivity/delete/${sportActivity.id}">
                             <input class="deleteButton" type="submit" value="">
                             <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                         </form>
                     </td>
 
                 </tr>
            </c:forEach>
        </table>

        <form:form method="post" action="${pageContext.request.contextPath}/sportActivity/update" modelAttribute="sportActivity">
            <fieldset><h3><fmt:message key="sportActivity.list.newSportActivity"/></h3>
                <%@include file="form.jsp"%>
                <input class="createButton" type="submit" value="<fmt:message key='sportActivity.list.createSportActivity'/>">
            </fieldset>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form:form>
    </jsp:attribute>
</my:layout>