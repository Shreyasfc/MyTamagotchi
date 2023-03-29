package softwaredesign;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class GUIMain {

    public static void runGUI() {
        JFrame frame = createWindow();

        try {
            setContentPaneWithImage(frame);
        } catch (IOException e) {
            e.printStackTrace();
        }

        parseCharacter(frame);

        addProgressBar(frame, new ProgressBarConfig(90, "Hunger", 10, new Color(128, 0, 0), new Color(255, 182, 193), 90, true));
        addProgressBar(frame, new ProgressBarConfig(85, "Hygiene", 50, new Color(0, 11, 255), new Color(204, 222, 255), 10, false));
        addProgressBar(frame, new ProgressBarConfig(70, "Bladder", 90, new Color(96, 77, 0), new Color(236, 224, 181), 90, true));
        addProgressBar(frame, new ProgressBarConfig(10, "Thirst", 130, new Color(6, 58, 0), new Color(225, 250, 225), 90, true));
        addProgressBar(frame, new ProgressBarConfig(25, "Mood", 170, new Color(86, 0, 66), new Color(255, 234, 253), 10, false));

        addButton(frame, "Feed", 10, e -> buttonClickAction(frame, e));
        addButton(frame, "Shower", 50, e -> buttonClickAction(frame, e));
        addButton(frame, "Pee", 90, e -> buttonClickAction(frame, e));
        addButton(frame, "Drink", 130, e -> buttonClickAction(frame, e));
        addButton(frame, "Minigame", 170, e -> buttonClickAction(frame, e));

        frame.setVisible(true);
    }

    private static JFrame createWindow() {
        JFrame frame = new JFrame("Tamagotchi");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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

    private static void parseCharacter(JFrame frame) {
        ImageIcon icon = new ImageIcon("src/main/java/softwaredesign/images/cristianobasic.png");
        JLabel label = new JLabel(icon);
        label.setBounds(135, 175, 250, 250);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(label);
    }

    private static void addProgressBar(JFrame frame, ProgressBarConfig config) {
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setValue(config.value);
        progressBar.setStringPainted(true);
        progressBar.setString(config.text + ": " + progressBar.getValue() + "%");
        progressBar.setBounds(10, config.y, 150, 20);
        progressBar.setForeground(config.fg);
        progressBar.setBackground(config.bg);
        progressBar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        progressBar.setName(config.text + "Bar");
        frame.add(progressBar);

        ImageIcon warningIcon = new ImageIcon("src/main/java/softwaredesign/images/warning.png");
        JLabel warningLabel = new JLabel(warningIcon);
        warningLabel.setBounds(165, config.y, warningIcon.getIconWidth(), warningIcon.getIconHeight());
        warningLabel.setVisible(false);
        warningLabel.setName(config.text + "Warning");
        frame.add(warningLabel);

        if (config.isValIncreasing && config.value >= config.criticalValue) {
            warningLabel.setVisible(true);
        }

        if (!config.isValIncreasing && config.value <= config.criticalValue) {
            warningLabel.setVisible(true);
        }
    }

    private static void addButton(JFrame frame, String text, int y, ActionListener action) {
        JButton button = new JButton(text);
        button.setBounds(375, y, 100, 30);
        button.setPreferredSize(new Dimension(80, 30));
        frame.add(button);
        button.addActionListener(action);
    }

    private static void buttonClickAction(JFrame frame, ActionEvent e) {
        Component[] components = frame.getContentPane().getComponents();
        disableAllButtons(components);

        JButton buttonClicked = (JButton) e.getSource();
        String progressBarToUpdate;
        String animationImage;
        int incrementVal;

        progressBarToUpdate = getProgressBarToUpdate(buttonClicked);
        animationImage = getAnimationImage(buttonClicked);
        incrementVal = getIncrementVal(buttonClicked);

        updateProgressBar(components, progressBarToUpdate, incrementVal, frame);
        JLabel feedingLabel = addLabelWithImageAndReturn(frame, animationImage);
        animateAndReEnableButtons(frame, components, feedingLabel);
    }

    private static void disableAllButtons(Component[] components) {
        for (Component component : components) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                button.setEnabled(false);
            }
        }
    }

    private static void updateProgressBar(Component[] components, String progressBarToUpdate, int incrementVal, JFrame frame) {
        for (Component component : components) {
            if (component instanceof JProgressBar) {
                JProgressBar progressBar = (JProgressBar) component;
                if (progressBar.getString().startsWith(progressBarToUpdate)) {
                    int newValue = progressBar.getValue() + incrementVal;
                    newValue = Math.max(0, Math.min(100, newValue));
                    progressBar.setValue(newValue);
                    progressBar.setString(progressBarToUpdate + ": " + newValue + "%");
                    frame.revalidate();
                    frame.repaint();

                    updateWarningLabel(components, progressBar, incrementVal, newValue);
                }
            }
        }
    }

    private static void animateAndReEnableButtons(JFrame frame, Component[] components, JLabel feedingLabel) {
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
                    reEnableAllButtons(components);
                }
            }
        });

        timer.start();
    }

    private static void reEnableAllButtons(Component[] components) {
        for (Component component : components) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                button.setEnabled(true);
            }
        }
    }

    private static String getProgressBarToUpdate(JButton buttonClicked) {
        switch (buttonClicked.getText()) {
            case "Feed":
                return "Hunger";
            case "Shower":
                return "Hygiene";
            case "Pee":
                return "Bladder";
            case "Drink":
                return "Thirst";
            case "Minigame":
                return "Mood";
            default:
                return "";
        }
    }

    private static String getAnimationImage(JButton buttonClicked) {
        switch (buttonClicked.getText()) {
            case "Feed":
                return "src/main/java/softwaredesign/images/chicken.png";
            case "Shower":
                return "src/main/java/softwaredesign/images/waterdroplet.png";
            case "Pee":
                return "src/main/java/softwaredesign/images/toilet.png";
            case "Drink":
                return "src/main/java/softwaredesign/images/bottle.png";
            case "Minigame":
                return "src/main/java/softwaredesign/images/chicken.png";
            default:
                return "";
        }
    }

    private static int getIncrementVal(JButton buttonClicked) {
        switch (buttonClicked.getText()) {
            case "Feed":
                return -10;
            case "Shower":
                return 10;
            case "Pee":
                return -10;
            case "Drink":
                return -10;
            case "Minigame":
                return 10;
            default:
                return 0;
        }
    }

    private static void updateWarningLabel(Component[] components, JProgressBar progressBar, int incrementVal, int newValue) {
        String warningLabelName = progressBar.getName().replace("Bar", "Warning");
        for (Component comp : components) {
            if (comp instanceof JLabel && comp.getName() != null && comp.getName().equals(warningLabelName)) {
                JLabel warningLabel = (JLabel) comp;
                warningLabel.setVisible((incrementVal < 0 && newValue >= 90) || (incrementVal > 0 && newValue <= 10));
                break;
            }
        }
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

class ProgressBarConfig {
    int value;
    String text;
    int y;
    Color fg;
    Color bg;
    int criticalValue;
    boolean isValIncreasing;

    public ProgressBarConfig(int value, String text, int y, Color fg, Color bg, int criticalValue, boolean isValIncreasing) {
        this.value = value;
        this.text = text;
        this.y = y;
        this.fg = fg;
        this.bg = bg;
        this.criticalValue = criticalValue;
        this.isValIncreasing = isValIncreasing;
    }
}
