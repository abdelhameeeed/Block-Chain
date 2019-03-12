package p2p;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.Socket;
import java.util.Random;

import javax.json.Json;

public class p2p {

    public static void main(String[] args) throws IOException 
    {
        
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter Username and port number for this peer : ");
        String[] values = bufferedReader.readLine().split(" ");
        ServerThread  serverThread = new ServerThread(values[1]);
        serverThread.start();
        new p2p().updateListenToPeers(bufferedReader,values[0],serverThread);
    }

    public void updateListenToPeers(BufferedReader bufferedReader, String username, ServerThread serverThread) throws IOException
    {
        System.out.println(">>> Enter ( Space Separated ) Hostname:PortNumber");
        System.out.println(">>> Peers to receive messages from ( press s to escape ):");
        String input = bufferedReader.readLine();
        String[] inputValues = input.split(" ");

        if(!input.equals("s"))
        {
            for ( int i=0 ; i<inputValues.length ; i++)
            {
                String[] address = inputValues[i].split(":");
                Socket socket = null ;
                try {
                    //hya5od el hostname k 2wl value fl array wl portNum 2nd value w y create mnhom object mn el socket 34an y2dr ysh8l beh el thread bta3t el peer
                      socket = new Socket(address[0],Integer.valueOf(address[1]));
                      new PeerThread(socket).start();

                    }
                    catch (Exception e)
                    {
                        if(socket != null)
                            socket.close();
                        else
                            System.out.println("Invalid Input !, Escaping to the next step ");
                    }
            }
        }
        communicate(bufferedReader,username,serverThread);
    }

    public void communicate(BufferedReader bufferedReader, String username, ServerThread serverThread)
    {
        try
        {
         System.out.println(">>> You can now communicate ( e to exit, c to change ) : ");
         boolean flag = true;

         while (flag)
         {    Random r=new Random ();
            // String message = bufferedReader.readLine();
         String message = r.toString();
             if (message.equals("e"))
             {
                 flag = false;
                 break;
             }
             else if (message.equals("c"))
             {
                updateListenToPeers(bufferedReader,username,serverThread);
             }
             else
             {
                 StringWriter stringWriter = new StringWriter();
                 Json.createWriter( stringWriter).writeObject(Json.createObjectBuilder()
                                                 .add("username", username)
                                                 .add("message", message)
                                                 .build());

                 serverThread.sendMessage(stringWriter.toString());
             }
         }
         System.exit(0);
        }
        catch (Exception e)
        {

        }

    }
}

