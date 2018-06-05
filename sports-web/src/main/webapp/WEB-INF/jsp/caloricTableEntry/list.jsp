<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="true"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<fmt:message var="title" key="caloricTableEntry.list.title"/>
<my:layout title="${title}">
    <jsp:attribute name="body">

        <h2><fmt:message key="caloricTableEntry.list.allCaloricTableEntries"/></h2>

        <table class="basic">
            <tr>
                <th style="display:none">id</th>
                <th class="width-stretch"><fmt:message key="caloricTableEntry.sportActivity"/></th>  
                <th><fmt:message key="caloricTableEntry.weightFrom"/></th>
                <th><fmt:message key="caloricTableEntry.weightTo"/></th>
                <th><fmt:message key="caloricTableEntry.calValue"/></th>    
                    <c:if test="${pageContext.request.isUserInRole('ROLE_ADMIN')}">
                    <th><fmt:message key='table.edit'/></th>
                    <th><fmt:message key='table.delete'/></th>
                    </c:if>
            </tr>

            <c:forEach items="${caloricTableEntries}" var="caloricTableEntry">
                <tr>
                    <td style="display:none">${caloricTableEntry.id}</td>
                    <td><c:out value="${caloricTableEntry.sportActivity.name}"/></td>                    
                    <td><c:out value="${caloricTableEntry.weightFrom}"/></td>
                    <td><c:out value="${caloricTableEntry.weightTo}"/></td>
                    <td><c:out value="${caloricTableEntry.calValue}"/></td>

                    <c:if test="${pageContext.request.isUserInRole('ROLE_ADMIN')}">
                        <td>
                            <form method="get" action="${pageContext.request.contextPath}/caloricTableEntry/update/${caloricTableEntry.id}">
                                <input class="editButton" type="submit" value="">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            </form>
                        </td>
                        <td>
                            <form method="post" action="${pageContext.request.contextPath}/caloricTableEntry/delete/${caloricTableEntry.id}">
                                <input class="deleteButton" type="submit" value="">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            </form>
                        </td>
                    </c:if>
                </tr>
            </c:forEach>
        </table>
        <c:if test="${pageContext.request.isUserInRole('ROLE_ADMIN')}">
            <form:form method="post" action="${pageContext.request.contextPath}/caloricTableEntry/update" modelAttribute="caloricTableEntry">
                <fieldset id="newCaloricTableEntry"><h3><fmt:message key="caloricTableEntry.list.newCaloricTableEntry"/></h3>
                        <%@include file="form.jsp"%>
                    <input class="createButton" type="submit" value="<fmt:message key='caloricTableEntry.list.createCaloricTableEntry'/>">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </fieldset>
            </form:form>
        </c:if>
    </jsp:attribute>
</my:layout>