package application.strategy;

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
	
	public TourList execute(TourList tourList) {
		switch(this) {
		case ONLY_CHILDREN:
			tourList = only_children(tourList);
			break;
		case PARENTS_AND_CHILDREN:
			tourList = parents_and_children(tourList);
			break;
		}
		
		return tourList;
	}

	private TourList only_children(TourList tourList) {
		// NEW Implement only_children
		
		return tourList;
	}
	
	private TourList parents_and_children(TourList tourList) {
		// NEW Implement parents_and_children
		
		return tourList;
	}

	@Override
	public String toString() {
		return getName();
	}
}
