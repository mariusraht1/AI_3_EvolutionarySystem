package application.model;

import java.util.Arrays;
import java.util.Collections;

import application.Log;
import application.Main;
import application.Utilities;
import application.strategy.CrossoverStrategy;
import application.strategy.MatingStrategy;
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

	public void setSuggestedNumOfTours() {
		int numOfTours = 1;

		for (int i = 2; i < this.numOfCities; i++) {
			numOfTours *= i;
		}

		numOfTours /= 2;

		if (numOfTours > Main.DefaultNumOfTours) {
			numOfTours = Main.DefaultNumOfTours;
		}

		Log.getInstance().add("Setze Touranzahl auf " + String.valueOf(numOfTours));
		this.numOfTours = numOfTours;
	}

	private double mutationProbability;
	
	public double getMutationProbability() {
		return mutationProbability;
	}

	public void setMutationProbability(double mutationProbability) {
		this.mutationProbability = mutationProbability;
	}

	private SelectionStrategy selectionStrategy;

	public SelectionStrategy getSelectionStrategy() {
		return selectionStrategy;
	}

	public void setSelectionStrategy(SelectionStrategy selectionStrategy) {
		this.selectionStrategy = selectionStrategy;
	}

	private MatingStrategy matingStrategy;

	public MatingStrategy getMatingStrategy() {
		return matingStrategy;
	}

	public void setMatingStrategy(MatingStrategy matingStrategy) {
		this.matingStrategy = matingStrategy;
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

		this.selectionStrategy = Main.DefaultSelectionStrategy;
		this.matingStrategy = Main.DefaultMatingStrategy;
		this.crossoverStrategy = Main.DefaultCrossoverStrategy;
		this.mutationStrategy = Main.DefaultMutationStrategy;
		this.replacementStrategy = Main.DefaultReplacementStrategy;
	}

	public CityList createCities(Canvas canvas) {
		CityList cityList = new CityList();

		for (int i = 1; i <= numOfCities; i++) {
			int x = Utilities.getInstance().getRandom(0, (int) canvas.getWidth());
			int y = Utilities.getInstance().getRandom(0, (int) canvas.getHeight());
			cityList.add(new City("Stadt " + i, x, y));
		}

		return cityList;
	}

	public void initialise(CityList cityList) {
		setSuggestedNumOfTours();
		this.tourList = new TourList(Arrays.asList(new Tour[this.numOfTours]));
		this.cityList = cityList;

		for (int i = 0; i < this.numOfTours; i++) {
			CityList cityListPerTour = new CityList(Arrays.asList(new City[numOfCities]));
			CityList tmpcityList = Utilities.getInstance().deepCopy(cityList);

			for (int j = 0; j < getNumOfCities(); j++) {
				int random = Utilities.getInstance().getRandom(0, tmpcityList.size() - 1);
				cityListPerTour.set(j, tmpcityList.get(random));
				tmpcityList.remove(random);
			}

			this.tourList.set(i, new Tour("Tour " + String.valueOf(i + 1), cityListPerTour));
		}

		rateFitness(this.tourList);
		sort(this.tourList);
	}

	// OPT Reduce calling of sort
	public void sort(TourList tourList) {
		Collections.sort(tourList, (c1, c2) -> Double.compare(c1.getFitness(), c2.getFitness()));

//		StringBuilder fitnessVector = new StringBuilder("Fitness: [");
//		for (Tour tour : tourList) {
//			if (tour.equals(tourList.get(tourList.size() - 1))) {
//				fitnessVector.append(tour.getFitness());
//			} else {
//				fitnessVector.append(tour.getFitness() + ", ");
//			}
//		}
//		Log.getInstance().add(fitnessVector.toString() + "]");
	}

	// INFO Considering in related logic that 1st city has to be the last too
	// TODO Fitness: Consider max(-f(x)) as minimization function
	// TODO Warn if there aren't enough tours to replace the previous generation,
	// cause something went wrong
	public void play(int numOfSteps, Canvas canvas, Label lbl_minTotalDistance, Label lbl_maxTotalDistance) {
		TourList nextGeneration = this.tourList;

		for (int n = 1; n <= numOfSteps; n++) {
			rateFitness(nextGeneration);
			TourList parentTourList = selection(nextGeneration);
			TourList childrenTourList = crossover(parentTourList);
			childrenTourList = mutate(childrenTourList);
			rateFitness(childrenTourList);
			nextGeneration = replace(parentTourList, childrenTourList);
		}

		Log.getInstance().add("Current Optimum: " + nextGeneration.get(0).getTotalDistance());
		for (int i = 0; i < getTourList().get(0).getCityList().size(); i++) {
			Log.getInstance().add(String.valueOf(i + 1) + ") " + getTourList().get(0).getCityList().get(i).getName());
		}

		draw(canvas);

		Population.getInstance().sort(this.tourList);

		double min = getTourList().get(0).getTotalDistance();
		double max = getTourList().get(getNumOfTours() - 1).getTotalDistance();

		lbl_minTotalDistance.setText(String.format("%,.2f", min));
		lbl_maxTotalDistance.setText(String.format("%,.2f", max));
	}

	public void rateFitness(TourList tourList) {
		for (Tour tour : tourList) {
			tour.setFitness(tour.getTotalDistance());
		}
	}

	public TourList selection(TourList nextGenerationTourList) {
		return selectionStrategy.execute(nextGenerationTourList);
	}

	public TourList mate(TourList parentTourList, Tour currentTour) {
		return matingStrategy.execute(parentTourList, currentTour);
	}

	public TourList crossover(TourList parentTourList) {
		return crossoverStrategy.execute(parentTourList);
	}

	public TourList mutate(TourList childrenTourList) {
		return mutationStrategy.execute(childrenTourList);
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

	public double getCumulatedFitness(TourList tourList) {
		double cumulatedFitness = 0.0;

		for (Tour tour : tourList) {
			cumulatedFitness += tour.getFitness();
		}

		return cumulatedFitness;
	}

	public int getNumOfParents() {
		// Each parent pair gets 2 children
		return Population.getInstance().getNumOfTours() / 2;
	}
}
