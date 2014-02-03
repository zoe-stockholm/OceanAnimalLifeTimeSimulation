import java.awt.*;
import java.util.*;

public class Simulation {
	private static final int cellSize = 4;
	private static int i = 80;
	private static int j = 80;
	private static int starveTime = 3;
	
	private static void drawOcean (Graphics graphics, Ocean ocean) {
		if (ocean != null) {
			int width = ocean.width();
			int height = ocean.height();
			
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					int contents = ocean.cellContents(x,y);
					if (contents == Ocean.SHARK) {
						graphics.setColor (Color.red);
						graphics.fillRect (x*cellSize, y*cellSize, cellSize, cellSize);
					} else if (contents == Ocean.FISH) {
						graphics.setColor (Color.green);
						graphics.fillRect (x*cellSize, y*cellSize, cellSize, cellSize);
					} else {
						graphics.clearRect (x*cellSize, y*cellSize, cellSize, cellSize);
					}
				}
			}
		}
	}
	
	public static void main (String[] argv) throws InterruptedException {
		Ocean sea;
		
		if (argv.length > 0) {
			try {
				j = Integer.parseInt(argv[0]);
			}
			catch (NumberFormatException e) {
				System.out.println ("First arguement to Simulation is not an number.");
			}
		}
		
		if (argv.length > 1) {
			try {
				j = Integer.parseInt(argv[1]);
			}
			catch (NumberFormatException e) {
				System.out.println ("Second arguement to Simulation is not an number.");
			}
		}
		
		if (argv.length > 2) {
			try {
				j = Integer.parseInt(argv[2]);
			}
			catch (NumberFormatException e) {
				System.out.println ("Third arguement to Simulation is not an number.");
			}
		}
		
		// create a  window on the screen
		Frame frame = new Frame ("Sharks and Fish");
		frame.setSize(i * cellSize + 10, j * cellSize +30);
		frame.show();
		
		// create a canvas that we can draw upon
		Canvas canvas = new Canvas();
		canvas.setBackground(Color.white);
		canvas.setSize(i * cellSize, j * cellSize);
		frame.add(canvas);
		Graphics graphics = canvas.getGraphics();
		
		// create the initial ocean
		sea = new Ocean (i, j, starveTime);
		
		// create a random object with seed 0
		Random random = new Random(0);
		int x = 0;
		int y = 0;
		
		for (int xx = 0; xx < i; xx++) {
			x = (x+78887) % i;
			if ((x & 8) == 0) {
				for (int yy = 0; yy < j; yy++) {
					y = (y + 78887) % j;
					if ((y & 8) == 0) {
						int r = random.nextInt();
						if (r < 0) {
							sea.addFish(x,y);
						} else if (r > 1500000000) {
							sea.addShark(x, y);
						}
					}
				}
			}
		}
		// perform timeSteps forever
		while (true) {
			Thread.sleep(1000);
			drawOcean(graphics, sea);
			sea = sea.timeStep();
		}	
	}

}
