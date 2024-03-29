package softwaredesign.gui;

import javax.swing.*;
import java.awt.*;

interface GUI {

    default void startAndRunGUI()  {

        JFrame frame = createWindow();

        setContentPaneWithImage(frame);

        customizeGUI(frame);

        frame.setVisible(true);

    }

    default void customizeGUI(JFrame frame) {

    }

    private JFrame createWindow() {
        JFrame frame = new JFrame("Tamagotchi");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        return frame;
    }

    private void setContentPaneWithImage(JFrame frame)  {
        Image backgroundImage = new ImageIcon("src/main/java/softwaredesign/images/background.png").getImage();
        JLabel backgroundLabel = new JLabel(new ImageIcon(backgroundImage));
        backgroundLabel.setBounds(0, 0, backgroundImage.getWidth(null), backgroundImage.getHeight(null));
        frame.setContentPane(backgroundLabel);
    }

}
