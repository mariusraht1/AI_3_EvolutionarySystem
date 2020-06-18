package application.model;

import java.util.ArrayList;
import java.util.List;

import application.Utilities;

/**
 * @author mraht
 * Switch the 20%-worst tours with mutations of the rest tours
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
	
	// TODO Mutate by switching 2 cities
	public void strategy1() {
		List<Tour> tours = Tourmanager.getInstance().orderByTotalDistance();
		
		for(int i = 0; i < tours.size() * 0.20; i++) {
			
		}
		
		
		
		
	}
	
	// TODO Mutating by switching cities between tours?
	public void strategy2(List<Tour> tours) {
		Tour t = new Tour(new ArrayList<City>());

		Tourmanager.getInstance().orderByTotalDistance();
		
	}
}
