package softwaredesign.gui;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import softwaredesign.FootballerDisplayer;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

import static softwaredesign.voicehandler.VoiceRecognitionHelper.*;

public class GUIMainMenu implements GUI {

    private final AtomicBoolean stopSignal = new AtomicBoolean(false);

    private final AtomicBoolean pauseAutoUpdateProgressBar = new AtomicBoolean(false);

    private final OnGuiClosedCallback onGuiClosedCallback;

    private final FootballerDisplayer footballerDisplayer;

    public GUIMainMenu(OnGuiClosedCallback onGuiClosedCallback, FootballerDisplayer footballerDisplayer) {
        this.onGuiClosedCallback = onGuiClosedCallback;
        this.footballerDisplayer = footballerDisplayer;
    }

    @Override
    public void customizeGUI(JFrame frame) {

        MainMenuDefaultComponentsProducer mainMenuDefaultComponentsProducer = new MainMenuDefaultComponentsProducer();

        displayModel(frame);

        StatusProgressBarConfigs[] statusProgressBarsConfigs = {
                new StatusProgressBarConfigs(46, "Hunger", 10, new Color(128, 0, 0), new Color(255, 182, 193), 90, true),
                new StatusProgressBarConfigs(47, "Hygiene", 50, new Color(0, 11, 255), new Color(204, 222, 255), 10, false),
                new StatusProgressBarConfigs(48, "Bladder", 90, new Color(96, 77, 0), new Color(236, 224, 181), 90, true),
                new StatusProgressBarConfigs(49, "Thirst", 130, new Color(6, 58, 0), new Color(225, 250, 225), 90, true),
                new StatusProgressBarConfigs(50, "Mood", 170, new Color(86, 0, 66), new Color(255, 234, 253), 10, false),
        };

        for (StatusProgressBarConfigs config : statusProgressBarsConfigs) {

            ObservableProgressBar progressBar = mainMenuDefaultComponentsProducer.createProgressBar(config.startVal, config.label, config.yPos, config.fg, config.bg, config.isValIncreasing);
            frame.add(progressBar);

            autoIncrementOrDecrementProgressBar(progressBar);

            DeathController deathController = new DeathController(progressBar.isValIncreasing(), frame, stopSignal);
            progressBar.addObserver(deathController);

            WarningLabel warningLabel = new WarningLabel(config.label, config.criticalValue, config.isValIncreasing, progressBar);
            progressBar.addObserver(warningLabel);
            frame.add(warningLabel);

        }

        addButton(frame, "Feed", 10, new ModifyStatusCommand(frame, "Hunger", "src/main/java/softwaredesign/images/chicken.png", -10), mainMenuDefaultComponentsProducer);
        addButton(frame, "Shower", 50, new ModifyStatusCommand(frame, "Hygiene", "src/main/java/softwaredesign/images/waterdroplet.png", 10), mainMenuDefaultComponentsProducer);
        addButton(frame, "Pee", 90, new ModifyStatusCommand(frame, "Bladder", "src/main/java/softwaredesign/images/toilet.png", -10), mainMenuDefaultComponentsProducer);
        addButton(frame, "Drink", 130, new ModifyStatusCommand(frame, "Thirst", "src/main/java/softwaredesign/images/bottle.png", -10), mainMenuDefaultComponentsProducer);
        addButton(frame, "Minigame", 170, new MiniGameExecuteCommand(pauseAutoUpdateProgressBar, new ModifyStatusCommand(frame, "Mood", "./images/chicken.png", 30)), mainMenuDefaultComponentsProducer);

        addVoiceRecognition(frame);
        addVoiceCommandLabels(frame);

    }

    private void displayModel(JFrame frame) {

        ImageIcon icon = new ImageIcon(footballerDisplayer.getOneFootballerModelImagePath(footballerDisplayer.getChosenFootballer()));
        JLabel label = new JLabel(icon);
        label.setBounds(135, 175, 250, 250);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(label);

    }

    private void autoIncrementOrDecrementProgressBar(ObservableProgressBar progressBar) {

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(() -> {
            if (stopSignal.get()) {
                executor.shutdown();
                try {
                    onGuiClosedCallback.onGuiClosed();
                } catch (IOException e) {
                    throw new GuiCloseException("Failed to close GUI", e);
                }
                return;
            }

            if (pauseAutoUpdateProgressBar.get()) {
                return;
            }

            SwingUtilities.invokeLater(() -> {
                int newValue = progressBar.getValue() + (progressBar.isValIncreasing() ? 1 : -1);
                newValue = Math.max(0, Math.min(100, newValue));
                progressBar.setValue(newValue);
                progressBar.setString(progressBar.getName() + ": " + newValue + "%");
            });
        }, 0, 1, TimeUnit.SECONDS);

    }

    private void addButton(JFrame frame, String text, int y, Command command, MainMenuDefaultComponentsProducer factory) {

        JButton button = factory.createButton(text, y);
        button.addActionListener(e -> command.execute());
        frame.add(button);

    }


    private void addVoiceRecognition(JFrame mainFrame) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.schedule(() -> {
            LiveSpeechRecognizer recognizer = configureRecognizer();
            if (recognizer != null) {
                recognizer.startRecognition(true);
                handleVoiceRecognitionResults(mainFrame, recognizer);
            }
        }, 5, TimeUnit.SECONDS);
    }

    private LiveSpeechRecognizer configureRecognizer() {
        // Mute logger messages
        Logger.getLogger("default.config").setLevel(java.util.logging.Level.OFF);
        if (System.getProperty("java.util.logging.config.file") == null) {
            System.setProperty("java.util.logging.config.file", "ignoreAllSphinx4LoggingOutput");
        }

        Configuration configuration = new Configuration();
        configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
        configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");

        try {
            return new LiveSpeechRecognizer(configuration);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void handleVoiceRecognitionResults(JFrame mainFrame, LiveSpeechRecognizer recognizer) {
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

            List<String> buttonTexts = getButtonTexts(mainFrame);
            for (String command : buttonTexts) {
                if (result.toLowerCase().contains(command)) {
                    SwingUtilities.invokeLater(() -> performButtonClickByVoice(mainFrame, command));
                }
            }
        }, 0, 500, TimeUnit.MILLISECONDS);
    }


    private void addVoiceCommandLabels(JFrame frame) {

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

}



