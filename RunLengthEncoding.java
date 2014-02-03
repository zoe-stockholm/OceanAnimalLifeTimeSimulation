/**
 * The RunLengthEncoding class defines an object that run-length encodes an
 * Ocean object. Descriptions of the methods you must implement appear below.
 * They include constructors of the form
 * 
 * public RunLengthEncoding(int i, int j, int starveTime); public
 * RunLengthEncoding(int i, int j, int starveTime, int[] runTypes, int[]
 * runLengths) { public RunLengthEncoding(Ocean ocean) {
 * 
 * that create a run-length encoding of an Ocean having width i and height j, in
 * which sharks starve after starveTime timesteps.
 * 
 * The first constructor creates a run-length encoding of an Ocean in which
 * every cell is empty. The second constructor creates a run-length encoding for
 * which the runs are provided as parameters. The third constructor converts an
 * Ocean object into a run-length encoding of that object.
 * 
 * See the README file accompanying this project for additional details.
 */

public class RunLengthEncoding {
	/**
	 * Define any variables associated with a RunLengthEncoding object here.
	 * These variables MUST be private.
	 */
	private DList d;
	private int i;
	private int j;
	private int starveTime;

	/**
	 * RunLengthEncoding() (with three parameters) is a constructor that creates
	 * a run-length encoding of an empty ocean having width i and height j, in
	 * which sharks starve after starveTime timesteps.
	 * 
	 * @param i
	 *            is the width of the ocean.
	 * @param j
	 *            is the height of the ocean.
	 * @param starveTime
	 *            is the number of timesteps sharks survive without food.
	 */
	public RunLengthEncoding(int i, int j, int starveTime) {

		// d.size = i * j;
		// d.head.item = Ocean.EMPTY;
		// d.tail.item = Ocean.EMPTY;
		// while (d.head != d.tail) {
		// d.head.next.item = Ocean.EMPTY;
		// d.head.next = d.head;
		// }
		d.size = 1;
		d.head.item = "." + i * j;
		d.tail = d.head;
	}

	/**
	 * RunLengthEncoding() (with five parameters) is a constructor that creates
	 * a run-length encoding of an ocean having width i and height j, in which
	 * sharks starve after starveTime timesteps. The runs of the run-length
	 * encoding are taken from two input arrays. Run i has length runLengths[i]
	 * and species runTypes[i].
	 * 
	 * @param i
	 *            is the width of the ocean.
	 * @param j
	 *            is the height of the ocean.
	 * @param starveTime
	 *            is the number of timesteps sharks survive without food.
	 * @param runTypes
	 *            is an array that represents the species represented by each
	 *            run. Each element of runTypes is Ocean.EMPTY, Ocean.FISH, or
	 *            Ocean.SHARK. Any run of sharks is treated as a run of newborn
	 *            sharks (which are equivalent to sharks that have just eaten).
	 * @param runLengths
	 *            is an array that represents the length of each run. The sum of
	 *            all elements of the runLengths array should be i * j.
	 */

	public RunLengthEncoding(int i, int j, int starveTime, int[] runTypes,
			int[] runLengths) {
		d.size = runTypes.length;
		String[] newRunTypes = new String[d.size];
		for (int m = 0; m < d.size; m++) {
			if (runTypes[m] == 0) {
				newRunTypes[m] = ".";
			} else if (runTypes[m] == 1) {
				newRunTypes[m] = "S" + Integer.toString(starveTime) + ",";
			} else {
				newRunTypes[m] = "F";
			}
		}
		for (int n = 0; n < d.size; n++) {
			d.head.item = newRunTypes[n] + Integer.toString(runLengths[n]);
			d.head.next = d.head;
		}
	}

