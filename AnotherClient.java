import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class AnotherClient 
{
    public static Socket socket;
    public int port;
    public static boolean flag;
    public String ip;
    public static String username;
    public static BufferedReader in;
    public static BufferedWriter out;
    public static BufferedReader sysRead = new BufferedReader(new InputStreamReader(System.in));

    AnotherClient (String ip, int port)
    {
        this.ip = ip;
        this.port = port;
        if (!createSocket())
            System.out.println("Cannot connect to the server. IP: " + ip + " PORT: " + port);
        else 
            System.out.println("Connected to " + ip + ":" + port);
    }

    public boolean createSocket ()
    {
        try
        {
            socket = new Socket(ip, port);
        }
        catch (IOException e) 
        {
            e.getMessage(); 
            return false;
        }
        return true;
    }

    public static void main (String [] args)throws IOException
    {
        AnotherClient client = new AnotherClient("127.0.0.1", 10008);
        
        
        
        //checkFloat();
        
        //GET TRANSACTION COMMAND MODULE
        System.out.println("\nCOMMANDS AVAILABLE");
        System.out.println("----record transaction");
        System.out.println("----forward Deposit transaction");
        System.out.println("----check and service pending forwarded transaction");
        System.out.println("----check Forward Status");
        String userInput;
        System.out.print ("input: ");
        System.out.println("Type message and Bye to quit");
        while ((userInput = in.readLine()) != null) 
    {
        //out.println(userInput);
        if(userInput.equals("Bye"))
            break;
        System.out.println("echo: " + in.readLine()); System.out.print ("input: ");
    }
        //checkRequests();
        //commandType();
        /*
        while(true)
            getCommand();
        */
        //LOG IN MODULE
        
        /*if(login().equals("success"))
            
            // System.out.println("yes please");
            getCommand(); 
        else
            System.out.println("no please");    
          */
        //commit deposit 0784127463 9800
}

        public static void checkRequests() throws IOException
        {
            Statement stat = getMyStatement();
            try
            {
                ResultSet j = stat.executeQuery("select * from exchange where request_to='alv' and status='pending'");
                while(j.next())
                {
                    
                    System.out.println("Customer Number: "+j.getString(4)+" Amount: " + j.getString(5));
                    
                }
            }
            catch(SQLException gh){gh.getMessage();}
        }
        public static void forwardTrans()throws IOException
        {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            System.out.println("Type command, also specifying name of the other status:");
            String cmdType = sysRead.readLine();
            out.write(cmdType + "\n");
            out.flush();
        }
    
        public static void commandType()throws IOException
        {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            System.out.println("Which transaction command do you want to use:");
            String cmdType = sysRead.readLine();
            String[] cmds = cmdType.split("\\s+");
            if(cmds[0].equals("record"))
            {
                getCommand();
                commandType();
            }
            else if(cmds[0].equals("forward"))
            {
                forwardTrans();
                commandType();
            }
            else if(cmds[0].equals("check"))
            {
                checkRequests();
                getCommand();
                commandType();
            }
            else if(cmds[0].equals("service"))
            {
                
            }
        }
        public static void getCommand()throws IOException
        {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            System.out.println("Type the commands to transact");
            String[] cmds = new String[3]; 
            for(int i = 0; i < cmds.length;i++)
            {
                cmds[i] = sysRead.readLine();
                out.write(cmds[i] + "\n");
                out.flush();
            }
            
             
            
        }
        public static void checkFloat()throws IOException
        {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            System.out.println("Enter command: ");
            String cmd = sysRead.readLine();
            out.write(cmd + "\n");
            
            out.flush();
            
            //String
            
        }
        public static String login()throws IOException
        {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            
            System.out.println("Enter username to log in: ");
            String username = sysRead.readLine();
            out.write(username + "\n");
            out.flush();
            if(in.readLine().equals("success"))
                return "success";
            else
                return "failed";
        }
            
        public static Statement getMyStatement()
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
            System.out.println("ERROR LOADING DRIVER " + var.getMessage());
        }
        return theState;
    }	
}