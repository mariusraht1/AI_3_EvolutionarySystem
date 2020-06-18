package application.model;

import java.util.ArrayList;
import java.util.List;

import application.Utilities;
import javafx.scene.canvas.GraphicsContext;

public class Tour {
	private List<City> cities;

	public List<City> getCities() {
		return cities;
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
	}

	public Tour(List<City> cities) {
		setCities(cities);
	}

	public double getTotalDistance() {
		double distance = 0;

		for (int i = 1; i < cities.size(); i++) {
			double xDistance = Math.pow((cities.get(i - 1).getX() - cities.get(1).getX()), 2);
			double yDistance = Math.pow((cities.get(i - 1).getY() - cities.get(1).getY()), 2);

			distance += Math.sqrt(xDistance + yDistance);
		}

		distance = Utilities.getInstance().formatDouble("0.00", distance);

		return distance;
	}

	public void draw(GraphicsContext gc) {
		gc.beginPath();

		for (int i = 0; i < getCities().size() - 1; i++) {
			if (i == 0) {
				gc.moveTo(getCities().get(i).getX(), getCities().get(i).getY());
			}

			gc.lineTo(getCities().get(i + 1).getX(), getCities().get(i + 1).getY());
			gc.moveTo(getCities().get(i + 1).getX(), getCities().get(i + 1).getY());
		}
		
		gc.closePath();
		gc.stroke();
	}

	// Switch 2 random cities
	public void mutate() {
		int c1 = Utilities.getInstance().generateRandom(0, getCities().size() - 1);
		int c2 = Utilities.getInstance().generateRandom(0, getCities().size() - 1);

		City tmpCity = getCities().get(c1);
		getCities().set(c1, getCities().get(c2));
		getCities().set(c2, tmpCity);
	}

	// NEW: Mix 2 Tours
	public void mutate(Tour tour) {
		
	}
}