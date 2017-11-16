package application;

import java.io.IOException;
import java.net.UnknownHostException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class mainMenuController 
{
	@FXML private TextArea txtOutput;
	@FXML private TextArea txtInput;
	@FXML private Button btnSend;
	
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
		c1 = new clientSendRec("localhost", 1111, txtOutput, "Person" + i, txtInput);
		i++;
	}
	
	public TextArea getTextArea()
	{
		return txtOutput;
	}
}