	/**
	 * restartRuns() and nextRun() are two methods that work together to return
	 * all the runs in the run-length encoding, one by one. Each time nextRun()
	 * is invoked, it returns a different run (represented as an array of two
	 * ints), until every run has been returned. The first time nextRun() is
	 * invoked, it returns the first run in the encoding, which contains cell
	 * (0, 0). After every run has been returned, nextRun() returns null, which
	 * lets the calling program know that there are no more runs in the
	 * encoding.
	 * 
	 * The restartRuns() method resets the enumeration, so that nextRun() will
	 * once again enumerate all the runs as if nextRun() were being invoked for
	 * the first time.
	 * 
	 * (Note: Don't worry about what might happen if nextRun() is interleaved
	 * with addFish() or addShark(); it won't happen.)
	 */

	/**
	 * restartRuns() resets the enumeration as described above, so that
	 * nextRun() will enumerate all the runs from the beginning.
	 */

	public void restartRuns() {
		d.head.next = d.head;
	}

	/**
	 * nextRun() returns the next run in the enumeration, as described above. If
	 * the runs have been exhausted, it returns null. The return value is an
	 * array of two ints (constructed here), representing the type and the size
	 * of the run, in that order.
	 * 
	 * @return the next run in the enumeration, represented by an array of two
	 *         ints. The int at index zero indicates the run type (Ocean.EMPTY,
	 *         Ocean.SHARK, or Ocean.FISH). The int at index one indicates the
	 *         run length (which must be at least 1).
	 */

	public int[] nextRun() {
		int[] runCell = new int[2];
		while (d.head != d.tail) {
			if (d.head.item.contains("F")) {
				runCell[0] = Ocean.FISH;
				runCell[1] = Integer.parseInt(d.head.item.substring(1));
			} else if (d.head.item.contains("S")) {
				runCell[0] = Ocean.SHARK;
				runCell[1] = Integer.parseInt(d.head.item.substring(3));
			} else {
				runCell[0] = Ocean.EMPTY;
				runCell[1] = Integer.parseInt(d.head.item.substring(1));
			}
			restartRuns();
			return runCell;
		}
		return null;
	}

	/**
	 * toOcean() converts a run-length encoding of an ocean into an Ocean
	 * object. You will need to implement the three-parameter addShark method in
	 * the Ocean class for this method's use.
	 * 
	 * @return the Ocean represented by a run-length encoding.
	 */

	public Ocean toOcean() {
		int x = 0;
		int y = 0;
		Ocean myOcean = new Ocean(this.i, this.j, this.starveTime);
		while (d.head != d.tail) {
			if (d.head.item.contains("F")) {
				myOcean.addFish(x, y);
				if (x == this.i) {
					x = 0;
					y++;
				} else {
					x++;
				}
			} else if (d.head.item.contains("S")) {
				myOcean.addShark(x, y,
						Integer.parseInt(d.head.item.substring(1, 2)));
				if (x == this.i) {
					x = 0;
					y++;
				} else {
					x++;
				}
			} else {
				if (x == this.i) {
					x = 0;
					y++;
				} else {
					x++;
				}
			}
		}
		return myOcean;
	}

	/**
	 * RunLengthEncoding() (with one parameter) is a constructor that creates a
	 * run-length encoding of an input Ocean. You will need to implement the
	 * sharkFeeding method in the Ocean class for this constructor's use.
	 * 
	 * @param sea
	 *            is the ocean to encode.
	 */

