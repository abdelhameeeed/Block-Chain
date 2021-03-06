package p2p;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;
import java.util.HashSet;



public class ServerThread extends Thread
{
    private ServerSocket  serverSocket;
    private Set<ServerThreadThread> serverThreadThreads = new HashSet<ServerThreadThread>();

    public ServerThread(String portNum)throws IOException
    {
        serverSocket= new ServerSocket(Integer.valueOf(portNum));
    }
    public void run()
    {
       try
       {
         while (true)
         {
             ServerThreadThread serverThreadThread =new ServerThreadThread(serverSocket.accept(),this);
             serverThreadThreads.add(serverThreadThread);
             serverThreadThread.start();
         }
       }
       catch (Exception e)
       {
           e.getStackTrace();
       }

    }


	 void sendMessage(String message)
    {
      try
      {
        serverThreadThreads.forEach( t-> t.getPrintWriter().println(message));
      }
      catch (Exception e)
      {
          e.printStackTrace();
      }
    }
	
    public Set<ServerThreadThread> getServerThreadThreads()
    {
        return serverThreadThreads ;
    }
    
    
}

