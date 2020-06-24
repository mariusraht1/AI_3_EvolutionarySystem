package application.strategy;

import java.util.Collections;

import application.model.Population;
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
		TourList tourList = childrenTourList;

		return selectBest(tourList);
	}

	private TourList parents_and_children(TourList parentTourList, TourList childrenTourList) {
		TourList tourList = parentTourList;
		parentTourList.addAll(childrenTourList);

		return selectBest(tourList);
	}
	
	private TourList selectBest(TourList tourList) {
		TourList result = new TourList();
		
		Collections.sort(tourList, (t1, t2) -> Double.compare(t1.getFitness(), t2.getFitness()));
		
		int i = 0;
		while(i < Population.getInstance().getNumOfTours() && !tourList.isEmpty())
		{
			result.add(tourList.get(i));
			tourList.remove(tourList.get(i));
			
			i++;
		}
		
		return result;
	}

	@Override
	public String toString() {
		return getName();
	}
}
