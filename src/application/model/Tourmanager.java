package application.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

	private int numOfCities = Main.DefaultNumOfCities;

	public int getNumOfCities() {
		return numOfCities;
	}

	public void setNumOfCities(int numOfCities) {
		this.numOfCities = numOfCities;
	}

	private int numOfTours = Main.DefaultNumOfTours;

	public int getNumOfTours() {
		return numOfTours;
	}

	public void setNumOfTours(int numOfTours) {
		this.numOfTours = numOfTours;
	}

	private List<Tour> tours = Arrays.asList(new Tour[numOfTours]);

	public List<Tour> getTours() {
		return tours;
	}

	public void setTours(List<Tour> tours) {
		this.tours = tours;
	}

	private Tourmanager() {
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

			tours.set(i, new Tour(citiesPerTour));
		}

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

	public Tour getTourByMinTotalDistance() {
		Tour min = tours.get(0);

		for (int i = 1; i < tours.size(); i++) {
			if (tours.get(i).getTotalDistance() < min.getTotalDistance()) {
				min = tours.get(i);
			}
		}

		return min;
	}

	public Tour getTourByMaxTotalDistance() {
		Tour max = tours.get(0);

		for (int i = 1; i < tours.size(); i++) {
			if (tours.get(i).getTotalDistance() > max.getTotalDistance()) {
				max = tours.get(i);
			}
		}

		return max;
	}

	public List<Tour> orderByTotalDistance() {
		Collections.sort(tours, (t1, t2) -> Double.compare(t1.getTotalDistance(), t2.getTotalDistance()));
		return tours;
	}

	public void play(int numOfSteps, Canvas canvas, Label lbl_minTotalDistance, Label lbl_maxTotalDistance) {
		for (int n = 1; n <= numOfSteps; n++) {
			MutationSystem.getInstance().strategy_1();
		}
				
		Tourmanager.getInstance().draw(canvas);
		
		lbl_minTotalDistance.setText(
				String.format("%,.2f", Tourmanager.getInstance().getTourByMinTotalDistance().getTotalDistance()));
		lbl_maxTotalDistance.setText(
				String.format("%,.2f", Tourmanager.getInstance().getTourByMaxTotalDistance().getTotalDistance()));
	}
}
