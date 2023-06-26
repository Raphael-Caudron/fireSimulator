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
	
	public void setNbrTarg(int nbr) {
		this.nbrTarg=nbr;
	}
	// Methods
	
	/**
	 * @return une boolean qui represente le fait que la case peut etre targeted ou non
	 */
	public Boolean isTargOk() {
		Boolean ret = false;
		if(this.status == Status.NEUTRAL || this.status == Status.TARGETED) {
			this.setStatus(Status.TARGETED);
			this.nbrTarg+=1;
			ret = true;
		}
		return ret;
	}
	
	/**
	 * @param prob
	 * La case verifie son etat et se met a jour.
	 * Si elle est Targeted, elle a une probabilite prob de se changer en ONFIRE.
	 * Ce processus est repete autant de fois qu'elle a de case ONFIRE qui la cible.
	 */
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
			this.setNbrTarg(0);
			this.setStatus(statusEnd);
		}
	}
}
