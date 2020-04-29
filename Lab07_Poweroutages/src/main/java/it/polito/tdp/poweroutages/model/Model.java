package it.polito.tdp.poweroutages.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.poweroutages.DAO.PowerOutageDAO;

public class Model {

	PowerOutageDAO podao;
	//dati in input
	private List<PowerOutage> blackout;
	private Integer YEARS;
	private Integer HOURS;
	
	// variabili per la soluzione
	private List<PowerOutage> bestSoluzione; 
	private Integer bestPersoneCoinvolte;
	private int yearsSoluzione;
	private int hoursSoluzine;
	
	//Per approccio 1
	int ore;
	int anni;
	int numPersone;
	
	public Model() {
		podao = new PowerOutageDAO();
	}

	public List<PowerOutage> getWorstCase(Nerc n, Integer years, Integer hours) {
		blackout = podao.getBlackout(n);
		bestPersoneCoinvolte = 0;
		bestSoluzione = null;
		yearsSoluzione = 0;
		hoursSoluzine = 0;
		this.YEARS = years;
		this.HOURS = hours;
		//
		ore = 0;
		anni = 0;
		numPersone = 0;
		
		if(blackout.size()==0) {
			return bestSoluzione;
		}

		List<PowerOutage> parziale = new ArrayList<>();

		cerca(parziale, 0);
		
		return bestSoluzione;
	}

	private void cerca(List<PowerOutage> parziale, int L) {
		
		anni = diffAnni(parziale);
		//int ore = sommaOre(parziale); da usare con APPROCCIO 2
		if (anni > YEARS || ore > HOURS) {
			return;
		}
	
		//int numPersone = calcolaPersone(parziale); da usare con approccio 2
		if (numPersone > bestPersoneCoinvolte) {
			bestSoluzione = new LinkedList<>(parziale);
			hoursSoluzine = ore;
			yearsSoluzione = anni;
			bestPersoneCoinvolte = numPersone;
		}

		if (L == blackout.size()) {
			return;
		}

		// 1) generiamo i sotto-problemi
		// provo ad aggiungere il blackout, le ore e le persone
		parziale.add(blackout.get(L));
		ore += blackout.get(L).getnOreDisservizio();
		numPersone += blackout.get(L).getPersoneCoinvolte();
		
		cerca(parziale, L + 1);
		
		parziale.remove(blackout.get(L));
		ore -= blackout.get(L).getnOreDisservizio();
		numPersone -= blackout.get(L).getPersoneCoinvolte();
		
		// provo a non aggiungerlo
		cerca(parziale, L + 1);
		
		
		/* 2) approccio 2
		for(PowerOutage p: blackout) {
			if(!parziale.contains(p)) {
			parziale.add(p);
			cerca(parziale, L + 1);
			parziale.remove(p);
			}
		}*/
		
	}
	/*
	private int calcolaPersone(List<PowerOutage> parziale) {
		int somma = 0;
		for (PowerOutage p : parziale)
			somma += p.getPersoneCoinvolte();
		return somma;
	}
	*/

	/*
	private int sommaOre(List<PowerOutage> parziale) {
		int somma = 0;
		for (PowerOutage p : parziale)
			somma += p.getnOreDisservizio();
		return somma;
	}*/
	

	private int diffAnni(List<PowerOutage> parziale) {
		int differenza = 0;
		if(parziale.size()>1) {
		differenza = parziale.get(parziale.size() - 1).getInizio().getYear()
				- parziale.get(0).getInizio().getYear();
		}
		return differenza;
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList();
	}
	public Integer getBestPersoneCoinvolte() {
		return bestPersoneCoinvolte;
	}
	public int getYearsSoluzione() {
		return yearsSoluzione;
	}

	public int getHoursSoluzine() {
		return hoursSoluzine;
	}

}
