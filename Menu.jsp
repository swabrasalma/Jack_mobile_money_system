<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Menu</title>
        <link rel="stylesheet" href="css/csshome.css">
        
    </head>
    <body>
        <jsp:include page="Header.jsp"/>
    <center>
        
        <fieldset style="width:60%;">
            <legend align="center">MENU 
            
            </legend>
            <table height="250" align="center">
                <tr>
	            <td>
                        <button class="but" onClick="location.href='RegisterAgent.jsp'" title="Register new agent">
                            ADD NEW AGENT
                        </button>
                    </td> 
	             <td>
                        <button class="but" onClick="location.href='Cash_float.jsp'" title="check float and cash on kiosks">
                            CHECK FLOATS & CASH
                        </button>
                    </td> 
                </tr>
		<tr>
                </tr>
		<tr>
		    <td>
                        <button class="but" onClick="location.href='Commission.jsp'" title="Check and add commissions ">
                            COMMISSIONS
			</button>
                    </td> 
                    <td>
			<button class="but" onClick="location.href='Transaction.jsp'" title="View transaction data">
                            TRANSACTIONS
			</button>
                    </td> 
                </tr>
            </table>
	</fieldset>
        <p>You are logged in as <%=session.getAttribute("username").toString()%>   </p>
        <button onclick="location.href='LogOut.jsp'" title="Log Out">Log Out</button>
        </center>     
        
    </body>
</html>
