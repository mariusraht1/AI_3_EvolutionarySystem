package application;

import java.io.IOException;

import application.strategy.CrossoverStrategy;
import application.strategy.MatingStrategy;
import application.strategy.MutationStrategy;
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
 * @version 25.06.2020-001
 */
public class Main extends Application {
	private static Stage primaryStage;

	public static Stage getPrimaryStage() {
		return primaryStage;
	}

	public static int MaxNumOfCities = 50;
	public static int DefaultNumOfCities = 5;
	public static int DefaultNumOfTours = 100;
	public static int MaxNumOfSteps = 500;
	public static int DefaultNumOfSteps = 1;

	public static SelectionStrategy DefaultSelectionStrategy = SelectionStrategy.TOURNAMENT;
	public static MatingStrategy DefaultMatingStrategy = MatingStrategy.NEXT_2;
	public static CrossoverStrategy DefaultCrossoverStrategy = CrossoverStrategy.ONE_POINT;
	public static MutationStrategy DefaultMutationStrategy = MutationStrategy.SWAPPING;
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
