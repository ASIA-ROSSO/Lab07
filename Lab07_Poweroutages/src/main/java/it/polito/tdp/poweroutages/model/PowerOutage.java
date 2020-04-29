package it.polito.tdp.poweroutages.model;

import java.time.Duration;
import java.time.LocalDateTime;


public class PowerOutage {
	private int id;
	private Nerc nerc;
	private LocalDateTime inizio;
	private LocalDateTime fine;
	private int personeCoinvolte;
	private long nOreDisservizio;
	
	public PowerOutage(int id, Nerc nerc, LocalDateTime inizio, LocalDateTime fine, int personeCoinvolte) {
		super();
		this.id = id;
		this.nerc = nerc;
		this.inizio = inizio;
		this.fine = fine;
		this.personeCoinvolte = personeCoinvolte;
		this.nOreDisservizio = Duration.between(inizio, fine).toHours();
	}

	public long getnOreDisservizio() {
		return nOreDisservizio;
	}

	public void setnOreDisservizio(int nOreDisservizio) {
		this.nOreDisservizio = nOreDisservizio;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Nerc getNerc() {
		return nerc;
	}

	public void setNerc(Nerc nerc) {
		this.nerc = nerc;
	}

	public LocalDateTime getInizio() {
		return inizio;
	}

	public void setInizio(LocalDateTime inizio) {
		this.inizio = inizio;
	}

	public LocalDateTime getFine() {
		return fine;
	}

	public void setFine(LocalDateTime fine) {
		this.fine = fine;
	}

	public int getPersoneCoinvolte() {
		return personeCoinvolte;
	}

	public void setPersoneCoinvolte(int personeCoinvolte) {
		this.personeCoinvolte = personeCoinvolte;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PowerOutage other = (PowerOutage) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return  inizio.getYear()+ " " +inizio + " " + fine + " " + nOreDisservizio + " " +personeCoinvolte;
	}
	
	
	
	
}
