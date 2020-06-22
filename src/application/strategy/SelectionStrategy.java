package application.strategy;

import java.util.List;

import application.model.Tour;

public enum SelectionStrategy {
	BEST("Bestenselektion"), FITNESS("Fitnessproportionale Selektion"), RANK("Rangselektion"),
	TOURNAMENT("Turnierselektion");

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private SelectionStrategy(String name) {
		setName(name);
	}

	public List<Tour> execute(List<Tour> tours) {
		switch (this) {
		case BEST:
			tours = best(tours);
			break;
		case FITNESS:
			tours = fitness(tours);
			break;
		case RANK:
			tours = rank(tours);
			break;
		case TOURNAMENT:
			tours = tournament(tours);
			break;
		}
		
		return tours;
	}

	private List<Tour> best(List<Tour> tours) {
		// NEW Implement best

		return tours;
	}

	private List<Tour> fitness(List<Tour> tours) {
		// NEW Implement fitness

		return tours;
	}

	private List<Tour> rank(List<Tour> tours) {
		// NEW Implement rank

		return tours;
	}

	private List<Tour> tournament(List<Tour> tours) {
		// NEW Implement tournament

		return tours;
	}

	@Override
	public String toString() {
		return getName();
	}
}
