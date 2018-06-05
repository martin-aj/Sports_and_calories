<!DOCTYPE html>
<%@ tag pageEncoding="utf-8" dynamic-attributes="dynattrs" trimDirectiveWhitespaces="true" %>
<%@ attribute name="title" required="true" %>
<%@ attribute name="head" fragment="true" %>
<%@ attribute name="body" fragment="true" required="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="${pageContext.request.locale}">
    <head>
        <title><c:out value="${title}"/></title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style.css"/>
        <link rel="stylesheet" href="http://code.jquery.com/ui/1.9.2/themes/base/jquery-ui.css" />
        <script src="http://code.jquery.com/jquery-1.8.3.js"></script>
        <script src="http://code.jquery.com/ui/1.9.2/jquery-ui.js"></script>
        <script src="${pageContext.request.contextPath}/general.js"></script>

        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/scrollbar.css" />             
        <script type="text/javascript" src="${pageContext.request.contextPath}/scrollbar.js"></script>

        <jsp:invoke fragment="head"/>
    </head>
    <body>
        <div id="wrap">      

            <!-- sidebar -->
            <div id="sidebar">    
               <div id="logo"> <a href="${pageContext.request.contextPath}/"><span>Nike</span></a></div>

                <!-- logout form -->
                <c:url value="/logout" var="logoutUrl" />
                <form class="form-inline" id="logoutForm" action="${logoutUrl}" method="post">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </form>
                <script>
                    function formSubmit() {
                            document.getElementById("logoutForm").submit();
                    }
                </script>
                
                <!-- navigation menu -->
                <ul id="navigation">                    
                    <li><a href="${pageContext.request.contextPath}/"><f:message key="navigation.index"/></a></li>
                    <li><a href="${pageContext.request.contextPath}/performedActivity/list"><f:message key="navigation.performedActivity"/></a></li>
                    <li><a href="${pageContext.request.contextPath}/sportsman/list"><f:message key="navigation.sportsmen"/></a></li>                                        
                    <li><a href="${pageContext.request.contextPath}/sportActivity/list"><f:message key="navigation.sportActivities"/></a></li>                                
                    <li><a href="${pageContext.request.contextPath}/caloricTableEntry/list"><f:message key="navigation.caloricTableEntry"/></a></li>                      
                </ul>                                                
            </div>
            <div id="container">
                <c:if test="${pageContext.request.userPrincipal.name != null}">
                <div id="userDiv">                    
                        <table id="userTable">
                            <td id="userIco"></td>                            
                            <td id="userName">${pageContext.request.userPrincipal.name}</td>
                            <c:if test="${pageContext.request.isUserInRole('ROLE_ADMIN')}">
                                    <td id="userName"><li><a href="${pageContext.request.contextPath}/user/register"><f:message key="navigation.registration"/></a></li></td>
                            </c:if>
                            <td id="userLogout"><a href="javascript:formSubmit()"></a></td>
                        </table>                                           
                </div>
                </c:if>
                <div class="page" id="home">
                    <!-- page header -->
                    <h1><c:out value="${title}"/></h1>
                    <!-- page content -->
                    <div class="page_content">              

                        <c:if test="${not empty message}">
                            <div class="message"><c:out value="${message}"/></div>                           
                        </c:if>
                        <c:if test="${not empty error}">
                            <div class="messageError"><c:out value="${error}"/></div>                        
                        </c:if> 
                        <jsp:invoke fragment="body"/>

                    </div>
                </div>
                <div class="footer">
                    <p>Copyright &copy; <a href="#">A-Team</a> | <f:message key="copyright.rights"/></p>
                    <p><f:message key="copyright.java"/></p>
                </div>
            </div>


        </div>
    </body>
</html>


