package softwaredesign.minigame;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel{

	static final int SCREE_WIDTH = 1000;
	static final int SCREEN_HEIGHT = 752;

	private final Players players;

	private final Ball puck;

	public GamePanel(Players players){

		this.players = players;
		this.puck = new Ball(players);

		// Adds the keylistener
		this.setFocusable(true);

	}

	//paints the background, pads, and the puck, (the background comes with the pads)
	@Override
	public void paint(Graphics g){
		players.paint(g);
		puck.paint(g);
	}

	//for when you move the pads or puck
	public void move() {
		players.movePlayers();
		puck.moveBall();
	}

}