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
	ServerSocket socket;
	static int clientCount = 0;
	int maxClients = 5;
	static ArrayList<ClientHandler> clientList = new ArrayList<ClientHandler>();
	
	public Server(int port)
	{
		try 
		{
			socket = new ServerSocket(port);
			Socket clients = null;
			System.out.println("Waiting for clients to Connect!)");
			while(true)
			{
				//Accept incoming request to connect to server
				clients = socket.accept();
				
				//Create client handler
				System.out.println("A client has Connected: " + clients);
				
				BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clients.getInputStream()));
				DataOutputStream outToClient = new DataOutputStream(clients.getOutputStream());
				
				ClientHandler ch = new ClientHandler(clients, clientCount, inFromClient, outToClient);
				
				
				//add client to list
				clientList.add(clientCount, ch);
				ch.updateClientList(clientList);
				
				//start new thread
				new Thread(ch).start();
				
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
	Socket client;
	String name;
	int clientID;
	final BufferedReader in;
	final DataOutputStream out;
	ArrayList<ClientHandler> clientList;
	
	public ClientHandler(Socket clients, int clientCount, BufferedReader read, DataOutputStream send) 
	{
		this.client = clients;
		this.clientID = clientCount;
		this.in = read;
		this.out = send;
	}

	public void run() 
	{
		Boolean flag = true;
		Boolean firstRun = true;
		try
		{    	
			while(flag)
	        {
				
				out.writeBytes(in.readLine());
				//PrintWriter out = new PrintWriter(client.getOutputStream(), true);
	            //DataInputStream is = new DataInputStream(client.getInputStream());
	            //String input = is.readUTF();
//	            if(firstRun)
//	            {
//	            	firstRun = false;
//	            	name = input;
//	            	input = name + "Connected!";
//	            }
//	            out.println(input);
//	            System.out.println("Client " + name + ": "+ input.toUpperCase());
	        }
			client.close();
	    }
	    catch(IOException e)
	    {
	        e.printStackTrace();
	    }
	}
	
	private ClientHandler findUsername(String userName)
	{
		for(int i = 0; i < clientList.size()-1; i++) 
		{
			if(clientList.get(i).getName().equals(userName))
			{
				return clientList.get(i);
			}
		}
		return null;
	}
	
	public void updateClientList(ArrayList<ClientHandler> clientList)
	{
		this.clientList = clientList;
	}
	
	public String getName()
	{
		return this.name;
	}
}