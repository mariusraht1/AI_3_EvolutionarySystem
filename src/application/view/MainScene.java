package application.view;

import java.util.List;

import application.History;
import application.Log;
import application.Main;
import application.Utilities;
import application.model.City;
import application.model.Population;
import application.model.Strategy;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class MainScene {
	@FXML
	private TextField tf_numOfCities;
	@FXML
	private ComboBox<Strategy> cb_strategy;
	@FXML
	private TextField tf_numOfSteps;
	@FXML
	private Label lbl_minTotalDistance;
	@FXML
	private Label lbl_maxTotalDistance;
	@FXML
	private Label lbl_round;
	@FXML
	private Canvas cv_tours;
	@FXML
	private ListView<String> lv_console;

	@FXML
	private void initialize() {
		Log.getInstance().setOutputControl(lv_console);

		List<City> cities = Population.getInstance().createCities(cv_tours);
		Population.getInstance().initialise(cities);

		tf_numOfCities.setText(String.valueOf(Population.getInstance().getNumOfCities()));
		cb_strategy.setItems(FXCollections.observableArrayList(Strategy.values()));
//		cb_strategy.getSelectionModel().select(Population.getInstance().getStrategy());
		tf_numOfSteps.setText(String.valueOf(Main.DefaultNumOfSteps));

		lbl_minTotalDistance
				.setText(String.format("%,.2f", Population.getInstance().getTours().get(0).getTotalDistance()));
		lbl_maxTotalDistance.setText(String.format("%,.2f", Population.getInstance().getTours()
				.get(Population.getInstance().getNumOfTours() - 1).getTotalDistance()));

		Population.getInstance().draw(cv_tours);
	}

	@FXML
	private void onAction_setOptions() {
		int numOfCities = Utilities.getInstance().parseInt(tf_numOfCities.getText());

		if (numOfCities < 2) {
			tf_numOfCities.setText(String.valueOf(Main.DefaultNumOfCities));
		} else if (numOfCities > Main.MaxNumOfCities) {
			tf_numOfCities.setText(String.valueOf(Main.MaxNumOfCities));
		} else {
			Population.getInstance().setNumOfCities(numOfCities);

			Strategy selectedStrategy = cb_strategy.getSelectionModel().getSelectedItem();
//			Population.getInstance().setStrategy(selectedStrategy);

			initialize();
		}
	}

	@FXML
	private void onAction_btnPlay() {
		try {
			int numOfSteps = Integer.parseInt(tf_numOfSteps.getText());

			if (numOfSteps <= 0) {
				tf_numOfSteps.setText(String.valueOf(Main.DefaultNumOfSteps));
			} else if (numOfSteps > Main.MaxNumOfSteps) {
				tf_numOfSteps.setText(String.valueOf(Main.MaxNumOfSteps));
			} else {
				Population.getInstance().play(numOfSteps, cv_tours, lbl_minTotalDistance, lbl_maxTotalDistance);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void onAction_btnReset() {
		initialize();
	}

	@FXML
	private void onAction_btnExport() {
		History.getInstance().export();
		History.getInstance().showExport();
	}
}
