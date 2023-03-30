package softwaredesign.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GUISelectChar extends DefaultScene {

    private static final String[] imageFiles = {
            "src/main/java/softwaredesign/images/cristianobasic.png",
            "src/main/java/softwaredesign/images/messibasic.png"
    };

    private static int currentImageIndex = 0;

    public static void runGUI(Runnable callback) {

        JFrame frame = createWindow();

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                if (callback != null) {
                    callback.run();
                }
            }
        });

        try {
            setContentPaneWithImage(frame);
            createImageCarousel(frame);
            addTextAboveCarousel(frame);
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame.setVisible(true);

    }

    private static void createImageCarousel(JFrame frame) {

        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBounds(75, 75, 350, 350);
        frame.getContentPane().add(imagePanel);

        JLabel imageLabel = new JLabel();
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        setImage(imageLabel, currentImageIndex);

        imageLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                System.out.println("Image clicked: " + imageFiles[currentImageIndex]);

                // Find the parent JFrame of the imageLabel
                Component component = (Component) e.getSource();
                while (!(component instanceof JFrame) && component != null) {
                    component = component.getParent();
                }

                // If a JFrame is found, dispose of it and close the GUI
                if (component != null) {
                    JFrame frame = (JFrame) component;
                    frame.dispose();
                }
            }
        });

        JButton leftButton = new JButton("<");
        leftButton.addActionListener(e -> {
            currentImageIndex = (currentImageIndex - 1 + imageFiles.length) % imageFiles.length;
            setImage(imageLabel, currentImageIndex);
        });
        imagePanel.add(leftButton, BorderLayout.WEST);

        JButton rightButton = new JButton(">");
        rightButton.addActionListener(e -> {
            currentImageIndex = (currentImageIndex + 1) % imageFiles.length;
            setImage(imageLabel, currentImageIndex);
        });
        imagePanel.add(rightButton, BorderLayout.EAST);
    }

    private static void setImage(JLabel imageLabel, int index) {
        try {
            Image image = ImageIO.read(new File(imageFiles[index]));
            imageLabel.setIcon(new ImageIcon(image));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addTextAboveCarousel(JFrame frame) {
        JLabel textLabel = new JLabel("Select your character. But remember: The choice is irreversible!");
        textLabel.setBounds(15, 35, 470, 25);
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        textLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
        textLabel.setBackground(Color.WHITE);
        textLabel.setOpaque(true);
        frame.getContentPane().add(textLabel);
    }
}
