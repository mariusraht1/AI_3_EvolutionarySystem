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
		Tour firstParentTour = parentTourList.get(0);

		int start = Utilities.getInstance().generateRandom(1, Population.getInstance().getNumOfCities() - 2);
		int end = Population.getInstance().getNumOfCities() - 1;

		while (!parentTourList.isEmpty()) {
			Tour fatherTour = parentTourList.get(0);
			Tour motherTour = parentTourList.get(1);

			if (parentTourList.size() == 1) {
				fatherTour = firstParentTour;
				motherTour = parentTourList.get(0);
			}

			for (int i = 0; i < 2; i++) {
				CityList parentPart1CityList = fatherTour.getCityList();
				CityList parentPart2CityList = motherTour.getCityList();

				if (i == 1) {
					CityList tmpParentPartCityList = parentPart1CityList;
					parentPart1CityList = parentPart2CityList;
					parentPart2CityList = tmpParentPartCityList;
				}

				CityList childCityList = new CityList();
				for (int j = 0; i < start; j++) {
					childCityList.add(parentPart1CityList.get(j));
				}

				int j = start;
				while (start < end) {
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

				int tourNumber = Population.getInstance().getNumOfTours() + childrenTourList.size();
				childrenTourList.add(new Tour("Tour " + String.valueOf(tourNumber), childCityList));
			}

			parentTourList.remove(0);
			parentTourList.remove(1);
		}

		return childrenTourList;
	}

	@Override
	public String toString() {
		return getName();
	}
}
