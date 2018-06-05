<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<table>    
    <tr>
        <th><form:label path="sportsman"><fmt:message key="performedActivity.sportsman"/> *</form:label></th>
            <td>
            <form:select path="sportsman.id"> 
                <c:forEach items="${sportsmen}" var="s">
                    <form:option value="${s.id}">${s.nickname}</form:option>
                </c:forEach>
            </form:select></td>        
    </tr>
    <tr>
        <td colspan="2"><form:errors path="sportsman" element="div" cssClass="messageError"/></td>
    </tr>
    <tr>
        <th><form:label path="sportActivity"><fmt:message key="performedActivity.sportActivity"/> *</form:label></th>
            <td>
            <form:select path="sportActivity.id"> 
                <c:forEach items="${sportActivities}" var="a">
                    <form:option value="${a.id}">${a.name}</form:option>
                </c:forEach>
            </form:select></td>        
    </tr>
    <tr>
        <td colspan="2"><form:errors path="sportActivity" element="div" cssClass="messageError"/></td>
    </tr>
    <tr>
        <th><form:label path="startOfActivity"><fmt:message key="performedActivity.startOfActivity"/> *</form:label></th>
            <td class="width-stretch">
            <fmt:formatDate var="fmtDate" value="${performedActivity.startOfActivity}" pattern="MM/dd/yyyy"/>
            <form:input type="text" path="startOfActivity" value="${fmtDate}"/>
    </tr> 
    <tr>
        <td colspan="2"><form:errors path="startOfActivity" element="div" cssClass="messageError"/></td>
    </tr>

    <tr>
        <th><form:label path="durationInSeconds"><fmt:message key="performedActivity.durationInSeconds"/> *</form:label></th>
        <td><form:input path="durationInSeconds" type="text"></form:input></td>        
        </tr> 
        <tr>
            <td colspan="2"><form:errors path="durationInSeconds" element="div" cssClass="messageError"/></td>
    </tr>
    <tr>
        <th><form:label path="distanceInMeters"><fmt:message key="performedActivity.distanceInMeters"/> *</form:label></th>
        <td><form:input path="distanceInMeters" type="text"></form:input></td>

        </tr>   
        <tr>
            <td colspan="2"><form:errors path="distanceInMeters" element="div" cssClass="messageError"/></td>
    </tr>
</table>

