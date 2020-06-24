package application.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CityList extends ArrayList<City> implements Serializable {
	private static final long serialVersionUID = 1L;

	public CityList(List<City> cityList) {
		this.addAll(cityList);
	}

	public CityList() {
	}
}
