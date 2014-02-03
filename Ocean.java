
public class Ocean {
	public final static int EMPTY = 0;
	public final static int SHARK = 1;
	public final static int FISH = 2;

	private int width;
	private int height;
	private int starveT;
	private Integer[][] myOcean;
	private Integer[][] myShark;

	public Ocean(int i, int j, int starveTime) {
		width = i;
		height = j;
		starveT = starveTime;
		myOcean = new Integer[i][j];
		myShark = new Integer[i][j];
		init (myOcean, i, j, EMPTY);
		init (myShark, i, j, starveTime);
	}

	private void init(Integer[][] array, int numRows, int numCols, int value) {
		for (int i = 0; i < numRows; i++){
			for (int j = 0; j < numCols; j++) {
				array[i][j] = value;
			}
		}
		
	}

	public int width() {
		return this.width;
	}

	public int height() {
		return this.height;
	}

	public int starveTime() {
		return this.starveT;
	}

	public void addFish(int x, int y) {
		if (cellContents(x, y) == EMPTY) {
			myOcean[x][y] = FISH;
		}
	}

	public void addShark(int x, int y) {
		if (cellContents(x, y) == EMPTY) {
			myOcean[x][y] = SHARK;
		}
	}
	
	public void addShark (int x, int y, int time) {
		if (cellContents(x, y) == EMPTY) {
			myOcean[x][y] = SHARK;
			myShark[x][y] = time;
		}
	}
	
	public int sharkFeeding (int x, int y) {		
		return myShark[x][y];	
	}

	public int cellContents(int x, int y) {
//		int result = EMPTY;
//		if (myOcean[x][y] == EMPTY) {
//			result = EMPTY;
//		}
//		if (myOcean[x][y] == SHARK) {
//			result = SHARK;
//		}
//		if (myOcean[x][y] == FISH) {
//			result = FISH;
//		}
		return myOcean[x][y];
	}

