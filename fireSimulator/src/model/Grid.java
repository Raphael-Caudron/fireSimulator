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
	
	/**
	 * 
	 * @return une liste d'etat des cases pour ne pas renvoyer directement la grille et compromettre l'encapsulation.
	 */
	private List<Status> getGrid(){
		Iterator<Cell> it = cells.iterator();
		List<Status> cellsState = new ArrayList<Status>();
		while(it.hasNext()) {
			cellsState.add(it.next().getStatus());
		}
		
		return cellsState;
	}
	
	/**
	 * 
	 * @return une Liste de String qui correspond a l'affichage en couleur des etats des cases
	 */
	public List<String> getGridDisplay(){
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
	
	/**
	 * @param nbr
	 * Initialisation au hasard d'un nombre de feu donné dnas le fichier de configuration
	 */
	private void initOnFire(int nbr) {
		// Boucle sur le nombre de feu
		for(int i = 0; i < nbr; i++) {
			Boolean test = true;
			Random rand = new Random();
			// Boucle While qui ne sort que quand le feu est bien initilalise 
			while(test) {
				// Choisit au hasard un numéro de case
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
	
	/**
	 * Cette methode se charge de mettre les bonnes cases en "Targeted" ou s'incrémenter
	 * l'attribut nbrTarg s'il est déjà targeted
	 */
	public void setTargeted() {
		Iterator<Cell> it1 = this.cells.iterator();
		int ind = 0;
		// parcourt toute la grille
		while(it1.hasNext()) {
			Cell cell = it1.next();
			ind++;
			// Teste si la case est en feu
			if(cell.getStatus() == Status.ONFIRE) {
				// La methode qui renvoie le tableau des indices des cibles est appelée 
				int[] indArray = this.getPlacesTargOneFire(cell, ind);
				// Deuxieme iterator pour reparcourir la grille
				Iterator<Cell> it2 = cells.iterator();
				for(int i = 0; i<4; i++) {
					int placeTargCell = indArray[i];
					if(placeTargCell != 0) {
						// Si le nombre n dans la tableau est different de 0, 
						// on itere n fois - 1 et on récupère la dernière case
						for(int j=0; j<placeTargCell-1; j++) {
							it2.next();
						}
						Cell cellTarg = it2.next();
						// On verifie que la case n'est pas ONFIRE ou ASH pour bien la changer ou non
						cellTarg.isTargOk();
					}
				}
			}
		}

	}
	
	/**
	 * 
	 * @param cell
	 * @param place
	 * @return indArray qui sont les indices des cases à ciblés
	 * Les indices s'incrémente donc le deuxième indice du tableau est en réalité
	 * l'indice réel de la case moins la premier indice du tableau.
	 * Cela pour parcourir le tableau avec une boucle qui utilise les indices en paramètre.
	 */
	private int[] getPlacesTargOneFire(Cell cell, int place) {
		int indArray [] = {place-this.largeur,this.largeur-1,2,this.largeur-1};
		
		int test = indArray[0];
		int rest = place%this.largeur;
		
		/* Dans l'ordre, il verifie si la cible est hors des limites de la grille
		 * en haut, a gauche, a droite et en bas.
		 */
		if(test < 0) { // verifie en haut 
			indArray[0]=0;
			indArray[1]=place-1;
			
		}
		if(rest == 1) { // verifie a gauche 
			indArray[1]=0;
			indArray[2]=place+1-indArray[0]; //-indArray[0] pour être en fonction de la première case
		}
		if(rest == 0) { // verifie droite
			indArray[2]=0; 
			indArray[3]=this.largeur+1;
		}
		if(place + this.largeur > this.largeur*this.hauteur) { // verifie en bas
			indArray[3]=0;
		}
		
		return indArray;
	}
	
	/**
	 * Demande aux cellules de se mettre à jour pour l'itération d'après.
	 * 
	 */
	private void updateOnFire() {
		Iterator<Cell> it = cells.iterator();
		while(it.hasNext()) {
			it.next().updateHimself(this.probability);
		}
	}
	
	/**
	 * se charge de mettre à jour les feux et les cases ciblées 
	 * par ces feux pour l'itération suivante
	 */
	public void update() {
		this.updateOnFire();
		this.setTargeted();
	}
	
	/**
	 * 
	 * @return true s'il y a au moins 1 feu sur la grille 
	 */
	public Boolean isFireAlive() { 
		for(Status s : this.getGrid()) {
			if(s == Status.ONFIRE)
				return true;
		}
		return false;
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
