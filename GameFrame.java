import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.border.Border;

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
public class GameFrame extends JFrame {
	
	private GamePanel gamePanel;
	private int level;
	private int delay = 8;
	private int speed = 2;
	private boolean welcome;
	Timer timer;
	
	public GameFrame() {
		this.gamePanel = new GamePanel();
		level = gamePanel.getLevel();
		this.setTitle("Jeux de Briques");
		this.setSize(701, 674);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		timer = new Timer(delay, gamePanel);
		timer.start();
		
		this.add(createMenuBar(), BorderLayout.NORTH);
		this.add(gamePanel, BorderLayout.CENTER);
		this.add(createStatuBar(), BorderLayout.SOUTH);
		
		welcome = true;
		GameSetting(welcome);
	}
	
	private JMenuBar createMenuBar() {
		
		JMenuBar menuBar = new JMenuBar();
		
		MouseListener menuListener = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				if( !gamePanel.gameOver() ) {
					gamePanel.gamePlayOptions(true, false);
				}				
			}			
			@Override
			public void mouseReleased(MouseEvent arg0) {
					gamePanel.gamePlayOptions(false, true);				
			}
		};
		/**************************************************/
		JMenu menuOption = new JMenu("Options");
		menuOption.addMouseListener(menuListener);
	
		JMenuItem menuNew = new JMenuItem("Nouvelle Partie");		
		menuNew.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK) );
		menuNew.addActionListener( this::menuNewListener );		
		menuOption.add(menuNew);
		
		JMenuItem menuExit = new JMenuItem("Qutter");	
		menuExit.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK) );
		menuExit.addActionListener( this::menuExitListener );
		menuOption.add(menuExit);
		menuBar.add(menuOption);
		/**************************************************/
		JMenu menuHelp = new JMenu("Aides");
		menuHelp.addMouseListener(menuListener);
	
		JMenuItem menuAbout = new JMenuItem("A propos");		
		menuAbout.addActionListener( this::menuAboutListener );
		menuHelp.add(menuAbout);
		menuBar.add(menuHelp);
		/**************************************************/
		return menuBar;
	}
	
	private void menuNewListener(ActionEvent event) {
		gamePanel.gamePlayOptions(true, false);
		welcome = false;
		GameSetting(welcome);
	}

	private void menuAboutListener(ActionEvent event) {
		JOptionPane.showMessageDialog(this, 
				"Jeux de Briques\nL3 Informatique UABT.\n\u00A9 2020 ,Tous droits réservés.", "A propos",
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	private void menuExitListener(ActionEvent event) {
		dispose();
	}	
	
	private JPanel createStatuBar() {
		JPanel statuBar = new JPanel();
		JLabel label = new JLabel("\u00A9 2020 ,Tous droits réservés.");
		statuBar.add(label);
		
		return statuBar;
	}
	
	private void GameSetting(boolean welcome) {
		String levelOptions[] =	{"Facile (3 X 7)", "Moyen (4 X 11)", "Difficile (6 X 13)"};	
		String speedOptions[] =	{"Lente", "Moyenne", "Rapide"};	
		Runnable runner = new Runnable() {
			@SuppressWarnings("deprecation")
			public void run() {
				JFrame frame = new JFrame("Paramètres de jeux");
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				
				JPanel levelsPanel = new JPanel(new GridLayout(0, 1));
				levelsPanel.setPreferredSize(new Dimension(148, 180));
				Border levelsPanelBorder = BorderFactory.createTitledBorder("Niveau de jeux");
				levelsPanel.setBorder(levelsPanelBorder);
				levelsPanel.setBackground(Color.decode("#D2AA4B"));
				
				JPanel SpeedsPanel = new JPanel(new GridLayout(0, 1));
				SpeedsPanel.setPreferredSize(new Dimension(148, 180));
				Border setSpeedPanelBorder = BorderFactory.createTitledBorder("Vitesse de jeux");
				SpeedsPanel.setBorder(setSpeedPanelBorder);
				SpeedsPanel.setBackground(Color.decode("#D2AA4B"));
				
				// Create group
				ButtonGroup levelsGroup = new ButtonGroup();
				ButtonGroup speedsGroup = new ButtonGroup();
				JRadioButton aRadioButton;
				
				JPanel btnWelcomePanel = new JPanel();
				JPanel btnPlayingPanel = new JPanel();
				
				JButton btnPlay = new JButton("Jouer");
				JButton btnExit = new JButton("Quitter");
				
				btnPlay.setMnemonic(1);
				btnExit.setMnemonic(2);
				btnWelcomePanel.add(btnPlay);
				btnWelcomePanel.add(btnExit);
				btnWelcomePanel.setBackground(Color.decode("#D2AA4B"));
				
				JButton btnApply = new JButton("Appliquer");
				JButton btnCancel = new JButton("Annuler");
				
				btnApply.setMnemonic(1);
				btnApply.enable(false);
				btnCancel.setMnemonic(2);
				btnPlayingPanel.add(btnApply);
				btnPlayingPanel.add(btnCancel);	
				btnPlayingPanel.setBackground(Color.decode("#D2AA4B"));

				ActionListener btnListener = new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						if(e.getSource() == btnApply || e.getSource() == btnPlay) {
							
							gamePanel.setTimer(delay);
							timer.setDelay(delay);
							timer.restart();
							gamePanel.setLevel(level);							
							gamePanel.restart(level);
							
							frame.dispose();
						} else if(e.getSource() == btnExit) {
							frame.dispose();
							GameFrame.this.dispose();
						}
						else {
							level = gamePanel.getLevel();
							delay = gamePanel.getDelay();
							frame.dispose();
						}
					}
				};
				
				btnPlay.addActionListener(btnListener);
				btnExit.addActionListener(btnListener);				
				
				btnWelcomePanel.add(btnPlay);				
				btnWelcomePanel.add(btnExit);	
				
				btnApply.addActionListener(btnListener);
				btnCancel.addActionListener(btnListener);				
				
				btnPlayingPanel.add(btnApply);				
				btnPlayingPanel.add(btnCancel);				
				
				ActionListener levelsListener = new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
							level = levelsGroup.getSelection().getMnemonic();
						}
				};	
				
				ActionListener speedsListener = new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
							speed = speedsGroup.getSelection().getMnemonic();
							switch(speed) {
							case 1: delay = 16;
								break;
							case 2: delay = 8;
								break;
							case 3: delay = 2;
								break;
							default : delay = 8;
							}
					}
				};	
				
				boolean selectedLevel = false;
				
				for (int i=0, n=levelOptions.length; i<n; i++) {
					if (i+1==level) selectedLevel = true;
					aRadioButton = new JRadioButton(levelOptions[i], selectedLevel);
					aRadioButton.setMnemonic(i+1);
					levelsPanel.add(aRadioButton);
					levelsGroup.add(aRadioButton);
					aRadioButton.addActionListener(levelsListener);
				}					
				
				boolean selectedSpeed = false;
				
				for (int i=0, n=speedOptions.length; i<n; i++) {
					if ((i+1) == speed) selectedSpeed = true;
					aRadioButton = new JRadioButton(speedOptions[i], selectedSpeed);
					aRadioButton.setMnemonic(i+1);
					SpeedsPanel.add(aRadioButton);
					speedsGroup.add(aRadioButton);
					aRadioButton.addActionListener(speedsListener);
				}	
				
				frame.add(levelsPanel, BorderLayout.WEST);
				frame.add(SpeedsPanel, BorderLayout.EAST);
				if(welcome) {
					frame.add(btnWelcomePanel, BorderLayout.SOUTH);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				} else {
					frame.add(btnPlayingPanel, BorderLayout.SOUTH);
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				}
				
				frame.setSize(300, 200);
				frame.setResizable(false);
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		};
		EventQueue.invokeLater(runner);
	}
}
