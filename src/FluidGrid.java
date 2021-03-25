import javax.swing.SwingWorker;

public class FluidGrid {

	private FluidCell[][] ValueField;
	
	private float[][] replace;
	
	private int sizeX;
	
	private int sizeY;
	
	private FluidRule rules;

	private int div = 4;
	
	public FluidGrid(int X, int Y) {
		sizeX = X;
		sizeY = Y;
		ValueField = new FluidCell[X][Y];
		replace = new float[X][Y];
		for (int i = 0; i < sizeX; i++) {
			for (int j = 0; j < sizeY; j++) {
				ValueField[i][j] = new FluidCell(0f);
			}
		}
		
	}
	
	public void setCell(int i, int j, float inp) {
		if ((i >= 0 & i < sizeX)&(j>=0 & j < sizeY)) {
			ValueField[i][j].setValue(inp);;
		}
	}
	
	public float calcSuroundSumm(int x, int y) {
		float summ = 0f;
		for (int i = x - 1; i <= x + 1; i++) {
			for (int j =  y - 1; j <= y + 1; j++) {
				if (i < 0 | j< 0 | i >=sizeX | j >= sizeY) {
					summ += 0f;
				}
				else if(!(i==x && j==y)) {
					summ += ValueField[i][j].getValue();
				}
			}
		} 
		return summ;
	}
	
	public void applyRules(int x, int y) {
		float summ = calcSuroundSumm(x,y);
		replace[x][y] = (rules.applyRule(summ, ValueField[x][y]));
	}
	
	private class GridWorker extends SwingWorker<Object, Object>{
		private int x;
		private int y;
		//private int squareX;
		//private int squareY;
		public GridWorker(int x, int y) {
			this.x = x;
			this.y = y;
			//squareX = sizeX/div;
			//squareY = sizeY/div;
		}
		
		@Override
		protected Object doInBackground() throws Exception {
			applyRules(x,y);
			ValueField[x][y].setValue(replace[x][y]);
			return null;
		}
		
	}
	public void setRules(FluidRule inp) {
		rules = inp;
	}
	
	public void update() {
		
		for (int i = 0; i < sizeX; i++) {
			for (int j = 0; j < sizeY; j++) {
				applyRules(i,j);
			}
		}
		for (int i = 0; i < sizeX; i++) {
			for (int j = 0; j < sizeY; j++) {
				ValueField[i][j].setValue(replace[i][j]);
			}
		}
		
		/*
		for (int i = 0; i < sizeX; i++) {
			for (int j = 0; j < sizeY; j++) {
				GridWorker grWrk = new GridWorker(i,j);
				grWrk.execute();
			}
		}
		*/
		
	}
	
	public void resetGrid() {
		for (int i = 0; i < sizeX; i++) {
			for (int j = 0; j < sizeY; j++) {
				ValueField[i][j].reset();
			}
		}
	}
	
	public FluidCell getCell(int x, int y) {
		return ValueField[x][y];
	}
}
