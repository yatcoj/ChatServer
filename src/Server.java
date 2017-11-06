import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Server 
{
	int port;
	ServerSocket socket;
	int clientCount = 0;
	int maxClients = 100;
	ArrayList<ClientHandler> clientList = new ArrayList<ClientHandler>();
	public Server(int port)
	{
		this.port = port;
		try {
			socket = new ServerSocket(port);
			Socket clients = null;
			System.out.println("Waiting for clients to Connect!)");
			while(true)
			{
				clients = socket.accept();
				System.out.println("A client has Connected!");
				clientCount++;
				ClientHandler ch = new ClientHandler(clients, clientCount);
				new Thread(ch).start();
				clientList.add(clientCount, ch);
				ch.updateClientList(clientList);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class ClientHandler implements Runnable 
{
	Socket client;
	String name;
	int clientID;
	ArrayList<ClientHandler> clientList;
	
	public ClientHandler(Socket clients, int clientCount) {
		this.client = clients;
		this.clientID = clientCount;
	}

	public void run() 
	{
		Boolean flag = true;
		Boolean firstRun = true;
		try
		{    	
			while(flag)
	        {
				PrintWriter out = new PrintWriter(client.getOutputStream(), true);
	            DataInputStream is = new DataInputStream(client.getInputStream());
	            String input = is.readUTF();
	            if(firstRun)
	            {
	            	firstRun = false;
	            	name = input;
	            	input = name + "Connected!";
	            }
	            out.println(input);
	            System.out.println("Client " + name + ": "+ input.toUpperCase());
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