package localLauncher;

import java.util.concurrent.TimeUnit;

import model.Grid;

public class LauncherCmdLine {
	
	public static void main(String[] args) throws InterruptedException {
		Grid myGrid = new Grid(5,5,10,50);
		while(true) {
			System.out.println(myGrid.getGrid());
			TimeUnit.SECONDS.sleep(2);
		    myGrid.update();
		}
	}
	
}
