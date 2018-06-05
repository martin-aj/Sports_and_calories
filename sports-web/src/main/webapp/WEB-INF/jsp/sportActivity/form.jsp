<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
   <table>
       <tr>
           <th><form:label path="name"><fmt:message key="sportActivity.name"/> *</form:label></th>
           <td class="width-stretch"><form:input path="name" type="text"></form:input></td>                       
       </tr>               
       <tr>
           <td colspan="2"><form:errors path="name" element="div" cssClass="messageError"/></td>
       </tr>
   </table>    
 