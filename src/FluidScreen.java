import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import java.util.Random;
public class FluidScreen {

	int sizeX = 500, sizeY = 400;
	private JImageDisplay img;
	private JImageDisplay imgPip;
	int zoom = 2;
	private FluidGrid grid;
	int viewX, viewY;
	int lenX , lenY;
	boolean perspectiveReady = true;
	int pipCenterX, pipCenterY;
	JFrame frame;

	JButton resetButton;
	JButton randomButton;
	JComboBox<FluidRule> ruleCBox;
	JPanel control;
	JComboBox<String> zoomCBox;
	Timer time = new Timer();
	TimerTask updateTask = new TimerTask() {
		@Override
		public void run() {
			update();
		}
	};
	
	public FluidScreen() {
		Random rng = new Random();
		this.grid = new FluidGrid(sizeX,sizeY);
		this.grid.setRules(new BasicRule());
		for (int i = 0; i < 223; i++) {
			randArr[i] = rng.nextFloat();
		}
		viewX = sizeX;
		viewY = sizeY;
		lenX = sizeX;
		lenY = sizeY;
		calcPipCoords(sizeX/2,sizeY/2);
	}
	
	static float[] randArr = new float[223];
	static int randNum = 0;
	public static float getRandVal() {
			randNum = randNum++ % 223;
	        return randArr[randNum];
	}
	
	public void createAndShowGUI() {
		frame = new JFrame("Fluid Life");
		img = new JImageDisplay(sizeX,sizeY);
		imgPip = new JImageDisplay(sizeX,sizeY);
		resetButton = new JButton("Reset");
		resetButton.setActionCommand("reset");
		randomButton = new JButton("Drop Random Pixels");
		randomButton.setActionCommand("random");
		ruleCBox = new JComboBox<FluidRule>();
		zoomCBox = new JComboBox<String>();
		control = new JPanel();
		control.add(resetButton);
		control.add(randomButton);
		control.add(ruleCBox);
		control.add(zoomCBox);
		ruleCBox.addItem(new BasicRule());
		ruleCBox.addItem(new GrowRule());
		ruleCBox.addItem(new ChaosRule());
		ruleCBox.addItem(new ClassicRules());
		for (int i = 2; i < 11; i++) {
			zoomCBox.addItem(new String("x"+Integer.toString(i) + " zoom"));
		}
		
		ActionHandler aHandler = new ActionHandler();
		MouseHandler mHandler = new MouseHandler();
		resetButton.addActionListener(aHandler);
		randomButton.addActionListener(aHandler);
		img.addMouseListener(mHandler);
		ruleCBox.addActionListener(aHandler);
		zoomCBox.addActionListener(aHandler);
		
		frame.setLayout(new java.awt.BorderLayout());
		frame.add(img, java.awt.BorderLayout.CENTER);
		frame.add(imgPip, java.awt.BorderLayout.EAST);
		frame.add(control,java.awt.BorderLayout.SOUTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
	}
	
	public void drawGrid() {
		for (int i = 0; i < sizeX ; i++) {
			for (int j = 0; j < sizeY; j++) {
				if (grid.getCell(i, j).isAlive()) {
					img.drawPixel(i, j, grid.getCell(i, j).getColour());
				}
				else img.drawPixel(i, j, 100);
			}
		}
		
		for (int i = viewX; i < viewX + lenX; ++i) {
			img.drawPixel(i, viewY+lenY, 30);
			img.drawPixel(i, viewY, 30);
		}
		for (int j = viewY; j < viewY + lenY; ++j) {
			img.drawPixel(viewX+lenX,j, 30);
			img.drawPixel(viewX,j, 30);
		}
		img.repaint();
	}
	
	public void randomGrid() {
		Random rng = new Random();
		boolean cls = (FluidRule)ruleCBox.getSelectedItem() instanceof ClassicRules;
		for (int i = 0; i < sizeX ; i++) {
			for (int j = 0; j < sizeY; j++) {
				if (!cls) {
					grid.setCell(i, j, rng.nextFloat() - 0.9f);
				}
				else {
					if(rng.nextBoolean()) {
						grid.setCell(i, j, 1f);
					}
					else {
						grid.setCell(i, j, 0f);
					}
				}
			}
		}
	}
	
	public void update() {
		grid.update();
		drawGrid();
		drawInPip();
	}
	
	public class ActionHandler implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand() == "reset") {
				grid.resetGrid();
				drawGrid();
			} else if (e.getActionCommand() == "random") {
				randomGrid();
			} else if (e.getSource() == (Object) ruleCBox) {
				grid.resetGrid();
				grid.setRules((FluidRule)ruleCBox.getSelectedItem());
			} else if (e.getSource() == (Object) zoomCBox) {
				zoom = zoomCBox.getSelectedIndex()+2;
				perspectiveReady = false;
			}
		}
	}
	
	
	public void calcPipCoords(int x, int y) {
		lenX = sizeX/zoom;
		lenY = sizeY/zoom;
		viewX = x - lenX/2;
		viewY = y - lenY/2;
		if (viewX < 0) {
			viewX = 1;
		} else if (viewX + lenX > sizeX) {
			viewX = sizeX - lenX-1;
		}
		if (viewY < 0) {
			viewY = 1;
		} else if (viewY + lenY > sizeY) {
			viewY = sizeY - lenY-1;
		}
		
	}
	
	public void drawInPip() {
		//System.out.println(viewX + " " + viewY + " " + (viewX + lenX) + " " + (viewY + lenY));
		if (!perspectiveReady) {
			calcPipCoords(pipCenterX,pipCenterY);
			perspectiveReady = true;
		}
		//System.out.println(viewX + " " + viewY + " " + lenX + " " + lenY);
		for (int i = viewX; i < viewX + lenX; ++i) {
			for (int ii = (i-viewX)*zoom; ii < (i-viewX + 1)* zoom ; ++ii) {
				for (int j = viewY; j < viewY + lenY; ++j) {
					for (int jj = (j-viewY)*zoom; jj < (j-viewY+1)*zoom; ++jj){
						imgPip.drawPixel(ii, jj, grid.getCell(i, j).getColour());
					}
				}
			}
		}
		
		imgPip.repaint();
	}
	
	public class MouseHandler extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				grid.setCell(e.getX(), e.getY(), 1.0f);
				update();
				System.out.println("click" + e.getX() + e.getY());
			}
			if (e.getButton() == MouseEvent.BUTTON2) {
				//calcPipCoords(e.getX(), e.getY());
				pipCenterX = e.getX();
				pipCenterY = e.getY();
				perspectiveReady = false;
			}
		}

	}
	
	public static void main(String[] args) {
		FluidScreen flScreen = new FluidScreen();
		flScreen.createAndShowGUI();
		flScreen.update();
		flScreen.randomGrid();
		flScreen.time.schedule(flScreen.updateTask, 100, 10);
	}
}
