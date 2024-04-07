import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

/**************************************
*********** Jeux de Briques **********
******** Travail réealisé par: *******
**				    **
** BOUZIANI Kheir Eddine - Groupe 2 **
** BRAHIMI Imad Eddine - Groupe 2   **
**				    **
**************************************
**************************************/

public class GameGridGenerator {
	
	public int grid[][];
	public int brickWidth;
	public int brickHeight;
	public int hgap;
	public int vgap;
	private int row;
	private int col;
	private int width;
	private int height;
	
	
	public GameGridGenerator(int level) {
		switch(level) {
		case 1: row = 3; col = 7; width = 490; height = 75; hgap =  105; vgap = 105;
			break;
		case 2:  row = 4; col = 11; width = 600; height = 100; hgap =  50; vgap = 50;
			break;
		case 3:  row = 6; col = 13; width = 650; height = 150; hgap =  25; vgap = 40;
			break;
		default:  row = 3; col = 7; width = 490; height = 75; hgap =  105; vgap = 105;
			break;
		}
		grid = new int[row][col];
		for(int i = 0; i<grid.length; i++) {
			for(int j = 0; j<grid[0].length; j++) {
				grid[i][j] = 1;
			}
		}
		brickWidth = width/col; // df_width 600
		brickHeight = height/row;	// df_height 105	
	}
	
	public void draw(Graphics2D g) {
		for(int i = 0, color = 0; i<grid.length; i++) {
			for(int j = 0; j<grid[0].length; j++) {
				if(color >= 5) color = 0;
				if(grid[i][j] > 0) {					
					switch(color) {
					case 0 : g.setColor(Color.decode("#C59217")); color++;
						break;
					case 1 : g.setColor(Color.decode("#B06914")); color++;
						break;						
					case 2 : g.setColor(Color.decode("#814D0F")); color++; 
						break;
					case 3 : g.setColor(Color.decode("#D2AA4B")); color++;
						break;
					case 4 : g.setColor(Color.decode("#CA7700")); color = 0;
						break;	
					default : color = 0;
						break;
					}					
					g.fillRect(j * brickWidth + hgap, i * brickHeight + vgap, brickWidth, brickHeight);					
					g.setStroke(new BasicStroke(2));
					g.setColor(Color.decode("#170312"));
					g.drawRect(j * brickWidth + hgap, i * brickHeight + vgap, brickWidth, brickHeight);
				} else color++;
			}
		}
	}
	
	public void setBrickValue(int value, int row, int col) {
		grid[row][col] = value;		
	}
	
	public int getTotalBricks() {
		return row * col;
	}

}
