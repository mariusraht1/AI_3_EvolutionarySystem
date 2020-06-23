package application.model;

import java.io.Serializable;

public class City implements Serializable {
	private static final long serialVersionUID = 1L;
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
		this.name = name;
		this.x = x;
		this.y = y;
	}

	public City(City city) {
		this.name = city.getName();
		this.x = city.getX();
		this.y = city.getY();
	}
}
