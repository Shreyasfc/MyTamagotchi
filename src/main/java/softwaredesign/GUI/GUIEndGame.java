package softwaredesign.GUI;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GUIEndGame extends GUI {

    public static void runGUI() {

        JFrame frame = createWindow();

        try {
            setContentPaneWithImage(frame);
            addGameOverText(frame);
            addQuitGameButton(frame);
        } catch (IOException e) {
            e.printStackTrace();
        }

        frame.setVisible(true);

    }

    private static void addGameOverText(JFrame frame) {
        JLabel gameOverLabel = new JLabel("Game Over :(");
        gameOverLabel.setBounds(175, 200, 150, 30);
        gameOverLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gameOverLabel.setFont(new Font("Dialog", Font.BOLD, 20));
        gameOverLabel.setBackground(Color.WHITE);
        gameOverLabel.setOpaque(true);
        frame.getContentPane().add(gameOverLabel);
    }

    private static void addQuitGameButton(JFrame frame) {
        JButton quitGameButton = new JButton("Quit game");
        quitGameButton.setBounds(200, 250, 100, 30);
        quitGameButton.addActionListener(e -> frame.dispose());
        frame.getContentPane().add(quitGameButton);
    }

}
