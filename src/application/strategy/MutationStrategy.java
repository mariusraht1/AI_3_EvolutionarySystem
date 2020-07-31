package application.strategy;

import application.Log;
import application.model.City;
import application.model.Tour;
import application.model.TourList;
import library.MathManager;

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
		Log.getInstance().logHeader("Mutation");
		TourList mutatedTourList = new TourList();

		switch (this) {
		case INVERSION:
			mutatedTourList = inversion(childrenTourList);
			break;
		case SWAPPING:
			mutatedTourList = swapping(childrenTourList);
			break;
		}

		Log.getInstance().logCities(mutatedTourList);

		return merge(childrenTourList, mutatedTourList);
	}

	private TourList inversion(TourList childrenTourList) {
		TourList mutatedTourList = childrenTourList.getPercentile();

		for (Tour tour : mutatedTourList) {
			int r1 = MathManager.getInstance().getRandom(0, tour.getCityList().size() - 1);
			int r2 = MathManager.getInstance().getRandom(0, tour.getCityList().size() - 1);

			Log.getInstance().add(tour.getName() + ": Zwischen " + r1 + " und " + r2 + " spiegeln.");

			if (r2 >= r1) {
				int n = 0;
				boolean isActive = true;
				while (isActive) {
					r1++;
					r2--;

					if (r1 > tour.getCityList().size() - 1) {
						r1 = 0;
					}

					if (r2 < 0) {
						r2 = tour.getCityList().size() - 1;
					}

					if ((r1 == r2 && n != 0) || r2 == r1 - 1) {
						isActive = false;
					} else if (r1 != r2) {
						Log.getInstance().add("Vertausche Indizes " + r1 + " und " + r2 + " miteinander.");

						// Switch r1 with r2
						City tmpCity = tour.getCityList().get(r1);
						tour.getCityList().set(r1, tour.getCityList().get(r2));
						tour.getCityList().set(r2, tmpCity);
					}

					n++;
				}
			} else {
				Log.getInstance().add("Keine Mutation: Zwischen r1 und r2 gibt es keine weiteren Indizes.");
				continue;
			}
		}

		return mutatedTourList;
	}

	private TourList swapping(TourList childrenTourList) {
		TourList mutatedTourList = childrenTourList.getPercentile();

		for (Tour tour : mutatedTourList) {
			int r1 = MathManager.getInstance().getRandom(0, tour.getCityList().size() - 1);
			int r2 = MathManager.getInstance().getRandom(0, tour.getCityList().size() - 1);

			Log.getInstance().add(tour.getName() + ": Vertausche Indizes " + r1 + " und " + r2 + " miteinander.");

			City tmpCity = new City(tour.getCityList().get(r1));
			tour.getCityList().set(r1, tour.getCityList().get(r2));
			tour.getCityList().set(r2, tmpCity);
		}

		return mutatedTourList;
	}

	private TourList merge(TourList childrenTourList, TourList mutatedTourList) {
		for (Tour mutatedTour : mutatedTourList) {
			for (int i = 0; i < childrenTourList.size(); i++) {
				if (mutatedTour.getId() == childrenTourList.get(i).getId()) {
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
