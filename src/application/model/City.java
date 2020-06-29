package application.model;

import java.io.Serializable;

public class City implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public City(int id, int x, int y) {
		this.id = id;

		if (id < 10 && id > 0) {
			this.name = "Stadt 00" + id;
		} else if (id < 100 && id > 0) {
			this.name = "Stadt 0" + id;
		} else {
			this.name = "Stadt " + id;
		}

		this.x = x;
		this.y = y;
	}

	public City(City city) {
		this.id = city.getId();
		this.name = city.getName();
		this.x = city.getX();
		this.y = city.getY();
	}

	@Override
	public boolean equals(Object object) {
		boolean equals = false;

		if (object != null && object instanceof City) {
			City city = (City) object;
			equals = this.id == city.getId() && this.x == city.getX() && this.y == city.getY();
		}

		return equals;
	}
}
