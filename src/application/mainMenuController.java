package application;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Timer;

import games.TickTackToe;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class mainMenuController 
{
	@FXML private AnchorPane ancChat;
	@FXML private TextArea txtOutput;
	@FXML private TextArea txtInput;
	
	@FXML private TextField txtIP;
	@FXML private TextField txtPort;
	@FXML private TextField txtName;
	
	@FXML private Button btnSend;
	
	@FXML private Pane paneConnect;
	@FXML private Pane paneChat;
	
	private clientSendRec c1;
	static int i = 0;
	
	Timer time = new Timer();
	private Boolean bolTurnLeft;
	@FXML
	Label myMessage;
	@FXML private ProgressBar leftFuel;
	@FXML private ProgressBar rightFuel;
	@FXML private ProgressBar leftHealth;
	@FXML private ProgressBar rightHealth;
	@FXML private Button btnStart;
	@FXML private Button btnExit;
	@FXML private Button btnBack;
	@FXML private ImageView tankLeft;
	@FXML private ImageView tankRight;
	@FXML private AnchorPane Background;
	@FXML private ImageView tankLeftCannon;
	@FXML private ImageView tankRightCannon;
	@FXML private ImageView fence;
	@FXML private ImageView bulType;
	@FXML private ImageView backGround;
	@FXML private ImageView ground;
	@FXML private Label txt;
	@FXML private Label txt1;
	@FXML private Label txt2;
	@FXML private Label txt3;
	@FXML private Label txt4;
	
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
	public void clickMe()
	{
		myMessage.setText("");
		bolTurnLeft = true;
		btnStart.setVisible(false);
		btnExit.setVisible(false);
		btnBack.setVisible(true);

		fence.setVisible(true);
		bulType.setVisible(true);
		backGround.setVisible(true);
		ground.setVisible(true);
		txt.setVisible(true);
		txt1.setVisible(true);
		txt2.setVisible(true);
		txt3.setVisible(true);
		txt4.setVisible(true);
		leftFuel.setVisible(true);
		rightFuel.setVisible(true);
		leftHealth.setVisible(true);
		rightHealth.setVisible(true);
		tankLeft.setVisible(true);
		tankRight.setVisible(true);
		tankLeftCannon.setVisible(true);
		tankRightCannon.setVisible(true);
		if(firstRun)
		{
			tkLS = tankLeft.getLayoutX();
			tkRS = tankRight.getLayoutX();
			tkLCS = tankLeftCannon.getLayoutX();
			tkRCS = tankRightCannon.getLayoutX();
			tkLCSY = tankLeftCannon.getLayoutY();
			tkRCSY = tankRightCannon.getLayoutY();
			
			mainMenuController.root.getChildren().add(boom);
			boom.setVisible(false);
			
			firstRun = false;
		}
	}
	
	public void exitClicked()
	{
		//Made the BackButton an exit button

				myMessage.setText("");
				btnBack.setVisible(false);

				btnStart.setVisible(true);
				btnExit.setVisible(true);
				
				txt.setVisible(false);
				txt1.setVisible(false);
				txt2.setVisible(false);
				txt3.setVisible(false);
				txt4.setVisible(false);
				ground.setVisible(false);
				backGround.setVisible(false);
				leftFuel.setVisible(false);
				rightFuel.setVisible(false);
				leftHealth.setVisible(false);
				rightHealth.setVisible(false);
				tankLeft.setVisible(false);
				tankRight.setVisible(false);
				tankLeftCannon.setVisible(false);
				tankRightCannon.setVisible(false);
				fence.setVisible(false);
				bulType.setVisible(false);
				
				leftHealth.setProgress(1);
				rightHealth.setProgress(1);
				leftFuel.setProgress(1);
				rightFuel.setProgress(1);
				bolTurnLeft = true;
				bulletType = 0;
				
				
				
				//Moves the tanks back to their orignal spots
				tankLeft.setLayoutX(tkLS);
				tankLeftCannon.setLayoutX(tkLCS);
				tankRight.setLayoutX(tkRS);
				tankRightCannon.setLayoutX(tkRCS);
				tankLeftCannon.setRotate(0);
				tankRightCannon.setRotate(0);
				tankLeftCannon.setLayoutY(tkLCSY);
				tankRightCannon.setLayoutY(tkRCSY);
				key = 0;
				key2 = 0;

				if(boom != null)
				{
					boom.setVisible(false);
				}
				bulType.setImage(new Image("/TankPictures/bullet.png"));
	}
	
	public void backClicked()
	{
		//Made the BackButton an exit button

		myMessage.setText("");
		btnBack.setVisible(false);

		btnStart.setVisible(true);
		btnExit.setVisible(true);
		
		txt.setVisible(false);
		txt1.setVisible(false);
		txt2.setVisible(false);
		txt3.setVisible(false);
		txt4.setVisible(false);
		ground.setVisible(false);
		backGround.setVisible(false);
		leftFuel.setVisible(false);
		rightFuel.setVisible(false);
		leftHealth.setVisible(false);
		rightHealth.setVisible(false);
		tankLeft.setVisible(false);
		tankRight.setVisible(false);
		tankLeftCannon.setVisible(false);
		tankRightCannon.setVisible(false);
		fence.setVisible(false);
		bulType.setVisible(false);
		
		leftHealth.setProgress(1);
		rightHealth.setProgress(1);
		leftFuel.setProgress(1);
		rightFuel.setProgress(1);
		bolTurnLeft = true;
		bulletType = 0;
		
		
		
		//Moves the tanks back to their orignal spots
		tankLeft.setLayoutX(tkLS);
		tankLeftCannon.setLayoutX(tkLCS);
		tankRight.setLayoutX(tkRS);
		tankRightCannon.setLayoutX(tkRCS);
		tankLeftCannon.setRotate(0);
		tankRightCannon.setRotate(0);
		tankLeftCannon.setLayoutY(tkLCSY);
		tankRightCannon.setLayoutY(tkRCSY);
		key = 0;
		key2 = 0;

		if(boom != null)
		{
			boom.setVisible(false);
		}
		bulType.setImage(new Image("/TankPictures/bullet.png"));
	}
	
	public void keyPressed()
	{		
		//Updates the left tank
		if(bolTurnLeft)
		{
			if(leftFuel.getProgress() > 0)
			{
				tankLeft.setOnKeyPressed(e -> {
					switch (e.getCode()){
					
					case A:
						if(tankLeft.getLayoutX() >= 0)
						{
							tankLeft.setLayoutX(tankLeft.getLayoutX()-10);
							tankLeftCannon.setLayoutX(tankLeftCannon.getLayoutX()-10);
							leftFuel.setProgress(leftFuel.getProgress() - .05);
						}
						break;
					case F: 
						if(bulletType == 0)
						{
							bulletType = 1;
							bulType.setImage(new Image("/TankPictures/missile.png"));
						}
						else
						{
							bulletType = 0;

							bulType.setImage(new Image("/TankPictures/bullet.png"));
						}
						break;
					case D:
						if(tankLeft.getLayoutX() <= 485)
						{
							
							tankLeft.setLayoutX(tankLeft.getLayoutX()+10);
							tankLeftCannon.setLayoutX(tankLeftCannon.getLayoutX()+10);
							leftFuel.setProgress(leftFuel.getProgress() - .05);
						}
						break;
					case Q:
						if(tankLeftCannon.getRotate()>-59)
						{
							tankLeftCannon.setRotate(tankLeftCannon.getRotate()-20);

							tankLeftCannon.setLayoutY(tankLeftCannon.getLayoutY()-5);
							tankLeftCannon.setLayoutX(tankLeftCannon.getLayoutX()-5);
							key++;
						}
						break;
					case E:
						if(tankLeftCannon.getRotate()<0)
						{
							tankLeftCannon.setRotate(tankLeftCannon.getRotate()+20);

							tankLeftCannon.setLayoutY(tankLeftCannon.getLayoutY()+5);
							tankLeftCannon.setLayoutX(tankLeftCannon.getLayoutX()+5);
							key--;
						}
						break;
					case SPACE:
						Projectile bul1;
						if(bulletType == 0)
						{
							bul1 = new Bullet(100+tankLeft.getLayoutX(), tankLeft.getLayoutY(), 1);
							bul1.movement(key,0);
						}
						else
						{
							bul1 = new specBullet(100+tankLeft.getLayoutX(),  tankLeft.getLayoutY(), 1);
							bul1.movement(key, 0);
						}
						
						bolTurnLeft = false;
						leftFuel.setProgress(0);
						rightFuel.setProgress(1);
						if(bul1.getXLast()-500 == bul1.getX())
						{
							if(tankRight.getLayoutX()<bul1.getXLast())
							{
								rightHealth.setProgress(rightHealth.getProgress()-.3);
							}
						}
						else if(tankRight.getLayoutX()<bul1.getXLast() && bul1.getXLast()<tankRight.getLayoutX()+200)
						{
							rightHealth.setProgress(rightHealth.getProgress()-.3);
						}
						if(rightHealth.getProgress() <= 0)
						{
							boom.setVisible(true);
							boom.setX(tankRight.getLayoutX());
							boom.setY(tankRight.getLayoutY()-35);
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
				});		
				tankLeft.requestFocus();
				}
			else
			{
				bolTurnLeft = false;
				leftFuel.setProgress(0);
				rightFuel.setProgress(1);
			}
		}
		else
		{
			//Updates the right tank
			if(rightFuel.getProgress() > 0)
			{
				tankRight.setOnKeyPressed(e -> {
					switch (e.getCode()){
					
					case J:
						if(tankRight.getLayoutX() > 750)
						{
							tankRight.setLayoutX(tankRight.getLayoutX()-10);

							tankRightCannon.setLayoutX(tankRightCannon.getLayoutX()-10);
							rightFuel.setProgress(rightFuel.getProgress() - .05);
						}
						break;
					case F: 
						if(bulletType == 0)
						{
							bulletType = 1;

							bulType.setImage(new Image("/TankPictures/missile.png"));
						}
						else
						{
							bulletType = 0;

							bulType.setImage(new Image("/TankPictures/bullet.png"));
						}
						break;
					case L:
						if(tankRight.getLayoutX() < 1240)
						{
							tankRight.setLayoutX(tankRight.getLayoutX() +10);

							tankRightCannon.setLayoutX(tankRightCannon.getLayoutX()+10);
							rightFuel.setProgress(rightFuel.getProgress() - .05);
						}
						break;
					case O:
						if(tankRightCannon.getRotate()<59)
						{
							tankRightCannon.setRotate(tankRightCannon.getRotate()+20);

							tankRightCannon.setLayoutY(tankRightCannon.getLayoutY()-5);
							tankRightCannon.setLayoutX(tankRightCannon.getLayoutX()+5);
							key2++;
						}
						break;
					case U:
						if(tankRightCannon.getRotate()>0)
						{
							tankRightCannon.setRotate(tankRightCannon.getRotate()-20);

							tankRightCannon.setLayoutY(tankRightCannon.getLayoutY()+5);
							tankRightCannon.setLayoutX(tankRightCannon.getLayoutX()-5);
							key2--;
						}
						break;
					case SPACE:
						Projectile bul;
						if(bulletType == 0)
						{
							bul = new Bullet(50+tankRight.getLayoutX(), tankRight.getLayoutY(), -1);
							bul.movement(key2,1);
						}
						else
						{
							bul = new specBullet(50+tankRight.getLayoutX(),  tankRight.getLayoutY(), -1);
							bul.movement(key2, 1);
						}
						
						bolTurnLeft = true;
						leftFuel.setProgress(1);
						rightFuel.setProgress(0);
						if(bul.getXLast()+500 == bul.getX())
						{
							if(tankLeft.getLayoutX()>bul.getXLast())
							{
								leftHealth.setProgress(leftHealth.getProgress()-.3);
							}
						}
						else if(tankLeft.getLayoutX()<bul.getXLast() && bul.getXLast()<tankLeft.getLayoutX()+200)
						{
							leftHealth.setProgress(leftHealth.getProgress()-.3);
						}
						if(leftHealth.getProgress() <= 0)
						{
							boom.setVisible(true);
							boom.setX(tankLeft.getLayoutX());
							boom.setY(tankLeft.getLayoutY()-35);
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
				});		
				tankRight.requestFocus();
			}
			else
			{
				bolTurnLeft = true;
				leftFuel.setProgress(1);
				rightFuel.setProgress(0);
			}
		}
	}
	
	public static Pane root;
	private static Stage stage;
	public void btnClientClicked() throws UnknownHostException, IOException 
	{
		try
		{
			root = (Pane)FXMLLoader.load(getClass().getResource("ClientStuff.fxml"));
			stage = new Stage();
			stage.setTitle("Client");
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
		} 
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void btnServerClicked()
	{
		int port = 1111;
		Server serv = new Server(port);
	}

	public void btnSendAct()
	{
		String text = txtInput.getText();
		if(!text.equals(""))
		{
			c1.sendMessage(text);
			txtInput.setText("");
		}
	}
	
	public void btnCon() throws UnknownHostException, IOException 
	{
		c1 = new clientSendRec(txtIP.getText(), Integer.parseInt(txtPort.getText()), txtName.getText(), txtOutput, txtInput, board, WhoWon, whosTurn);
		i++;
		paneConnect.setVisible(false);
		paneConnect.setDisable(true);
		paneChat.setVisible(true);
		paneChat.setDisable(false);		
	}
	
	public void setUpGame() 
	{
		stage.setWidth(1360);
		stage.setHeight(780);
		Background.setVisible(true);
		paneConnect.setDisable(true);
		paneConnect.setVisible(false);
		paneChat.setVisible(false);
		paneChat.setDisable(true);
	}
	
	
	//TicTacToe
	@FXML private GridPane board;
	@FXML private Label WhoWon;
	@FXML private Label whosTurn;
	
	private String opponent = "";
	//The winner number, -1 if undecided, 0 if you, 1 if them
	private int winner = -1;
	
	public void setUpTic()
	{
		for (Node node : board.getChildren()) 
		{
            if (node instanceof Button) 
            {
            	if(!((Button) node).getText().equals("Exit"))
            	{
            		((Button) node).setText("");
            	}
            }
		}
		paneChat.setVisible(false);
		paneChat.setDisable(true);

		board.setVisible(true);
		board.setDisable(false);
		WhoWon.setText("");
		whosTurn.setText("Your Turn");
	}
	
	public void exitTic()
	{
		for (Node node : board.getChildren()) 
		{
            if (node instanceof Button) 
            {
            	if(!((Button) node).getText().equals("Exit"))
            	{
            		((Button) node).setText("");
            	}
            }
		}
		paneChat.setVisible(true);
		paneChat.setDisable(false);
		board.setVisible(false);
		board.setDisable(true);

		WhoWon.setText("");
	}
	
	public void boardController(Event e)
	{
		
		//if the button is not an x or an o and it is your turn and noone has won
		if(!((Button)((Control) e.getSource())).getText().equals("X") 
				&& !((Button)((Control) e.getSource())).getText().equals("O") 
				&& whosTurn.getText().equals("Your Turn") 
				&& WhoWon.getText().equals(""))
		{
			((Button)((Control) e.getSource())).setText("X");
			//Send this
			//((Button)((Control) e.getSource())).getId();
			
			whosTurn.setText("Their Turn");
			c1.sendMessage("g@m3T" + ((Button)((Control) e.getSource())).getId());


			if(checkForWin())
			{

				WhoWon.setText("You Won!");
			}
		}
		
	}

	
	
	//returns true if someone won
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
				winner = 0;
				return true;
			}
			
			//if any of the rows are all o
			if(sBoard[i*3].equals("O") && sBoard[i*3+1].equals("O") && sBoard[i*3+2].equals("O"))
			{
				winner = 1;
				return true;
			}
		}
		
		//Checks all columns
		for(int i = 0; i < 3; i++)
		{
			//if any of the cols are all x
			if(sBoard[i].equals("X") && sBoard[i+3].equals("X") && sBoard[i+6].equals("X"))
			{
				winner = 0;
				return true;
			}
			
			//if any of the cols are all o
			if(sBoard[i].equals("O") && sBoard[i+3].equals("O") && sBoard[i+6].equals("O"))
			{
				winner = 1;
				return true;
			}
		}
		
		//Checks the diagnals
		if(sBoard[0].equals("X") && sBoard[4].equals("X") && sBoard[8].equals("X"))
		{
			winner = 0;
			return true;
		}
		else if(sBoard[0].equals("O") && sBoard[4].equals("O") && sBoard[8].equals("O"))
		{
			winner = 1;
			return true;
		}
		else if(sBoard[2].equals("X") && sBoard[4].equals("X") && sBoard[6].equals("X"))
		{
			winner = 0;
			return true;
		}
		else if(sBoard[2].equals("O") && sBoard[4].equals("O") && sBoard[6].equals("O"))
		{
			winner = 1;
			return true;
		}
		return false;
	}
}
