<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<fmt:message var="title" key="sportsman.edit.title"/>
<my:layout title="${title}">
 <jsp:attribute name="body">
<form:form method="post" action="${pageContext.request.contextPath}/sportsman/update" modelAttribute="sportsman">
    <form:hidden path="id"/>
    <fieldset><h3><fmt:message key="sportsman.edit.edit"/></h3>
        <%@include file="form.jsp"%>
        <input type="submit" class="saveButton" value="<fmt:message key='sportsman.edit.save'/>">
    </fieldset>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form:form>
</jsp:attribute>
</my:layout>