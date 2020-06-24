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

	public TourList execute(TourList tourList) {
		switch (this) {
		case INVERSION:
			tourList = inversion(tourList);
			break;
		case SWAPPING:
			tourList = swapping(tourList);
			break;
		}

		return tourList;
	}

	private TourList inversion(TourList tourList) {
		TourList result = Population.getInstance().getPercentile(tourList);

		for (Tour tour : result) {
			int r1 = Utilities.getInstance().generateRandom(0, tour.getCityList().size() - 1);
			int r2 = Utilities.getInstance().generateRandom(0, tour.getCityList().size() - 1);

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

		return result;
	}

	private TourList swapping(TourList tourList) {
		TourList result = Population.getInstance().getPercentile(tourList);

		for (Tour tour : result) {
			int r1 = Utilities.getInstance().generateRandom(0, tour.getCityList().size() - 1);
			int r2 = Utilities.getInstance().generateRandom(0, tour.getCityList().size() - 1);

			City tmpCity = new City(tour.getCityList().get(r1));
			tour.getCityList().set(r1, tour.getCityList().get(r2));
			tour.getCityList().set(r2, tmpCity);
		}

		return result;
	}

	@Override
	public String toString() {
		return getName();
	}
}
