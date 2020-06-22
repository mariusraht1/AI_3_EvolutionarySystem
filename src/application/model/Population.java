package application.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import application.Log;
import application.Main;
import application.Utilities;
import application.strategy.CrossoverStrategy;
import application.strategy.ReplacementStrategy;
import application.strategy.SelectionStrategy;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class Population {
	private static Population instance;

	public static Population getInstance() {
		if (instance == null) {
			instance = new Population();
		}

		return instance;
	}

	private List<Tour> tours;

	public List<Tour> getTours() {
		return tours;
	}

	public void setTours(List<Tour> tours) {
		this.tours = tours;
	}

	private List<City> cities;

	public List<City> getCities() {
		return cities;
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
	}

	private int numOfCities;

	public int getNumOfCities() {
		return numOfCities;
	}

	public void setNumOfCities(int numOfCities) {
		this.numOfCities = numOfCities;
	}

	private int numOfTours;

	public int getNumOfTours() {
		return numOfTours;
	}

	public void setNumOfTours(int numOfTours) {
		this.numOfTours = numOfTours;
	}

	private SelectionStrategy selectionStrategy;

	public SelectionStrategy getSelectionStrategy() {
		return selectionStrategy;
	}

	public void setSelectionStrategy(SelectionStrategy selectionStrategy) {
		this.selectionStrategy = selectionStrategy;
	}

	private CrossoverStrategy crossoverStrategy;

	public CrossoverStrategy getCrossoverStrategy() {
		return crossoverStrategy;
	}

	public void setCrossoverStrategy(CrossoverStrategy crossoverStrategy) {
		this.crossoverStrategy = crossoverStrategy;
	}

	private ReplacementStrategy replacementStrategy;

	public ReplacementStrategy getReplacementStrategy() {
		return replacementStrategy;
	}

	public void setReplacementStrategy(ReplacementStrategy replacementStrategy) {
		this.replacementStrategy = replacementStrategy;
	}

	private Population() {
		reset();
	}

	public void reset() {
		this.numOfCities = Main.DefaultNumOfCities;
		this.numOfTours = Main.DefaultNumOfTours;
		this.tours = Arrays.asList(new Tour[numOfTours]);
		
		this.selectionStrategy = Main.DefaultSelectionStrategy;
		this.crossoverStrategy = Main.DefaultCrossoverStrategy;
		this.replacementStrategy = Main.DefaultReplacementStrategy;
	}

	public ArrayList<City> createCities(Canvas canvas) {
		ArrayList<City> cities = new ArrayList<City>();

		for (int i = 1; i <= numOfCities; i++) {
			int x = Utilities.getInstance().generateRandom(0, (int) canvas.getWidth());
			int y = Utilities.getInstance().generateRandom(0, (int) canvas.getHeight());

			cities.add(new City("Stadt " + i, x, y));
		}

		return cities;
	}

	public void initialise(List<City> cities) {
		this.tours = Arrays.asList(new Tour[numOfTours]);
		this.cities = cities;

		for (int i = 0; i < numOfTours; i++) {
			List<City> citiesPerTour = Arrays.asList(new City[numOfCities]);
			List<City> tmpCities = new ArrayList<City>(cities);

			for (int j = 0; j < getNumOfCities(); j++) {
				int random = Utilities.getInstance().generateRandom(0, tmpCities.size() - 1);
				citiesPerTour.set(j, tmpCities.get(random));
				tmpCities.remove(random);
			}

			this.tours.set(i, new Tour("Tour " + String.valueOf(i + 1), citiesPerTour));
		}
	}

	public void rateFitness() {
		for (Tour tour : tours) {
			tour.setFitness(tour.getTotalDistance());
		}

		Collections.sort(tours, (t1, t2) -> Double.compare(t1.getTotalDistance(), t2.getTotalDistance()));
	}

	public void mutate(List<Tour> tours) {
		for (int i = 0; i < tours.size() * 0.20; i++) {
			int random = Utilities.getInstance().generateRandom(0, (int) (tours.size() * 0.80) - 1);
			Tour betterTour = new Tour(tours.get(random));
			Tour worseTour = tours.get(tours.size() - 1 - i);

			int c1 = Utilities.getInstance().generateRandom(0, betterTour.getCities().size() - 1);
			int c2 = Utilities.getInstance().generateRandom(0, betterTour.getCities().size() - 1);

			City tmpCity = betterTour.getCities().get(c1);
			betterTour.getCities().set(c1, betterTour.getCities().get(c2));
			betterTour.getCities().set(c2, tmpCity);

			worseTour.setCities(betterTour.getCities());
		}
	}

	public void draw(Canvas canvas) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		gc.setStroke(Color.rgb(0, 0, 255, 0.02));
		gc.setLineWidth(1.0);

		for (Tour tour : tours) {
			tour.draw(gc);
		}
	}

	public void play(int numOfSteps, Canvas canvas, Label lbl_minTotalDistance, Label lbl_maxTotalDistance) {
		for (int n = 1; n <= numOfSteps; n++) {
//		if (Tourmanager.getInstance().hasDifferentDistances()) {
//			MutationSystem.getInstance().strategy_1();
//		} else {
//			break;
//		}
		}

		Log.getInstance().add("Current Optimum:");
		for (int i = 0; i < getTours().get(0).getCities().size(); i++) {
			Log.getInstance().add(String.valueOf(i + 1) + ") " + getTours().get(0).getCities().get(i).getName());
		}

//	Tourmanager.getInstance().orderByTotalDistance();
		draw(canvas);

		double min = getTours().get(0).getTotalDistance();
		double max = getTours().get(getNumOfTours() - 1).getTotalDistance();

		lbl_minTotalDistance.setText(String.format("%,.2f", min));
		lbl_maxTotalDistance.setText(String.format("%,.2f", max));
	}
}
