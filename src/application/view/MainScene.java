package application.view;

import application.History;
import application.Log;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ListView;

public class MainScene {
	@FXML
	private Canvas cv_tours;
	@FXML
	private ListView<String> lv_console;
	
	@FXML
	private void initialize() {
		Log.getInstance().setOutputControl(lv_console);

		
		
	}
	
	@FXML
	private void onAction_btnPlay() {
		
	}
	
	@FXML
	private void onAction_btnReset() {
		
	}
	
	@FXML
	private void onAction_btnExport() {
		History.getInstance().export();
		History.getInstance().showExport();
	}
}
