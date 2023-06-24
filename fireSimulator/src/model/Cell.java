package model;

import java.util.Random;

public class Cell {
	
	private Coord coord;
	private Status status;
	private int nbrTarg; // nbr cells targetting this cell
	
	// Constructor
	public Cell(Coord coord) {
		this.coord = coord;
		this.status = Status.NEUTRAL;
		this.nbrTarg = 0;
		
	}
	
	// Methods
	@Override
	public String toString() {
		return "Etat " + this.getStatus() + " : (" + this.coord.x + ", " + this.coord.y + ")";
	}
	
	
	// Getters & Setters
	public Status getStatus() {
		return this.status;
	}
	
	public void setStatus(Status newStatus) {
		this.status = newStatus;
	}
	
	// Methods
	
	public Boolean isTargOk() {
		Boolean ret = false;
		if(this.status == Status.NEUTRAL) {
			this.setStatus(Status.TARGETED);
			this.nbrTarg+=1;
			ret = true;
		}
		return ret;
	}
	
	public void updateHimself(int prob) {
		if(this.status == Status.ONFIRE) {
			this.setStatus(Status.ASH);
		}
		else if(this.status == Status.TARGETED) {
			Random rand = new Random();
			Status statusEnd = Status.NEUTRAL;
			
			for(int i = 0; i < this.nbrTarg; i++) {
				int nbrRand = rand.nextInt(100);
				if(nbrRand<prob) {
					statusEnd = Status.ONFIRE;
				}
			}
			this.setStatus(statusEnd);
		}
	}
}
