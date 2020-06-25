package application.strategy;

import application.model.Tour;
import application.model.TourList;

public enum MatingStrategy {
	NEXT_2("Mit den 2 nächstbesten Touren paaren");

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private MatingStrategy(String name) {
		setName(name);
	}

	public TourList execute(TourList tourList, Tour currentTour) {
		switch (this) {
		case NEXT_2:
			tourList = next_2(tourList, currentTour);
			break;
		}

		return tourList;
	}

	private TourList next_2(TourList parentTourList, Tour currentTour) {
		TourList result = new TourList();
		int i = parentTourList.indexOf(currentTour);

		if (i < parentTourList.size() - 3) {
			result.add(parentTourList.get(i + 1));
			result.add(parentTourList.get(i + 2));
		} else if (i < parentTourList.size() - 2) {
			result.add(parentTourList.get(i + 1));
		}

		return result;
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
