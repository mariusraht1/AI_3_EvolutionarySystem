package application.strategy;

import java.util.List;

import application.model.Tour;

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
	
	public List<Tour> execute(List<Tour> tours) {
		switch(this) {
		case ONLY_CHILDREN:
			tours = only_children(tours);
			break;
		case PARENTS_AND_CHILDREN:
			tours = parents_and_children(tours);
			break;
		}
		
		return tours;
	}

	private List<Tour> only_children(List<Tour> tours) {
		// NEW Implement only_children
		
		return tours;
	}
	
	private List<Tour> parents_and_children(List<Tour> tours) {
		// NEW Implement parents_and_children
		
		return tours;
	}

	@Override
	public String toString() {
		return getName();
	}
}
