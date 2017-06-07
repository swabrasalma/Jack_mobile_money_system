import java.io.*;
import java.net.*;
public class EchoClient 
{     
    public static void main(String[] args) throws IOException 
    {
        String serverHostname = new String ("127.0.0.1");
        
        if (args.length > 0)   
            serverHostname = args[0];   
        System.out.println ("Attemping to connect to host " + serverHostname + " on port 10007.");

        Socket echoSocket = null; 
        PrintWriter out = null; 
        BufferedReader in = null;
   
        try{
            echoSocket = new Socket(serverHostname, 10008);
            out = new PrintWriter(echoSocket.getOutputStream(),true);
            in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
        }
        catch(UnknownHostException e)
        {
            System.err.println("Dont know about host :" + serverHostname);
            System.exit(1);
        }
        catch(IOException e)
        {
            System.err.println("couldnot get IO");
            System.exit(1);
        }
        BufferedReader stdIn = new BufferedReader( new InputStreamReader(System.in));
        String userInput;
        System.out.print ("input: ");
        System.out.println("Type message and Bye to quit");
        while ((userInput = stdIn.readLine()) != null) 
    {
        out.println(userInput);
        if(userInput.equals("Bye"))
            break;
        System.out.println("echo: " + in.readLine()); System.out.print ("input: ");
    }
    out.close();
    in.close();
    stdIn.close();
    echoSocket.close();
    }
    }
