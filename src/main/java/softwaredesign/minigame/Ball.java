package softwaredesign.minigame;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Ball extends JPanel {

	private final Random random = new Random();
	private static final int BALL_SIZE = 90;
	private final transient Image ballImage = new ImageIcon("src/main/java/softwaredesign/images/soccerballminigame.png").getImage();
	private int ballX = GamePanel.SCREE_WIDTH / 2;
	private int ballY = GamePanel.SCREEN_HEIGHT / 2;
	private int deltaX = 2;
	private int deltaY = 2;
	private int playOneScore = 0;
	private int playerTwoScore = 0;
	private final Players players;

	public Ball(Players players) {
		this.players = players;
	}

	public int addOff(ballMovement toMove) {
		if (toMove == ballMovement.UP) {
			deltaY = -Math.abs(deltaY);
			return deltaY;
		}
		if (toMove == ballMovement.DOWN) {
			deltaY = Math.abs(deltaY);
			return deltaY;
		}
		if (toMove == ballMovement.LEFT) {
			deltaX = random.nextInt(6) - 5;
			return deltaX >= -4 ? -5 : deltaX;
		}
		if (toMove == ballMovement.RIGHT) {
			deltaX = random.nextInt(6) + 5;
			return deltaX;
		}
		return 0;
	}

	public void moveBall(){
		ballY += deltaY;
		ballX += deltaX;

		// check upper and lower wall
		if(ballY + BALL_SIZE + 50 >= GamePanel.SCREEN_HEIGHT){
			ballY += addOff(ballMovement.UP);
		}else if(ballY <= 0){
			ballY += addOff(ballMovement.DOWN);
		}

		// check left and right wall
		int goalStart = 460;
		int goalEnd = 300;

		if(ballX <= 0 && (ballY >= goalStart || ballY <= goalEnd)){
			ballX += addOff(ballMovement.RIGHT);
		}else if(ballX + BALL_SIZE >= GamePanel.SCREE_WIDTH && (ballY >= goalStart || ballY <= goalEnd)){
			ballX += addOff(ballMovement.LEFT);
		}

		// check goal
		if(ballX + BALL_SIZE >= GamePanel.SCREE_WIDTH && (ballY <= goalStart && ballY >= goalEnd)) {
			playOneScore += 1;
			ballY = GamePanel.SCREEN_HEIGHT / 2;
			ballX = GamePanel.SCREE_WIDTH / 2;

			deltaX = 1;
			deltaY = 1;

		}

		if(ballX <= 0 && (ballY <= goalStart && ballY >= goalEnd)){
			playerTwoScore += 1;
			ballY = (GamePanel.SCREEN_HEIGHT / 2);
			ballX = (GamePanel.SCREE_WIDTH / 2);

			deltaX = -1;
			deltaY = -1;
	    }

		collisionMechanisms();
	}

	public void collisionMechanisms() {
		if (hitboxBall().intersects(players.hitboxPlayerOne())) {
			int hitSpeed = random.nextInt(3) - 2;
			if (hitSpeed >= 0) {
				hitSpeed = -1;
			}

			ballX += addOff(ballMovement.RIGHT);
			ballY += hitSpeed;
		} else if (hitboxBall().intersects(players.hitboxPlayerTwo())) {
			int hitSpeed = random.nextInt(3) + 1;

			ballX += addOff(ballMovement.LEFT);
			ballY += hitSpeed;
		}
	}

	@Override
	public void paint(Graphics g){

		super.paint(g);
		g.setFont(new Font("Arial", Font.BOLD, 48));

		g.setColor(Color.BLACK);
		g.drawString(Integer.toString(playOneScore), 130, 550);

		g.setColor(Color.BLACK);
		g.drawString(Integer.toString(playerTwoScore), 830, 550);

		g.drawImage(ballImage, ballX, ballY, null);

	}

	public Rectangle hitboxBall(){
		return new Rectangle(ballX, ballY, BALL_SIZE, BALL_SIZE);
	}

}
