package application.strategy;

import application.Log;
import application.model.Evolution;
import application.model.Tour;
import application.model.TourList;
import library.GeneralUtilities;
import library.MathManager;

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
		Log.getInstance().logHeader("Selection");

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

		tourList.sort();
		Log.getInstance().logCities(tourList);

		return tourList;
	}

	private TourList best(TourList tourList) {
		TourList result = new TourList();

		for (int i = 0; i < Evolution.getInstance().getNumOfParents(); i++) {
			result.add(GeneralUtilities.getInstance().deepCopy(tourList.get(i)));
		}

		return result;
	}

	private TourList fitness(TourList tourList) {
		TourList tmpTourList = GeneralUtilities.getInstance().deepCopy(tourList);
		TourList result = new TourList();

		double cumulatedFitness = tmpTourList.getTotalFitness();

		while (result.size() < Evolution.getInstance().getNumOfParents() && !tmpTourList.isEmpty()) {
			double random = MathManager.getInstance().getRandom(0.0, 1.0);

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
		TourList tmpTourList = GeneralUtilities.getInstance().deepCopy(tourList);
		TourList result = new TourList();

		double cumulatedRank = 0;
		for (double i = 0; i < tmpTourList.size(); i++) {
			cumulatedRank += (i + 1);
		}

		while (result.size() < Evolution.getInstance().getNumOfParents() && !tmpTourList.isEmpty()) {
			double random = MathManager.getInstance().getRandom(0.0, 1.0);

			for (Tour tour : tmpTourList) {
				double rank = tourList.indexOf(tour) + 1;

				// rank => shortest distance is better
				double p = 1 - (rank / cumulatedRank);

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
		TourList tmpTourList = GeneralUtilities.getInstance().deepCopy(tourList);
		TourList result = new TourList();

		while (result.size() < Evolution.getInstance().getNumOfParents() && !tmpTourList.isEmpty()) {
			int t1 = MathManager.getInstance().getRandom(0, tmpTourList.size() - 1);
			int t2 = MathManager.getInstance().getRandom(0, tmpTourList.size() - 1);

			double random = MathManager.getInstance().getRandom(0.0, 1.0);

			double cumulatedFitness = tmpTourList.get(t1).getFitness() + tmpTourList.get(t2).getFitness();
			double p1 = tmpTourList.get(t1).getFitness() / cumulatedFitness;
			double p2 = tmpTourList.get(t2).getFitness() / cumulatedFitness;

			int tourIndex = t1;
			if ((p2 > p1 && random >= p2) || (p2 < p1 && random <= p2)) {
				tourIndex = t2;
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
