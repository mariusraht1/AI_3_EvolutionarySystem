package application.strategy;

public enum SelectionStrategy {
	BEST("Bestenselektion"), FITNESS("Fitnessproportionale Selektion"), RANK("Rangselektion"), TOURNAMENT("Turnierselektion");

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
	
	public void execute() {
		switch(this) {
		case BEST:
			best();
			break;
		case FITNESS:
			fitness();
			break;
		case RANK:
			rank();
			break;
		case TOURNAMENT:
			tournament();
			break;
		}
	}
	
	private void best() {
		// NEW Implement best
		
	}
	
	private void fitness() {
		// NEW Implement fitness
		
	}
	
	private void rank() {
		// NEW Implement rank
		
	}
	
	private void tournament() {
		// NEW Implement tournament
		
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
