package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class clientControl
{
	@FXML TextArea txtOutput;
	@FXML TextArea txtInput;
	@FXML Button btnSend;
	
	public void btnSendAct()
	{
		StringBuilder temp = new StringBuilder();
		temp.append(txtOutput.getText());
		temp.append("\n" + txtInput.getText());
		txtOutput.setText(temp.toString());
	}
}
