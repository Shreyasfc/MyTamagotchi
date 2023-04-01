package softwaredesign.minigame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Players extends JPanel{

	private static final int PLAYER_WIDTH = 100;
	private static final int PLAYER_HEIGHT = 100;

	private final transient Image backgroundImage = new ImageIcon("src/main/java/softwaredesign/images/fieldminigame.png").getImage();
	private final transient Image playerOneImage = new ImageIcon("src/main/java/softwaredesign/images/messiminigame.png").getImage();
	private final transient Image playerTwoImage = new ImageIcon("src/main/java/softwaredesign/images/ronaldominigame.png").getImage();

	private int playerOneX = 1;
	private int playerOneY = GamePanel.SCREEN_HEIGHT / 2;
	private int playerTwoX = GamePanel.SCREEN_WIDTH - PLAYER_WIDTH - 1;
	private int playerTwoY = GamePanel.SCREEN_HEIGHT / 2;
	private int deltaXPlayerOne = 0;
	private int deltaXPlayerTwo = 0;
	private int deltaYPlayerOne = 0;
	private int deltaYPlayerTwo = 0;

	public void parseUserInput (KeyEvent e){

		switch (e.getKeyCode()) {
			case KeyEvent.VK_W:
				deltaXPlayerOne = -3;
				break;
			case KeyEvent.VK_A:
				deltaYPlayerOne = -2;
				break;
			case KeyEvent.VK_S:
				deltaXPlayerOne = 3;
				break;
			case KeyEvent.VK_D:
				deltaYPlayerOne = 2;
				break;
			case KeyEvent.VK_UP:
				deltaXPlayerTwo = -3;
				break;
			case KeyEvent.VK_LEFT:
				deltaYPlayerTwo = -2;
				break;
			case KeyEvent.VK_DOWN:
				deltaXPlayerTwo = 3;
				break;
			case KeyEvent.VK_RIGHT:
				deltaYPlayerTwo = 2;
				break;
			default:
				break;
		}

	}

	public void processUserInput(KeyEvent e){

		switch (e.getKeyCode()) {
			case KeyEvent.VK_W:
				deltaXPlayerOne = 0;
				break;
			case KeyEvent.VK_A:
				deltaYPlayerOne = 0;
				break;
			case KeyEvent.VK_S:
				deltaXPlayerOne = 0;
				break;
			case KeyEvent.VK_D:
				deltaYPlayerOne = 0;
				break;
			case KeyEvent.VK_UP:
				deltaXPlayerTwo = 0;
				break;
			case KeyEvent.VK_DOWN:
				deltaXPlayerTwo = 0;
				break;
			case KeyEvent.VK_LEFT:
				deltaYPlayerTwo = 0;
				break;
			case KeyEvent.VK_RIGHT:
				deltaYPlayerTwo = 0;
				break;
			default:
				break;
		}

	}

	public void movePlayers(){

		playerOneY += deltaXPlayerOne;
		playerTwoY += deltaXPlayerTwo;

		playerOneX += deltaYPlayerOne;
		playerTwoX += deltaYPlayerTwo;

		//hit top or bottom wall
		if(playerOneY + PLAYER_HEIGHT > GamePanel.SCREEN_HEIGHT){
			deltaXPlayerOne = -1;
		}else if(playerOneY <= 0){
			deltaXPlayerOne = 1;
		}

		if(playerTwoY + PLAYER_HEIGHT >= GamePanel.SCREEN_HEIGHT){
			deltaXPlayerTwo = -1;
		}else if(playerTwoY <= 0){
			deltaXPlayerTwo = 1;
		}

		// hit left or right wall
		if(playerOneX + PLAYER_WIDTH >= GamePanel.SCREEN_WIDTH){
			deltaYPlayerOne = -1;
		}else if(playerOneX <= 0){
			deltaYPlayerOne = 1;
		}

		if(playerTwoX + PLAYER_WIDTH >= GamePanel.SCREEN_WIDTH){
			deltaYPlayerTwo = -1;
		}else if(playerTwoX <= 0){
			deltaYPlayerTwo = 1;
		}

	}

	//This is for collision purposes, aka if the puck hits the pad, bounce the puck back
	public Rectangle hitboxPlayerOne(){
		return new Rectangle(playerOneX, playerOneY, PLAYER_WIDTH, PLAYER_HEIGHT);
	}

	public Rectangle hitboxPlayerTwo(){
		return new Rectangle(playerTwoX, playerTwoY, PLAYER_WIDTH, PLAYER_HEIGHT);
	}

	//Draws both of the pads
	@Override
	public void paint(Graphics g){
		super.paint(g);
		//the Background
		g.drawImage(backgroundImage, 0, 0, null);
		g.drawImage(playerOneImage, playerOneX, playerOneY, null);
		g.drawImage(playerTwoImage, playerTwoX, playerTwoY, null);
	}

}