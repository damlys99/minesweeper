package minesweeper;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Minesweeper extends JPanel implements Runnable{

	private static final long serialVersionUID = -9170425595899214912L;
	
	static Minesweeper tetris = new Minesweeper();
	static JFrame window = new JFrame();
	static Area area;
	static Timer timer;
	final static short SIZE = 300;
	static Thread thread = new Thread(tetris);
	boolean start;
	static long timeS;
	static long timeSt;
	static long timeE;

	public static short difficulty=10;


	
	Minesweeper(){
		super();
		setBackground(Color.darkGray);
		setLayout(null);
		start = false;
	}

	public static void main(String[] args) {
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(tetris);
		window.setSize(0,0);
		window.setLocation(600,0);
		window.setResizable(false);

		thread.start();
		
	}
	private void begin() {
		String input="0";
		while(Integer.parseInt(input)<6 || Integer.parseInt(input)>25)
		input = JOptionPane.showInputDialog("Set difficulty( 6-25 )");
		difficulty= (short) Integer.parseInt(input);
		area = new Area();
		area.setLocation(0,50);
		tetris.add(area);
		timer = new Timer();
		timer.setLocation(0,0);
		tetris.add(timer);
		
		window.setSize(difficulty*25+6,difficulty*25+79);
		window.setVisible(true);
		timeS=System.nanoTime();
		//start=true;

	}
	@SuppressWarnings("static-access")
	@Override
	public void run() {
		begin();
		while(!start) {
			area.run();
			timer.run();
			if(area.isLost()) {
				JOptionPane.showMessageDialog(null, "You lost.");
		        System.exit(0);
			}
			if(area.ifWon()) {
				JOptionPane.showMessageDialog(null, "You won, your time: "+timer.timeConvert(timer.timeSet()));
		        System.exit(0);
			}
			try {thread.sleep(10);}catch (InterruptedException e) { e.printStackTrace();}
		}
		
	}

}
