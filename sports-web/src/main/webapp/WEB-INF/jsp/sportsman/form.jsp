<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    <table>
        <tr>
            <th><form:label path="nickname"><fmt:message key="sportsman.nickname"/></form:label></th>
            <td class="width-stretch"><form:input path="nickname"/></td>            
        </tr>
        <tr>
          <td colspan="2"><form:errors path="nickname" element="div" cssClass="messageError"/></td>
       </tr>
        <tr>
            <th><form:label path="weightKg"><fmt:message key="sportsman.weightKg"/></form:label></th>
            <td><form:input path="weightKg"/></td>
            
        </tr>
        <tr>
           <td colspan="2"><form:errors path="weightKg" element="div" cssClass="messageError"/></td>
       </tr>
        <tr>
            <th><form:label path="heightCm"><fmt:message key="sportsman.heightCm"/></form:label></th>
            <td><form:input path="heightCm"/></td>
            
        </tr>
        <tr>
          <td colspan="2"><form:errors path="heightCm" element="div" cssClass="messageError"/></td>
       </tr>
        <tr>
            <th><form:label path="age"><fmt:message key="sportsman.age"/></form:label></th>
            <td><form:input path="age"/></td>
            
        </tr>
        <tr>
           <td colspan="2"><form:errors path="age" element="div" cssClass="messageError"/></td>
       </tr>
        <tr>
            <th><form:label path="sex"><fmt:message key="sportsman.sex"/></form:label></th>
            <td><form:select path="sex">
                <c:forEach items="${sexes}" var="c">
                    <form:option value="${c}"><fmt:message key="sportsman.sex.${c}"/></form:option>
                </c:forEach>
            </form:select>
            </td>            
        </tr>
    </table>
