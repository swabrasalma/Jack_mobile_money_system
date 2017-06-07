<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>

 


<!DOCTYPE html>
<html>
    <head>
        <title>index</title>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
	<link rel="stylesheet" href="css/main.css" />
        <script type="text/javaScript">
          <!--
	    function shout(x)
		{
		   alert(x);
		   }
                   -->

            
            </script>
    </head>
    <body>
        <header id="header">
            <h1>Control Panel</h1>
            <p>Getting rich through effective mobile money business management </p>
	</header>
        <form id="signup-form" method="post" action="/MobileMoney/Index.jsp">
            <input type="text" name="username" id="email" placeholder="User-name" />
            <input type="submit" value="Log in" name ="login" />
        </form>
    </body>
    <%
    if(request.getParameter("login")!=null)
    {
        String username = request.getParameter("username");
        %>
        <sql:setDataSource 
                                var="dbcon" driver="com.mysql.jdbc.Driver"  
                                url="jdbc:mysql://localhost/mobile"  
                                user="root" password=""
                    />
        <sql:query dataSource="${dbcon}" var="rs">select * from user where username="<%=username%>" and status="admin";</sql:query>
        <c:set var="count" value="${0}"/>
        <c:forEach items="${rs.rows}" var="rt">
            <c:set var="count" value="${count+1}"/>
        </c:forEach>
        <c:if test="${count==1}">
            <script type="text/javascript"> 
                shout("Log in successful");
                window.location='/MobileMoney/Menu.jsp';
                <%
                    session.setAttribute("username",username);
                %>
            </script>
        </c:if>
    <%
        }
    %>
</html>
