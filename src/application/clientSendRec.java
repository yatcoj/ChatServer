package application;

import java.io.*;
import java.net.*;
import java.util.Scanner;

import javafx.scene.control.TextArea;

public class clientSendRec
{
	static int ServerPort;
	DataOutputStream out;
	DataInputStream in;
	String serverIp;
	int port;
	String userName;
	Boolean flag = true;
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
		
//		//actively look to send new message
//		Thread sendMessage = new Thread(new Runnable() 
//		{
//			@Override
//			public void run() 
//			{
//				while (true) 
//				{
//					// read the message to deliver.
//					String msg = txtIn.getText();
//					try 
//					{
//						// write on the output stream
//						out.writeUTF(msg);
//					}
//					catch (IOException e)
//					{
//						e.printStackTrace();
//					}
//				}
//			}
//		});
		 
		// readMessage thread
		Thread readMessage = new Thread(new Runnable() 
		{
			@Override
			public void run() 
			{
				while (true)
				{
					try 
					{
						// read the message sent to this client
						String msg = in.readUTF();
						txtOut.setText(msg);
						System.out.println(msg);
					} 
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}
			}
		});
//		sendMessage.start();
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