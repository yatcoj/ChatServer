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
	
	public clientSendRec(String ip, int port, String user, TextArea txtOut, TextArea txtIn) throws UnknownHostException, IOException 
	{		
		ServerPort = port;
		serverIp = ip;
		userName = user;
		
		// establish the connection
		Socket socket = new Socket(serverIp, ServerPort);
		  
		// obtaining input and out streams
		in = new DataInputStream(socket.getInputStream());
		out = new DataOutputStream(socket.getOutputStream());
		
		out.writeUTF(this.userName);
		
		// readMessage thread
		Thread read = new Thread(new Runnable() 
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
						txtOut.appendText(msg+"\n");
					} 
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		});
		
		read.start();
	}
	
	public void sendMessage(String message)
	{
		try
		{
			out.writeUTF(message);
		}
		catch(IOException e)
		{
		}
	}
}