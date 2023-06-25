package localLauncher;

import java.util.List;
import java.util.concurrent.TimeUnit;

import model.Grid;

public class LauncherCmdLine {
	
	public static void main(String[] args) throws InterruptedException {
		Grid myGrid = new Grid(10,5,2,50);
		Boolean test = true;
		while(test) {
			List<String> grid = myGrid.getGrid2();
			System.out.println(grid);
			int haut = myGrid.getHauteur();
			int larg = myGrid.getLargeur();
			for(int i = 0;i<haut;i++){//Change
				for(int j= 0;j<larg;j++) {
					System.out.print(grid.get(i*larg+j));
				}
				System.out.println();
			}
			TimeUnit.SECONDS.sleep(2);
		    myGrid.update();
		}
	}
	
}
