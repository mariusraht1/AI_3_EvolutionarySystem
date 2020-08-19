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
 * @version 19.08.2020-001
 */
public class Main extends Application {
	private static Stage primaryStage;

	public static Stage getPrimaryStage() {
		return primaryStage;
	}

	public final static int MinNumOfCities = 3;
	public final static int MaxNumOfCities = 50;
	public final static int DefaultNumOfCities = 15;
	public final static int DefaultNumOfTours = 100;
	public final static int MinNumOfSteps = 1;
	public final static int MaxNumOfSteps = 500;
	public final static int DefaultNumOfSteps = 1;
	public final static double MinMutationProbability = 0.00;
	public final static double MaxMutationProbability = 1.00;
	public final static double DefaultMutationProbability = 0.01;

	public final static SelectionStrategy DefaultSelectionStrategy = SelectionStrategy.TOURNAMENT;
	public final static MatingStrategy DefaultMatingStrategy = MatingStrategy.NEXT_2;
	public final static CrossoverStrategy DefaultCrossoverStrategy = CrossoverStrategy.ONE_POINT;
	public final static MutationStrategy DefaultMutationStrategy = MutationStrategy.SWAPPING;
	public final static ReplacementStrategy DefaultReplacementStrategy = ReplacementStrategy.ONLY_CHILDREN;

	@Override
	public void start(Stage primaryStage) {
		try {
			Main.primaryStage = primaryStage;

			primaryStage.setTitle("Evolutionary System");
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
