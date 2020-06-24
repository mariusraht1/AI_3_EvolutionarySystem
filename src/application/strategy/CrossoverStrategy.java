package application.strategy;

import application.model.TourList;

public enum CrossoverStrategy {
	PLACEHOLDER("---");

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

		return tourList;
	}

	public TourList placeholder(TourList tourList) {
		// NEW Implement CrossoverStrategy execution

		return tourList;
	}

	@Override
	public String toString() {
		return getName();
	}
}
