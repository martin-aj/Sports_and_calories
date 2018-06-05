<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<table>
    <tr>
        <th><form:label path="sportActivity"><fmt:message key="caloricTableEntry.sportActivity"/> *</form:label></th>
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
        <th><form:label path="weightFrom"><fmt:message key="caloricTableEntry.weightFrom"/> *</form:label></th>
        <td class="width-stretch"><form:input path="weightFrom" type="text"></form:input></td>
        
    </tr>
    <tr>
           <td colspan="2"><form:errors path="weightFrom" element="div" cssClass="messageError"/></td>
       </tr>
    <tr>
        <th><form:label path="weightTo"><fmt:message key="caloricTableEntry.weightTo"/> *</form:label></th>
        <td><form:input path="weightTo" type="text"></form:input></td>
        
    </tr> 
    <tr>
           <td colspan="2"><form:errors path="weightTo" element="div" cssClass="messageError"/></td>
       </tr>
    <tr>
        <th><form:label path="calValue"><fmt:message key="caloricTableEntry.calValue"/> *</form:label></th>
        <td><form:input path="calValue" type="text"></form:input></td>
        
    </tr>
    <tr>
           <td colspan="2"><form:errors path="calValue" element="div" cssClass="messageError"/></td>
       </tr>
    
</table>

