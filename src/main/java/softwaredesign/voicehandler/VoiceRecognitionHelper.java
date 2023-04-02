package softwaredesign.voicehandler;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VoiceRecognitionHelper {

    private VoiceRecognitionHelper(){}

    public static List<String> getButtonTexts(JFrame frame) {
        List<String> buttonTexts = new ArrayList<>();
        for (Component component : frame.getContentPane().getComponents()) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                buttonTexts.add(button.getText().toLowerCase());
            }
        }
        return buttonTexts;
    }

    public static void performButtonClickByVoice(JFrame frame, String command) {
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

    public static JLabel getVoiceCommandLabel(JFrame frame) {
        for (Component component : frame.getContentPane().getComponents()) {
            if (component instanceof JLabel && component.getName() != null && component.getName().equals("voiceCommandLabel")) {
                return (JLabel) component;
            }
        }

        return null;
    }

}
