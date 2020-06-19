package application.model;

import java.util.List;

import application.Log;
import application.Utilities;

/**
 * @author mraht Switch the 20%-worst tours with mutations of the rest tours
 */
public class MutationSystem {
	private static MutationSystem instance;

	public static MutationSystem getInstance() {
		if (instance == null) {
			instance = new MutationSystem();
		}

		return instance;
	}

	private MutationSystem() {
	}

	// Mutate by switching 2 cities
	public void strategy_1() {
		List<Tour> tours = Tourmanager.getInstance().getTours();

		for (int i = 0; i < tours.size() * 0.20; i++) {
			int random = Utilities.getInstance().generateRandom(0, (int) (tours.size() * 0.80) - 1);
			Tour betterTour = new Tour(tours.get(random));		
			Tour worseTour = tours.get(tours.size() - 1 - i);

			Log.getInstance().add("Mutate " + betterTour.getName() + " (" + betterTour.getTotalDistance()
					+ ") and replace " + worseTour.getName() + " (" + worseTour.getTotalDistance() + ")");

			int c1 = Utilities.getInstance().generateRandom(0, betterTour.getCities().size() - 1);
			int c2 = Utilities.getInstance().generateRandom(0, betterTour.getCities().size() - 1);

			City tmpCity = betterTour.getCities().get(c1);
			betterTour.getCities().set(c1, betterTour.getCities().get(c2));
			betterTour.getCities().set(c2, tmpCity);

			worseTour.setCities(betterTour.getCities());
		}
	}

	// TODO Mutating by switching cities between tours?
	public void strategy_2(List<Tour> tours) {

	}
}
