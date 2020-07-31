package application.model;

import java.io.Serializable;

import javafx.scene.canvas.GraphicsContext;
import library.GeneralUtilities;
import library.MathManager;

public class Tour implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private CityList cityList;

	public CityList getCityList() {
		return cityList;
	}

	public void setCityList(CityList cityList) {
		this.cityList = cityList;
	}

	private double fitness;

	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	public Tour(int id, CityList cityList) {
		this.id = id;

		if (id < 10 && id > 0) {
			this.name = "Tour 00" + id;
		} else if (id < 100 && id > 0) {
			this.name = "Tour 0" + id;
		} else {
			this.name = "Tour " + id;
		}

		setCityList(cityList);
	}

	public Tour(Tour tour) {
		this.id = tour.getId();
		this.name = tour.getName();
		this.cityList = GeneralUtilities.getInstance().deepCopy(tour.getCityList());
	}

	public double getTotalDistance() {
		// last city -> first city
		double xDistance = Math.pow((cityList.get(cityList.size() - 1).getX() - cityList.get(0).getX()), 2);
		double yDistance = Math.pow((cityList.get(cityList.size() - 1).getY() - cityList.get(0).getY()), 2);
		double distance = Math.sqrt(xDistance + yDistance);

		for (int i = 1; i < cityList.size(); i++) {
			xDistance = Math.pow((cityList.get(i - 1).getX() - cityList.get(1).getX()), 2);
			yDistance = Math.pow((cityList.get(i - 1).getY() - cityList.get(1).getY()), 2);
			distance += Math.sqrt(xDistance + yDistance);
		}

		distance = MathManager.getInstance().formatDouble("0.00", distance);

		return distance;
	}

	public void draw(GraphicsContext gc) {
		gc.beginPath();

		for (int i = 0; i < getCityList().size() - 1; i++) {
			if (i == 0) {
				gc.moveTo(getCityList().get(i).getX(), getCityList().get(i).getY());
			}

			gc.lineTo(getCityList().get(i + 1).getX(), getCityList().get(i + 1).getY());
			gc.moveTo(getCityList().get(i + 1).getX(), getCityList().get(i + 1).getY());
		}

		gc.closePath();
		gc.stroke();
	}

	@Override
	public boolean equals(Object object) {
		boolean equals = false;

		if (object != null && object instanceof Tour) {
			Tour tour = (Tour) object;
			equals = this.id == tour.getId() && this.cityList.equals(tour.getCityList());
		}

		return equals;
	}

	@Override
	public String toString() {
		return getName();
	}
}