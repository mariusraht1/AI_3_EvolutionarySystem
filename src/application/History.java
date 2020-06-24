package application;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import application.Utilities.OSType;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

public class History {
	private static History instance;

	public static History getInstance() {
		if (instance == null) {
			instance = new History();
		}

		return instance;
	}

	private List<String[]> development = new ArrayList<String[]>();

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
//		development.add(new String[] { "Round", "Product", "Stock", "Demand", "Demand Rate", "Fuzz. Demand",
//				"New Stock", "New Stock Rate", "Fuzz. New Stock", "Fuzz. Order Amount", "Order Amount Factor",
//				"Order Amount" });
	}

	public void clear(ArrayList<XYChart<String, Integer>> charts, ArrayList<Series<String, Integer>> seriesList) {
		development.clear();
		initHeader();
	}

	public void add() {

//		development.add(new String[] { String.valueOf(round), productName, String.valueOf(stock),
//				String.valueOf(demand), String.valueOf(0.00), FuzzyAmount.NOTHING.toString(), String.valueOf(0),
//				String.valueOf(0.00), FuzzyAmount.NOTHING.toString(), FuzzyAmount.NOTHING.toString(),
//				String.valueOf(0.00), String.valueOf(0) });
	}

	public void export() {
		try {
			StringBuilder stringBuilder = new StringBuilder();
			for (String[] x : development) {
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
