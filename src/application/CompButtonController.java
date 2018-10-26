package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class CompButtonController implements Initializable{

	@FXML Label BFSLabel, DijistraLabel, aStarLabel;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		BFSLabel.setText("aef");
		
	}
	static void showComp() {
		
	}
}
