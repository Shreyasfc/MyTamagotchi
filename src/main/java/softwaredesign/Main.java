package softwaredesign;

import softwaredesign.gui.GUIEndGame;
import softwaredesign.gui.GUIMainMenu;
import softwaredesign.gui.GUISelectChar;
import softwaredesign.gui.OnGuiClosedCallback;
public class Main {
    public static void main(String[] args) {

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