	public RunLengthEncoding(Ocean sea) {
		int countFish = 0;
		int countShark0 = 0;
		int countShark1 = 0;
		int countShark2 = 0;
		int countShark3 = 0;
		int countEmpty = 0;
		String currentPointer = null;

		if (sea.cellContents(0, 0) == Ocean.FISH) {
			countFish++;
			d.head.item = "F" + Integer.toString(1);
			currentPointer = "F";
		} else if (sea.cellContents(0, 0) == Ocean.SHARK) {
			if (sea.sharkFeeding(0, 0) == 0) {
				countShark0++;
				d.head.item = "S0," + Integer.toString(1);
				currentPointer = "S0";
			} else if (sea.sharkFeeding(0, 0) == 1) {
				countShark1++;
				d.head.item = "S1," + Integer.toString(1);
				currentPointer = "S1";
			} else if (sea.sharkFeeding(0, 0) == 2) {
				countShark2++;
				d.head.item = "S2," + Integer.toString(1);
				currentPointer = "S2";
			} else {
				countShark3++;
				d.head.item = "S3," + Integer.toString(1);
				currentPointer = "S3";
			}
		} else {
			countEmpty++;
			d.head.item = "." + Integer.toString(1);
			currentPointer = "E";
		}

		for (int i = 1; i < sea.width(); i++) {
			for (int j = 1; j < sea.height(); j++) {
				if (sea.cellContents(i, j) == Ocean.FISH) {
					if (currentPointer.equals("F")) {
						countFish++;
						d.head.item = "F" + Integer.toString(countFish);
					} else {
						countFish = 0;
						countFish++;
						d.head = d.head.next;
						d.head.item = "F" + Integer.toString(countFish);
						currentPointer = "F";
					}
				} else if (sea.cellContents(i, j) == Ocean.SHARK
						&& sea.sharkFeeding(i, j) == 0) {
					if (currentPointer.equals("S0")) {
						countShark0++;
						d.head.item = "S0," + Integer.toString(countShark0);
					} else {
						countShark0 = 0;
						countShark0++;
						d.head = d.head.next;
						d.head.item = "S0," + Integer.toString(countShark0);
						currentPointer = "S0";
					}
				} else if (sea.cellContents(i, j) == Ocean.SHARK
						&& sea.sharkFeeding(i, j) == 1) {
					if (currentPointer.equals("S1")) {
						countShark1++;
						d.head.item = "S1," + Integer.toString(countShark1);
					} else {
						countShark1 = 0;
						countShark1++;
						d.head = d.head.next;
						d.head.item = "S1," + Integer.toString(countShark1);
						currentPointer = "S1";
					}
				} else if (sea.cellContents(i, j) == Ocean.SHARK
						&& sea.sharkFeeding(i, j) == 2) {
					if (currentPointer.equals("S2")) {
						countShark2++;
						d.head.item = "S2," + Integer.toString(countShark2);
					} else {
						countShark2 = 0;
						countShark2++;
						d.head = d.head.next;
						d.head.item = "S2," + Integer.toString(countShark2);
						currentPointer = "S2";
					}
				} else if (sea.cellContents(i, j) == Ocean.SHARK
						&& sea.sharkFeeding(i, j) == 3) {
					if (currentPointer.equals("S3")) {
						countShark3++;
						d.head.item = "S3," + Integer.toString(countShark3);
					} else {
						countShark3 = 0;
						countShark3++;
						d.head = d.head.next;
						d.head.item = "S3," + Integer.toString(countShark3);
						currentPointer = "S3";
					}
				} else if (sea.cellContents(i, j) == Ocean.EMPTY) {
					if (currentPointer.equals("E")) {
						countEmpty++;
						d.head.item = "." + Integer.toString(countEmpty);
					} else {
						countEmpty = 0;
						countEmpty++;
						d.head = d.head.next;
						d.head.item = "." + Integer.toString(countEmpty);
						currentPointer = "E";
					}
				}
			}
		}
		check();
	}
	
	/**
	   *  addFish() places a fish in cell (x, y) if the cell is empty.  If the
	   *  cell is already occupied, leave the cell as it is.  The final run-length
	   *  encoding should be compressed as much as possible; there should not be
	   *  two consecutive runs of sharks with the same degree of hunger.
	   *  @param x is the x-coordinate of the cell to place a fish in.
	   *  @param y is the y-coordinate of the cell to place a fish in.
	   */

