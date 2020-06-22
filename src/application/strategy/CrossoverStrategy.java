package application.strategy;

import java.util.List;

import application.model.Tour;

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
	
	public List<Tour> execute(List<Tour> tours) {
		// NEW Implement CrossoverStrategy execution
		
		return tours;
	}

	public List<Tour> placeholder(List<Tour> tours) {
		// NEW Implement CrossoverStrategy execution
		
		return tours;
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
