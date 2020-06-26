package application.view;

import application.History;
import application.Log;
import application.Main;
import application.Utilities;
import application.model.CityList;
import application.model.Population;
import application.strategy.CrossoverStrategy;
import application.strategy.MatingStrategy;
import application.strategy.MutationStrategy;
import application.strategy.ReplacementStrategy;
import application.strategy.SelectionStrategy;
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
	private ComboBox<SelectionStrategy> cb_selection_strategy;
	@FXML
	private ComboBox<MatingStrategy> cb_mating_strategy;
	@FXML
	private ComboBox<CrossoverStrategy> cb_crossover_strategy;
	@FXML
	private ComboBox<MutationStrategy> cb_mutation_strategy;
	@FXML
	private ComboBox<ReplacementStrategy> cb_replacement_strategy;
	@FXML
	private TextField tf_mutationProbability;
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

		CityList cityList = Population.getInstance().createCities(cv_tours);
		Population.getInstance().initialise(cityList);

		tf_numOfCities.setText(String.valueOf(Population.getInstance().getNumOfCities()));
		tf_numOfCities.setPromptText(String.valueOf(Main.MinNumOfCities) + "-" + String.valueOf(Main.MaxNumOfCities));
		tf_numOfSteps.setText(String.valueOf(Main.DefaultNumOfSteps));
		tf_numOfSteps.setPromptText(String.valueOf(Main.MinNumOfSteps) + "-" + String.valueOf(Main.MaxNumOfSteps));
		tf_mutationProbability.setText(String.valueOf(Main.DefaultMutationProbability));
		cb_selection_strategy.setItems(FXCollections.observableArrayList(SelectionStrategy.values()));
		cb_selection_strategy.getSelectionModel().select(Population.getInstance().getSelectionStrategy());
		cb_mating_strategy.setItems(FXCollections.observableArrayList(MatingStrategy.values()));
		cb_mating_strategy.getSelectionModel().select(Population.getInstance().getMatingStrategy());
		cb_crossover_strategy.setItems(FXCollections.observableArrayList(CrossoverStrategy.values()));
		cb_crossover_strategy.getSelectionModel().select(Population.getInstance().getCrossoverStrategy());
		cb_mutation_strategy.setItems(FXCollections.observableArrayList(MutationStrategy.values()));
		cb_mutation_strategy.getSelectionModel().select(Population.getInstance().getMutationStrategy());
		cb_replacement_strategy.setItems(FXCollections.observableArrayList(ReplacementStrategy.values()));
		cb_replacement_strategy.getSelectionModel().select(Population.getInstance().getReplacementStrategy());

		lbl_minTotalDistance
				.setText(String.format("%,.2f", Population.getInstance().getTourList().get(0).getTotalDistance()));
		lbl_maxTotalDistance.setText(String.format("%,.2f", Population.getInstance().getTourList()
				.get(Population.getInstance().getNumOfTours() - 1).getTotalDistance()));
		
		Population.getInstance().setNumOfRounds(0);
		lbl_round.setText("Round 0");
		
		Population.getInstance().draw(cv_tours);
	}

	@FXML
	private void onAction_setOptions() {
		int numOfCities = Utilities.getInstance().parseInt(tf_numOfCities.getText());
		double mutationProbability = Utilities.getInstance().parseDouble(tf_mutationProbability.getText());

		if (numOfCities < Main.MinNumOfCities) {
			tf_numOfCities.setText(String.valueOf(Main.MinNumOfCities));
		} else if (numOfCities > Main.MaxNumOfCities) {
			tf_numOfCities.setText(String.valueOf(Main.MaxNumOfCities));
		} else if(mutationProbability < Main.MinMutationProbability) {
			tf_mutationProbability.setText(String.valueOf(Main.MinMutationProbability));
		} else if(mutationProbability > Main.MaxMutationProbability) {
			tf_mutationProbability.setText(String.valueOf(Main.MaxMutationProbability));
		} else {
			Population.getInstance().setNumOfCities(numOfCities);
			Population.getInstance().setMutationProbability(mutationProbability);

			SelectionStrategy selectedSelectionStrategy = cb_selection_strategy.getSelectionModel().getSelectedItem();
			Population.getInstance().setSelectionStrategy(selectedSelectionStrategy);

			MatingStrategy selectedMatingStrategy = cb_mating_strategy.getSelectionModel().getSelectedItem();
			Population.getInstance().setMatingStrategy(selectedMatingStrategy);
			
			CrossoverStrategy selectedCrossoverStrategy = cb_crossover_strategy.getSelectionModel().getSelectedItem();
			Population.getInstance().setCrossoverStrategy(selectedCrossoverStrategy);

			MutationStrategy selectedMutationStrategy = cb_mutation_strategy.getSelectionModel().getSelectedItem();
			Population.getInstance().setMutationStrategy(selectedMutationStrategy);

			ReplacementStrategy selectedReplacementStrategy = cb_replacement_strategy.getSelectionModel()
					.getSelectedItem();
			Population.getInstance().setReplacementStrategy(selectedReplacementStrategy);

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
				Population.getInstance().play(numOfSteps, cv_tours, lbl_minTotalDistance, lbl_maxTotalDistance, lbl_round);
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
