package application.model;

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

	public Tour(Tour tour) {
		cities = tour.getCities();
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
}