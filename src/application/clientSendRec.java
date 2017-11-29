package application;

import java.io.*;
import java.net.*;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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
	
	//TANKS
	private Boolean bolTurnLeft;
	Label myMessage;
	private ProgressBar leftFuel;
	private ProgressBar rightFuel;
	private ProgressBar leftHealth;
	private ProgressBar rightHealth;
	private Button btnStart;
	private Button btnExit;
	private Button btnBack;
	private ImageView tankLeft;
	private ImageView tankRight;
	private AnchorPane Background;
	private ImageView tankLeftCannon;
	private ImageView tankRightCannon;
	private ImageView fence;
	private ImageView bulType;
	private ImageView backGround;
	private ImageView ground;
	private Label txt;
	private Label txt1;
	private Label txt2;
	private Label txt3;
	private Label txt4;
	
	private ImageView boom= new ImageView(new Image("/TankPictures/boom.png"));;
	private int key = 0;
	private int key2 = 0;
	private int bulletType = 0;
	
	private boolean firstRun = true;
	//tankLeft x location at start
	private double tkLS;
	//same for right
	private double tkRS;
	//left tanks cannon x start
	private double tkLCS;
	//same for right
	private double tkRCS;
	//left tanks cannon y start
	private double tkLCSY;
	//same for right
	private double tkRCSY;
	public clientSendRec(String ip, int port, String user, 
			TextArea txtOut, TextArea txtIn, GridPane board, 
			Label whoWon, Label whosTurn, GraphicsContext gc, Boolean bolTurnLeft, Label myMessage, ProgressBar leftFuel,
			ProgressBar rightFuel, ProgressBar leftHealth, ProgressBar rightHealth, Button btnStart,
			Button btnExit, Button btnBack, ImageView tankLeft, ImageView tankRight, AnchorPane Background,
			ImageView tankLeftCannon, ImageView tankRightCannon, ImageView fence, ImageView bulType,
			ImageView backGround, ImageView ground, Label txt, Label txt1, Label txt2, Label txt3,
			Label txt4, double tkLS, double tkRS, double tkLCS, double tkRCS, double tkLCSY, double tkRCSY, ImageView boom,
			int key, int key2, int bulletType) throws UnknownHostException, IOException 
	{		
		ServerPort = port;
		serverIp = ip;
		userName = user;
		
		this.board = board;
		this.whosTurn = whosTurn;
		this.whoWon = whoWon;
		
		this.gc = gc;
		
		this.bolTurnLeft = bolTurnLeft;
		this.myMessage = myMessage;
		this.leftFuel = leftFuel;
		this.rightFuel = rightFuel;
		this.leftHealth = leftHealth;
		this.rightHealth = rightHealth;
		this.btnStart = btnStart;
		this.btnExit = btnExit;
		this.btnBack = btnBack;
		this.tankLeft = tankLeft;
		this.tankRight = tankRight;
		this.Background = Background;
		this.tankLeftCannon =tankLeftCannon;
		this.tankRightCannon = tankRightCannon;
		this.fence = fence;
		this.bulType = bulType;
		this.backGround = backGround;
		this.ground = ground;
		this.txt = txt;
		this.txt1 = txt1;
		this.txt2 = txt2;
		this.txt3 = txt3;
		this.txt4 = txt4;
		
		this.boom= boom;
		this.key = key;
		this.key2 = key2;
		this.bulletType = bulletType;
		
		//tankLeft x location at start
		this.tkLS = tkLS;
		//same for right
		this.tkRS = tkRS;
		//left tanks cannon x start
		this.tkLCS = tkLCS;
		//same for right
		this.tkRCS = tkRCS;
		//left tanks cannon y start
		this.tkLCSY = tkLCSY;
		//same for right
		this.tkRCSY = tkRCSY;
		
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
							else if(msg.contains("g@m3K"))
							{
								updateTanks(msg);
							}
							else if(!msg.contains("g@m3T") && !msg.contains("g@m3P"))
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
	
	//Tic tac toe
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
	
	//Tanks
	public void updateTanks(String incoming)
	{
		incoming = incoming.substring(incoming.indexOf("g@m3K"));		
		incoming = incoming.replaceFirst("g@m3K", "");		
		if (bolTurnLeft) {
			if (leftFuel.getProgress() > .1) {
				switch(incoming)
				{
				//transfer all things over...make update on both ends...
					case "A":
						if (tankLeft.getLayoutX() >= 0) 
						{
							tankLeft.setLayoutX(tankLeft.getLayoutX() - 10);
							tankLeftCannon.setLayoutX(tankLeftCannon.getLayoutX() - 10);
							leftFuel.setProgress(leftFuel.getProgress() - .05);
						}
						break;
					case "F":
						if (bulletType == 0) {
							bulletType = 1;
							bulType.setImage(new Image("/TankPictures/missile.png"));
						} else {
							bulletType = 0;

							bulType.setImage(new Image("/TankPictures/bullet.png"));
						}
						break;
					case "D":
						if (tankLeft.getLayoutX() <= 485) {

							tankLeft.setLayoutX(tankLeft.getLayoutX() + 10);
							tankLeftCannon.setLayoutX(tankLeftCannon.getLayoutX() + 10);
							leftFuel.setProgress(leftFuel.getProgress() - .05);
						}
						break;
					case "W":
						if (tankLeftCannon.getRotate() > -59) {
							tankLeftCannon.setRotate(tankLeftCannon.getRotate() - 20);

							tankLeftCannon.setLayoutY(tankLeftCannon.getLayoutY() - 5);
							tankLeftCannon.setLayoutX(tankLeftCannon.getLayoutX() - 5);
							key++;
						}
						break;
					case "S":
						if (tankLeftCannon.getRotate() < 0) {
							tankLeftCannon.setRotate(tankLeftCannon.getRotate() + 20);

							tankLeftCannon.setLayoutY(tankLeftCannon.getLayoutY() + 5);
							tankLeftCannon.setLayoutX(tankLeftCannon.getLayoutX() + 5);
							key--;
						}
						break;
					case " ":
						Projectile bul1;
						if (bulletType == 0) {
							bul1 = new Bullet(100 + tankLeft.getLayoutX(), tankLeft.getLayoutY(), 1);
							bul1.movement(key, 0);
						} else {
							bul1 = new specBullet(100 + tankLeft.getLayoutX(), tankLeft.getLayoutY(), 1);
							bul1.movement(key, 0);
						}

						bolTurnLeft = false;
						leftFuel.setProgress(0);
						rightFuel.setProgress(1);
						if (bul1.getXLast() - 500 == bul1.getX()) {
							if (tankRight.getLayoutX() < bul1.getXLast()) {
								rightHealth.setProgress(rightHealth.getProgress() - .3);
							}
						} else if (tankRight.getLayoutX() < bul1.getXLast()
								&& bul1.getXLast() < tankRight.getLayoutX() + 200) {
							rightHealth.setProgress(rightHealth.getProgress() - .3);
						}
						if (rightHealth.getProgress() <= 0) {
							boom.setVisible(true);
							boom.setX(tankRight.getLayoutX());
							boom.setY(tankRight.getLayoutY() - 35);
							boom.toFront();

							tankRight.setVisible(false);
							tankRightCannon.setVisible(false);
							rightFuel.setProgress(0);
							rightHealth.setProgress(0);

							myMessage.setText("Player 1 Wins!");
						}
						break;
					default:
						break;
				}
			}
			else {
				bolTurnLeft = false;
				leftFuel.setProgress(0);
				rightFuel.setProgress(1);
			}
		}
		else
		{
			if (rightFuel.getProgress() > .1) {
				switch(incoming)
				{
					case "J":
						if (tankRight.getLayoutX() > 750) {
							tankRight.setLayoutX(tankRight.getLayoutX() - 10);
			
							tankRightCannon.setLayoutX(tankRightCannon.getLayoutX() - 10);
							rightFuel.setProgress(rightFuel.getProgress() - .05);
						}
						break;
					case "F":
						if (bulletType == 0) {
							bulletType = 1;

							bulType.setImage(new Image("/TankPictures/missile.png"));
						} else {
							bulletType = 0;

							bulType.setImage(new Image("/TankPictures/bullet.png"));
						}
						break;
					case "L":
						if (tankRight.getLayoutX() < 1240) {
							tankRight.setLayoutX(tankRight.getLayoutX() + 10);

							tankRightCannon.setLayoutX(tankRightCannon.getLayoutX() + 10);
							rightFuel.setProgress(rightFuel.getProgress() - .05);
						}
						break;
					case "I":
						if (tankRightCannon.getRotate() < 59) {
							tankRightCannon.setRotate(tankRightCannon.getRotate() + 20);

							tankRightCannon.setLayoutY(tankRightCannon.getLayoutY() - 5);
							tankRightCannon.setLayoutX(tankRightCannon.getLayoutX() + 5);
							key2++;
						}
						break;
					case "K":
						if (tankRightCannon.getRotate() > 0) {
							tankRightCannon.setRotate(tankRightCannon.getRotate() - 20);

							tankRightCannon.setLayoutY(tankRightCannon.getLayoutY() + 5);
							tankRightCannon.setLayoutX(tankRightCannon.getLayoutX() - 5);
							key2--;
						}
						break;
					case " ":
						Projectile bul;
						if (bulletType == 0) {
							bul = new Bullet(50 + tankRight.getLayoutX(), tankRight.getLayoutY(), -1);
							bul.movement(key2, 1);
						} else {
							bul = new specBullet(50 + tankRight.getLayoutX(), tankRight.getLayoutY(), -1);
							bul.movement(key2, 1);
						}

						bolTurnLeft = true;
						leftFuel.setProgress(1);
						rightFuel.setProgress(0);
						if (bul.getXLast() + 500 == bul.getX()) {
							if (tankLeft.getLayoutX() > bul.getXLast()) {
								leftHealth.setProgress(leftHealth.getProgress() - .3);
							}
						} else if (tankLeft.getLayoutX() < bul.getXLast()
								&& bul.getXLast() < tankLeft.getLayoutX() + 200) {
							leftHealth.setProgress(leftHealth.getProgress() - .3);
						}
						if (leftHealth.getProgress() <= 0) {
							boom.setVisible(true);
							boom.setX(tankLeft.getLayoutX());
							boom.setY(tankLeft.getLayoutY() - 35);
							boom.toFront();

							tankLeft.setVisible(false);
							tankLeftCannon.setVisible(false);
							leftFuel.setProgress(0);
							leftHealth.setProgress(0);
							myMessage.setText("Player 2 Wins!");
						}
						break;
					default:
						break;
				}
			} 
			else {
				bolTurnLeft = true;
				leftFuel.setProgress(1);
				rightFuel.setProgress(0);
			}
		}
	}
}