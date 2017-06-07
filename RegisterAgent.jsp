<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register Agent</title>
        <link rel="stylesheet" href="css/style.css">
        <style type="text/css">
           
        </style>
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
    <center>
        <h1 align="center" >MOBILE MONEY SYSTEM</h1></center>
        <div class="container">  
            
            <form id="contact" action="/MobileMoney/RegisterAgent.jsp" method="post">
              
                <h3>Register Agent</h3>
                <h4>Enter the fields below</h4>
                <fieldset>
                    <input placeholder="Username" name="username" type="text"tabindex="1"   required autofocus>
                </fieldset>
                <fieldset>
                    <input placeholder="Kiosk name/Location" name="kioskName" type="text" tabindex="2" required>
                </fieldset>
                <fieldset>
                    <input placeholder="Mtn Phone Number" name="mtn" type="text" maxlength="10" tabindex="3" required>
                </fieldset>
                <fieldset>
                    <input placeholder="Airtel Phone Number"maxlength="10" name="warid" type="text" tabindex="4" required>
                </fieldset>
                <fieldset>
                    <input placeholder="Initial Mtn Float" name="mtn_f" type="text" tabindex="5" required>
                </fieldset>
                <fieldset>
                    <input placeholder="Initial Airtel Float" name="warid_f" type="text" tabindex="6" required>
                </fieldset>
                <fieldset>
                    <input placeholder="Initial Mtn Cash" name="mtn_c" type="text" tabindex="7" required>
                </fieldset>
                <fieldset>
                    <input placeholder="Initial Airtel Cash" name="warid_c" type="text" tabindex="8" required>
                </fieldset>
               
                <fieldset>
                    <button name="submit" value="make" type="submit" id="contact-submit" data-submit="...Sending">Make Agent</button>
                    <button name="submit" value="skipp" type="submit" id="contact-submit" onClick="location.href='Menu.jsp'" data-submit="...Sending">Skip</button>
                </fieldset>
                
            </form>
            <p>You are logged in as <%=session.getAttribute("username").toString()%>   </p>
        <button onclick="location.href='LogOut.jsp'" title="Log Out">Log Out</button>
    </body>
  <sql:setDataSource 
            var="dbcon" driver="com.mysql.jdbc.Driver"  
            url="jdbc:mysql://localhost/mobile"  
            user="root" password=""
        />
<%
       String check = request.getParameter("submit");
       if(check!=null && check.equals("make"))
       {
        %>
            <sql:update dataSource="${dbcon}" var="insert">
            
            insert into kiosk set name='<%= request.getParameter("kioskName") %>',
                                  agentName = '<%= request.getParameter("username") %>',
                                  mtn_Num = '<%= request.getParameter("mtn") %>',
                                  warid_Num = '<%= request.getParameter("warid") %>',
                                  mtn_Float = '<%= request.getParameter("mtn_f") %>',
                                  warid_Float = '<%= request.getParameter("warid_f") %>',
                                  mtn_Cash = '<%= request.getParameter("mtn_c") %>',
                                  warid_Cash = '<%= request.getParameter("warid_c") %>';
                               
            </sql:update>
                                 
            <sql:update dataSource="${dbcon}" var="ins">
                insert into user set username='<%= request.getParameter("username") %>',
                                     status='agent';
            </sql:update>
            <script type="text/javascript"> 
                shout("Agent successfully added");
                window.location='/MobileMoney/Menu.jsp';
            </script>
            <%
                }
%>  
    
    
    
     
     
        
     
    
    
</html>