	  public void addFish(int x, int y) {
	    int position = this.i * y + x + 1;
	    if (this.d.head.item.substring(0, 1).equals(".")) {
	    	int emptyNum = Integer.parseInt(this.d.head.item.substring(1));
	    	if (position < emptyNum) {	
	    		d.size = d.size + 2;
	    		DListNode node1 = new DListNode();
	    		DListNode node2 = new DListNode();
	    		d.head.item = "." + Integer.toString(position - 1);
	    		node1.item = "F" + Integer.toString(1);
	    		node2.item = "." + Integer.toString(emptyNum - position + 1);
	    		d.head.next = node1;
	    		node1.prev = d.head;
	    		node1.next = node2;
	    		node2.prev = node1;
	    		node2.next = d.head.next;
	    		d.head.next.prev = node2;
	    				                                                           
	    	} else if (position == 0) {
	    		d.size = d.size + 1;
	    		DListNode node1 = new DListNode();
	    		node1.item = "F" + Integer.toString(1);
	    		d.head.item = "." + Integer.toString(emptyNum - 1);
	    		node1.next = d.head;
	    		d.head.prev = node1;
	    		
	    	} else if (position == emptyNum) {
	    		if (this.d.head.next.item.substring(0, 1).equals("F")) {
	    			d.head.item =  "." + Integer.toString(emptyNum - 1);
		    		d.head.next.item = "F" + Integer.toString(Integer.parseInt(d.head.next.item.substring(1))+1);
	    		} else {
	    			d.size = d.size + 1;
		    		d.head.item =  "." + Integer.toString(emptyNum - 1);
		    		DListNode node1 = new DListNode();
		    		node1.item = "F" + Integer.toString(1);
		    		d.head.next = node1;
		    		node1.prev = d.head;
		    		node1.next = d.head.next;
		    		d.head.next.prev = node1;
	    		}		
	    	}
	    } else {
	    	d.head = d.head.next;
	    	addFish(x,y);
	    }
	    check();
	  }

	  /**
	   *  addShark() (with two parameters) places a newborn shark in cell (x, y) if
	   *  the cell is empty.  A "newborn" shark is equivalent to a shark that has
	   *  just eaten.  If the cell is already occupied, leave the cell as it is.
	   *  The final run-length encoding should be compressed as much as possible;
	   *  there should not be two consecutive runs of sharks with the same degree
	   *  of hunger.
	   *  @param x is the x-coordinate of the cell to place a shark in.
	   *  @param y is the y-coordinate of the cell to place a shark in.
	   */

	  public void addShark(int x, int y) {
		  int position = this.i * y + x + 1;
		    if (this.d.head.item.substring(0, 1).equals(".")) {
		    	int emptyNum = Integer.parseInt(this.d.head.item.substring(1));
		    	if (position < emptyNum) {	
		    		d.size = d.size + 2;
		    		d.head.item = "." + Integer.toString(position - 1);
		    		d.head.next.item = "S3," + Integer.toString(1);
		    		d.head.next.next.item = "." + Integer.toString(emptyNum - position + 1);
		    		d.head.next.prev = d.head;
		    		d.head.next.next = d.head.next.next.prev;		                                                           
		    	} else if (position == 0) {
		    		d.size = d.size + 1;
		    		d.head.item = "S3," + Integer.toString(1);
		    		d.head.next.item = "." + Integer.toString(emptyNum - 1);
		    		d.head = d.head.next.prev;
		    	} else if (position == emptyNum) {
		    		if (this.d.head.next.item.substring(0, 2).equals("S3")) {
		    			d.head.item =  "." + Integer.toString(emptyNum - 1);
			    		d.head.next.item = "S3," + Integer.toString(Integer.parseInt(d.head.next.item.substring(3))+1);
		    		} else {
		    			d.size = d.size + 1;
			    		d.head.item =  "." + Integer.toString(emptyNum - 1);
			    		d.head.next.item = "S3," + Integer.toString(1);
			    		d.head.next.prev = d.head; 
			    		d.head.next.next = d.head.next.next.next.prev;
		    		}		
		    	}
		    } else {
		    	d.head = d.head.next;
		    	addShark(x,y);
		    }
	    check();
	  }

	  /**
	   *  check() walks through the run-length encoding and prints an error message
	   *  if two consecutive runs have the same contents, or if the sum of all run
	   *  lengths does not equal the number of cells in the ocean.
	   */

	  private void check() {
	  }

	}

