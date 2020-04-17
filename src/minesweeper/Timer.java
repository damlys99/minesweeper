package minesweeper;

import java.awt.Color;
import java.awt.Font;

@SuppressWarnings("serial")
public class Timer extends ACanvas {
	final static short WIDTH=(short) (25*Minesweeper.difficulty);
	final static short HEIGHT = 50;
	long timeStart;
	long time;
	@Override
	public void drawImage() {
		timeAreaDraw();
		timeDraw();
		bombCounter();
		
	}
	private void timeAreaDraw() {
		g.setColor(Color.black);
		g.fillRect(0,0,Area.WIDTH,50);
		
	}
	private void timeDraw() {
		g.setColor(Color.white);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 32)); 
		g.drawString(timeConvert(timeSet()),Area.WIDTH-82,48);
	}
	private void bombCounter() {
		g.setColor(Color.red);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 32)); 
		g.fillOval(5,23, 25,25);
		g.setColor(Color.white);
		g.drawString(""+Area.bombcount,32,48);
	}
	
	int timeSet() {
		timeStart=Minesweeper.timeS;
		time=System.nanoTime()-timeStart;
		return (int) (time/1000000000);
	}
	String timeConvert(int seconds) {
		int minutes = seconds/60;
		return String.format("%02d:%02d", minutes,seconds%60);
	}

	Timer(){
		super(WIDTH,HEIGHT);
		
	}

}
