package application;

import java.io.*;
import java.net.*;

import javafx.scene.control.TextArea;

public class clientSendRec
{
	static int ServerPort;
	private final DataOutputStream out;
	private final DataInputStream in;
	private String serverIp;
	int port;
	private String userName;
	
	public clientSendRec(String ip, int port, TextArea txtOut, String user, TextArea txtIn) throws UnknownHostException, IOException 
	{		
		ServerPort = port;
		serverIp = ip;
		userName = user;
		
		// establish the connection
		Socket socket = new Socket(serverIp, ServerPort);
		  
		// obtaining input and out streams
		in = new DataInputStream(socket.getInputStream());
		out = new DataOutputStream(socket.getOutputStream());
		
		// readMessage thread
		Thread readMessage = new Thread(new Runnable() 
		{
			@Override
			public void run() 
			{
				try 
				{
					while (true)
					{
						// read the message sent to this client
						String msg = in.readUTF();
						txtOut.appendText("\n"+msg);
					} 
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		});
		
		readMessage.start();
	}
	
	public void sendMessage(String message)
	{
		try
		{
			out.writeUTF(userName + "@"+message);
		}
		catch(IOException e)
		{
		}
	}
}