package application;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import application.Utilities.OSType;
import application.model.City;
import application.model.Tour;
import application.model.TourList;

public class History {
	private static History instance;

	public static History getInstance() {
		if (instance == null) {
			instance = new History();
		}

		return instance;
	}

	private List<String[]> evolution = new ArrayList<String[]>();

	private File file = new File("history.csv");

	public File getFile() {
		return file;
	}

	private History() {
		try {
			file = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			file = new File(file.getParentFile().getPath() + "//history.txt");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		initHeader();
	};

	private void initHeader() {
		evolution.add(new String[] { "Round", "Tour", "Fitness", "Order" });
	}

	public void clear() {
		evolution.clear();
		initHeader();
	}

	public void add(int round, TourList tourList) {
		for (Tour tour : tourList) {
			StringBuilder order = new StringBuilder("[");
			for (City city : tour.getCityList()) {
				if (city.equals(tour.getCityList().get(tour.getCityList().size() - 1))) {
					order.append(city.getId());
				} else {
					order.append(city.getId() + ",");
				}
			}
			order.append("]");
			evolution.add(new String[] { String.valueOf(round), tour.getName(), String.valueOf(tour.getFitness()), order.toString() });
		}
	}

	public void export() {
		try {
			StringBuilder stringBuilder = new StringBuilder();
			for (String[] x : evolution) {
				for (int i = 0; i < x.length; i++) {
					stringBuilder.append(x[i]);
					if (i < x.length - 1) {
						stringBuilder.append(";");
					}
				}
				stringBuilder.append("\n");
			}

			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.append(stringBuilder);
			writer.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showExport() {
		try {
			if (Utilities.getInstance().getOperatingSystemType().equals(OSType.Windows)) {
				Runtime.getRuntime().exec("explorer.exe /select, " + file);
			} else {
				Desktop.getDesktop().open(History.getInstance().getFile());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
