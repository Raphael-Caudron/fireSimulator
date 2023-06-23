package tools;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Iterator;

import model.Cell;
import model.Coord;
import model.Status;

/**
 * @author raphael
 * Classe qui fabrique des cases pour la grille 
 *
 */

public class CellsFactory {
	
	// private pour ne pas instancier d'objet
	private CellsFactory() {
		
	}
	
	public static Set<Cell> initGrid(int hauteur, int largeur){
		Set<Cell> cells = null;
		cells = new LinkedHashSet<Cell>();
		
		for(int x = 0; x < largeur; x++) {
			for(int y = 0; y < hauteur; y++) {
				Coord coord = new Coord(x,y);
				cells.add(new Cell(coord));
				System.out.println(coord);
			}
		}
		return cells;
	}

	/**
	 * Tests unitaires
	 * @param args
	 */
	public static void main(String[] args) {
		Set<Cell> cells = CellsFactory.initGrid(5,5);
		Iterator<Cell> it = cells.iterator();
		for(int i = 0; i<5*5-1; i++) {
			it.next();
		}
		it.next().setStatus(Status.ONFIRE);; 
		System.out.println(cells);
		
	}
}
