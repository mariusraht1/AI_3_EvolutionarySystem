package application;

import java.io.IOException;

import application.strategy.CrossoverStrategy;
import application.strategy.ReplacementStrategy;
import application.strategy.SelectionStrategy;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Evolutionary System for Traveling Salesman Problem
 * 
 * @author Marius Raht
 * @version 17.06.2020-001
 */
public class Main extends Application {
	private static Stage primaryStage;

	public static Stage getPrimaryStage() {
		return primaryStage;
	}

	public static int MaxNumOfCities = 50;
	public static int DefaultNumOfCities = 15;
	public static int DefaultNumOfTours = 100;
	public static int MaxNumOfSteps = 500;
	public static int DefaultNumOfSteps = 1;
	
	public static SelectionStrategy DefaultSelectionStrategy = SelectionStrategy.TOURNAMENT;
	public static CrossoverStrategy DefaultCrossoverStrategy = CrossoverStrategy.SWITCH_2_CITIES_FROM_RANDOM_BEST_TOUR;
	public static ReplacementStrategy DefaultReplacementStrategy = ReplacementStrategy.ONLY_CHILDREN;

	@Override
	public void start(Stage primaryStage) {
		try {
			Main.primaryStage = primaryStage;

			primaryStage.setTitle("Fuzzy System");
			primaryStage.centerOnScreen();

			Scene scene = new Scene(FXMLLoader.load(Main.class.getResource("/application/view/MainScene.fxml")));
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
