package application;

import java.io.*;
import java.net.*;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class clientSendRec
{
	static int ServerPort;
	private final DataOutputStream out;
	private final DataInputStream in;
	private String serverIp;
	int port;
	private String userName;
	private Socket socket;
	
	//TicTacToe
	private GridPane board;
	private Label whoWon;
	private Label whosTurn;
	
	//Paint
	private GraphicsContext gc;
	
	public clientSendRec(String ip, int port, String user, TextArea txtOut, TextArea txtIn, GridPane board, Label whoWon, Label whosTurn, GraphicsContext gc) throws UnknownHostException, IOException 
	{		
		ServerPort = port;
		serverIp = ip;
		userName = user;
		
		this.board = board;
		this.whosTurn = whosTurn;
		this.whoWon = whoWon;
		
		this.gc = gc;
		
		// establish the connection
		socket = new Socket(serverIp, ServerPort);
		  
		// obtaining input and out streams
		in = new DataInputStream(socket.getInputStream());
		out = new DataOutputStream(socket.getOutputStream());
		
		out.writeUTF(this.userName);
		
		// readMessage thread
		Thread read = new Thread(new Runnable() 
		{
			boolean run = true;
			@Override
			public void run() 
			{
				try 
				{
					while (run)
					{
						// read the message sent to this client
						try
						{
							String msg = in.readUTF();
							
							//If the incoming message contains gameT and does not contain this clients username, send it to tictactoe
							if(msg.contains("g@m3T") && !msg.contains(userName))
							{
								incomingMove(msg);
							}
							else if(msg.contains("g@m3P") && !msg.contains(userName))
							{

						       // c1.sendMessage("g@m3P" + e.getX() +"/" + e.getY() +"/" + rad +"/"+ "RED");
								paintMe(msg);
								
							}
							else if(!msg.contains("g@m3T"))
							{

								txtOut.appendText(msg+"\n");
							}
						}
						catch(SocketException e)
						{
							run = false;
							System.out.println("Closing...");
						}
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
			if(message == "D!sc0nn3ct*")
			{
				in.close();
				out.close();
				socket.close();
			}
		}
		catch(IOException e)
		{
		}
	}
	//TicTacToe
	public void incomingMove(String incoming)
	{
		incoming = incoming.substring(incoming.length()-3);
		for (Node node : board.getChildren()) 
		{
            if (node.getId() != null && node instanceof Button) 
            {
            	if(node.getId().equals(incoming))
            	{
            		Platform.runLater(new Runnable() {
            		    @Override
            		    public void run() {
            		    	((Button) node).setText("O");
            		    	whosTurn.setText("Your Turn");

            				if(checkForWin())
            				{

            					whoWon.setText("You Lost!");
            				}
            		    }
            		});
            	}
            }
		}
		
		
	}
	
	private boolean checkForWin()
	{
		String[] sBoard = new String[9];
		int x = 0;
		//Get all the values and stick them into the sBoard array for processing
		for (Node node : board.getChildren()) 
		{
            if (node instanceof Button && x<9) 
            {
            	sBoard[x] = ((Button) node).getText();
            	x++;
            }
		}
		
		//Check all rows
		for(int i = 0; i < 3; i++)
		{
			//if any of the rows are all x
			if(sBoard[i*3].equals("X") && sBoard[i*3+1].equals("X") && sBoard[i*3+2].equals("X"))
			{
				return true;
			}
			
			//if any of the rows are all o
			if(sBoard[i*3].equals("O") && sBoard[i*3+1].equals("O") && sBoard[i*3+2].equals("O"))
			{
				return true;
			}
		}
		
		//Checks all columns
		for(int i = 0; i < 3; i++)
		{
			//if any of the cols are all x
			if(sBoard[i].equals("X") && sBoard[i+3].equals("X") && sBoard[i+6].equals("X"))
			{
				return true;
			}
			
			//if any of the cols are all o
			if(sBoard[i].equals("O") && sBoard[i+3].equals("O") && sBoard[i+6].equals("O"))
			{
				return true;
			}
		}
		
		//Checks the diagnals
		if(sBoard[0].equals("X") && sBoard[4].equals("X") && sBoard[8].equals("X"))
		{
			return true;
		}
		else if(sBoard[0].equals("O") && sBoard[4].equals("O") && sBoard[8].equals("O"))
		{
			return true;
		}
		else if(sBoard[2].equals("X") && sBoard[4].equals("X") && sBoard[6].equals("X"))
		{
			return true;
		}
		else if(sBoard[2].equals("O") && sBoard[4].equals("O") && sBoard[6].equals("O"))
		{
			return true;
		}
		return false;
	}

	//Paint
	public void paintMe(String incoming)
	{
		 //c1.sendMessage("g@m3P" + e.getX() +"/" + e.getY() +"/" + rad +"/"+ "RED");
		double x, y;
		int rad = 30;

		incoming = incoming.substring(incoming.indexOf("g@m3P"));		
		incoming = incoming.replaceFirst("g@m3P", "");
		x = Double.parseDouble(incoming.substring(0, incoming.indexOf("/")-1));
		
		incoming = incoming.substring(incoming.indexOf("/"));
		incoming = incoming.replaceFirst("/", "");
		y = Double.parseDouble(incoming.substring(0, incoming.indexOf("/")-1));
		
		incoming = incoming.substring(incoming.indexOf("/"));
		incoming = incoming.replaceFirst("/", "");
		System.out.println(incoming);
		rad = Integer.parseInt(incoming.substring(0, incoming.indexOf("/")-1));
		
		incoming = incoming.substring(incoming.indexOf("/"));
		incoming = incoming.replaceFirst("/", "");
		String color = incoming;
		
		rad *= 10;

    	//gc.fillOval(40, 50, 30, 60);
		gc.setFill(Color.RED);
        gc.fillOval(x-rad/2 , y-rad/2, rad, rad);
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {

		    }
		});
	}
}