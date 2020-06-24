package application.strategy;

import application.Utilities;
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
		// NEW Implement CrossoverStrategy execution

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
		Tour firstParentTour = parentTourList.get(0); // TODO If odd number of tours choose firstParentTour for last entry

		while (!parentTourList.isEmpty()) {
			Tour father = parentTourList.get(0);
			Tour mother = parentTourList.get(1);

			int start = Utilities.getInstance().generateRandom(1, Population.getInstance().getNumOfCities() - 2);
			int end = Population.getInstance().getNumOfCities() - 1;
			
			CityList child1 = new CityList();
			CityList child2 = new CityList();
			
			for(int i = 0; i < start; i++) {
				child1.add(father.getCityList().get(i));
				child2.add(mother.getCityList().get(i));
			}
			
			// NEW one_point: Determine location of father/mother city at i
			for(int i = start; i < end; i++) {
				
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
