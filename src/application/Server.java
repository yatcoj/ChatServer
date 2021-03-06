package application;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import encrypt.Encryption;

public class Server 
{
	static int clientCount = 0;
	int maxClients = 5;
	public static ArrayList<ClientHandler> clientList = new ArrayList<ClientHandler>();
	
	public Server(int port)
	{
		try 
		{
			ServerSocket socket = new ServerSocket(port);
			Socket clients = null;
			System.out.println("Waiting for clients to Connect!)");
			while(true)
			{
				//Accept incoming request to connect to server
				clients = socket.accept();
				
				//BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clients.getInputStream()));
				DataInputStream inFromClient = new DataInputStream(clients.getInputStream());
				DataOutputStream outToClient = new DataOutputStream(clients.getOutputStream());
				
				String n = inFromClient.readUTF();
				n = (new Encryption()).decrypt(n);
				//Create client handler
				ClientHandler ch = new ClientHandler(clients, clientCount, n, inFromClient, outToClient);
				
				System.out.println(ch.getName() + " has Connected: " + clients);
				
				//add client to list
				clientList.add(ch);
				
				for(ClientHandler all: Server.clientList)
				{
					all.out.writeUTF((new Encryption()).encrypt(ch.getName() + " has Connected: " + clients));
				}
				
				//start new thread; multi threading to handle multiple clients
				Thread t = new Thread(ch);
				t.start();
				
				//increment client count
				clientCount++;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}

class ClientHandler implements Runnable 
{
	private Socket client;
	private String name;
	private int clientID;
	public final DataInputStream in;
	public final DataOutputStream out;
	
	public ClientHandler(Socket clients, int clientCount, String n, DataInputStream read, DataOutputStream send) 
	{
		this.client = clients;
		this.clientID = clientCount;
		this.name = n;
		this.in = read;
		this.out = send;
	}

	@Override
	public void run() 
	{
		try
		{    	
			boolean run = true;
			while(run)
	        {
				String msg = "";
				//Put an if statement to check if want to disconnect
				try
				{
					msg = in.readUTF();
					msg = (new Encryption()).decrypt(msg);
					System.out.println(msg);
				}
				catch(SocketException e)
				{
					Server.clientList.remove(this);
				}
				
				if(msg == "D!sc0nn3ct*")
				{
					run = false;
					client.close();
					Server.clientList.remove(this);
					break;
				}
				else
				{			
					for(ClientHandler all: Server.clientList)
					{
						all.out.writeUTF((new Encryption()).encrypt(this.name + ": " + msg));
					}
				}
	        }
	    }
	    catch(IOException e)
	    {
	        e.printStackTrace();
	        System.out.println("this is an error");
	    }
	}
	
	public String getName()
	{
		return this.name;
	}
}