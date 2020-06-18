package application.model;

import java.util.ArrayList;
import java.util.List;

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

	// FIX Minimum distance changes and gets worse
	// Mutate by switching 2 cities
	public void strategy_1() {
		List<Tour> tours = Tourmanager.getInstance().orderByTotalDistance();

		for (int i = 0; i < tours.size() * 0.20; i++) {
			int random = Utilities.getInstance().generateRandom(0, (int)(tours.size() * 0.80));
			Tour tour = new Tour(tours.get(random));
			
			int c1 = Utilities.getInstance().generateRandom(0, tour.getCities().size() - 1);
			int c2 = Utilities.getInstance().generateRandom(0, tour.getCities().size() - 1);

			City tmpCity = tour.getCities().get(c1);
			tour.getCities().set(c1, tour.getCities().get(c2));
			tour.getCities().set(c2, tmpCity);
			
			tours.get(tours.size() - 1 - i).setCities(tour.getCities());
		}
		
		Tourmanager.getInstance().setTours(tours);
	}

	// TODO Mutating by switching cities between tours?
	public void strategy_2(List<Tour> tours) {
		Tour t = new Tour(new ArrayList<City>());

		Tourmanager.getInstance().orderByTotalDistance();

	}
}
