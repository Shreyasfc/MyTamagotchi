package softwaredesign.minigame;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel{

	static final int SCREEN_WIDTH = 1000;
	static final int SCREEN_HEIGHT = 752;

	private final Players currentPlayers;

	private final Ball currentBall;

	public GamePanel(Players players){

		this.currentPlayers = players;
		this.currentBall = new Ball(players);

		// Adds the keylistener
		this.setFocusable(true);

	}

	//paints the background, pads, and the puck, (the background comes with the pads)
	@Override
	public void paint(Graphics g){
		currentPlayers.paint(g);
		currentBall.paint(g);
	}

	//for when you move the pads or puck
	public void move() {
		currentPlayers.movePlayers();
		currentBall.moveBall();
	}

}