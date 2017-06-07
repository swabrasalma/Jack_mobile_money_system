<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Transaction</title>
        <link rel="stylesheet" href="css/cssdp.css">
    </head>
    <body>
        <jsp:include page="Header.jsp"/>
        <div align="center">
            <ul class="nav">
	            <td><li><a href="/MobileMoney/Menu.jsp">MENU PAGE</a></li></td>
	            <td> <li><a href="/MobileMoney/Cash_float.jsp">CASH & FLOAT</a></li></td>
	            <td><li><a href="/MobileMoney/Commission.jsp">COMMISSION</a></li></td>  
	    </ul>
	    </div>
         <center>
        <fieldset style="width:60%;">
            <legend align="center">TRANSACTIONS 
            
            </legend>
            <table border="0" width="1200px;">
		    <tr>
                        <th>Transaction Type</th>
                        <th>Customer No</th>
                        <th>Amount</th>
                        <th>Commission(Shs)</th>
                        <th>Kiosk No</th>
                        <th>Agent Name</th>
                        <th>Date/Time</th>
                        <th>Transaction Status</th>
                        
                    </tr> 
                    <sql:setDataSource 
                                var="dbcon" driver="com.mysql.jdbc.Driver"  
                                url="jdbc:mysql://localhost/mobile"  
                                user="root" password=""
                    />
                    <sql:query dataSource="${dbcon}" var="get">
                        select * from transaction order by agent_Name;
                    </sql:query>
                       
                    <c:forEach var="write" items="${get.rows}">
                         <tr>
                        <td><c:out value="${write.type}"/></td>
                        <td><c:out value="${write.customer_num}"/></td>
                        <td><c:out value="${write.amount}"/></td>
                        <td><c:out value="${write.commission}"/></td>
                        <td><c:out value="${write.kiosk_fonNum}"/></td>
                        <td><c:out value="${write.agent_Name}"/></td>
                        <td><c:out value="${write.date_time}"/></td>
                        <td><c:out value="${write.status}"/></td>
                         </tr>
                    </c:forEach>    
       </table>
        </fieldset></center>
                    <center>
        <fieldset style="width:60%;">
            <legend align="center">DEPOSIT EXCHANGED TRANSACTIONS
            
            </legend>
            <table border="0" width="1200px;">
		    <tr>
                        <th>Request From</th>
                        <th>Request To</th>
                        <th>Customer Number</th>
                        <th>Amount(Shs)</th>
                        <th>Status</th>
                       
                        
                    </tr> 
                    <sql:setDataSource 
                                var="dbcon" driver="com.mysql.jdbc.Driver"  
                                url="jdbc:mysql://localhost/mobile"  
                                user="root" password=""
                    />
                    <sql:query dataSource="${dbcon}" var="get">
                        select * from exchange order by status;
                    </sql:query>
                       
                    <c:forEach var="write" items="${get.rows}">
                         <tr>
                        <td><c:out value="${write.request_from}"/></td>
                        <td><c:out value="${write.request_to}"/></td>
                        <td><c:out value="${write.customer_Num}"/></td>
                        <td><c:out value="${write.amount}"/></td>
                        <td><c:out value="${write.status}"/></td>
                        
                         </tr>
                    </c:forEach>    
       </table>
        </fieldset>
                 <p>You are logged in as <%=session.getAttribute("username").toString()%>   </p>
        <button onclick="location.href='LogOut.jsp'" title="Log Out">Log Out</button>   
                    </center>
    </body>
</html>
