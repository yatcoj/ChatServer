package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class mainMenuController 
{
	
	@FXML TextArea txtOutput;
	@FXML TextArea txtInput;
	@FXML Button btnSend;
	Client c1;
	
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
			c1 = new Client("localhost", 1111, txtOutput, "Person");
		} 
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void btnServerClicked()
	{
		try
		{
			
			Parent root = FXMLLoader.load(getClass().getResource("Test.fxml"));
			Stage stage = new Stage();
			stage.setTitle("Server");
			Scene scene = new Scene(root, 400, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) 
		{
			e.printStackTrace();
		}
	}

	public void btnSendAct()
	{
		String text = txtInput.getText();
		txtOutput.setText("");
		if(!text.equals(""))
		{
			c1.sendMessage(text);
		}
	}
}
