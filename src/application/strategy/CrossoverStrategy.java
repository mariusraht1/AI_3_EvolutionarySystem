package application.strategy;

import application.Log;
import application.Utilities;
import application.model.City;
import application.model.CityList;
import application.model.Evolution;
import application.model.Tour;
import application.model.TourList;

public enum CrossoverStrategy {
	ONE_POINT("1-Punkt Rekombination");

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private CrossoverStrategy(String name) {
		setName(name);
	}

	public TourList execute(TourList tourList) {
		Log.getInstance().logHeader("Crossover");

		switch (this) {
		case ONE_POINT:
			tourList = one_point(tourList);
			break;
		}

		Log.getInstance().logCities(tourList);

		return tourList;
	}

	public TourList one_point(TourList tourList) {
		TourList childrenTourList = new TourList();
		TourList parentTourList = new TourList(tourList);

		for (Tour parentTour : parentTourList) {
			int start = Utilities.getInstance().getRandom(0, Evolution.getInstance().getNumOfCities() - 2);
			int end = Evolution.getInstance().getNumOfCities() - 1;
			Log.getInstance().add("Crossover: From " + start + " to " + end);

			TourList matingTourList = Evolution.getInstance().mate(parentTourList, parentTour);

			while (!matingTourList.isEmpty()) {
				Tour fatherTour = parentTour;
				Tour motherTour = matingTourList.get(0);

				Log.getInstance().add(fatherTour.getName() + " + " + motherTour.getName());
				Log.getInstance().logCities(fatherTour);
				Log.getInstance().logCities(motherTour);

				for (int i = 0; i < 2; i++) {
					Tour parentPart1Tour = fatherTour;
					Tour parentPart2Tour = motherTour;

					if (i == 1) {
						Tour tmpParentPartTour = parentPart1Tour;
						parentPart1Tour = parentPart2Tour;
						parentPart2Tour = tmpParentPartTour;
					}

					CityList childCityList = new CityList();
					for (int j = 0; j < start; j++) {
						childCityList.add(parentPart1Tour.getCityList().get(j));
					}

					CityList remainingCities = new CityList();
					for (int j = start; j <= end; j++) {
						remainingCities.add(parentPart1Tour.getCityList().get(j));
					}

					while (!remainingCities.isEmpty()) {
						City nextCity = remainingCities.get(0);
						int nextCityIndex = parentPart2Tour.getCityList().indexOf(nextCity);

						for (int j = nextCityIndex - 1; j >= 0; j--) {
							City alternativeCity = parentPart2Tour.getCityList().get(j);
							if (remainingCities.contains(alternativeCity)) {
								nextCity = alternativeCity;
							} else if (parentPart1Tour.getCityList().indexOf(alternativeCity) >= start
									&& parentPart1Tour.getCityList().indexOf(alternativeCity) <= end) {
								break;
							}
						}

						childCityList.add(nextCity);
						remainingCities.remove(nextCity);
					}

					for (int j = end + 1; j < parentPart1Tour.getCityList().size(); j++) {
						childCityList.add(parentPart1Tour.getCityList().get(j));
					}

					int tourNumber = Evolution.getInstance().getNumOfTours() + childrenTourList.size() + 1;
					Tour childTour = new Tour(tourNumber, childCityList);
					Log.getInstance().logCities(childTour);
					childrenTourList.add(childTour);
				}

				matingTourList.remove(0);
			}
		}

		return childrenTourList;
	}

	@Override
	public String toString() {
		return getName();
	}
}
