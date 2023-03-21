package softwaredesign;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

//TODO:
//- Implement GUI interface (from class diagram)
//- Restructure the code to be more organized
//- Finish animations
//- Structure the code to enable multiple models
//- Wire it up the class
//- More immutable
//- Scale with size increase

public class GUIMain {


    public static void runGUI() {
        JFrame frame = createWindow();

        try {
            setContentPaneWithImage(frame);
        } catch (IOException e) {
            e.printStackTrace();
        }

        addLabelWithImage(frame, "src/main/java/softwaredesign/images/cristianobasic.png", 135, 175, 250, 250);

        addProgressBar(frame, 90, "Hunger", 10, new Color(128, 0, 0), new Color(255, 182, 193));
        //addLabelWithImage(frame, "src/main/java/softwaredesign/images/warning.png", 155, 10, 125, 25);
        addProgressBar(frame, 85, "Hygiene", 50, new Color(0, 11, 255), new Color(204, 222, 255));
        addProgressBar(frame, 70, "Bladder", 90, new Color(96, 77, 0), new Color(236, 224, 181));
        addProgressBar(frame, 10, "Thirst", 130, new Color(6, 58, 0), new Color(225, 250, 225));
        addProgressBar(frame, 25, "Mood", 170, new Color(86, 0, 66), new Color(255, 234, 253));

        addButton(frame, "Feed", 10, e -> buttonClickAction(frame, e));
        addButton(frame, "Shower", 50, e -> buttonClickAction(frame, e));
        addButton(frame, "Pee", 90, e -> buttonClickAction(frame, e));
        addButton(frame, "Drink", 130, e -> buttonClickAction(frame, e));
        addButton(frame, "Minigame", 170, e -> buttonClickAction(frame, e));

        frame.setVisible(true);
    }

    private static JFrame createWindow() {
        JFrame frame = new JFrame("Tamagotchi");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        return frame;
    }

    private static void setContentPaneWithImage(JFrame frame) throws IOException {
        Image backgroundImage = ImageIO.read(new File("src/main/java/softwaredesign/images/background.png"));
        JLabel backgroundLabel = new JLabel(new ImageIcon(backgroundImage));
        backgroundLabel.setBounds(0, 0, backgroundImage.getWidth(null), backgroundImage.getHeight(null));
        frame.setContentPane(backgroundLabel);
    }

    private static void addLabelWithImage(JFrame frame, String imagePath, int x, int y, int width, int height) {
        ImageIcon icon = new ImageIcon(imagePath);
        JLabel label = new JLabel(icon);
        label.setBounds(x, y, width, height);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(label);
    }

    private static void addProgressBar(JFrame frame, int value, String text, int y, Color fg, Color bg) {
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setValue(value);
        progressBar.setStringPainted(true);
        progressBar.setString(text + ": " + progressBar.getValue() + "%");
        progressBar.setBounds(10, y, 150, 20);
        progressBar.setForeground(fg);
        progressBar.setBackground(bg);
        progressBar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        frame.add(progressBar);
    }

    private static void addButton(JFrame frame, String text, int y, ActionListener action) {
        JButton button = new JButton(text);
        button.setBounds(375, y, 100, 30);
        button.setPreferredSize(new Dimension(80, 30));
        frame.add(button);
        button.addActionListener(action);
    }

    private static void buttonClickAction(JFrame frame, ActionEvent e) {
        // Get all components in the frame's content pane
        Component[] components = frame.getContentPane().getComponents();

        // Disable all buttons
        for (Component component : components) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                button.setEnabled(false);
            }
        }

        JButton buttonClicked = (JButton) e.getSource();

        // Determine the progress bar to update based on the clicked button
        String progressBarToUpdate = "";
        String animationImage = "";
        int incrementVal = 0;
        switch (buttonClicked.getText()) {
            case "Feed":
                progressBarToUpdate = "Hunger";
                animationImage = "src/main/java/softwaredesign/images/chicken.png";
                incrementVal = -10;
                break;
            case "Shower":
                progressBarToUpdate = "Hygiene";
                animationImage = "src/main/java/softwaredesign/images/waterdroplet.png";
                incrementVal = 10;
                break;
            case "Pee":
                progressBarToUpdate = "Bladder";
                animationImage = "src/main/java/softwaredesign/images/toilet.png";
                incrementVal = -10;
                break;
            case "Drink":
                progressBarToUpdate = "Thirst";
                animationImage = "src/main/java/softwaredesign/images/bottle.png";
                incrementVal = -10;
                break;
            case "Minigame":
                progressBarToUpdate = "Mood";
                animationImage = "src/main/java/softwaredesign/images/chicken.png";
                incrementVal = 10;
                break;
            default:
                progressBarToUpdate = "";
                break;
        }

        // Iterate through the components and update the specified progress bar
        for (Component component : components) {
            if (component instanceof JProgressBar) {
                JProgressBar progressBar = (JProgressBar) component;
                if (progressBar.getString().startsWith(progressBarToUpdate)) {
                    int newValue = progressBar.getValue() + incrementVal;
                    if (newValue > 100) newValue = 100;
                    else if (newValue < 0) newValue = 0;
                    progressBar.setValue(newValue);
                    progressBar.setString(progressBarToUpdate + ": " + newValue + "%");
                    frame.revalidate();
                    frame.repaint();
                }
            }
        }

        JLabel feedingLabel = addLabelWithImageAndReturn(frame, animationImage);

        Timer timer = new Timer(50, null);
        timer.addActionListener(new ActionListener() {
            int count = 0;
            final int maxCount = 8;
            int yPosition = 30;

            public void actionPerformed(ActionEvent e) {
                count++;
                yPosition += 18;
                feedingLabel.setLocation(200, yPosition);

                if (count >= maxCount) {
                    timer.stop();
                    frame.remove(feedingLabel);
                    frame.revalidate();
                    frame.repaint();

                    // Re-enable all buttons
                    for (Component component : components) {
                        if (component instanceof JButton) {
                            JButton button = (JButton) component;
                            button.setEnabled(true);
                        }
                    }
                }
            }
        });

        timer.start();
    }


    private static JLabel addLabelWithImageAndReturn(JFrame frame, String imagePath) {
        ImageIcon icon = new ImageIcon(imagePath);
        JLabel label = new JLabel(icon);
        label.setBounds(200, 30, icon.getIconWidth(), icon.getIconHeight());
        label.setHorizontalAlignment(0);
        frame.add(label);
        return label;
    }

    public static void main(String[] args) {

        runGUI();

    }

}





