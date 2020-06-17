package application.view;

import application.History;
import application.Log;
import application.Main;
import application.model.Strategy;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class MainScene {
	@FXML
	private TextField tf_numOfCities;
	@FXML
	private ComboBox<Strategy> cb_strategy;
	@FXML
	private Canvas cv_tours;
	@FXML
	private ListView<String> lv_console;
	
	@FXML
	private void initialize() {
		Log.getInstance().setOutputControl(lv_console);

		tf_numOfCities.setText(String.valueOf(Main.DefaultNumOfCities));
		
		GraphicsContext graphicsContext = cv_tours.getGraphicsContext2D();
		graphicsContext.clearRect(0, 0, cv_tours.getWidth(), cv_tours.getHeight());
		
		
		graphicsContext.setFill(Color.BLACK);
		graphicsContext.setStroke(Color.FIREBRICK);
		graphicsContext.setLineWidth(1.0);

		// x1,y1,x2,y2

		graphicsContext.beginPath();
		graphicsContext.moveTo(110, 30);
		graphicsContext.lineTo(170, 20);
		graphicsContext.moveTo(170, 20);
		graphicsContext.lineTo(200, 80);
		graphicsContext.closePath();
		graphicsContext.stroke();
	}
	
	@FXML
	private void onAction_setOptions() {
		
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
