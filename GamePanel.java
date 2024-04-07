import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

/**************************************
*********** Jeux de Briques **********
******** Travail réealisé par: *******
**				    **
** BOUZIANI Kheir Eddine - Groupe 2 **
** BRAHIMI Imad Eddine - Groupe 2   **
**				    **
**************************************
**************************************/

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements ActionListener{
	
	private boolean play = false;
	private boolean firstTime = true;
	private boolean pause = false;
	private boolean win = false;
	
	private int level = 1;
	private int score = 0;	
	private int totalBriks;	
	private int delay = 8;
	private int playerX = 297;
	
	private int ballposX = 125;
	private int ballposY = 400;
	private int ballXdir = -1;
	private int ballYdir = -2;
	
	private Timer timer;
	
	private GameGridGenerator gridGenerator;
	
	public GamePanel() {
		gridGenerator = new GameGridGenerator(level);
		
		setFocusable(true);
		setPreferredSize(new Dimension(701, 674));
		
		timer = new Timer(delay, this);
  		timer.start();
  		
		firstTime = true;
		totalBriks = gridGenerator.getTotalBricks();
		
		addKeyListener(keyListener);
		addMouseMotionListener(mouseMotionListener);
	}
	
	public KeyListener keyListener =  new KeyAdapter() {
        @Override 
        public void keyPressed( KeyEvent e ) {
        	if(play) {
        		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
        			if (playerX >= 600) {
        				playerX = 600;
        			} else {
        				moveRight(30);
        			}				
        		}
        		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
        			if (playerX < 10) {
        				playerX = 10;
        			} else {
        				moveLeft(30);
        			}				
        		}          		
        	} 
        	if(e.getKeyCode() == KeyEvent.VK_ENTER) {
        		if(firstTime) {
        			firstTime = false;
        		} else if (win) {
        			level++;
        			if (level>3) level = 1;
        			restart(level);
        		} else if (gameOver()) {
        			restart(level);     			
        		}
    		}
        	if (e.getKeyCode() == KeyEvent.VK_SPACE) {
        		if(play) {
        			pause = true;
        			play = false;
        			repaint();
        		} else if(pause) {
        			play = true;
        			pause = false;
        		}
    		}  
        }
    };
	
	public MouseMotionListener mouseMotionListener = new MouseMotionAdapter() {			
		@Override
		public void mouseMoved(MouseEvent e) {		
			if(play) {
				if (e.getPoint().getX() >= 650) {
    				playerX = 592;
    			} else if (e.getPoint().getX() <= 50) {
    				playerX = 0;
    			} else {
    				playerX = (int) e.getPoint().getX() - 50;
    			}
			}						
		}
	};

	public void paint(Graphics gSimple) {
		
		Graphics2D g = (Graphics2D) gSimple;
		
		g.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );

		/************ Background ************/
		g.setColor(Color.decode("#170312"));
		g.fillRect(1,  1,  692,  592);		 
		/*************** Grid ***************/
		gridGenerator.draw(g);
		/************** Borders *************/
		g.setColor(Color.decode("#C59217"));
		g.fillRect(0,  0, 3, 596);
		g.fillRect(0,  0, 692, 3);
		g.fillRect(692,  0, 3, 596);
		g.fillRect(0,  593, 692, 3);	
		/*************** Score **************/
		if(!firstTime && !gameOver() && totalBriks > 0) {
			g.setColor(new Color(195,242,181));
			g.setFont(new Font("serif", Font.BOLD, 25));
			g.drawString("Score : "+score, 560, 30);
			g.drawString("Briques à casser : "+totalBriks, 30, 30);
		}
		
		g.setStroke(new BasicStroke(1));
		
		if( !gameOver() && !win) {
			/************** Paddle *************/
			g.setColor(new Color(251,80,18));
			g.fillRect(playerX, 550, 100, 8);
			/*************** Ball **************/
			g.setColor(new Color(195,242,181));
			g.fillOval(ballposX, ballposY, 20, 20);
		}		
		/*************** Pause **************/
		if(play) {
			g.setColor(new Color(195,242,181));
			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Appuyez sur Espace pour mèttre en Pause", 165, 580);
			g.drawRect(272, 563, 65, 23);
		}					
		/*************** Resume **************/
		if(pause && !play && !gameOver() && !win) {
			g.setColor(Color.decode("#C59217"));
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("Appuyez sur Espace pour reprendre", 123, 300);
			g.drawRect(285, 270, 97, 40);			
		}
		
		if(firstTime) {
			
			g.setColor(Color.decode("#C59217"));
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("JEUX DE BRIQUES", 210, 480);
		}
		
		if(totalBriks <= 0) {
			win = true;
			play = false;
			firstTime = false;
			ballXdir = 0;
			ballYdir = 0;
			if(level < 3) {
				g.setColor(new Color(255,215,0));
				g.drawRect(130, 270, 440, 150);				
				
				g.setFont(new Font("serif", Font.BOLD, 25));
				g.drawString("BIEN JOUÉ!", 275, 320);
				
				g.setFont(new Font("serif", Font.BOLD, 20));
				g.drawString("Appuyez sur Entrée pour passer au niveau suivant", 140, 380);
				g.drawRect(247, 360, 62, 27);
				
			} else {
				g.setColor(new Color(255,215,0));
				g.drawRect(150, 250, 400, 200);				
				
				g.setFont(new Font("serif", Font.BOLD, 30));
				g.drawString("BRAVO!", 290, 300);
				g.drawString("Vous avez gagné", 245, 350);
				
				g.setFont(new Font("serif", Font.BOLD, 20));
				g.drawString("Appuyez sur Entrée pour rejouer", 215, 420);
				g.drawRect(322, 400, 62, 27);
			}			
		}
		
		if(gameOver()) {
			win = false;
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			
			g.setColor(Color.red);
			g.drawRect(150, 250, 400, 200);
			
			g.setColor(Color.red);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("Game Over", 280, 300);
			
			g.setFont(new Font("serif", Font.BOLD, 25));
			g.drawString("Score: "+score+", Briques restantes: "+totalBriks, 180, 350);
			
			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Appuyez sur Entrée pour recommencer", 185, 420);
			g.drawRect(292, 400, 62, 27);
			firstTime = false;
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {		
		if(play) {
			Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
			Rectangle paddleRect = new Rectangle(playerX, 550, 100, 8);
			if(ballRect.intersects(paddleRect)) {
				ballYdir = -ballYdir;
			}
			
			A: for(int i = 0; i<gridGenerator.grid.length; i++) {
					for(int j = 0; j<gridGenerator.grid[0].length; j++) {
						if(gridGenerator.grid[i][j] == 1) {
							int brickWidth = gridGenerator.brickWidth;
							int brickHeight = gridGenerator.brickHeight;
							int brickX = j * brickWidth + gridGenerator.hgap;
							int brickY = i * brickHeight + gridGenerator.vgap;							
							
							Rectangle brickRect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
							
							if(ballRect.intersects(brickRect)) {
								gridGenerator.setBrickValue(0, i, j);
								totalBriks--;
								score += 5;
								
								if(ballposX + 19 <= brickRect.x || ballposX +1 >= brickRect.x + brickRect.width) {
									ballXdir = -ballXdir;
								} else {
									ballYdir = -ballYdir;
								}
								break A;
							}
						}
					}
			}			
			ballposX += ballXdir;
			ballposY += ballYdir;
			if(ballposX < 0) {
				ballXdir = -ballXdir;
			}
			if(ballposY < 0) {
				ballYdir = -ballYdir;
			}
			if(ballposX > 670) {
				ballXdir = -ballXdir;
			}
		}	
		
		repaint();		
	}
	
	public void moveRight (int step) {
		play = true;
		playerX += step;
	}
	
	public void moveLeft (int step) {
		play = true;
		playerX -= step;
	}
	
	public void gamePlayOptions(boolean pause, boolean play) {
		if( !firstTime ) {
			this.pause = pause;
			this.play = play;
		}
	}
	
	public boolean gameOver() {
		return ( (ballposY > 552) && (totalBriks > 0) );
	}
	
	public int getDelay() {
		return delay;
	}
	
	public void setDelay(int delay) {
		this.delay = delay;
	}
	
	public void setTimer(int delay) {
		setDelay(delay);
		timer.setDelay(delay);
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public int getLevel() {
		return level;
	}
	
	public boolean isFirstTime() {
		return firstTime;
	}
	
	public void setFirstTime(boolean firstTime) {
		this.firstTime = firstTime;
	}
	
	public void restart(int level) {
		win = false;
		firstTime = false;
		play = true;
		pause = false;
		ballposX = 200;
		ballposY = 350;
		ballXdir = -1;
		ballYdir = -2;
		playerX = 297;
		score = 0;
		gridGenerator = new GameGridGenerator(level);
		totalBriks = gridGenerator.getTotalBricks();
		
		repaint();
	}	
	
}
