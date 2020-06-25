package application.strategy;

import application.Utilities;
import application.model.City;
import application.model.Population;
import application.model.Tour;
import application.model.TourList;

public enum MutationStrategy {
	SWAPPING("2 zufällige Städte vertauschen"), INVERSION("Sequenz zwischen 2 zufälligen Punkten spiegeln");

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private MutationStrategy(String name) {
		setName(name);
	}

	public TourList execute(TourList childrenTourList) {
		TourList mutatedTourList = new TourList();

		switch (this) {
		case INVERSION:
			mutatedTourList = inversion(childrenTourList);
			break;
		case SWAPPING:
			mutatedTourList = swapping(childrenTourList);
			break;
		}

		return addMissingTours(childrenTourList, mutatedTourList);
	}

	private TourList inversion(TourList childrenTourList) {
		TourList mutatedTourList = getPercentile(childrenTourList);

		for (Tour tour : mutatedTourList) {
			int r1 = Utilities.getInstance().getRandom(0, tour.getCityList().size() - 1);
			int r2 = Utilities.getInstance().getRandom(0, tour.getCityList().size() - 1);

			// Define termination condition
			int stop = 0; // r2 > r1
			if (r1 < r2) {
				stop = 1; // r2 <= r1
			} else if (r1 > r2) {
				stop = 2; // r2 >= r1
			}

			boolean isActive = true;
			while (isActive) {
				r1++;
				r2--;

				// Switch r1 with r2
				City tmpCity = tour.getCityList().get(r1);
				tour.getCityList().set(r1, tour.getCityList().get(r2));
				tour.getCityList().set(r2, tmpCity);

				if ((stop == 0 && r2 > r1) || (stop == 1 && r2 <= r1) || (stop == 2 && r2 >= r1)) {
					isActive = false;
				}
			}
		}

		return mutatedTourList;
	}

	private TourList swapping(TourList childrenTourList) {
		TourList mutatedTourList = getPercentile(childrenTourList);

		for (Tour tour : mutatedTourList) {
			int r1 = Utilities.getInstance().getRandom(0, tour.getCityList().size() - 1);
			int r2 = Utilities.getInstance().getRandom(0, tour.getCityList().size() - 1);

			City tmpCity = new City(tour.getCityList().get(r1));
			tour.getCityList().set(r1, tour.getCityList().get(r2));
			tour.getCityList().set(r2, tmpCity);
		}

		return mutatedTourList;
	}

	private TourList getPercentile(TourList tourList) {
		TourList tmptourList = Utilities.getInstance().deepCopy(tourList);
		TourList mutabletourList = new TourList();

		for (Tour tour : tmptourList) {
			double p = Utilities.getInstance().getRandom(0.0, 1.0);

			if (p <= Population.getInstance().getMutationProbability()) {
				mutabletourList.add(tour);
			}
		}

		return mutabletourList;
	}
	
	private TourList addMissingTours(TourList childrenTourList, TourList mutatedTourList) {
		for (Tour mutatedTour : mutatedTourList) {
			for (int i = 0; i < childrenTourList.size(); i++) {
				if (mutatedTour.getName().equals(childrenTourList.get(i).getName())) {
					childrenTourList.set(i, mutatedTour);
					break;
				}
			}
		}

		return childrenTourList;
	}

	@Override
	public String toString() {
		return getName();
	}
}
