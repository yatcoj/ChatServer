package application;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Random;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class mainMenuController {
	// Chat
	@FXML
	private AnchorPane ancChat;
	@FXML
	private TextArea txtOutput;
	@FXML
	private TextArea txtInput;

	@FXML
	private TextField txtIP;
	@FXML
	private TextField txtPort;
	@FXML
	private TextField txtName;

	@FXML
	private Button btnSend;

	@FXML
	private Pane paneConnect;
	@FXML
	private Pane paneChat;

	private clientSendRec c1;
	static int i = 0;

	// Tanks objects
	private Boolean bolTurnLeft = true;
	@FXML
	private Label myMessage;
	@FXML
	private ProgressBar leftFuel;
	@FXML
	private ProgressBar rightFuel;
	@FXML
	private ProgressBar leftHealth;
	@FXML
	private ProgressBar rightHealth;
	@FXML
	private Button btnBack;
	@FXML
	private ImageView tankLeft;
	@FXML
	private ImageView tankRight;
	@FXML
	private AnchorPane Background;
	@FXML
	private ImageView tankLeftCannon;
	@FXML
	private ImageView tankRightCannon;
	@FXML
	private ImageView fence;
	@FXML
	private ImageView bulType;
	@FXML
	private ImageView backGround;
	@FXML
	private ImageView ground;
	@FXML
	private ImageView bul;
	@FXML
	private Label txt;
	@FXML
	private Label txt1;
	@FXML
	private Label txt2;
	@FXML
	private Label txt3;
	@FXML
	private Label txt4;

	private ImageView boom = new ImageView(new Image("/TankPictures/boom.png"));;
	private int key = 0;
	private int key2 = 0;
	private int bulletType = 0;

	private boolean firstRun = true;
	// tankLeft x location at start
	private double tkLS;
	// same for right
	private double tkRS;
	// left tanks cannon x start
	private double tkLCS;
	// same for right
	private double tkRCS;
	// left tanks cannon y start
	private double tkLCSY;
	// same for right
	private double tkRCSY;

	// Paint

	Canvas canvas = new Canvas(1360, 800);
	GraphicsContext gc = canvas.getGraphicsContext2D();

	//set up tank game initial
	public void clickMe() {
		myMessage.setText("");
		bolTurnLeft = true;

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
		if (firstRun) {
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

	//reset tank button
	public void backClicked() {
		// Made the BackButton an exit button

		myMessage.setText("");
		btnBack.setVisible(false);


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

		// Moves the tanks back to their orignal spots
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

		if (boom != null) {
			boom.setVisible(false);
		}
		bulType.setImage(new Image("/TankPictures/bullet.png"));

		stage.setWidth(400);
		stage.setHeight(400);
		Background.setVisible(false);
		paneChat.setVisible(true);
		paneChat.setDisable(false);
	}

	//sends messages to server for tnak game
	public void keyPressed() {
		// Updates the left tank
		if (bolTurnLeft) {
			if (leftFuel.getProgress() > .1) {
				tankLeft.setOnKeyPressed(e -> {
					switch (e.getCode()) {

					case A:
						c1.sendMessage("g@m3K" + "A");
						break;
					case F:
						c1.sendMessage("g@m3K" + "F");
						break;
					case D:
						c1.sendMessage("g@m3K" + "D");
						break;
					case W:
						c1.sendMessage("g@m3K" + "W");
						break;
					case S:
						c1.sendMessage("g@m3K" + "S");
						break;
					case SPACE:
						c1.sendMessage("g@m3K" + " ");
						break;
					default:
						break;
					}
				});
				tankLeft.requestFocus();
			} else {
				bolTurnLeft = false;
				leftFuel.setProgress(0);
				rightFuel.setProgress(1);
			}
		} else {
			// Updates the right tank
			if (rightFuel.getProgress() > .1) {
				tankRight.setOnKeyPressed(e -> {
					switch (e.getCode()) {

					case J:
						c1.sendMessage("g@m3K" + "J");
						break;
					case F:
						c1.sendMessage("g@m3K" + "F");
						break;
					case L:
						c1.sendMessage("g@m3K" + "L");
						break;
					case I:
						c1.sendMessage("g@m3K" + "I");
						break;
					case K:
						c1.sendMessage("g@m3K" + "K");
						break;
					case SPACE:
						c1.sendMessage("g@m3K" + " ");
						break;
					default:
						break;
					}
				});
				tankRight.requestFocus();
			} else {
				bolTurnLeft = true;
				leftFuel.setProgress(1);
				rightFuel.setProgress(0);
			}
		}
	}

	public static Pane root;
	private static Stage stage;

	//connect to server
	public void btnClientClicked() throws UnknownHostException, IOException {
		try {
			root = (Pane) FXMLLoader.load(getClass().getResource("ClientStuff.fxml"));
			stage = new Stage();
			stage.setTitle("Client");
			Scene scene = new Scene(root, 400, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//create server
	public void btnServerClicked() {
		int port = 1111;
		Server serv = new Server(port);
	}

	public void btnSendAct() {
		String text = txtInput.getText();
		if (!text.equals("")) {
			c1.sendMessage(text);
			txtInput.setText("");
		}
	}

	public void btnCon() throws UnknownHostException, IOException {
		c1 = new clientSendRec(txtIP.getText(), Integer.parseInt(txtPort.getText()), txtName.getText(), txtOutput,
				txtInput, board, WhoWon, whosTurn, gc, bolTurnLeft, myMessage, leftFuel, rightFuel, leftHealth,
				rightHealth, btnBack, tankLeft, tankRight, Background, tankLeftCannon,
				tankRightCannon, fence, bulType, backGround, ground, txt, txt1, txt2, txt3, txt4, tkLS, tkRS, tkLCS,
				tkRCS, tkLCSY, tkRCSY, boom, key, key2, bulletType, bul);
		i++;
		paneConnect.setVisible(false);
		paneConnect.setDisable(true);
		paneChat.setVisible(true);
		paneChat.setDisable(false);
	}

	//MsPaintSpoof
 	
 	@FXML private BorderPane msPS;
 	int rad = 30;
 	int pColor = 0;
 	boolean firstRunPaint = true;
 	public void setUpPaint()
 	{ 
 		if(firstRunPaint)
 		{
 			pColor = (new Random()).nextInt(7);
 			firstRunPaint = false;
 			msPS.getChildren().add(canvas);
 		}
        
        msPS.setVisible(true);
        msPS.setDisable(false);
         
        stage.setWidth(1360);
 		stage.setHeight(800);
 	
		paneConnect.setDisable(true);
		paneConnect.setVisible(false);
		paneChat.setVisible(false);
		paneChat.setDisable(true);
	}
 	
 	public void exitPaint()
 	{
 		msPS.setVisible(false);
 		msPS.setDisable(true);
 		
 		paneChat.setVisible(true);
 		paneChat.setDisable(false);
 		stage.setWidth(400);
 		stage.setHeight(400);
 	}
 	
 	
 	public void changeRad(ScrollEvent e)
 	{
 		if(rad < 10)
 		{
 			rad = 10;
 		}
 		if(e.getDeltaY() < 0)
 		{
 			if(rad < 100)
 			{
 				rad+=5;
 			}
 		}
 		else
 		{
 			if(rad -5 > 10 )
 			{
 				rad -= 5;
 			}
 		}
 	}
 	
 	public void clickedPaints(MouseEvent e)
 	{
 		String spColor = "RED";
 		Color col = Color.RED;
 		switch (pColor)
 		{
 			case 0: 
 				spColor = "RED";
 				col = Color.RED;
 				break;
 			case 1:
 				spColor = "GREEN";
 				col = Color.GREEN;
 				break;
 			case 2:
 				spColor = "BLUE";
 				col = Color.BLUE;
 				break;
 			case 3:
 				spColor = "YELLOW";
 				col = Color.YELLOW;
 				break;
 			case 4:
 				spColor = "WHITE";
 				col = Color.WHITE;
 				break;
 			case 5:
 				spColor = "BLACK";
 				col = Color.BLACK;
 				break;
 			case 6:
 				spColor = "GRAY";
 				col = Color.GRAY;
 				break;
 		}
 		c1.sendMessage("g@m3P" + e.getX() +"/" + e.getY() +"/" + rad +"/"+ spColor);
 		gc.setFill(col);
        gc.fillOval(e.getX()-rad/2 , e.getY()-rad/2, rad, rad);
 	}
 	
	//tanks
	public void setUpGameTank() 
	{
		stage.setWidth(1360);
		stage.setHeight(800);
		Background.setVisible(true);
		paneConnect.setDisable(true);
		paneConnect.setVisible(false);
		paneChat.setVisible(false);
		paneChat.setDisable(true);
		myMessage.setText("");
		bolTurnLeft = true;
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
		if (firstRun) {
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

	// TicTacToe
	@FXML
	private GridPane board;
	@FXML
	private Label WhoWon;
	@FXML
	private Label whosTurn;

	private String opponent = "";
	// The winner number, -1 if undecided, 0 if you, 1 if them
	private int winner = -1;

	public void setUpTic() {
		for (Node node : board.getChildren()) {
			if (node instanceof Button) {
				if (!((Button) node).getText().equals("Exit")) {
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

	public void exitTic() {
		for (Node node : board.getChildren()) {
			if (node instanceof Button) {
				if (!((Button) node).getText().equals("Exit")) {
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

	public void boardController(Event e) {

		// if the button is not an x or an o and it is your turn and noone has won
		if (!((Button) ((Control) e.getSource())).getText().equals("X")
				&& !((Button) ((Control) e.getSource())).getText().equals("O") && whosTurn.getText().equals("Your Turn")
				&& WhoWon.getText().equals("")) {
			((Button) ((Control) e.getSource())).setText("X");
			// Send this
			// ((Button)((Control) e.getSource())).getId();

			whosTurn.setText("Their Turn");
			c1.sendMessage("g@m3T" + ((Button) ((Control) e.getSource())).getId());

			if (checkForWin()) {

				WhoWon.setText("You Won!");
			}
		}

	}

	// returns true if someone won
	private boolean checkForWin() {
		String[] sBoard = new String[9];
		int x = 0;
		// Get all the values and stick them into the sBoard array for processing
		for (Node node : board.getChildren()) {
			if (node instanceof Button && x < 9) {
				sBoard[x] = ((Button) node).getText();
				x++;
			}
		}

		// Check all rows
		for (int i = 0; i < 3; i++) {
			// if any of the rows are all x
			if (sBoard[i * 3].equals("X") && sBoard[i * 3 + 1].equals("X") && sBoard[i * 3 + 2].equals("X")) {
				winner = 0;
				return true;
			}

			// if any of the rows are all o
			if (sBoard[i * 3].equals("O") && sBoard[i * 3 + 1].equals("O") && sBoard[i * 3 + 2].equals("O")) {
				winner = 1;
				return true;
			}
		}

		// Checks all columns
		for (int i = 0; i < 3; i++) {
			// if any of the cols are all x
			if (sBoard[i].equals("X") && sBoard[i + 3].equals("X") && sBoard[i + 6].equals("X")) {
				winner = 0;
				return true;
			}

			// if any of the cols are all o
			if (sBoard[i].equals("O") && sBoard[i + 3].equals("O") && sBoard[i + 6].equals("O")) {
				winner = 1;
				return true;
			}
		}

		// Checks the diagnals
		if (sBoard[0].equals("X") && sBoard[4].equals("X") && sBoard[8].equals("X")) {
			winner = 0;
			return true;
		} else if (sBoard[0].equals("O") && sBoard[4].equals("O") && sBoard[8].equals("O")) {
			winner = 1;
			return true;
		} else if (sBoard[2].equals("X") && sBoard[4].equals("X") && sBoard[6].equals("X")) {
			winner = 0;
			return true;
		} else if (sBoard[2].equals("O") && sBoard[4].equals("O") && sBoard[6].equals("O")) {
			winner = 1;
			return true;
		}
		return false;
	}
}
