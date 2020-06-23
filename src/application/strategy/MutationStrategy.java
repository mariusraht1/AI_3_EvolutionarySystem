package application.strategy;

import java.util.List;

import application.Utilities;
import application.model.City;
import application.model.Population;
import application.model.Tour;

public enum MutationStrategy {
	SWAPPING("2 zuf�llige St�dte vertauschen"), INVERSION("Sequenz zwischen 2 zuf�lligen Punkten spiegeln");

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

	public List<Tour> execute(List<Tour> tours) {
		switch (this) {
		case INVERSION:
			tours = inversion(tours);
			break;
		case SWAPPING:
			tours = swapping(tours);
			break;
		}

		return tours;
	}

	private List<Tour> inversion(List<Tour> tours) {
		List<Tour> mutatedTours = Population.getInstance().getPercentile(tours);

		for (Tour tour : mutatedTours) {
			int r1 = Utilities.getInstance().generateRandom(0, tour.getCities().size() - 2);
			int r2 = Utilities.getInstance().generateRandom(r1 + 1, tour.getCities().size() - 1);

			int j = r1;
			for (int i = r2; i >= r1; i--) {
				if (i == j) {
					break;
				}

				// i mit j vertauschen
				City tmpCity = tour.getCities().get(i);
				tour.getCities().set(i, tour.getCities().get(j));
				tour.getCities().set(j, tmpCity);
			}
		}

		return mutatedTours;
	}

	private List<Tour> swapping(List<Tour> tours) {
		List<Tour> mutatedTours = Population.getInstance().getPercentile(tours);

		for (Tour tour : mutatedTours) {
			int r1 = Utilities.getInstance().generateRandom(0, tour.getCities().size() - 1);
			int r2 = Utilities.getInstance().generateRandom(0, tour.getCities().size() - 1);

			City tmpCity = new City(tour.getCities().get(r1));
			tour.getCities().set(r1, tour.getCities().get(r2));
			tour.getCities().set(r2, tmpCity);
		}

		return mutatedTours;
	}

	@Override
	public String toString() {
		return getName();
	}
}
