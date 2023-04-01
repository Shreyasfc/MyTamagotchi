package softwaredesign.minigame;

import softwaredesign.gui.OnGuiClosedCallback;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class MiniGameController {

	private final JFrame frame = new JFrame();

	private final OnGuiClosedCallback onGuiClosedCallback;

	public MiniGameController(OnGuiClosedCallback onGuiClosedCallback) {
		this.onGuiClosedCallback = onGuiClosedCallback;
	}

	private void setUpFrame(Players players){

		KeyInput mainPanel = new KeyInput(players);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.getContentPane().add(mainPanel);
		frame.setSize(GamePanel.SCREEN_WIDTH,GamePanel.SCREEN_HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setTitle("Football");

		// Add a WindowAdapter to handle window closing event
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					onGuiClosedCallback.onGuiClosed();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		});

	}

	public void initGame(){

		Players players = new Players();
		setUpFrame(players);
		GamePanel game = new GamePanel(players);
		frame.add(game);
		frame.setVisible(true);

		Timer timer = new Timer(
				5, e -> {
					game.move();
					game.repaint();
				}
		);
		timer.start();

	}

}
