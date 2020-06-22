package application.strategy;

public enum ReplacementStrategy {
	ONLY_CHILDREN("Auswahl der N-besten Touren aus Kind-Touren"),
	PARENTS_AND_CHILDREN("Auswahl der N-besten Touren aus Eltern- und Kind-Touren");

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private ReplacementStrategy(String name) {
		setName(name);
	}
	
	public void execute() {
		switch(this) {
		case ONLY_CHILDREN:
			only_children();
			break;
		case PARENTS_AND_CHILDREN:
			parents_and_children();
			break;
		}
	}

	private void only_children() {
		// NEW Implement only_children
		
	}
	
	private void parents_and_children() {
		// NEW Implement parents_and_children
		
	}

	@Override
	public String toString() {
		return getName();
	}
}
