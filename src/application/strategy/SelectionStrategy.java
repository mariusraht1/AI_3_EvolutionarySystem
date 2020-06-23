package application.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import application.Utilities;
import application.model.Population;
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
		List<Tour> result = new ArrayList<Tour>();
		int numOfTours = Population.getInstance().getNumOfTours();
		int numOfParents = numOfTours / 2;

		Collections.sort(tours, (c1, c2) -> Double.compare(c1.getFitness(), c2.getFitness()));
		for (int i = 0; i < numOfParents; i++) {
			try {
				result.add((Tour)Utilities.getInstance().deepCopy(tours.get(i)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return result;
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
