package application;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Timer;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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
		Platform.exit();
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
		c1 = new clientSendRec(txtIP.getText(), Integer.parseInt(txtPort.getText()), txtName.getText(), txtOutput, txtInput);
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
}
