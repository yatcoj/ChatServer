package application;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JTextArea;

import javafx.scene.control.TextArea;

public class Client 
{
	DataOutputStream out;
	BufferedReader in;
	String serverIp;
	int port;
	String userName;
	Boolean flag = true;
	
	public Client(String ip, int port, TextArea removeFrom, String userName)
	{
		this.serverIp = ip;
		this.port = port;
		this.userName = userName;
		try 
		{
			Socket socket = new Socket(this.serverIp, this.port);
			out = new DataOutputStream(socket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			ServerInput wt = new ServerInput(socket, removeFrom);
			Thread thr = new Thread(wt);
			thr.start();
			sendMessage("");
		} 
		catch (UnknownHostException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
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

class ServerInput implements Runnable
{
	Socket socket;
	TextArea removeFrom;
	public ServerInput(Socket socket,TextArea removeFrom2) 
	{
		this.removeFrom = removeFrom2;
		this.socket = socket;
	}
	
	public void run()
	{
		BufferedReader in = null;
		try 
		{
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while(true)
			{
				String text = in.readLine();
				String[] str = getName(text);
				removeFrom.setText(removeFrom.getText()+("\n" + str[0] +": "+ str[1]));
			}
		}
		catch (IOException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	private String[] getName(String text)
	{
		//Returns the name and the text without the name
		String ans[] = {"", ""};
		boolean flag = true;
		for(int i = 0; i < text.length(); i++)
		{
			if(flag && !String.valueOf(text.charAt(i)).equals("@"))
			{
				ans[0] += String.valueOf(text.charAt(i));
			}
			else
			{
				if(flag)
				{
					flag = false;
					i++;
				}
				ans[1] += String.valueOf(text.charAt(i));
			}
		}
		return ans;
	}
}