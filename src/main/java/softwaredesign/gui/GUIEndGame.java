package softwaredesign.gui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GUIEndGame implements GUI {

    private final OnGuiClosedCallback onGuiClosedCallback;

    public GUIEndGame(OnGuiClosedCallback onGuiClosedCallback){

        this.onGuiClosedCallback = onGuiClosedCallback;

    }

    @Override
    public void customizeGUI(JFrame frame) {

        addGameOverText(frame);
        addQuitGameButton(frame);

    }

    private void addGameOverText(JFrame frame) {
        JLabel gameOverLabel = new JLabel("Game Over :(");
        gameOverLabel.setBounds(175, 200, 150, 30);
        gameOverLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gameOverLabel.setFont(new Font("Dialog", Font.BOLD, 20));
        gameOverLabel.setBackground(Color.WHITE);
        gameOverLabel.setOpaque(true);
        frame.getContentPane().add(gameOverLabel);
    }

    private void addQuitGameButton(JFrame frame) {
        JButton quitGameButton = new JButton("Quit game");
        quitGameButton.setBounds(200, 250, 100, 30);
        quitGameButton.addActionListener(e -> {
            frame.dispose();
            try {
                onGuiClosedCallback.onGuiClosed();
            } catch (IOException ex) {
                throw new GuiCloseException("Failed to close GUI", ex);
            }
        });
        frame.getContentPane().add(quitGameButton);
    }

}
