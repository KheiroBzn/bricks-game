import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

/**************************************
*********** Jeux de Briques **********
******** Travail réealisé par: *******
**				              **
** BOUZIANI Kheir Eddine            **
**				              **
**************************************
**************************************/

public class GameMain {

	public static void main(String[] args) throws UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(new NimbusLookAndFeel());
		GameFrame gameWindow = new GameFrame();
		gameWindow.setVisible(true);
	}
}
