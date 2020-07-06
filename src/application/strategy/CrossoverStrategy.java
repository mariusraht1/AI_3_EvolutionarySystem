package application.strategy;

import java.util.ArrayList;
import java.util.HashMap;

import application.Log;
import application.Utilities;
import application.model.City;
import application.model.CityList;
import application.model.Evolution;
import application.model.Tour;
import application.model.TourList;

public enum CrossoverStrategy {
	ONE_POINT("1-Point Crossover"), EDGE_OPERATOR("Edge Recombination Operator");

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

				switch (this) {
				case EDGE_OPERATOR:
					edge_operator(childrenTourList, fatherTour, motherTour);
					break;
				case ONE_POINT:
					one_point(childrenTourList, fatherTour, motherTour);
					break;
				}
			}
		}

		Log.getInstance().logCities(childrenTourList);

		return childrenTourList;
	}

	private void edge_operator(TourList childrenTourList, Tour fatherTour, Tour motherTour) {
		HashMap<City, CityList> neighbourCityList = new HashMap<City, CityList>();

		for (City city : fatherTour.getCityList()) {
			CityList neighbours = new CityList();

			for (int i = 0; i < 2; i++) {
				Tour parentPart1Tour = fatherTour;
				Tour parentPart2Tour = motherTour;

				if (i == 1) {
					Tour tmpParentPartTour = parentPart1Tour;
					parentPart1Tour = parentPart2Tour;
					parentPart2Tour = tmpParentPartTour;
				}

				int indexInParentPart1Tour = parentPart1Tour.getCityList().indexOf(city);

				// 0 => Get right neighbour
				// 1 => Get left neighbour
				for (int j = 0; j < 2; j++) {
					int indexNeighbour = indexInParentPart1Tour + 1;
					if (j == 1) {
						indexNeighbour = indexInParentPart1Tour - 1;
					}

					if (indexNeighbour < 0) {
						indexNeighbour = parentPart1Tour.getCityList().size() - 1;
					} else if (indexNeighbour >= parentPart1Tour.getCityList().size()) {
						indexNeighbour = 0;
					}

					if (!neighbours.contains(parentPart1Tour.getCityList().get(indexNeighbour))) {
						neighbours.add(parentPart1Tour.getCityList().get(indexNeighbour));
					}
				}
			}

			neighbourCityList.put(city, neighbours);
		}

		for (int i = 0; i < 2; i++) {
			Tour parentPart1Tour = fatherTour;
			Tour parentPart2Tour = motherTour;

			if (i == 1) {
				Tour tmpParentPartTour = parentPart1Tour;
				parentPart1Tour = parentPart2Tour;
				parentPart2Tour = tmpParentPartTour;
			}
			
			HashMap<City, CityList> tmpNeighbourCityList = Utilities.getInstance().deepCopy(neighbourCityList);
			ArrayList<City> keys = new ArrayList<City>(tmpNeighbourCityList.keySet());
			CityList childCityList = new CityList();
			boolean firstRound = true;

			while (childCityList.size() < parentPart1Tour.getCityList().size()) {
				City childCity = parentPart1Tour.getCityList().get(0);
				if (firstRound) {
					firstRound = false;
				} else {
					int minNumOfNeighbours = tmpNeighbourCityList.get(childCity).size();
					for (int j = 1; j < keys.size(); j++) {
						if (tmpNeighbourCityList.get(keys.get(j)).size() < minNumOfNeighbours) {
							minNumOfNeighbours = tmpNeighbourCityList.get(keys.get(j)).size();
						}
					}

					ArrayList<Integer> cityWithMinNumOfNeighbours = new ArrayList<Integer>();
					for (int j = 0; j < keys.size(); j++) {
						if (tmpNeighbourCityList.get(keys.get(j)).size() == minNumOfNeighbours) {
							cityWithMinNumOfNeighbours.add(j);
						}
					}

					double r = Utilities.getInstance().getRandom(0.0, 1.0);
					double stepSize = 1.0 / cityWithMinNumOfNeighbours.size();
					double start = 0.0;
					double end = stepSize;
					
					for (int j = 0; j < cityWithMinNumOfNeighbours.size(); j++) {
						int keyIndex = cityWithMinNumOfNeighbours.get(j);
						if (r >= start && r <= end) {
							childCity = keys.get(cityWithMinNumOfNeighbours.get(keyIndex));
							break;
						}

						start += stepSize;
						end += stepSize;
					}
				}

				childCityList.add(childCity);
				tmpNeighbourCityList.remove(childCity);
				keys.remove(childCity);

				for (int j = 0; j < tmpNeighbourCityList.size(); j++) {
					City key = keys.get(j);
					tmpNeighbourCityList.get(key).remove(childCity);
				}
			}

			int tourNumber = Evolution.getInstance().getNumOfTours() + childrenTourList.size() + 1;
			Tour childTour = new Tour(tourNumber, childCityList);
			Log.getInstance().logCities(childTour);
			childrenTourList.add(childTour);
		}
	}

	private void one_point(TourList childrenTourList, Tour fatherTour, Tour motherTour) {
		int start = Utilities.getInstance().getRandom(0, Evolution.getInstance().getNumOfCities() - 2);
		int end = Evolution.getInstance().getNumOfCities() - 1;
		Log.getInstance().add("Crossover: From " + start + " to " + end);

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
	}

	@Override
	public String toString() {
		return getName();
	}
}
