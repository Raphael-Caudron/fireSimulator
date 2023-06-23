package model;

import java.util.Iterator;
import java.util.Set;
import java.util.Random;

import tools.CellsFactory;

public class Grid {
	
	private Set<Cell> cells;
	private int hauteur;
	private int largeur;
	
	//Constructor
	public Grid(int h, int l, int nbr) {
		this.hauteur = h;
		this.largeur = l;
		this.cells = CellsFactory.initGrid(h,l);
		this.setOnFire(nbr);
		
		//Gives a ONFIRE status randomly to a set number of cells
		
		
	}
	
	//Methods
	
	private void setOnFire(int nbr) {
		for(int i = 0; i < nbr; i++) {
			Boolean test = true;
			Random rand = new Random();
			
			while(test) {
				int nbrAlea = rand.nextInt(this.hauteur*this.largeur);
				Iterator<Cell> it = cells.iterator();
				for(int j = 0; j<nbrAlea - 1; j++) {
					it.next();
				}
				Cell currentCell = it.next();
				if(currentCell.getStatus() == Status.NEUTRAL) {
					currentCell.setStatus(Status.ONFIRE);
					test = false;
				}
			}
		}
	}
	
	public void setTargeted() {
		Iterator<Cell> it = cells.iterator();
		int ind = 0;
		while(it.hasNext()) {
			Cell cell = it.next();
			ind++;
			if(cell.getStatus() == Status.ONFIRE) {
				this.setTargOneFire(cell, ind);
			}
		}

	}
	
	private void setTargOneFire(Cell cell, int place) {
		Iterator<Cell> it = cells.iterator();
		int indArray [] = {place-this.largeur,this.largeur-1,2,this.largeur-1};
		
		int rest = place%this.largeur;
		if(rest == 1) {
			indArray[1]=0;
		}
		if(rest == 0) {
			indArray[2]=0; 
		}
		
		for(int i = 0; i < 4; i+=3) {
			int test = indArray[i];
			if(test < 0 || test >= this.largeur*this.hauteur) {
				indArray[i]=0;
			}
		}
	}
	
	// Tests unitaires
	public static void main(String[] args) {
		
	}
}
