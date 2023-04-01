package softwaredesign;

import softwaredesign.gui.GUIEndGame;
import softwaredesign.gui.GUIMainMenu;
import softwaredesign.gui.GUISelectChar;
import softwaredesign.gui.OnGuiClosedCallback;

import java.io.IOException;
public class Main {
    public static void main(String[] args) throws IOException {

        FootballerDisplayer footballerDisplayer = new FootballerDisplayer();

        OnGuiClosedCallback endGameGUIClosed = () -> System.exit(0);
        GUIEndGame onGUIEndGameClosed = new GUIEndGame(endGameGUIClosed);

        OnGuiClosedCallback mainMenuGUIClosed = onGUIEndGameClosed::startGUI;
        GUIMainMenu onGUIMainMenuClosed = new GUIMainMenu(mainMenuGUIClosed, footballerDisplayer);

        OnGuiClosedCallback onGUISelectCharClosed = onGUIMainMenuClosed::startGUI;
        GUISelectChar guiSelectChar = new GUISelectChar(footballerDisplayer, onGUISelectCharClosed);
        guiSelectChar.startGUI();

    }

}