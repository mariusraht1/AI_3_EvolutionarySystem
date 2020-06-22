package application.strategy;

import java.util.List;

import application.Utilities;
import application.model.City;
import application.model.Tour;

public enum MutationStrategy {
	SWAPPING("2 zufällige Städte vertauschen"), INVERSION("Sequenz zwischen 2 zufälligen Punkten spiegeln");

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

	public List<Tour> execute(List<Tour> tours) {
		switch (this) {
		case INVERSION:
			tours = inversion(tours);
			break;
		case SWAPPING:
			tours = swapping(tours);
			break;
		}
		
		return tours;
	}
	
	private List<Tour> inversion(List<Tour> tours) {
		
		
		
		
		
		return tours;
	}

	private List<Tour> swapping(List<Tour> tours) {
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
		
		return tours;
	}
	
	private List<Tour> getPercentileOfTours(List<Tour> tours) {
		// NEW 1% probability
		
		
		
		return tours;
	}

	@Override
	public String toString() {
		return getName();
	}
}
