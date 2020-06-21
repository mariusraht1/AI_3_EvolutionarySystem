//package application.model;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//import application.Log;
//import application.Main;
//import application.Utilities;
//import javafx.scene.canvas.Canvas;
//import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.control.Label;
//import javafx.scene.paint.Color;
//
//public class Tourmanager {
//	private static Tourmanager instance;
//
//	public static Tourmanager getInstance() {
//		if (instance == null) {
//			instance = new Tourmanager();
//		}
//
//		return instance;
//	}
//
//	private Strategy strategy;
//
//	public Strategy getStrategy() {
//		return strategy;
//	}
//
//	public void setStrategy(Strategy strategy) {
//		this.strategy = strategy;
//	}
//
//	private Tourmanager() {
//		reset();
//	}
//
//	public void reset() {
//		setNumOfCities(Main.DefaultNumOfCities);
//		setNumOfTours(Main.DefaultNumOfTours);
//		setTours(Arrays.asList(new Tour[numOfTours]));
//		setStrategy(Strategy.SWITCH_2_CITIES_FROM_RANDOM_BEST_TOUR);
//	}
//
//	public void draw(Canvas canvas) {
//		GraphicsContext gc = canvas.getGraphicsContext2D();
//		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
//		gc.setStroke(Color.rgb(0, 0, 255, 0.02));
//		gc.setLineWidth(1.0);
//
//		for (Tour tour : tours) {
//			tour.draw(gc);
//		}
//	}
//
//	public List<Tour> orderByTotalDistance() {
//		Collections.sort(tours, (t1, t2) -> Double.compare(t1.getTotalDistance(), t2.getTotalDistance()));
//		return tours;
//	}
//
//	public void play(int numOfSteps, Canvas canvas, Label lbl_minTotalDistance, Label lbl_maxTotalDistance) {
//		for (int n = 1; n <= numOfSteps; n++) {
//			if (Tourmanager.getInstance().hasDifferentDistances()) {
//				MutationSystem.getInstance().strategy_1();
//			} else {
//				break;
//			}
//		}
//
//		Log.getInstance().add("Current Optimum:");
//		for (int i = 0; i < Tourmanager.getInstance().getTours().get(0).getCities().size(); i++) {
//			Log.getInstance().add(String.valueOf(i + 1) + ") "
//					+ Tourmanager.getInstance().getTours().get(0).getCities().get(i).getName());
//		}
//
//		Tourmanager.getInstance().orderByTotalDistance();
//		Tourmanager.getInstance().draw(canvas);
//
//		double min = Tourmanager.getInstance().getTours().get(0).getTotalDistance();
//		double max = Tourmanager.getInstance().getTours().get(getNumOfTours() - 1).getTotalDistance();
//
//		lbl_minTotalDistance.setText(String.format("%,.2f", min));
//		lbl_maxTotalDistance.setText(String.format("%,.2f", max));
//	}
//
//	private boolean hasDifferentDistances() {
//		for (int i = 1; i < tours.size(); i++) {
//			if (tours.get(i).getTotalDistance() != tours.get(i - 1).getTotalDistance()) {
//				return true;
//			}
//		}
//
//		return false;
//	}
//}
