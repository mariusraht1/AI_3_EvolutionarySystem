package application.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import application.Log;
import application.Main;
import application.Utilities;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class Tourmanager {
	private static Tourmanager instance;

	public static Tourmanager getInstance() {
		if (instance == null) {
			instance = new Tourmanager();
		}

		return instance;
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

	private List<Tour> tours;

	public List<Tour> getTours() {
		return tours;
	}

	public void setTours(List<Tour> tours) {
		this.tours = tours;
	}

	private Strategy strategy;

	public Strategy getStrategy() {
		return strategy;
	}

	public void setStrategy(Strategy strategy) {
		this.strategy = strategy;
	}

	private Tourmanager() {
		reset();
	}

	public void reset() {
		setNumOfCities(Main.DefaultNumOfCities);
		setNumOfTours(Main.DefaultNumOfTours);
		setTours(Arrays.asList(new Tour[numOfTours]));
		setStrategy(Strategy.SWITCH_2_CITIES_FROM_RANDOM_BEST_TOUR);
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

	public List<Tour> createTours(List<City> cities) {
		tours = Arrays.asList(new Tour[numOfTours]);

		for (int i = 0; i < numOfTours; i++) {
			List<City> citiesPerTour = Arrays.asList(new City[numOfCities]);
			List<City> tmpCities = new ArrayList<City>(cities);

			for (int j = 0; j < getNumOfCities(); j++) {
				int random = Utilities.getInstance().generateRandom(0, tmpCities.size() - 1);
				citiesPerTour.set(j, tmpCities.get(random));
				tmpCities.remove(random);
			}

			tours.set(i, new Tour("Tour " + String.valueOf(i + 1), citiesPerTour));
		}

		orderByTotalDistance();

		return tours;
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

	public List<Tour> orderByTotalDistance() {
		Collections.sort(tours, (t1, t2) -> Double.compare(t1.getTotalDistance(), t2.getTotalDistance()));
		return tours;
	}

	public void play(int numOfSteps, Canvas canvas, Label lbl_minTotalDistance, Label lbl_maxTotalDistance) {
		for (int n = 1; n <= numOfSteps; n++) {
			if (Tourmanager.getInstance().hasDifferentDistances()) {
				MutationSystem.getInstance().strategy_1();
			} else {
				break;
			}
		}

		Log.getInstance().add("Current Optimum:");
		for (int i = 0; i < Tourmanager.getInstance().getTours().get(0).getCities().size(); i++) {
			Log.getInstance().add(String.valueOf(i + 1) + ") "
					+ Tourmanager.getInstance().getTours().get(0).getCities().get(i).getName());
		}

		Tourmanager.getInstance().orderByTotalDistance();
		Tourmanager.getInstance().draw(canvas);

		double min = Tourmanager.getInstance().getTours().get(0).getTotalDistance();
		double max = Tourmanager.getInstance().getTours().get(getNumOfTours() - 1).getTotalDistance();

		lbl_minTotalDistance.setText(String.format("%,.2f", min));
		lbl_maxTotalDistance.setText(String.format("%,.2f", max));
	}

	private boolean hasDifferentDistances() {
		for (int i = 1; i < tours.size(); i++) {
			if (tours.get(i).getTotalDistance() != tours.get(i - 1).getTotalDistance()) {
				return true;
			}
		}

		return false;
	}
}
