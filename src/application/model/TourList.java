package application.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TourList extends ArrayList<Tour> implements Serializable {
	private static final long serialVersionUID = 1L;

	public TourList(List<Tour> tourList) {
		this.addAll(tourList);
	}

	public TourList() {
	}
}
