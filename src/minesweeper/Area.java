package minesweeper;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.SwingUtilities;


@SuppressWarnings("serial")
public class Area extends ACanvas implements MouseListener{
	
	static short difficulty=Minesweeper.difficulty;
	static short SIZE=25;
	static short WIDTH=	(short) (SIZE* difficulty);;
	boolean lost;
	boolean won;
	boolean start=false;
	static short bombcount;

	Random gen = new Random();
	
	final Color[] COLOR = {Color.white,Color.green,Color.cyan,Color.magenta,Color.red};
	
	boolean[][] revealed;
	byte[][] tab;
	boolean[][] signed;

	Area(){
		super(WIDTH,WIDTH);
		this.addMouseListener(this);
		SIZE=25;

		revealed = new boolean[difficulty][difficulty];
		tab = new byte[difficulty][difficulty];
		signed = new boolean[difficulty][difficulty];
		bombcount = difficulty;
	}

	@Override
	public void drawImage() {
		drawArea();
		drawValues();

		drawRevealed();
		drawSigned();
		
	}
	
	private void drawArea() {
		for(byte x=0;x<difficulty;x++)
			for(byte y=0;y<difficulty;y++) {
				g.setColor(Color.gray);
				for (int i = 0; i < 4; i++) {
					g.fill3DRect(x*SIZE+i, y*SIZE+i, SIZE-2*i, SIZE-2*i,true);
			}
				g.setColor(Color.white);
				g.drawRect(x*SIZE, y*SIZE, SIZE-1, SIZE-1);
			}
	}
	
	private void drawValues() {
		for(byte x=0;x<difficulty;x++)
			for(byte y=0;y<difficulty;y++) {
				g.setColor(Color.black);
				if(tab[x][y]==-2) {drawBombs();lost=true;}
			}
	}
	
	private void drawRevealed() {
		for(byte x=0;x<difficulty;x++)
			for(byte y=0;y<difficulty;y++) {
				g.setColor(Color.gray);
				if(revealed[x][y]) 
				{	g.fillRect(x*SIZE,y*SIZE,SIZE,SIZE);
					g.setColor(Color.white);
					g.drawRect(x*SIZE, y*SIZE, SIZE-1, SIZE-1);
				}
				g.setColor(Color.black);
				if(revealed[x][y]) {
					g.setFont(new Font("TimesRoman", Font.PLAIN, 14)); 
					g.setColor(COLOR[getMineCount(x,y)]);
					g.drawString(""+getMineCount(x,y),x*SIZE+9, y*SIZE+17);
				}
			}
	}
	private void drawSigned() {
		for(byte x=0;x<difficulty;x++)
			for(byte y=0;y<difficulty;y++) {
				g.setColor(Color.orange);
				if(signed[x][y] && !revealed[x][y]) {g.fillOval(x*SIZE+SIZE/4,y*SIZE+SIZE/4, SIZE/2,SIZE/2);}
			}
		
	}
	private void drawBombs() {
		for(byte x=0;x<difficulty;x++)
			for(byte y=0;y<difficulty;y++) {
				if(tab[x][y]==-1 || tab[x][y]==-2) {
				g.setColor(Color.red);
				g.fillOval(x*SIZE+SIZE/4, y*SIZE+SIZE/4, SIZE/2, SIZE/2);
				}
			}
	}
	public void generate() {
		byte x;
		byte y;
		for(byte i=0;i<difficulty;i++) {
			x=(byte) gen.nextInt(difficulty);
			y=(byte) gen.nextInt(difficulty);
			if(tab[x][y]!=0 || revealed[x][y]) i--;
			else tab[x][y]=-1;
		}
	}
	private byte getMineCount(byte x,byte y) {
		byte count=0;
		if(x-1>=0 &&  tab[x-1][y]==-1)count++;
		if(x-1>=0 && y-1>=0 && tab[x-1][y-1]==-1)count++;
		if(y+1<=(difficulty-1) && tab[x][y+1]==-1)count++;
		if(x-1>=0 && y+1<=(difficulty-1) && tab[x-1][y+1]==-1 )count++;
		if(x+1<=(difficulty-1) && y-1>=0 && tab[x+1][y-1]==-1 )count++;
		if(y+1<=(difficulty-1) && x+1<=(difficulty-1) && tab[x+1][y+1]==-1 )count++;
		if(x+1<=(difficulty-1) && tab[x+1][y]==-1 )count++;
		if(x+1<=(difficulty-1) && y-1>=0 && tab[x][y-1]==-1 )count++;;
		return count;
	}
	
	public boolean isLost() {
		return lost;
	}
	private void reveal(int x,int y) {
		byte v = (byte) Math.round(difficulty/3);
		for(byte i=0;i<v+gen.nextInt(3);i++) {
			for(byte j=0;j<v+gen.nextInt(3);j++)
			{
				if(i+x<difficulty && j+y<difficulty)revealed[i+x][j+y]=true;
			}
		}
	}
	public static void setDifficulty(short x) {
		difficulty=x;
	}
	private void sign(int x, int y) {
		signed[x][y]=true;
	}
	private short countNonBombs() {
	
		short nonbombs=0;
		for(byte i=0;i<difficulty;i++) {
			for(byte j=0;j<difficulty;j++)
			{
				if(revealed[i][j]==false) {
					nonbombs++;
				}
			}
		}
		return nonbombs;
	}
	@Override
	public void mouseClicked(MouseEvent e) {

		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {

		
		
	}
	public boolean ifWon() {
		return won;
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton()==MouseEvent.BUTTON1) {
		if(start==false) {
			reveal(e.getX()/SIZE,e.getY()/SIZE);
			generate();
			start=true;
		}

		if((e.getX()/SIZE)>=0 && (e.getX()/SIZE)<difficulty && (e.getY()/SIZE)>=0 && (e.getY()/SIZE)<difficulty && tab[e.getX()/SIZE][e.getY()/SIZE]==-1 || tab[e.getX()/SIZE][e.getY()/SIZE]==-2) tab[e.getX()/SIZE][e.getY()/SIZE]=-2;
		else revealed[e.getX()/SIZE][e.getY()/SIZE]=true;
		}
		if(countNonBombs()-difficulty==0) {
			revealed[e.getX()/SIZE][e.getY()/SIZE]=true;
			won=true;
		}
		else if (SwingUtilities.isRightMouseButton(e)) {
			if(signed[e.getX()/SIZE][e.getY()/SIZE]== false) {
				sign(e.getX()/SIZE,e.getY()/SIZE);
			bombcount--;
			}
			else {
				bombcount++;
				signed[e.getX()/SIZE][e.getY()/SIZE]=false;
			}
		}
	}

	

}
