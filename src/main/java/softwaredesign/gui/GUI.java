package softwaredesign.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

interface GUI {

    void customizeGUI(JFrame frame);

    default void startGUI() throws IOException {

        JFrame frame = createWindow();

        setContentPaneWithImage(frame);

        customizeGUI(frame);

        frame.setVisible(true);

    }

    private JFrame createWindow() {
        JFrame frame = new JFrame("Tamagotchi");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        return frame;
    }

    private void setContentPaneWithImage(JFrame frame) throws IOException {
        Image backgroundImage = ImageIO.read(new File("src/main/java/softwaredesign/images/background.png"));
        JLabel backgroundLabel = new JLabel(new ImageIcon(backgroundImage));
        backgroundLabel.setBounds(0, 0, backgroundImage.getWidth(null), backgroundImage.getHeight(null));
        frame.setContentPane(backgroundLabel);
    }

}
