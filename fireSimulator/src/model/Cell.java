package model;

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
}
