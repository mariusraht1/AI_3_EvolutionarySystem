package application.model;

import java.util.ArrayList;
import java.util.List;

import application.Utilities;

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

		return distance;
	}

	// Switch 2 random cities
	public Tour mutate() {
		Tour t = new Tour(new ArrayList<City>(cities));

		int c1 = Utilities.getInstance().generateRandom(0, t.getCities().size() - 1);
		int c2 = Utilities.getInstance().generateRandom(0, t.getCities().size() - 1);

		City tmpCity = t.getCities().get(c1);
		t.getCities().set(c1, t.getCities().get(c2));
		t.getCities().set(c2, tmpCity);

		return t;
	}

	// NEW: Mix 2 Tours
	public Tour mutate(Tour tour) {
		Tour t = new Tour(new ArrayList<City>());

		return t;
	}
}