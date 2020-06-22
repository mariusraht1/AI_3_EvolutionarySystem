package application.strategy;

import java.util.List;

import application.Utilities;
import application.model.City;
import application.model.Tour;

public enum MutationStrategy {
	SWITCH_2_CITIES_FROM_RANDOM_BEST_TOUR("Switch 2 cities from random best tour");

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private MutationStrategy(String name) {
		setName(name);
	}
	
	public void execute(List<Tour> tours) {
		switch(this) {
		case SWITCH_2_CITIES_FROM_RANDOM_BEST_TOUR:
			switch_2_cities_from_random_best_tour(tours);
			break;
		}
	}
	
	private void switch_2_cities_from_random_best_tour(List<Tour> tours) {
		for (int i = 0; i < tours.size() * 0.20; i++) {
			int random = Utilities.getInstance().generateRandom(0, (int) (tours.size() * 0.80) - 1);
			Tour betterTour = new Tour(tours.get(random));
			Tour worseTour = tours.get(tours.size() - 1 - i);

			int c1 = Utilities.getInstance().generateRandom(0, betterTour.getCities().size() - 1);
			int c2 = Utilities.getInstance().generateRandom(0, betterTour.getCities().size() - 1);

			City tmpCity = betterTour.getCities().get(c1);
			betterTour.getCities().set(c1, betterTour.getCities().get(c2));
			betterTour.getCities().set(c2, tmpCity);

			worseTour.setCities(betterTour.getCities());
		}
	}

	@Override
	public String toString() {
		return getName();
	}
}
