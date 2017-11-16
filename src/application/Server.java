package application;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
				
				//Create client handler
				System.out.println("A client has Connected: " + clients);
				
				//BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clients.getInputStream()));
				DataInputStream inFromClient = new DataInputStream(clients.getInputStream());
				DataOutputStream outToClient = new DataOutputStream(clients.getOutputStream());
				
				ClientHandler ch = new ClientHandler(clients, clientCount, inFromClient, outToClient);
				
				
				//add client to list
				clientList.add(ch);
				
				//start new thread
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
	private final DataInputStream in;
	private final DataOutputStream out;
	
	public ClientHandler(Socket clients, int clientCount, DataInputStream read, DataOutputStream send) 
	{
		this.client = clients;
		this.clientID = clientCount;
		this.in = read;
		this.out = send;
	}

	@Override
	public void run() 
	{
		try
		{    	
			while(true)
	        {
				String msg = in.readUTF();
				System.out.println(msg);
				
				for(ClientHandler all: Server.clientList)
				{
					all.out.writeUTF(msg);
				}
	        }
	    }
	    catch(IOException e)
	    {
	        e.printStackTrace();
	    }
	}
	
	public String getName()
	{
		return this.name;
	}
}