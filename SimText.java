import java.util.*;

public class SimText {
	private static int i = 50;
	private static int j = 25;
	private static int starveTime = 3;
	
	// print an Ocean
	public static void paint (Ocean sea) {
		if (sea != null) {
			int width = sea.width();
			int height = sea.height();
			
			// draw the ocean
			for (int x = 0; x < width + 2; x++) {
				System.out.print("-");
			}
			System.out.println();
			for (int y = 0; y < height; y++) {
				System.out.print("|");
				for (int x = 0; x < width; x++) {
					int contents = sea.cellContents(x, y);
					if (contents == Ocean.SHARK) {
						System.out.print("S");
					} else if (contents == Ocean.FISH) {
						System.out.print("~");
					} else {
						System.out.print(" ");
					}
				}
				System.out.println("|");
			}
			for (int x = 0; x < width + 2; x++) {
				System.out.print("-");
			}
			System.out.println();
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
		
		// create the initial ocean
		sea = new Ocean (i, j, starveTime);
		
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
		
		while (true) {
			paint (sea);
			Thread.sleep(1000);
			sea = sea.timeStep();
		}
	}

}