	public Ocean timeStep() {
		Ocean oceanBegin = this;
		Ocean oceanEnd = new Ocean(width, height, starveT);
		Integer[][] neighbour = new Integer [3][3];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++){
				int countFish = 0;
				int countShark = 0;
				int countEmpty = 0;
				if (x == 0 && y == 0) {
					neighbour[1][1] = oceanBegin.cellContents(x,y);
					neighbour[1][2] = oceanBegin.cellContents(x,y+1);
					neighbour[2][1] = oceanBegin.cellContents(x+1,y);
					neighbour[2][2] = oceanBegin.cellContents(x+1,y+1);
					neighbour[0][0] = oceanBegin.cellContents(width-1,height-1);
					neighbour[1][0] = oceanBegin.cellContents(0,height-1);
					neighbour[2][0] = oceanBegin.cellContents(1,height-1);
					neighbour[0][1] = oceanBegin.cellContents(width-1,0);
					neighbour[0][2] = oceanBegin.cellContents(width-1,1);
					
					for (int i = 1; i < 3; i++){
						for (int j = 1; j < 3; j++){
							if (neighbour[i][j] == FISH) {
								countFish++;
							} else if (neighbour[i][j] == SHARK) {
								countShark++;
							} else {
								countEmpty++;
							}
						}
					}			
				} else if (x == width - 1 && y == 0) {
					neighbour[1][1] = oceanBegin.cellContents(x,y);
					neighbour[0][1] = oceanBegin.cellContents(x-1,y);
					neighbour[0][2] = oceanBegin.cellContents(x-1,y+1);
					neighbour[1][2] = oceanBegin.cellContents(x,y+1);
					neighbour[0][0] = oceanBegin.cellContents(width-2,height-1);
					neighbour[1][0] = oceanBegin.cellContents(width-1,height-1);
					neighbour[2][0] = oceanBegin.cellContents(0,height-1);
					neighbour[2][1] = oceanBegin.cellContents(0,0);
					neighbour[2][2] = oceanBegin.cellContents(0,1);
					for (int i = 0; i < 2; i++){
						for (int j = 1; j < 3; j++){
							if (neighbour[i][j] == FISH) {
								countFish++;
							} else if (neighbour[i][j] == SHARK) {
								countShark++;
							} else {
								countEmpty++;
							}
						}
					}			
				} else if (x == 0 && y == height - 1) {
					neighbour[1][1] = oceanBegin.cellContents(x,y);
					neighbour[1][0] = oceanBegin.cellContents(x,y-1);
					neighbour[2][0] = oceanBegin.cellContents(x+1,y-1);
					neighbour[2][1] = oceanBegin.cellContents(x+1,y);
					neighbour[0][0] = oceanBegin.cellContents(width-1,height-2);
					neighbour[0][1] = oceanBegin.cellContents(width-1,height-1);
					neighbour[0][2] = oceanBegin.cellContents(width-1,0);
					neighbour[1][2] = oceanBegin.cellContents(0,0);
					neighbour[2][2] = oceanBegin.cellContents(1,0);
					for (int i = 1; i < 3; i++){
						for (int j = 0; j < 2; j++){
							if (neighbour[i][j] == FISH) {
								countFish++;
							} else if (neighbour[i][j] == SHARK) {
								countShark++;
							} else {
								countEmpty++;
							}
						}
					}			
				
				} else if (x == width - 1 && y == height - 1) {
					neighbour[1][1] = oceanBegin.cellContents(x,y);
					neighbour[0][0] = oceanBegin.cellContents(x-1,y-1);
					neighbour[0][1] = oceanBegin.cellContents(x-1,y);
					neighbour[1][0] = oceanBegin.cellContents(x,y-1);
					neighbour[2][0] = oceanBegin.cellContents(0,height-2);
					neighbour[2][1] = oceanBegin.cellContents(0,height-1);
					neighbour[2][2] = oceanBegin.cellContents(0,0);
					neighbour[0][2] = oceanBegin.cellContents(width-2,0);
					neighbour[1][2] = oceanBegin.cellContents(width-1,0);
		
					for (int i = 0; i < 2; i++){
						for (int j = 0; j < 2; j++){
							if (neighbour[i][j] == FISH) {
								countFish++;
							} else if (neighbour[i][j] == SHARK) {
								countShark++;
							} else {
								countEmpty++;
							}
						}
					}			
				} else if (x == 0 && y != 0 && y != height - 1) {
					neighbour[1][1] = oceanBegin.cellContents(x,y);
					neighbour[1][0] = oceanBegin.cellContents(x,y-1);
					neighbour[2][0] = oceanBegin.cellContents(x+1,y-1);
					neighbour[2][1] = oceanBegin.cellContents(x+1,y);
					neighbour[1][2] = oceanBegin.cellContents(x,y+1);
					neighbour[2][2] = oceanBegin.cellContents(x+1,y+1);
					neighbour[0][0] = oceanBegin.cellContents(width-1,y-1);
					neighbour[0][1] = oceanBegin.cellContents(width-1,y);
					neighbour[0][2] = oceanBegin.cellContents(width-1,y+1);
					for (int i = 1; i < 3; i++){
						for (int j = 0; j < 3; j++){
							if (neighbour[i][j] == FISH) {
								countFish++;
							} else if (neighbour[i][j] == SHARK) {
								countShark++;
							} else {
								countEmpty++;
							}
						}
					}			
				} else if (x == width - 1 && y != 0 && y != height - 1) {
					neighbour[1][1] = oceanBegin.cellContents(x,y);
					neighbour[0][0] = oceanBegin.cellContents(x-1,y-1);
					neighbour[0][1] = oceanBegin.cellContents(x-1,y);
					neighbour[1][0] = oceanBegin.cellContents(x,y-1);
					neighbour[0][2] = oceanBegin.cellContents(x-1,y+1);
					neighbour[1][2] = oceanBegin.cellContents(x,y+1);
					neighbour[2][0] = oceanBegin.cellContents(0,y-1);
					neighbour[2][1] = oceanBegin.cellContents(0,y);
					neighbour[2][2] = oceanBegin.cellContents(0,y+1);
					for (int i = 0; i < 2; i++){
						for (int j = 0; j < 3; j++){
							if (neighbour[i][j] == FISH) {
								countFish++;
							} else if (neighbour[i][j] == SHARK) {
								countShark++;
							} else {
								countEmpty++;
							}
						}
					}			
				} else if (y == 0 && x != 0 && x != width - 1) {
					neighbour[1][1] = oceanBegin.cellContents(x,y);
					neighbour[0][1] = oceanBegin.cellContents(x-1,y);
					neighbour[1][2] = oceanBegin.cellContents(x,y+1);
					neighbour[0][2] = oceanBegin.cellContents(x-1,y+1);
					neighbour[2][1] = oceanBegin.cellContents(x+1,y);
					neighbour[2][2] = oceanBegin.cellContents(x+1,y+1);
					neighbour[0][0] = oceanBegin.cellContents(x-1,height-1);
					neighbour[1][0] = oceanBegin.cellContents(x,height-1);
					neighbour[2][0] = oceanBegin.cellContents(x+1,height-1);
					for (int i = 0; i < 3; i++){
						for (int j = 1; j < 3; j++){
							if (neighbour[i][j] == FISH) {
								countFish++;
							} else if (neighbour[i][j] == SHARK) {
								countShark++;
							} else {
								countEmpty++;
							}
						}
					}			
				} else if (y == height - 1 && x != 0 && x != width - 1) {
					neighbour[1][1] = oceanBegin.cellContents(x,y);
					neighbour[0][0] = oceanBegin.cellContents(x-1,y-1);
					neighbour[0][1] = oceanBegin.cellContents(x-1,y);
					neighbour[1][0] = oceanBegin.cellContents(x,y-1);
					neighbour[2][0] = oceanBegin.cellContents(x+1,y-1);
					neighbour[2][1] = oceanBegin.cellContents(x+1,y);
					neighbour[0][2] = oceanBegin.cellContents(x-1,0);
					neighbour[1][2] = oceanBegin.cellContents(x,0);
					neighbour[2][2] = oceanBegin.cellContents(x+1,0);
					for (int i = 0; i < 3; i++){
						for (int j = 0; j < 2; j++){
							if (neighbour[i][j] == FISH) {
								countFish++;
							} else if (neighbour[i][j] == SHARK) {
								countShark++;
							} else {
								countEmpty++;
							}
						}
					}			
				} else {
					neighbour[1][1] = oceanBegin.cellContents(x,y);
					neighbour[0][0] = oceanBegin.cellContents(x-1,y-1);
					neighbour[0][1] = oceanBegin.cellContents(x-1,y);
					neighbour[0][2] = oceanBegin.cellContents(x-1,y+1);
					neighbour[1][0] = oceanBegin.cellContents(x,y-1);
					neighbour[1][2] = oceanBegin.cellContents(x,y+1);
					neighbour[2][0] = oceanBegin.cellContents(x+1,y-1);
					neighbour[2][1] = oceanBegin.cellContents(x+1,y);
					neighbour[2][2] = oceanBegin.cellContents(x+1,y+1);
					for (int i = 0; i < 3; i++){
						for (int j = 0; j < 3; j++){
							if (neighbour[i][j] == FISH) {
								countFish++;
							} else if (neighbour[i][j] == SHARK) {
								countShark++;
							} else {
								countEmpty++;
							}
						}
					}			
				}
				
				if (neighbour[1][1] == SHARK) {
					if (countFish > 0) {
						starveT = 3;
						neighbour[1][1] = SHARK;
						oceanEnd.addShark(x, y);
//						return oceanEnd;
					} else {
						if (starveT == 0) {
							neighbour[1][1] = EMPTY;
//							return oceanEnd;
						} else {
							neighbour[1][1] = SHARK;
							oceanEnd.addShark(x, y);
							starveT = starveT - 1;
//							return oceanEnd;
						}
					}
				} else if (neighbour[1][1] == FISH) {
					if (countEmpty == 8) {
						neighbour[1][1] = FISH;
						oceanEnd.addFish(x, y);
//						return oceanEnd;
					} else if (countFish == 8) {
						neighbour[1][1] = FISH;
						oceanEnd.addFish(x, y);
//						return oceanEnd;
					} else if (countShark == 1) {
						neighbour[1][1] = EMPTY;
//						return oceanEnd;
					} else if (countShark >= 2) {
						neighbour[1][1] = SHARK;
						starveT = 3;
						oceanEnd.addShark(x, y);
//						return oceanEnd;
					}
				} else {
					if (countFish <= 2) {
						neighbour[1][1] = EMPTY;
//						return oceanEnd;
					} else if (countFish >= 2 && countShark <= 1) {
						neighbour[1][1] = FISH;
						oceanEnd.addFish(x, y);
//						return oceanEnd;
					} else if (countFish >= 2 && countShark >= 2) {
						neighbour[1][1] = SHARK;
						starveT = 3;
						oceanEnd.addShark(x, y);
//						return oceanEnd;
					}
				}
			}
			
		}		
		return oceanEnd;
	}
}

	