package softwaredesign.GUI;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class GUIMain extends DefaultScene {

    public static JFrame runGUI() {

        JFrame frame = createWindow();

        ComponentFactory factory = new DefaultComponentFactory();

        try {
            setContentPaneWithImage(frame);
            parseCharacter(frame);

            addProgressBar(frame, new ProgressBarConfig(90, "Hunger", 10, new Color(128, 0, 0), new Color(255, 182, 193), 90, true), factory);
            addProgressBar(frame, new ProgressBarConfig(85, "Hygiene", 50, new Color(0, 11, 255), new Color(204, 222, 255), 10, false), factory);
            addProgressBar(frame, new ProgressBarConfig(70, "Bladder", 90, new Color(96, 77, 0), new Color(236, 224, 181), 90, true), factory);
            addProgressBar(frame, new ProgressBarConfig(10, "Thirst", 130, new Color(6, 58, 0), new Color(225, 250, 225), 90, true), factory);
            addProgressBar(frame, new ProgressBarConfig(25, "Mood", 170, new Color(86, 0, 66), new Color(255, 234, 253), 10, false), factory);

            addButton(frame, "Feed", 10, e -> buttonClickAction(frame, e), factory);
            addButton(frame, "Shower", 50, e -> buttonClickAction(frame, e), factory);
            addButton(frame, "Pee", 90, e -> buttonClickAction(frame, e), factory);
            addButton(frame, "Drink", 130, e -> buttonClickAction(frame, e), factory);
            addButton(frame, "Minigame", 170, e -> buttonClickAction(frame, e), factory);

            addVoiceCommandLabels(frame);

        } catch (IOException e) {
            e.printStackTrace();
        }

        frame.setVisible(true);

        return frame;
    }

    private static void parseCharacter(JFrame frame) {
        ImageIcon icon = new ImageIcon("src/main/java/softwaredesign/images/messibasic.png");
        JLabel label = new JLabel(icon);
        label.setBounds(135, 175, 250, 250);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(label);
    }


    private static void addProgressBar(JFrame frame, ProgressBarConfig config, ComponentFactory factory) {
        ObservableProgressBar progressBar = (ObservableProgressBar) factory.createProgressBar(config.value, config.text, config.y, config.fg, config.bg, config.criticalValue, config.isValIncreasing);
        frame.add(progressBar);

        WarningLabel warningLabel = new WarningLabel(config.text + " Warning!", config.criticalValue, config.isValIncreasing);
        warningLabel.setName(config.text + "Warning");
        warningLabel.setBounds(progressBar.getX() + progressBar.getWidth() + 5, progressBar.getY(), 100, 20);
        warningLabel.setForeground(Color.RED);
        warningLabel.setVisible(false);
        progressBar.addObserver(warningLabel);
        frame.add(warningLabel);
    }

    private static void addButton(JFrame frame, String text, int y, ActionListener action, ComponentFactory factory) {
        JButton button = factory.createButton(text, y, action);
        frame.add(button);
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

        visuallyUpdateProgressBar(components, progressBarToUpdate, incrementVal, frame);
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

    private static void visuallyUpdateProgressBar(Component[] components, String progressBarToUpdate, int incrementVal, JFrame frame) {

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

    private static JLabel addLabelWithImageAndReturn(JFrame frame, String imagePath) {
        ImageIcon icon = new ImageIcon(imagePath);
        JLabel label = new JLabel(icon);
        label.setBounds(200, 30, icon.getIconWidth(), icon.getIconHeight());
        label.setHorizontalAlignment(0);
        frame.add(label);
        return label;
    }

    private static void addVoiceCommandLabels(JFrame frame) {
        JLabel voiceCommandLabel = new JLabel("Your voice command: ");
        voiceCommandLabel.setName("voiceCommandLabel");
        voiceCommandLabel.setBounds(10, 210, 200, 30);
        voiceCommandLabel.setForeground(Color.BLACK); // Set the text color to black
        voiceCommandLabel.setBackground(Color.WHITE); // Set the background color to white
        voiceCommandLabel.setOpaque(true); // Make the background visible
        frame.add(voiceCommandLabel);

        JLabel warningLabel = new JLabel("*Voice command may take a few minutes to load");
        warningLabel.setName("warningLabel");
        warningLabel.setBounds(10, 240, 200, 30);
        warningLabel.setForeground(Color.RED); // Set the text color to red
        warningLabel.setFont(new Font("Arial", Font.PLAIN, 8));
        frame.add(warningLabel);
    }

    public static void setupVoiceRecognition(JFrame mainFrame) {

        // This mini-segment aims to block the annoying logger messages
        Logger cmRootLogger = Logger.getLogger("default.config");
        cmRootLogger.setLevel(java.util.logging.Level.OFF);
        String conFile = System.getProperty("java.util.logging.config.file");
        if (conFile == null) {
            System.setProperty("java.util.logging.config.file", "ignoreAllSphinx4LoggingOutput");
        }

        Configuration configuration = new Configuration();

        configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
        configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");

        // Use the Singleton pattern for the voice recognition setup
        VoiceRecognitionSingleton voiceRecognitionSingleton = VoiceRecognitionSingleton.getInstance(configuration);
        LiveSpeechRecognizer recognizer = voiceRecognitionSingleton.getRecognizer();
        recognizer.startRecognition(true);

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            String result = recognizer.getResult().getHypothesis();
            SwingUtilities.invokeLater(() -> {
                if (mainFrame != null) {
                    JLabel voiceCommandLabel = getVoiceCommandLabel(mainFrame);
                    if (voiceCommandLabel != null) {
                        voiceCommandLabel.setText("Your voice command: " + result);
                    }
                }
            });
            for (String command : new String[]{"feed", "shower", "pee", "drink", "minigame"}) {
                if (result.toLowerCase().contains(command)) {
                    SwingUtilities.invokeLater(() -> {
                        if (mainFrame != null) {
                            performButtonClick(mainFrame, command);
                        }
                    });
                }
            }
        }, 0, 500, TimeUnit.MILLISECONDS);
    }

    private static JLabel getVoiceCommandLabel(JFrame frame) {
        for (Component component : frame.getContentPane().getComponents()) {
            if (component instanceof JLabel && component.getName() != null && component.getName().equals("voiceCommandLabel")) {
                return (JLabel) component;
            }
        }
        System.out.println("Voice command label not found.");
        return null;
    }

    static void performButtonClick(JFrame frame, String command) {
        for (Component component : frame.getContentPane().getComponents()) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                if (button.getText().equalsIgnoreCase(command)) {
                    button.doClick();
                    break;
                }
            }
        }
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
