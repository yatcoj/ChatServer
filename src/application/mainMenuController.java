package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class mainMenuController 
{
	@FXML TextArea txtOutput;
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
}
