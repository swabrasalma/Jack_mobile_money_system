import java.net.*;
import java.io.*;

public class EchoServer extends Thread
{
    protected Socket clientSocket;
    public static void main(String[] args)throws IOException
    {
        ServerSocket server = null;
        try
        {
            server = new ServerSocket(10008);
            System.out.println("Connection socket created");
            try
            {
                while(true)
                {
                    System.out.println("Waiting for connection");
                    new EchoServer(server.accept());
                }
            }
            catch(IOException e)
            {
                System.err.println("Accept failed, ");
                System.exit(1);
            }
        }
        catch(IOException e)
        {
            System.err.println("Could not listen on port 10008");
            System.exit(1);
        }
        finally
        {
            try
            {
                server.close();
            }
            catch(IOException e)
            {
                System.err.println("Could not close port : 10008");
                System.exit(1);
            }
        }
    }
    private EchoServer(Socket clientsoc)
    {
        clientSocket = clientsoc;
        start();
    }
    public void run()
        {
            try
            {
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                String inputLine;
                while((inputLine=in.readLine())!=null)
                {
                    System.out.println("Server: " + inputLine);
                    if(inputLine.equals("STOP"))
                        break;
                }
                
            }
            catch(IOException e)
            {
                System.err.println("Problem with server");
                System.exit(1);
            }
        }
        
        
}
        