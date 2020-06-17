package application.model;

public class City {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private int x;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	private int y;

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public City(String name, int x, int y) {
		setName(name);
		setX(x);
		setY(y);
	}
}
