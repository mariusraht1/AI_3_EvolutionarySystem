package application.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import application.Log;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import library.GeneralUtilities;
import library.MathManager;

public class TourList extends ArrayList<Tour> implements Serializable {
	private static final long serialVersionUID = 1L;

	public TourList(List<Tour> tourList) {
		this.addAll(tourList);
	}

	public TourList() {
	}

	public void rateFitness() {
		for (Tour tour : this) {
			tour.setFitness(tour.getTotalDistance());
		}
	}

	public double getTotalFitness() {
		double cumulatedFitness = 0.0;

		for (Tour tour : this) {
			cumulatedFitness += tour.getFitness();
		}

		return cumulatedFitness;
	}

	public double getFitnessMean() {
		return getTotalFitness() / this.size();
	}

	public void sort() {
		Collections.sort(this, (c1, c2) -> Double.compare(c1.getFitness(), c2.getFitness()));
		Log.getInstance().logFitness(this);
	}

	public void draw(Canvas canvas) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		gc.setStroke(Color.rgb(0, 0, 255, 0.02));
		gc.setLineWidth(1.0);

		for (Tour tour : this) {
			tour.draw(gc);
		}
	}

	public TourList getPercentile() {
		TourList tmptourList = GeneralUtilities.getInstance().deepCopy(this);
		TourList mutabletourList = new TourList();

		for (Tour tour : tmptourList) {
			double p = MathManager.getInstance().getRandom(0.0, 1.0);

			if (p <= Evolution.getInstance().getMutationProbability()) {
				mutabletourList.add(tour);
			}
		}

		return mutabletourList;
	}
}
