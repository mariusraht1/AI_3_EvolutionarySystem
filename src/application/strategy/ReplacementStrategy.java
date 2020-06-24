package application.strategy;

import java.util.Collections;

import application.model.TourList;

public enum ReplacementStrategy {
	ONLY_CHILDREN("Auswahl der N-besten Touren aus Kind-Touren"),
	PARENTS_AND_CHILDREN("Auswahl der N-besten Touren aus Eltern- und Kind-Touren");

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private ReplacementStrategy(String name) {
		setName(name);
	}

	public TourList execute(TourList parentTourList, TourList childrenTourList) {
		TourList tourList = null;

		switch (this) {
		case ONLY_CHILDREN:
			tourList = only_children(childrenTourList);
			break;
		case PARENTS_AND_CHILDREN:
			tourList = parents_and_children(parentTourList, childrenTourList);
			break;
		}

		return tourList;
	}

	private TourList only_children(TourList childrenTourList) {
		// NEW Implement only_children: Select N-best children
		TourList result = new TourList();

		TourList tmpTourList = childrenTourList;
		Collections.sort(tmpTourList, (t1, t2) -> Double.compare(t1.getFitness(), t2.getFitness()));

		return result;
	}

	private TourList parents_and_children(TourList parentTourList, TourList childrenTourList) {
		// NEW Implement parents_and_children: Select N-best parents and children
		TourList result = new TourList();
		TourList tmpTourList = parentTourList;
		parentTourList.addAll(childrenTourList);

		Collections.sort(tmpTourList, (t1, t2) -> Double.compare(t1.getFitness(), t2.getFitness()));

		return result;
	}

	@Override
	public String toString() {
		return getName();
	}
}
