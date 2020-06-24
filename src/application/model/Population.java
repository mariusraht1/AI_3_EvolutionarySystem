package application.model;

import java.util.Arrays;
import java.util.Collections;

import application.Log;
import application.Main;
import application.Utilities;
import application.strategy.CrossoverStrategy;
import application.strategy.MutationStrategy;
import application.strategy.ReplacementStrategy;
import application.strategy.SelectionStrategy;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class Population {
	private static Population instance;

	public static Population getInstance() {
		if (instance == null) {
			instance = new Population();
		}

		return instance;
	}

	private TourList tourList;

	public TourList getTourList() {
		return tourList;
	}

	public void setTourList(TourList tourList) {
		this.tourList = tourList;
	}

	private CityList cityList;

	public CityList getCityList() {
		return cityList;
	}

	public void setCityList(CityList cityList) {
		this.cityList = cityList;
	}

	private int numOfCities;

	public int getNumOfCities() {
		return numOfCities;
	}

	public void setNumOfCities(int numOfCities) {
		this.numOfCities = numOfCities;
	}

	private int numOfTours;

	public int getNumOfTours() {
		return numOfTours;
	}

	public void setNumOfTours(int numOfTours) {
		this.numOfTours = numOfTours;
	}

	private SelectionStrategy selectionStrategy;

	public SelectionStrategy getSelectionStrategy() {
		return selectionStrategy;
	}

	public void setSelectionStrategy(SelectionStrategy selectionStrategy) {
		this.selectionStrategy = selectionStrategy;
	}

	private CrossoverStrategy crossoverStrategy;

	public CrossoverStrategy getCrossoverStrategy() {
		return crossoverStrategy;
	}

	public void setCrossoverStrategy(CrossoverStrategy crossoverStrategy) {
		this.crossoverStrategy = crossoverStrategy;
	}

	private MutationStrategy mutationStrategy;

	public MutationStrategy getMutationStrategy() {
		return mutationStrategy;
	}

	public void setMutationStrategy(MutationStrategy mutationStrategy) {
		this.mutationStrategy = mutationStrategy;
	}

	private ReplacementStrategy replacementStrategy;

	public ReplacementStrategy getReplacementStrategy() {
		return replacementStrategy;
	}

	public void setReplacementStrategy(ReplacementStrategy replacementStrategy) {
		this.replacementStrategy = replacementStrategy;
	}

	private Population() {
		reset();
	}

	public void reset() {
		this.numOfCities = Main.DefaultNumOfCities;
		this.numOfTours = Main.DefaultNumOfTours;
		this.tourList = new TourList(Arrays.asList(new Tour[numOfTours]));

		this.selectionStrategy = Main.DefaultSelectionStrategy;
		this.crossoverStrategy = Main.DefaultCrossoverStrategy;
		this.mutationStrategy = Main.DefaultMutationStrategy;
		this.replacementStrategy = Main.DefaultReplacementStrategy;
	}

	public CityList createCities(Canvas canvas) {
		CityList cityList = new CityList();

		for (int i = 1; i <= numOfCities; i++) {
			int x = Utilities.getInstance().generateRandom(0, (int) canvas.getWidth());
			int y = Utilities.getInstance().generateRandom(0, (int) canvas.getHeight());

			cityList.add(new City("Stadt " + i, x, y));
		}

		return cityList;
	}

	public void initialise(CityList cityList) {
		this.tourList = new TourList(Arrays.asList(new Tour[numOfTours]));
		this.cityList = cityList;

		for (int i = 0; i < numOfTours; i++) {
			CityList cityListPerTour = new CityList(Arrays.asList(new City[numOfCities]));
			CityList tmpcityList = Utilities.getInstance().deepCopy(cityList);

			for (int j = 0; j < getNumOfCities(); j++) {
				int random = Utilities.getInstance().generateRandom(0, tmpcityList.size() - 1);
				cityListPerTour.set(j, tmpcityList.get(random));
				tmpcityList.remove(random);
			}

			this.tourList.set(i, new Tour("Tour " + String.valueOf(i + 1), cityListPerTour));
		}
	}

	// TODO Considering in related logic that 1st city has to be the last too
	// TODO Fitness: Consider max(-f(x)) as minimization function
	// TODO Warn if there aren't enough tours to replace the previous generation,
	// cause something went wrong
	public void play(int numOfSteps, Canvas canvas, Label lbl_minTotalDistance, Label lbl_maxTotalDistance) {
		TourList nextGeneration = this.tourList;
		
		for (int n = 1; n <= numOfSteps; n++) {
			TourList parentTourList = selection(nextGeneration);
			TourList childrenTourList = crossover(parentTourList);
			childrenTourList = mutate(childrenTourList);
			nextGeneration = replace(parentTourList, childrenTourList);
		}

		Log.getInstance().add("Current Optimum:");
		for (int i = 0; i < getTourList().get(0).getCityList().size(); i++) {
			Log.getInstance().add(String.valueOf(i + 1) + ") " + getTourList().get(0).getCityList().get(i).getName());
		}

		draw(canvas);

		double min = getTourList().get(0).getTotalDistance();
		double max = getTourList().get(getNumOfTours() - 1).getTotalDistance();

		lbl_minTotalDistance.setText(String.format("%,.2f", min));
		lbl_maxTotalDistance.setText(String.format("%,.2f", max));
	}

	public void rateFitness(TourList tourList) {
		for (Tour tour : tourList) {
			tour.setFitness(tour.getTotalDistance());
		}

		Collections.sort(tourList, (t1, t2) -> Double.compare(t1.getTotalDistance(), t2.getTotalDistance()));
	}

	public TourList selection(TourList tourList) {
		return selectionStrategy.execute(tourList);
	}

	public TourList crossover(TourList tourList) {
		return crossoverStrategy.execute(tourList);
	}

	public TourList mutate(TourList tourList) {
		return mutationStrategy.execute(tourList);
	}

	public TourList replace(TourList parentTourList, TourList childrenTourList) {
		return replacementStrategy.execute(parentTourList, childrenTourList);
	}

	public void draw(Canvas canvas) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		gc.setStroke(Color.rgb(0, 0, 255, 0.02));
		gc.setLineWidth(1.0);

		for (Tour tour : this.tourList) {
			tour.draw(gc);
		}
	}

	public TourList getPercentile(TourList tourList) {
		TourList tmptourList = Utilities.getInstance().deepCopy(tourList);
		TourList mutabletourList = new TourList();
		int n = (int) (Population.getInstance().getNumOfTours() * 0.01);

		for (int i = 0; i < n; i++) {
			int random = Utilities.getInstance().generateRandom(0, tmptourList.size() - 1);

			mutabletourList.add(tmptourList.get(random));
			tmptourList.remove(random);
		}

		return mutabletourList;
	}

	public double getCumulatedFitness(TourList tourList) {
		double cumulatedFitness = 0.0;

		for (Tour tour : tourList) {
			cumulatedFitness += tour.getFitness();
		}

		return cumulatedFitness;
	}

	public int getNumOfParents() {
		return Population.getInstance().getNumOfTours() / 2;
	}
}
