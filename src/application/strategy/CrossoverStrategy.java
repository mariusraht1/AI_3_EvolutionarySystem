package application.strategy;

import application.Utilities;
import application.model.City;
import application.model.CityList;
import application.model.Population;
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
		Population.getInstance().sort(tourList);
		
		switch (this) {
		case ONE_POINT:
			tourList = one_point(tourList);
			break;
		}

		return tourList;
	}

	public TourList one_point(TourList tourList) {
		TourList childrenTourList = new TourList();
		TourList parentTourList = new TourList(tourList);

		int start = Utilities.getInstance().getRandom(1, Population.getInstance().getNumOfCities() - 2);
		int end = Population.getInstance().getNumOfCities() - 1;

		for (Tour parentTour : parentTourList) {
			TourList matingTourList = Population.getInstance().mate(parentTourList, parentTour);

			while (!matingTourList.isEmpty()) {
				Tour fatherTour = parentTour;
				Tour motherTour = matingTourList.get(0);

				for (int i = 0; i < 2; i++) {
					CityList parentPart1CityList = fatherTour.getCityList();
					CityList parentPart2CityList = motherTour.getCityList();

					if (i == 1) {
						CityList tmpParentPartCityList = parentPart1CityList;
						parentPart1CityList = parentPart2CityList;
						parentPart2CityList = tmpParentPartCityList;
					}

					CityList childCityList = new CityList();
					for (int j = 0; j < start; j++) {
						childCityList.add(parentPart1CityList.get(j));
					}

					int j = start;
					while (j < end) {
						City nextCity = parentPart1CityList.get(j);
						int indexOfNext = parentPart2CityList.indexOf(nextCity);

						int k = 0;
						for (k = j + 1; k < parentPart1CityList.size(); k++) {
							if (parentPart2CityList.indexOf(parentPart1CityList.get(k)) < indexOfNext) {
								nextCity = parentPart1CityList.get(k);
								indexOfNext = parentPart2CityList.indexOf(parentPart1CityList.get(k));
							}
						}

						childCityList.add(nextCity);
						j++;
					}

					childCityList.add(parentPart1CityList.get(end));
					
					int tourNumber = Population.getInstance().getNumOfTours() + childrenTourList.size() + 1;
					childrenTourList.add(new Tour("Tour " + String.valueOf(tourNumber), childCityList));
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
