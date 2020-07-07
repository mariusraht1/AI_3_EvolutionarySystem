package application;

import java.util.ArrayList;
import java.util.Locale;

import application.model.City;
import application.model.Evolution;
import application.model.Tour;
import application.model.TourList;
import javafx.scene.control.ListView;

public class Log {
	private static Log instance;

	public static Log getInstance() {
		if (instance == null) {
			instance = new Log();
		}

		return instance;
	}

	private Log() {
	}

	private ArrayList<String> buffer = new ArrayList<String>();

	private ListView<String> control;

	public void setOutputControl(ListView<String> control) {
		this.control = control;
		clear();
	}

	public void add(String message) {
		System.out.println(message);

		if (control != null) {
			if (buffer.size() > 0) {
				for (String msg : buffer) {
					control.getItems().add(msg + "\n");
				}

				buffer.clear();
			}

			control.getItems().add(message + "\n");
		} else {
			buffer.add(message);
		}
	}

	public void clear() {
		buffer.clear();
		control.getItems().clear();
	}

	public void logCities(Tour tour) {
		String fitness = String.format(Locale.ROOT, "%.2f", tour.getFitness());
		StringBuilder tourString = new StringBuilder(tour.getName() + " (" + fitness + "): [");

		for (City city : tour.getCityList()) {
			if (city.equals(tour.getCityList().get(Evolution.getInstance().getNumOfCities() - 1))) {
				tourString.append(city.getId());
			} else {
				tourString.append(city.getId() + ", ");
			}
		}

		Log.getInstance().add(tourString.toString() + "]");
	}

	public void logCities(TourList tourList) {
		for (Tour tour : tourList) {
			logCities(tour);
		}
	}

	public void logFitness(TourList tourList) {
		StringBuilder fitnessVector = new StringBuilder("Fitness: [");
		for (Tour tour : tourList) {
			if (tour.equals(tourList.get(tourList.size() - 1))) {
				fitnessVector.append(tour.getFitness());
			} else {
				fitnessVector.append(tour.getFitness() + ", ");
			}
		}
		Log.getInstance().add(fitnessVector.toString() + "]");
	}

	public void logHeader(String processName) {
		Log.getInstance().add("=== " + processName + " ===============================================");
	}

}
