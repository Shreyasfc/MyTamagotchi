package softwaredesign;

import softwaredesign.gui.GUIEndGame;
import softwaredesign.gui.GUIMain;
import softwaredesign.gui.GUISelectChar;
import softwaredesign.gui.OnGuiClosedCallback;

import java.io.IOException;
public class Main {
    public static void main(String[] args) throws IOException {

        FootballerDisplayer footballerDisplayer = new FootballerDisplayer();

        OnGuiClosedCallback endGameGUIClosed = () -> System.exit(0);
        GUIEndGame guiEndGame = new GUIEndGame(endGameGUIClosed);

        OnGuiClosedCallback mainMenuGUIClosed = guiEndGame::startGUI;
        GUIMain guiMain = new GUIMain(mainMenuGUIClosed, footballerDisplayer);

        OnGuiClosedCallback selectCharGUIClosed = guiMain::startGUI;
        GUISelectChar guiSelectChar = new GUISelectChar(footballerDisplayer, selectCharGUIClosed);
        guiSelectChar.startGUI();

    }

}