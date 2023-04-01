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
        GUIEndGame guiEndGame = new GUIEndGame(endGameGUIClosed);

        OnGuiClosedCallback mainMenuGUIClosed = guiEndGame::startGUI;
        GUIMainMenu guiMainMenu = new GUIMainMenu(mainMenuGUIClosed, footballerDisplayer);

        OnGuiClosedCallback selectCharGUIClosed = guiMainMenu::startGUI;
        GUISelectChar guiSelectChar = new GUISelectChar(footballerDisplayer, selectCharGUIClosed);
        guiSelectChar.startGUI();

    }

}