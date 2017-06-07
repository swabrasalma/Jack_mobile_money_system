import java.io.*;
/*
import java.io.IOException;
import java.io.PrintWriter;
*/
import java.sql.*;
/*
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
*/
import javax.servlet.*;
import javax.servlet.http.*;
/*
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
*/


public class DBConnection extends HttpServlet 
{
    PrintWriter out;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException
    {
        //service does not throw the ClassNotFoundException
            out = response.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DBConnection</title>");         
            out.println("</head>");
            out.println("<body>");           
            out.println("</table>");                  
            out.println("</body>");
            out.println("</html>");
        }
    public Statement getMyStatement()
    {
        Statement theState = null;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost/mobile";
            Connection conn =  DriverManager.getConnection(url,"root","");
            theState = conn.createStatement();   
        }
        catch(ClassNotFoundException | SQLException var)
        {
            out.println("ERROR LOADING DRIVER " + var.getMessage());
        }
        
            return theState;
    }

   

}
