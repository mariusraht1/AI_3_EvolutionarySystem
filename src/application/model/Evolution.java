package application.model;

import java.util.Arrays;

import application.History;
import application.Log;
import application.Main;
import application.strategy.CrossoverStrategy;
import application.strategy.MatingStrategy;
import application.strategy.MutationStrategy;
import application.strategy.ReplacementStrategy;
import application.strategy.SelectionStrategy;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import library.GeneralUtilities;
import library.MathManager;

public class Evolution {
	private static Evolution instance;

	public static Evolution getInstance() {
		if (instance == null) {
			instance = new Evolution();
		}

		return instance;
	}

	private int numOfRounds;

	public int getNumOfRounds() {
		return numOfRounds;
	}

	public void setNumOfRounds(int numOfRounds) {
		this.numOfRounds = numOfRounds;
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

	private Evolution() {
		reset();
	}

	public void reset() {
		this.numOfCities = Main.DefaultNumOfCities;
		this.mutationProbability = Main.DefaultMutationProbability;

		this.selectionStrategy = Main.DefaultSelectionStrategy;
		this.matingStrategy = Main.DefaultMatingStrategy;
		this.crossoverStrategy = Main.DefaultCrossoverStrategy;
		this.mutationStrategy = Main.DefaultMutationStrategy;
		this.replacementStrategy = Main.DefaultReplacementStrategy;
	}

	public CityList createCities(Canvas canvas) {
		CityList cityList = new CityList();

		for (int i = 1; i <= numOfCities; i++) {
			int x = MathManager.getInstance().getRandom(0, (int) canvas.getWidth());
			int y = MathManager.getInstance().getRandom(0, (int) canvas.getHeight());
			cityList.add(new City(i, x, y));
		}

		return cityList;
	}

	public void initialise(CityList cityList) {
		setSuggestedNumOfTours();
		this.tourList = new TourList(Arrays.asList(new Tour[this.numOfTours]));
		this.cityList = cityList;

		for (int i = 0; i < this.numOfTours; i++) {
			CityList cityListPerTour = new CityList(Arrays.asList(new City[numOfCities]));
			CityList tmpcityList = GeneralUtilities.getInstance().deepCopy(cityList);

			for (int j = 0; j < getNumOfCities(); j++) {
				int random = MathManager.getInstance().getRandom(0, tmpcityList.size() - 1);
				cityListPerTour.set(j, tmpcityList.get(random));
				tmpcityList.remove(random);
			}

			this.tourList.set(i, new Tour((i + 1), cityListPerTour));
		}

		this.tourList.rateFitness();
		this.tourList.sort();
	}

	public void play(int numOfSteps, Canvas canvas, Label lbl_minTotalDistance, Label lbl_maxTotalDistance,
			Label lbl_round) {
		TourList prevGeneration = this.tourList;
		TourList nextGeneration = this.tourList;
		nextGeneration.rateFitness();

		int n = 1;
		while (!exit(n, numOfSteps, prevGeneration, nextGeneration)) {
			TourList parentTourList = selection(nextGeneration);
			TourList childrenTourList = crossover(parentTourList);
			childrenTourList = mutate(childrenTourList);
			childrenTourList.rateFitness();
			prevGeneration = GeneralUtilities.getInstance().deepCopy(nextGeneration);
			nextGeneration = replace(parentTourList, childrenTourList);
			nextGeneration.sort();

			History.getInstance().add(this.numOfRounds, nextGeneration);
			this.numOfRounds++;

			n++;
		}

		this.tourList = nextGeneration;
		this.tourList.draw(canvas);

		double min = nextGeneration.get(0).getTotalDistance();
		double max = nextGeneration.get(getNumOfTours() - 1).getTotalDistance();

		lbl_minTotalDistance.setText(String.format("%,.2f", min));
		lbl_maxTotalDistance.setText(String.format("%,.2f", max));
		lbl_round.setText("Runde " + this.numOfRounds);
	}

	private boolean exit(int n, int numOfSteps, TourList prevGeneration, TourList nextGeneration) {
		boolean exit = false;

		int i = 0;
		while (!exit && i <= 2) {
			switch (i) {
			case 0:
				exit = true;
				double minFitness = nextGeneration.get(0).getFitness();

				for (int j = 1; j < nextGeneration.size() - 1; j++) {
					if (nextGeneration.get(j).getFitness() != minFitness) {
						exit = false;
						break;
					}
				}

				if (exit) {
					Log.getInstance().add("Ende: Lösungsmenge besitzt homogene Fitness-Werte.");
				}

				break;
			case 1:
				if (prevGeneration != nextGeneration
						&& prevGeneration.getFitnessMean() == nextGeneration.getFitnessMean()) {
					Log.getInstance().add("Ende: Keine Veränderung der Lösungsmenge im Mittel mehr.");
					exit = true;
				}
				break;
			case 2:
				if (n > numOfSteps) {
					exit = true;
				}
				break;
			}

			i++;
		}

		return exit;
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

	public int getNumOfParents() {
		// Each parent pair gets 2 children
		return Evolution.getInstance().getNumOfTours() / 2;
	}
}
