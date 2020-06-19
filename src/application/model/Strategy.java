package application.model;

public enum Strategy {
	SWITCH_2_CITIES_FROM_RANDOM_BEST_TOUR("Switch 2 cities from random best tour");

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private Strategy(String name) {
		setName(name);
	}
}
