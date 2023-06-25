package model;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.Random;

import tools.CellsFactory;

public class Grid {
	
	private Set<Cell> cells;
	private int hauteur;
	private int largeur;
	private int probability;
	
	// Constructor
	public Grid(int h, int l, int nbr, int probPropag) {
		this.hauteur = h;
		this.largeur = l;
		this.probability=probPropag;
		this.cells = CellsFactory.initGrid(h,l);
		this.initOnFire(nbr);
		this.setTargeted();
		
		// Gives a ONFIRE status randomly to a set number of cells
		
		
	}
	
	// Getter
	
	public List<Status> getGrid(){
		Iterator<Cell> it = cells.iterator();
		List<Status> cellsState = new ArrayList<Status>();
		while(it.hasNext()) {
			cellsState.add(it.next().getStatus());
		}
		
		return cellsState;
	}
	
	public List<String> getGrid2(){
		Iterator<Cell> it = cells.iterator();
		List<String> cellsState = new ArrayList<String>();
		while(it.hasNext()) {
			String text;
			Status cellStatus = it.next().getStatus();
			if(cellStatus==Status.NEUTRAL) {
				text = "\u001B[32m" + "◼ " + "\u001B[0m";
			}
			else if(cellStatus==Status.ASH) {
				text = "◻ ";
			}
			else if(cellStatus==Status.TARGETED) {
				text = "\u001B[33m" + "◼ " + "\u001B[0m";
			}
			else {
				text = "\u001B[31m" + "◼ " + "\u001B[0m";
			}
			cellsState.add(text);
		}
		
		return cellsState;
	}
	
	public int getHauteur() {
		return this.hauteur;
	}
	
	public int getLargeur() {
		return this.largeur;
	}
	
	// Methods
	
	private void initOnFire(int nbr) {
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
		Iterator<Cell> it1 = cells.iterator();
		int ind = 0;
		while(it1.hasNext()) {
			Cell cell = it1.next();
			ind++;
			if(cell.getStatus() == Status.ONFIRE) {
				int[] indArray = this.getPlacesTargOneFire(cell, ind);
				Iterator<Cell> it2 = cells.iterator();
				System.out.println("\n"+cell+"  "+ind+"\n");
				for(int i = 0; i<4; i++) {
					int placeTargCell = indArray[i];
					if(placeTargCell != 0) {
						
						//Cell cellTarg;
						//System.out.println(indArray[i]);
						//System.out.println("ind"+i);
						
						for(int j=0; j<placeTargCell-1; j++) {
							
							it2.next();
							//cellTarg = it2.next();
							//System.out.println(cellTarg);
						}
						
						//cellTarg = it2.next();
						//System.out.println(cellTarg);
						
						Cell cellTarg = it2.next();
						cellTarg.isTargOk();
					}
				}
			}
		}

	}
	
	private int[] getPlacesTargOneFire(Cell cell, int place) {
		int indArray [] = {place-this.largeur,this.largeur-1,2,this.largeur-1};
		
		int test = indArray[0];
		int rest = place%this.largeur;
		/* In order, checks if the target is out of bound
		 * at the top, to the left, to the right and lastly, at the bottom
		 */
		if(test < 0) {
			indArray[0]=0;
			indArray[1]=place-1;
			
		}
		if(rest == 1) {
			indArray[1]=0;
			indArray[2]=place+1-indArray[0]; //pb when at the top so calculated considering if ind0 is existent or not 
		}
		if(rest == 0) {
			indArray[2]=0; 
			indArray[3]=this.largeur+1;
		}
		if(place + this.largeur > this.largeur*this.hauteur) {
			indArray[3]=0;
		}
		
		return indArray;
	}
	
	private void updateOnFire() {
		Iterator<Cell> it = cells.iterator();
		while(it.hasNext()) {
			it.next().updateHimself(this.probability);
		}
	}
	
	public void update() {
		this.updateOnFire();
		this.setTargeted();
	}
	
	// Tests unitaires
	public static void main(String[] args) throws InterruptedException {
		Grid myGrid = new Grid(5,5,10,50);
		while(true) {
			System.out.println(myGrid.cells);
			TimeUnit.SECONDS.sleep(2);
		    myGrid.update();
		}
		
	}
}
