
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
     <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cash and Floats</title>
        <link rel="stylesheet" href="css/cssdp.css">
        
    </head>
    <body>
        
        <jsp:include page="Header.jsp"/>
            <div align="center">
            <ul class="nav">
	            <td><li><a href="/MobileMoney/Menu.jsp">MENU PAGE</a></li></td>
	            <td> <li><a href="/MobileMoney/Transaction.jsp">TRANSACTIONS</a></li></td>
	            <td><li><a href="/MobileMoney/Commission.jsp">COMMISSION</a></li></td>
		    
	        </ul>
	    </div>
    <center>
        <fieldset style="width:60%;">
            <legend align="center">CASH AND FLOAT 
                </legend>
            <table border="1" width="850px;">
		    <tr>
                        <th>Kiosk-Name</th>
                        <th>Agent-Name</th>
			<th>Mtn Float</th>
			<th>Mtn Cash</th>
			<th>Warid Float</th>
                        <th>Warid Cash</th>
                    </tr> 
                    <sql:setDataSource 
                                var="dbcon" driver="com.mysql.jdbc.Driver"  
                                url="jdbc:mysql://localhost/mobile"  
                                user="root" password=""
                    />
                    <sql:query dataSource="${dbcon}" var="get">
                        select * from kiosk;
                    </sql:query>
                       
                    <c:forEach var="write" items="${get.rows}">
                         <tr>
                        <td><c:out value="${write.name}"/></td>
                        <td><c:out value="${write.agentName}"/></td>
                        <td><c:out value="${write.mtn_Float}"/></td>
                        <td><c:out value="${write.mtn_Cash}"/></td>
                        <td><c:out value="${write.warid_Float}"/></td>
                        <td><c:out value="${write.warid_Cash}"/></td>
                         </tr>
                    </c:forEach>    
       </table>
        </fieldset>
    <p>You are logged in as <%=session.getAttribute("username").toString()%>   </p>
        <button onclick="location.href='LogOut.jsp'" title="Log Out">Log Out</button>
    </center>
    </body>
</html>
