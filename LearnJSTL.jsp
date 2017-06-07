<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Learn JSTL</title>
    </head>
    <body>
        <sql:setDataSource 
            var="dbcon" driver="com.mysql.jdbc.Driver"  
            url="jdbc:mysql://localhost/day"  
            user="root" password=""
        />
        <%-- selecting data from db using the jstl query tag
        <sql:query dataSource="${dbcon}" var="rs">select * from user;</sql:query> 
        <c:forEach var="user" items="${rs.rows}">
            <c:out value="${user.id}"/>
            <c:out value="${user.fname}"/>
            <c:out value="${user.lname}"/>
            <c:out value="${user.district}"/>
        </c:forEach>
        --%>
        <%-- inserting using the update DML tag
        <sql:update dataSource="${dbcon}" var="insert">
            insert into user set fname='allan',lname='katamba',district='singo';
        </sql:update>
        --%>
        <%--
        <c:set var="fname" value="allan"/>
        <sql:update var="del" dataSource="${dbcon}">
            delete from user where fname = ?;
            <sql:param value="${fname}"></sql:param>
        </sql:update>
        --%>
        <%--  c statements  --%>
            
        <%-- just prints the value <c:out value="${'Mukiibi'}" />  --%>
        <%--  <c:import var="data" url="/Template.jsp"/>
        <c:out value="${data}" />
        --%>
        <%--
        <c:set var="age" scope="session" value="${2017-1992}"  />
        <c:out value="${age}" />
        --%>   
        <%--
        <c:set var="income" scope="session" value="${500*6}"/>
        <c:if test="${income>700}">
            <c:out value="${income}" />
        </c:if>
        --%>
        <%--
        <c:set var="income" scope="session" value="${4000*4}"/>  
            <p>Your income is : <c:out value="${income}"/></p>  
        <c:choose>  
            <c:when test="${income <= 1000}">  
                Income is not good.  
            </c:when>  
            <c:when test="${income > 10000}">  
                Income is very good.  
            </c:when>  
            <c:otherwise>  
                Income is undetermined...  
            </c:otherwise>  
        </c:choose> 
        --%>
        <%--
        <c:forEach var="counter" begin="1" end="10" step="2">
            <c:out value="${counter}"/>
        </c:forEach>
        --%>
        <%-- 
        <c:url value="/Template.jsp" var="theURL">  
            <c:param name="trackingId" value="786"/>  
            <c:param name="user" value="Nakul"/>  
        </c:url>
        --%>
        <%--
        <c:redirect url="/Template.jsp">
            
            <c:param name="trackingId" value="786"/>  
            <c:param name="user" value="Nakul"/>  
        </c:redirect>
        --%>
    </body>
</html>
