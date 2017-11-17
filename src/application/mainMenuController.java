package application;

import java.io.IOException;
import java.net.UnknownHostException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class mainMenuController 
{
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
	
	public void btnClientClicked() throws UnknownHostException, IOException 
	{
		try
		{
			Parent root = FXMLLoader.load(getClass().getResource("ClientStuff.fxml"));
			Stage stage = new Stage();
			stage.setTitle("Client");
			Scene scene = new Scene(root, 400, 400);
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
	
	public TextArea getTextArea()
	{
		return txtOutput;
	}
	
	public void btnDiscon() 
	{
		//Figure out how to disconnect sockets without crashing lul;
	}
}
