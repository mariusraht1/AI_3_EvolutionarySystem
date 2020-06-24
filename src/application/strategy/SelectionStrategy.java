package application.strategy;

import java.util.Collections;

import application.Utilities;
import application.model.Population;
import application.model.Tour;
import application.model.TourList;

public enum SelectionStrategy {
	BEST("Bestenselektion"), FITNESS("Fitnessproportionale Selektion"), RANK("Rangselektion"),
	TOURNAMENT("Turnierselektion");

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private SelectionStrategy(String name) {
		setName(name);
	}

	public TourList execute(TourList tourList) {
		switch (this) {
		case BEST:
			tourList = best(tourList);
			break;
		case FITNESS:
			tourList = fitness(tourList);
			break;
		case RANK:
			tourList = rank(tourList);
			break;
		case TOURNAMENT:
			tourList = tournament(tourList);
			break;
		}

		return tourList;
	}

	private TourList best(TourList tourList) {
		Collections.sort(tourList, (c1, c2) -> Double.compare(c1.getFitness(), c2.getFitness()));

		TourList result = new TourList();

		for (int i = 0; i < Population.getInstance().getNumOfParents(); i++) {
			result.add((Tour) Utilities.getInstance().deepCopy(tourList.get(i)));
		}

		return result;
	}

	private TourList fitness(TourList tourList) {
		Collections.sort(tourList, (c1, c2) -> Double.compare(c1.getFitness(), c2.getFitness()));

		TourList tmpTourList = Utilities.getInstance().deepCopy(tourList);
		TourList result = new TourList();

		double cumulatedFitness = Population.getInstance().getCumulatedFitness();

		while (result.size() < Population.getInstance().getNumOfParents() && !tmpTourList.isEmpty()) {
			double random = Utilities.getInstance().getRandom(0, 1);

			for (Tour tour : tmpTourList) {
				// fitness => shortest distance is better
				double p = 1 - (tour.getFitness() / cumulatedFitness);

				if (random <= p) {
					result.add(tour);
					tmpTourList.remove(tour);
					break;
				}
			}
		}

		return result;
	}

	private TourList rank(TourList tourList) {
		Collections.sort(tourList, (c1, c2) -> Double.compare(c1.getFitness(), c2.getFitness()));

		TourList tmpTourList = Utilities.getInstance().deepCopy(tourList);
		TourList result = new TourList();

		int cumulatedRank = 0;
		for (int i = 0; i < tmpTourList.size(); i++) {
			cumulatedRank += (i + 1);
		}

		while (result.size() < Population.getInstance().getNumOfParents() && !tmpTourList.isEmpty()) {
			double random = Utilities.getInstance().getRandom(0, 1);

			for (int j = 0; j < tmpTourList.size(); j++) {
				Tour tour = tmpTourList.get(j);
				int rank = tourList.indexOf(tour) + 1;

				// rank => shortest distance is better
				double p = rank / cumulatedRank;

				if (random <= p) {
					result.add(tour);
					tmpTourList.remove(tour);
					break;
				}
			}
		}

		return result;
	}

	private TourList tournament(TourList tourList) {
		Collections.sort(tourList, (c1, c2) -> Double.compare(c1.getFitness(), c2.getFitness()));

		TourList tmpTourList = Utilities.getInstance().deepCopy(tourList);
		TourList result = new TourList();

		double cumulatedFitness = Population.getInstance().getCumulatedFitness();

		while (result.size() < Population.getInstance().getNumOfParents() && !tmpTourList.isEmpty()) {
			int c1 = Utilities.getInstance().generateRandom(0, tmpTourList.size() - 1);
			int c2 = Utilities.getInstance().generateRandom(0, tmpTourList.size() - 1);

			double p1 = 1 - (tmpTourList.get(c1).getFitness() / cumulatedFitness);
			double p2 = 1 - (tmpTourList.get(c2).getFitness() / cumulatedFitness);

			double max = p1;
			double min = p2;
			if (p2 > max) {
				max = p2;
				min = p1;
			}

			int tourIndex = c1;
			double cumulatedProbability = max + min;
			double chanceToWin = max / cumulatedProbability;
			double random = Utilities.getInstance().getRandom(0, 1);

			if (random <= chanceToWin && max == p2) {
				tourIndex = c2;
			} else if (min == p2) {
				tourIndex = c2;
			}

			result.add(tmpTourList.get(tourIndex));
			tmpTourList.remove(tourIndex);
		}

		return result;
	}

	@Override
	public String toString() {
		return getName();
	}
}
