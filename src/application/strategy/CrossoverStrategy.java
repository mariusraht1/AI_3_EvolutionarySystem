package application.strategy;

public enum CrossoverStrategy {
	SWITCH_2_CITIES_FROM_RANDOM_BEST_TOUR("Switch 2 cities from random best tour");

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
	
	@Override
	public String toString() {
		return getName();
	}
}
