package softwaredesign;

import softwaredesign.GUI.GUIMain;
import softwaredesign.GUI.GUISelectChar;

import javax.swing.*;

public class EventManager {

    private static void startGame(){

        GUISelectChar.runGUI(() -> {
            JFrame mainFrame = GUIMain.runGUI();
            GUIMain.setupVoiceRecognition(mainFrame);
        });

    }

    private void deathController(){
        // GUI stuff?
    }

    private int[] getStats(){
        return Footballer.playerStatus.values().stream().mapToInt(Integer::intValue).toArray();
    }

    private void decreaseStats(){
        for(Status stat : Status.values()){
            if(Footballer.playerStatus.get(stat) > 0){
                Footballer.playerStatus.put(stat, Footballer.playerStatus.get(stat) - 1);
            }
        }
    }

    public static void main (String[] args){

        startGame();


    }

}