package application;

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
	
	public void btnClientClicked()
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
			c1 = new clientSendRec("localhost", 1111, txtOutput, "Person", txtInput);
			
			System.out.println(c1.userName+ " ekhewvfk");
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
		System.out.println(txtInput.getText());
		System.out.println(c1.userName+ " ekhewvfk");
		String text = txtInput.getText();
		System.out.println(text);
		//txtOutput.setText("");
		if(!text.equals(""))
		{
			c1.sendMessage(text);
		}
	}
	
	public TextArea getTextArea()
	{
		return txtOutput;
	}
}
