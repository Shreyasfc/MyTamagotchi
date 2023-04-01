package softwaredesign.voicehandler;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;

import java.io.IOException;

public class VoiceRecognitionSingleton {

    private static VoiceRecognitionSingleton instance;
    private LiveSpeechRecognizer recognizer;

    private VoiceRecognitionSingleton(Configuration configuration) {
        try {
            recognizer = new LiveSpeechRecognizer(configuration);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized VoiceRecognitionSingleton getInstance(Configuration configuration) {
        if (instance == null) {
            instance = new VoiceRecognitionSingleton(configuration);
        }
        return instance;
    }

    public LiveSpeechRecognizer getRecognizer() {
        return recognizer;
    }

}
