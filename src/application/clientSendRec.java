package application;

import java.io.*;
import java.net.*;
import java.util.HashMap;

import encrypt.Encryption;
import javafx.animation.PathTransition;
import javafx.application.Platform;
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
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

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
	private Label myMessage;
	private ProgressBar leftFuel;
	private ProgressBar rightFuel;
	private ProgressBar leftHealth;
	private ProgressBar rightHealth;
	private ImageView tankLeft;
	private ImageView tankRight;
	private ImageView tankLeftCannon;
	private ImageView tankRightCannon;
	private ImageView bulType;
	private ImageView bul;
	private ImageView boom= new ImageView(new Image("/TankPictures/boom.png"));;
	private int key = 0;
	private int key2 = 0;
	private int bulletType = 0;
	
	//Giant constructor to pass all the parameters we need to access in the mainController class
	public clientSendRec(String ip, int port, String user, 
			TextArea txtOut, TextArea txtIn, GridPane board, 
			Label whoWon, Label whosTurn, GraphicsContext gc, Boolean bolTurnLeft, Label myMessage, ProgressBar leftFuel,
			ProgressBar rightFuel, ProgressBar leftHealth, ProgressBar rightHealth, Button btnBack, ImageView tankLeft, ImageView tankRight, AnchorPane Background,
			ImageView tankLeftCannon, ImageView tankRightCannon, ImageView fence, ImageView bulType,
			ImageView backGround, ImageView ground, Label txt, Label txt1, Label txt2, Label txt3,
			Label txt4, double tkLS, double tkRS, double tkLCS, double tkRCS, double tkLCSY, double tkRCSY, ImageView boom,
			int key, int key2, int bulletType, ImageView bul) throws UnknownHostException, IOException 
	{		
		//Socket stuff with username
		ServerPort = port;
		serverIp = ip;
		userName = user;
		
		//tic tac toe
		this.board = board;
		this.whosTurn = whosTurn;
		this.whoWon = whoWon;
		
		//paint
		this.gc = gc;
		
		//tanks
		this.bolTurnLeft = bolTurnLeft;
		this.myMessage = myMessage;
		this.leftFuel = leftFuel;
		this.rightFuel = rightFuel;
		this.leftHealth = leftHealth;
		this.rightHealth = rightHealth;
		this.tankLeft = tankLeft;
		this.tankRight = tankRight;
		this.tankLeftCannon =tankLeftCannon;
		this.tankRightCannon = tankRightCannon;
		this.bulType = bulType;
		this.bul = bul;
		
		this.boom= boom;
		this.key = key;
		this.key2 = key2;
		this.bulletType = bulletType;
		
		// establish the connection
		socket = new Socket(serverIp, ServerPort);
		  
		// obtaining input and out streams
		in = new DataInputStream(socket.getInputStream());
		out = new DataOutputStream(socket.getOutputStream());
		
		out.writeUTF((new Encryption()).encrypt(this.userName));
		
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
							msg = (new Encryption()).decrypt(msg);
							
							//checks for flags to see if message is for games
							//If the incoming message contains gameT and does not contain this clients username, send it to tictactoe
							if(msg.contains("g@m3T") && !msg.contains(userName))
							{
								incomingMove(msg);
							}
							else if(msg.contains("g@m3P") && !msg.contains(userName))
							{
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
		
		//Start thread
		read.start();
	}
	
	//Main method for sending data to server
	public void sendMessage(String message)
	{
		try
		{
			message = (new Encryption()).encrypt(message);
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
		rad = Integer.parseInt(incoming.substring(0, incoming.indexOf("/")-1));
		
		incoming = incoming.substring(incoming.indexOf("/"));
		incoming = incoming.replaceFirst("/", "");
		rad *= 10;

    	//gc.fillOval(40, 50, 30, 60);
		Color col = Color.RED;
		
		switch (incoming)
 		{
 			case "RED": 
 				col = Color.RED;
 				break;
 			case "GREEN":
 				col = Color.GREEN;
 				break;
 			case "BLUE":	
 				col = Color.BLUE;
 				break;
 			case "YELLOW":
 				col = Color.YELLOW;
 				break;
 			case "WHITE":
 				col = Color.WHITE;
 				break;
 			case "BLACK":
 				col = Color.BLACK;
 				break;
 			case "GRAY":
 				col = Color.GRAY;
 				break;
 		}
		gc.setFill(col);
        gc.fillOval(x-rad/2 , y-rad/2, rad, rad);
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {

		    }
		});
	}
	
	//Tanks
	String incoming1 = "";
	public void updateTanks(String incoming)
	{
		incoming1 = incoming;
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {
		    	//Read message
		    	String incoming = incoming1;
				incoming = incoming.substring(incoming.indexOf("g@m3K"));		
				incoming = incoming.replaceFirst("g@m3K", "");	
				//if player 1 turn
				if (bolTurnLeft) {
					//has fuel to move
					if (leftFuel.getProgress() > .1) {
						switch(incoming)
						{
							//move tank 1 left
							case "A":
								if (tankLeft.getLayoutX() >= 0) 
								{
									tankLeft.setLayoutX(tankLeft.getLayoutX() - 10);
									tankLeftCannon.setLayoutX(tankLeftCannon.getLayoutX() - 10);
									leftFuel.setProgress(leftFuel.getProgress() - .05);
								}
								break;
							//change bullet icon bottom right for fun
							case "F":
								if (bulletType == 0) {
									bulletType = 1;
									bulType.setImage(new Image("/TankPictures/missile.png"));
								} else {
									bulletType = 0;
		
									bulType.setImage(new Image("/TankPictures/bullet.png"));
								}
								break;
							//move tank 1 right
							case "D":
								if (tankLeft.getLayoutX() <= 485) {
		
									tankLeft.setLayoutX(tankLeft.getLayoutX() + 10);
									tankLeftCannon.setLayoutX(tankLeftCannon.getLayoutX() + 10);
									leftFuel.setProgress(leftFuel.getProgress() - .05);
								}
								break;
							//change tank 1 cannon angle up
							case "W":
								if (tankLeftCannon.getRotate() > -59) {
									tankLeftCannon.setRotate(tankLeftCannon.getRotate() - 20);
									
									tankLeftCannon.setLayoutY(tankLeftCannon.getLayoutY() - 5);
									tankLeftCannon.setLayoutX(tankLeftCannon.getLayoutX() - 5);
									key++;
								}
								break;
							//change tank 1 cannnon angle down
							case "S":
								if (tankLeftCannon.getRotate() < 0) {
									tankLeftCannon.setRotate(tankLeftCannon.getRotate() + 20);
		
									tankLeftCannon.setLayoutY(tankLeftCannon.getLayoutY() + 5);
									tankLeftCannon.setLayoutX(tankLeftCannon.getLayoutX() + 5);
									key--;
								}
								break;
							//fire cannon
							case " ":
								bul.setVisible(true);
								fire(100 + tankLeft.getLayoutX(), 340, 1);
								movement(key, 0, 1);
								
		
								bolTurnLeft = false;
								leftFuel.setProgress(0);
								rightFuel.setProgress(1);
								if (setXLast - 500 == bul.getX()) {
									if (tankRight.getLayoutX() < setXLast) {
										rightHealth.setProgress(rightHealth.getProgress() - .3);
									}
								} else if (tankRight.getLayoutX() < setXLast
										&& setXLast < tankRight.getLayoutX() + 200) {
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
					//change turns
					else {
						bolTurnLeft = false;
						leftFuel.setProgress(0);
						rightFuel.setProgress(1);
					}
				}
				//second player
				else
				{
					if (rightFuel.getProgress() > .1) {
						switch(incoming)
						{
							//move tank2 left
							case "J":
								if (tankRight.getLayoutX() > 750) {
									tankRight.setLayoutX(tankRight.getLayoutX() - 10);
					
									tankRightCannon.setLayoutX(tankRightCannon.getLayoutX() - 10);
									rightFuel.setProgress(rightFuel.getProgress() - .05);
								}
								break;
							//change bullet icon bottom right
							case "F":
								if (bulletType == 0) {
									bulletType = 1;
		
									bulType.setImage(new Image("/TankPictures/missile.png"));
								} else {
									bulletType = 0;
		
									bulType.setImage(new Image("/TankPictures/bullet.png"));
								}
								break;
							//move tank2 right
							case "L":
								if (tankRight.getLayoutX() < 1240) {
									tankRight.setLayoutX(tankRight.getLayoutX() + 10);
		
									tankRightCannon.setLayoutX(tankRightCannon.getLayoutX() + 10);
									rightFuel.setProgress(rightFuel.getProgress() - .05);
								}
								break;
							//change tank2 angle up
							case "I":
								if (tankRightCannon.getRotate() < 59) {
									tankRightCannon.setRotate(tankRightCannon.getRotate() + 20);
		
									tankRightCannon.setLayoutY(tankRightCannon.getLayoutY() - 5);
									tankRightCannon.setLayoutX(tankRightCannon.getLayoutX() + 5);
									key2++;
								}
								break;
							//change tank2 angle down
							case "K":
								if (tankRightCannon.getRotate() > 0) {
									tankRightCannon.setRotate(tankRightCannon.getRotate() - 20);
		
									tankRightCannon.setLayoutY(tankRightCannon.getLayoutY() + 5);
									tankRightCannon.setLayoutX(tankRightCannon.getLayoutX() - 5);
									key2--;
								}
								break;
							//fire
							case " ":
								bul.setVisible(true);
								fire(50 + tankRight.getLayoutX(), 340, -1);
								movement(key2, 1, -1);
		
								bolTurnLeft = true;
								leftFuel.setProgress(1);
								rightFuel.setProgress(0);
								if (setXLast + 500 == bul.getX()) {
									if (tankLeft.getLayoutX() > setXLast) {
										leftHealth.setProgress(leftHealth.getProgress() - .3);
									}
								} else if (tankLeft.getLayoutX() < setXLast
										&& setXLast < tankLeft.getLayoutX() + 200) {
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
					//change turn
					else {
						bolTurnLeft = true;
						leftFuel.setProgress(1);
						rightFuel.setProgress(0);
					}
				}

		    }
		});
	}
	
	//create angle maps
	HashMap<Integer, Integer> angles1 = new HashMap<>();
	HashMap<Integer, Integer> angles2 = new HashMap<>();
	Boolean firstFire = true;
	
	//method to set up bullet
	public void fire(double x, double y, int direction)
	{		
		bul.setX(x);
		bul.setY(y);
		
		if(firstFire)
		{
			angles1.put(0, 0);
			angles1.put(1, 20);
			angles1.put(2, 40);
			angles1.put(3, 60);
			angles2.put(0, 0);
			angles2.put(1, 20);
			angles2.put(2, 40);
			angles2.put(3, 60);
			firstFire = false;
		}
	}
	
	//methhod to animate bullet and check if hit
	private double setXLast;
	public void movement(int key1, int player, int direction)
	{
		if(player == 0 &&  direction == 1)
		{
			switch (angles1.get(key1))
			{
			case 0:
				Path path = new Path();
				path.getElements().add(new MoveTo(bul.getX(),bul.getY() + 20));
				path.getElements().add(new CubicCurveTo(bul.getX() + 50, 360, bul.getX() + 400, 360, bul.getX() + 500, 400));
				PathTransition pT = new PathTransition();
				pT.setDuration(Duration.millis(1000));
				pT.setPath(path);
				pT.setNode(bul);
				pT.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
				pT.setCycleCount(1);
				setXLast = bul.getX() + 500;
				pT.play();
				break;
			case 20:
				Path path1 = new Path();
				path1.getElements().add(new MoveTo(bul.getX(),bul.getY()));
				path1.getElements().add(new CubicCurveTo(bul.getX() + 70, 300 , bul.getX() + 400, 200, bul.getX() + 750, 400));
				PathTransition pT1 = new PathTransition();
				pT1.setDuration(Duration.millis(1000));
				pT1.setPath(path1);
				pT1.setNode(bul);
				pT1.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
				pT1.setCycleCount(1);
				setXLast = bul.getX() + 750;
				pT1.play();
				break;
			case 40:
				Path path2 = new Path();
				path2.getElements().add(new MoveTo(bul.getX(),bul.getY()));
				path2.getElements().add(new CubicCurveTo(bul.getX() + 50, 160, bul.getX() + 1000, 140, bul.getX() + 1050, 400));
				PathTransition pT2 = new PathTransition();
				pT2.setDuration(Duration.millis(1000));
				pT2.setPath(path2);
				pT2.setNode(bul);
				pT2.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
				pT2.setCycleCount(1);
				setXLast = bul.getX() + 1050;
				pT2.play();
				break;
			case 60:
				Path path3 = new Path();
				path3.getElements().add(new MoveTo(bul.getX() - 20,bul.getY()));
				path3.getElements().add(new CubicCurveTo(bul.getX() + 20, 50, bul.getX() + 1050, 80, bul.getX() + 1250, 400));
				PathTransition pT3 = new PathTransition();
				pT3.setDuration(Duration.millis(1000));
				pT3.setPath(path3);
				pT3.setNode(bul);
				pT3.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
				pT3.setCycleCount(1);

				setXLast = bul.getX() + 1250;
				pT3.play();
				break;
			}
		}
		if(player == 1 && direction == -1)
		{
			switch (angles2.get(key1))
			{
			case 0:
				Path path = new Path();
				path.getElements().add(new MoveTo(bul.getX(),bul.getY() + 20));
				path.getElements().add(new CubicCurveTo(bul.getX() - 50, 360, bul.getX() - 400, 360, bul.getX() - 500, 400));
				PathTransition pT = new PathTransition();
				pT.setDuration(Duration.millis(1000));
				pT.setPath(path);
				pT.setNode(bul);
				pT.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
				pT.setCycleCount(1);

				setXLast = bul.getX() -500;
				pT.play();
				break;
			case 20:
				Path path1 = new Path();
				path1.getElements().add(new MoveTo(bul.getX(),bul.getY()));
				path1.getElements().add(new CubicCurveTo(bul.getX() - 70, 300 , bul.getX() - 400, 200, bul.getX() - 750, 400));
				PathTransition pT1 = new PathTransition();
				pT1.setDuration(Duration.millis(1000));
				pT1.setPath(path1);
				pT1.setNode(bul);
				pT1.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
				pT1.setCycleCount(1);

				setXLast = bul.getX() -750;
				pT1.play();
				break;
			case 40:
				Path path2 = new Path();
				path2.getElements().add(new MoveTo(bul.getX(),bul.getY()));
				path2.getElements().add(new CubicCurveTo(bul.getX() - 50, 160, bul.getX() - 1000, 140, bul.getX() - 1050, 400));
				PathTransition pT2 = new PathTransition();
				pT2.setDuration(Duration.millis(1000));
				pT2.setPath(path2);
				pT2.setNode(bul);
				pT2.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
				pT2.setCycleCount(1);

				setXLast = bul.getX() - 1050;
				pT2.play();
				break;
			case 60:
				Path path3 = new Path();
				path3.getElements().add(new MoveTo(bul.getX()+35,bul.getY()));
				path3.getElements().add(new CubicCurveTo(bul.getX(), 100, bul.getX()-1200, 120, bul.getX() - 1150, 400));
				PathTransition pT3 = new PathTransition();
				pT3.setDuration(Duration.millis(1000));
				pT3.setPath(path3);
				pT3.setNode(bul);
				pT3.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
				pT3.setCycleCount(1);

				setXLast = bul.getX() - 1150;
				pT3.play();
				break;
			}
		}
		
		//resets cannon barrel to resting position
		if(key== 1)
		{
			tankLeftCannon.setLayoutX(tankLeftCannon.getLayoutX() + 5);
		}
		else if(key == 2)
		{
			tankLeftCannon.setLayoutX(tankLeftCannon.getLayoutX() + 10);
		}
		else if(key == 3)
		{
			tankLeftCannon.setLayoutX(tankLeftCannon.getLayoutX() + 15);
		}
		
		if(key2 == 1)
		{
			tankRightCannon.setLayoutX(tankRightCannon.getLayoutX() - 5);
		}
		else if(key2 == 2)
		{
			tankRightCannon.setLayoutX(tankRightCannon.getLayoutX() - 10);
		}
		else if(key2 == 3)
		{
			tankRightCannon.setLayoutX(tankRightCannon.getLayoutX() - 15);
		}
		
		key = 0;
		key2 = 0;
		tankLeftCannon.setRotate(0);
		tankRightCannon.setRotate(0);
		tankLeftCannon.setLayoutY(361);
		tankRightCannon.setLayoutY(361);
	}
}