package games;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/*
 * TO DO
 * 1) Send Message to players when someone has won
 * 2) Actually send the id value to the other computer over the network
 * 
 * */

public class TickTackToe
{
	@FXML private GridPane board;
	
	private String opponent = "Dave";
	private String XorO = "X";
	//The winner number, -1 if undecided, 0 if you, 1 if them
	private int winner = -1;
	
//	public static void main(String[] args)
//	{
//		launch();
//	}

	public TickTackToe(String opponent, Stage stage) 
	{
		this.opponent = opponent;
		try {
			start(stage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void start(Stage primaryStage) throws IOException
	{
		Parent root = FXMLLoader.load(getClass().getResource("TicTacToe.fxml"));
		Scene scene = new Scene(root, 300, 300);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setTitle("Tic Tac Toe with " + opponent);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	public void boardController(Event e)
	{
		if(!((Button)((Control) e.getSource())).getText().equals("X") && !((Button)((Control) e.getSource())).getText().equals("O"))
		{
			((Button)((Control) e.getSource())).setText("X");
		}
		
		//remove this next line before release
		incomingMove(((Button)((Control) e.getSource())).getId());
		
	}

	public void incomingMove(String incoming)
	{
		for (Node node : board.getChildren()) 
		{
            if (node instanceof Button) 
            {
            	if(node.getId().equals(incoming))
            	{
            		((Button) node).setText("O");
            	}
            }
		}
		System.out.println(checkForWin() + " " + winner);
	}
	
	//returns true if someone won
	private boolean checkForWin()
	{
		String[] sBoard = new String[9];
		int x = 0;
		//Get all the values and stick them into the sBoard array for processing
		for (Node node : board.getChildren()) 
		{
            if (node instanceof Button) 
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
