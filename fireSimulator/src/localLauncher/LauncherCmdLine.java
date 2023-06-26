package localLauncher;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.Properties;

import model.Grid;

public class LauncherCmdLine {
	
	public static void main(String[] args) throws InterruptedException {
		int hauteur;
		int largeur;
		int nbrFeu;
		int proba;
		
		try (InputStream input = new FileInputStream("src/ressources/application.properties")) {
		    Properties prop = new Properties();
		    prop.load(input);
		    hauteur = Integer.parseInt(prop.getProperty("hauteur"));
		    largeur = Integer.parseInt(prop.getProperty("largeur"));
		    nbrFeu = Integer.parseInt(prop.getProperty("nbrFeu"));
		    proba = Integer.parseInt(prop.getProperty("probabilite"));
		} catch (IOException ex) {
			ex.printStackTrace();
			hauteur = 8;
			largeur = 8;
			nbrFeu = 3;
			proba = 50;
					
		}
		
		Grid myGrid = new Grid(hauteur,largeur,nbrFeu,proba);
		Boolean test = true;
		while(test) {
			List<String> grid = myGrid.getGridDisplay();
			test = myGrid.getIsFire();
			for(int i = 0;i<hauteur;i++){//Change
				for(int j= 0;j<largeur;j++) {
					System.out.print(grid.get(i*largeur+j));
				}
				System.out.println();
			}
			System.out.println("--------------------------------");
			TimeUnit.SECONDS.sleep(2);
		    myGrid.update();
		}
	}
	
}
