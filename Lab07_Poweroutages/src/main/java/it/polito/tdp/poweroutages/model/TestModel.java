package it.polito.tdp.poweroutages.model;

import java.util.List;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		System.out.println(model.getNercList());
		
		Nerc n = new Nerc(3,"MAAC");
		List<PowerOutage> blackout = model.getWorstCase(n, 4, 200);
		for(PowerOutage p : blackout )
		System.out.println(p);
	}

}
