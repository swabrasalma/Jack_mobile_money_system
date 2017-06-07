import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.Date;
public class OurServer extends Thread
{
    public  int portNumber;
    public  static ServerSocket server;
    public static BufferedReader in;
    public static BufferedWriter out;
    public static boolean flag;
    public static Socket socket;
    
    OurServer(int portNumber)
    {
	this.portNumber = portNumber;
	if(!createServer())
            System.out.println("Cannot start the server");
	else
            System.out.println("Server is running at port: " + portNumber);
    }
    public boolean createServer()
    {
	try
	{
	    server = new ServerSocket(portNumber,3);
	}
	catch (IOException error)
	{
	    error.getMessage();
	    System.exit(-1);
	}
	return true;
    }
    public OurServer(Socket soc)
    {
        socket = soc;
        start();
    }
    public static void main (String [] args)throws IOException, InterruptedException
    {
        OurServer serv = new OurServer(4002);
        while(true)
        {
            new OurServer(serv.server.accept()); //blocks waiting for a client to establish a connection
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            System.out.println("Client connected");   
        }
    }
    public void run()
    {
        try
        {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            String result = VerifyLogin();
            String[] resultStrings = result.split("\\s+");
            while(true)
            {
                if(resultStrings[0].equals("success"))
                {
                    out.write(resultStrings[0] + "\n");
                    out.flush();
                    String todo = in.readLine();
                    if(todo.equals("record"))
                    {
                        insertTrans(resultStrings[1]);
                    }
                    else if(todo.equals("forward"))
                    {
                        forwardTrans(resultStrings[1]);
                    }
                    else if(todo.equals("check"))
                    {
                        checkRequests(resultStrings[1]); 
                        String k = checkRequests(resultStrings[1]);
                        serviceRequest(resultStrings[1], k);
                        insertTrans(resultStrings[1]);
                    }
                    else if(todo.equals("status"))
                    {
                        out.write(statusForward(resultStrings[1]) + "\n");
                        out.flush();
                    }
                }
                else
                {
                    socket.close();
                    in.close();
                    out.close();
                }
            }
        }
        catch(IOException io)
        {
            System.err.println("Problem with server");
            System.exit(1);
        }
    }
    public static String statusForward(String user)
    {
        Statement stat = getMyStatement(); 
        String status = null;
        try
        {
            ResultSet se = stat.executeQuery("select status from exchange where request_from ='"+user+"'");
            while(se.next())
            {
                status = se.getString(1);
            }
        }
        catch(SQLException h){ h.getMessage(); }
        return status;
    }
    public static void serviceRequest(String user, String id)
    {
        Statement stat = getMyStatement();
        try
        {
            stat.executeUpdate("update exchange set status='served' where ID='"+id+"'");    
        }
        catch(SQLException sq){ sq.getMessage(); }
    }
    public static String checkRequests(String user)throws IOException
    {
        Statement stat = getMyStatement();
        String field = null;
        try
        {
            ResultSet re = stat.executeQuery("select * from exchange where request_to='"+user+"' and status='pending' limit 1");
            while(re.next())
            {
                String sendBack = re.getString(4) +" "+re.getString(5);
                out.write(sendBack + "\n");
                out.flush();
                field = re.getString(1);                     
            }
        }
        catch(SQLException sq){ sq.getMessage(); } 
        return field;
    }
    public static void forwardTrans(String user)throws IOException
    {
        Statement stat = getMyStatement();
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        String cmd = in.readLine();
        String[] cmdArgs = cmd.split("\\s+");
        System.out.println("Forwaded Transaction Recieved: " + cmd);
        try
        {
            ResultSet t = stat.executeQuery("select agentName from kiosk where name='"+cmdArgs[3]+"'");
            while(t.next())
            {
                String na = t.getString(1);
                stat.execute("insert into exchange set request_from='"+user+"',customer_Num='"+cmdArgs[1]+"', amount='"+cmdArgs[2]+"',request_to='"+na+"',status='pending'");
            }  
        }
        catch(SQLException ss){ss.getMessage();}
    }
    public static void checkFloat()throws IOException
    {
        Statement stat = getMyStatement();
        String checkCMD = in.readLine();
        String[] cmds = checkCMD.split("\\s+");
        if(cmds[0].equals("check"))
        {
            String compact = null;
            if(cmds[1].equals("mtn"))
            {
                try
                {
                    ResultSet resd = stat.executeQuery("select name,mtn_Float from kiosk");
                    System.out.println("Kiosk name\tmtn_Float");
                    while(resd.next())
                    {
                        compact = resd.getString(1) +" " + resd.getString(2);
                        System.out.println(compact);
                    }
                }
                catch(SQLException hh){hh.getMessage();}
            }
            else
            {
                try
                {
                    ResultSet resd = stat.executeQuery("select name,warid_Float from kiosk");
                    System.out.println("Kiosk name\twarid_Float");
                    while(resd.next())
                    {
                        System.out.println(resd.getString(1)+"\t\t"+resd.getString(2));
                    }
                }
                catch(SQLException hh){hh.getMessage();}
            }
        }
    }
    public static void insertTrans(String user)throws IOException
    {
        String[] arr = new String[3];
        for(int i = 0; i<3;i++)
        {
            arr[i] = in.readLine();
            String[] cmdArgs = arr[i].split("\\s+");
            System.out.println("Recieved: " + arr[i]);
            String theTime = new Date().toString();
            Statement stat = getMyStatement();            
            if(cmdArgs[0].equals("commit"))
            {
                if((cmdArgs[2].startsWith("075") && Integer.parseInt(cmdArgs[3])<5000000)||(cmdArgs[2].startsWith("078") && Integer.parseInt(cmdArgs[3])<4000000))
                {
                    try
                    {
                        ResultSet ress = stat.executeQuery("select mtn_Num,warid_Num,mtn_Float,mtn_Cash,warid_Float,warid_Cash from kiosk where agentName='"+user+"'");
                        if(cmdArgs[2].startsWith("078"))
                        {
                            int com = 0;
                            if(cmdArgs[1].equals("deposit"))
                            {
                                com=getMtnCommission_dep(Integer.parseInt(cmdArgs[3]));
                                while(ress.next())
                                { 
                                    String no = ress.getString(1);
                                    int floa = ress.getInt(3)-Integer.parseInt(cmdArgs[3]);
                                    int cash = ress.getInt(4)+Integer.parseInt(cmdArgs[3]);
                                    stat.execute("insert into transaction set type='"+cmdArgs[1]+"',customer_num='"+cmdArgs[2]+"',amount='"+cmdArgs[3]+"',commission='"+com+"',agent_name='"+user+"',status='served',date_time='"+theTime+"',kiosk_fonNum='"+no+"'"); 
                                    stat.execute("update kiosk set mtn_Float="+floa+", mtn_Cash="+cash+" where agentName='"+user+"'");
                                }
                            }
                            else
                            {
                                com=getMtnCommission_with(Integer.parseInt(cmdArgs[3]));
                                while(ress.next())
                                {
                                    String no = ress.getString(1);
                                    int floa = Integer.parseInt(ress.getString(3))+Integer.parseInt(cmdArgs[3]);
                                    int cash = Integer.parseInt(ress.getString(4))-Integer.parseInt(cmdArgs[3]);
                                    stat.execute("insert into transaction set type='"+cmdArgs[1]+"',customer_num='"+cmdArgs[2]+"',amount='"+cmdArgs[3]+"',commission='"+com+"',agent_name='"+user+"',status='served',date_time='"+theTime+"',kiosk_fonNum='"+no+"'");
                                    stat.execute("update kiosk set mtn_Float="+floa+", mtn_Cash="+cash+" where agentName='"+user+"'");
                                }
                           }
                        }
                        else 
                        {
                            int com = 0;
                            if(cmdArgs[1].equals("deposit"))
                            {
                                com=getAirtelCommission_dep(Integer.parseInt(cmdArgs[3]));
                                while(ress.next())
                                { 
                                    String no = ress.getString(2);
                                    int floa = ress.getInt(5)-Integer.parseInt(cmdArgs[3]);
                                    int cash = ress.getInt(6)+Integer.parseInt(cmdArgs[3]);//kiosk_fonNum='"+ress.getString(1)+"'
                                    stat.execute("insert into transaction set type='"+cmdArgs[1]+"',customer_num='"+cmdArgs[2]+"',amount='"+cmdArgs[3]+"',commission='"+com+"',agent_name='"+user+"',status='served',date_time='"+theTime+"',kiosk_fonNum='"+no+"'"); 
                                    stat.execute("update kiosk set warid_Float="+floa+", warid_Cash="+cash+" where agentName='"+user+"'");
                                }
                            }
                            else
                            {
                                com=getAirtelCommission_with(Integer.parseInt(cmdArgs[3]));
                                while(ress.next())
                                {
                                    String no = ress.getString(2);
                                    int floa = Integer.parseInt(ress.getString(5))+Integer.parseInt(cmdArgs[3]);
                                    int cash = Integer.parseInt(ress.getString(6))-Integer.parseInt(cmdArgs[3]);
                                    stat.execute("insert into transaction set type='"+cmdArgs[1]+"',customer_num='"+cmdArgs[2]+"',amount='"+cmdArgs[3]+"',commission='"+com+"',agent_name='"+user+"',status='served',date_time='"+theTime+"',kiosk_fonNum='"+no+"'");
                                    stat.execute("update kiosk set warid_Float="+floa+", warid_Cash="+cash+" where agentName='"+user+"'");
                                }
                            }
                        }
                    }
                    catch(SQLException sq){sq.getMessage();}
                }  
            }
            else if(cmdArgs[0].equals("forward"))
            {
                //forwardTrans();
            }
        }
    } 
    public static int getMtnCommission_dep(int amount)
    {
        int commission = 0;
        if(amount>=500 && amount<=5000)
            commission = 0;
        else if(amount>5000 && amount<=60000)
            commission = 285;
        else if(amount>60000 && amount<=125000)
            commission = 440;
        else if(amount>125000 && amount<=250000)
                commission = 520;
            else if(amount>250000 && amount<=500000)
                commission = 850;
            else if(amount>500000 && amount<=1000000)
                commission = 2500;
            else if(amount>1000000 && amount<=2000000)
                commission = 4500;
            else if(amount>2000000 && amount<=4000000)
                commission = 8000;
            
            
            return commission;
        }
        public static int getMtnCommission_with(int amount)
        {
            int commission = 0;
            
            if(amount>=500 && amount<=2500)
                commission = 100;
            else if(amount>2500 && amount<=5000)
                commission = 125;
            else if(amount>5000 && amount<=15000)
                commission = 450;
            else if(amount>15000 && amount<=30000)
                commission = 500;
            else if(amount>30000 && amount<=45000)
                commission = 525;
            else if(amount>45000 && amount<=60000)
                commission = 575;
            else if(amount>60000 && amount<=125000)
                commission = 700;
            else if(amount>125000 && amount<=250000)
                commission = 1300;
            else if(amount>250000 && amount<=500000)
                commission = 2600;
            else if(amount>500000 && amount<=1000000)
                commission = 5000;
            else if(amount>1000000 && amount<=2000000)
                commission = 7500;
            else if(amount>2000000 && amount<=4000000)
                commission = 12500;
            
            
            return commission;
        }
        public static int getAirtelCommission_dep(int amount)
        {
            int commission = 0;
            
            if(amount>=500 && amount<=5000)
                commission = 150;
            else if(amount>5000 && amount<=60000)
                commission = 285;
            else if(amount>60000 && amount<=125000)
                commission = 440;
            else if(amount>125000 && amount<=250000)
                commission = 520;
            else if(amount>250000 && amount<=500000)
                commission = 850;
            else if(amount>500000 && amount<=1000000)
                commission = 2500;
            else if(amount>1000000 && amount<=2000000)
                commission = 4500;
            else if(amount>2000000 && amount<=4000000)
                commission = 8000;
            else if(amount>4000000 && amount<=5000000)
                commission = 9000;
            
            
            return commission;
        }
        public static int getAirtelCommission_with(int amount)
        {
            int commission = 0;
            
            if(amount>=500 && amount<=2500)
                commission = 100;
            else if(amount>2500 && amount<=5000)
                commission = 125;
            else if(amount>5000 && amount<=15000)
                commission = 450;
            else if(amount>15000 && amount<=30000)
                commission = 500;
            else if(amount>30000 && amount<=45000)
                commission = 525;
            else if(amount>45000 && amount<=60000)
                commission = 575;
            else if(amount>60000 && amount<=125000)
                commission = 700;
            else if(amount>125000 && amount<=250000)
                commission = 1300;
            else if(amount>250000 && amount<=500000)
                commission = 2600;
            else if(amount>500000 && amount<=1000000)
                commission = 5000;
            else if(amount>1000000 && amount<=2000000)
                commission = 7500;
            else if(amount>2000000 && amount<=4000000)
                commission = 12500;
            else if(amount>4000000 && amount<=5000000)
                commission = 15000;
            
            
            return commission;
        }
        public static String VerifyLogin()throws IOException
        {
        Statement stat_2 = getMyStatement();
        String name = in.readLine();
        
        System.out.println("Recieved username: " + name);
        try
        {
            int count = 0;
            ResultSet  user = stat_2.executeQuery("select * from user where username='"+name+"' and status='agent'");
            while(user.next())
            {
                ++count;
            }
            if(count==1)
            
                return "success "+name+"";
                
            
            else
            {
               return "failure";
                
            }
        }
        catch(SQLException sq){sq.getMessage();}
        return "failure";
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
