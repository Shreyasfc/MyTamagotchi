package softwaredesign.gui;

import softwaredesign.minigame.MiniGameController;

import java.util.concurrent.atomic.AtomicBoolean;

public class MiniGameExecuteCommand implements Command {

    private final AtomicBoolean minigameActive ;

    private final ModifyStatusCommand modifyStatusCommand;

    public MiniGameExecuteCommand(AtomicBoolean minigameActive, ModifyStatusCommand modifyStatusCommand) {
        this.minigameActive = minigameActive;
        this.modifyStatusCommand = modifyStatusCommand;
    }

    @Override
    public void execute() {
        minigameActive.set(!minigameActive.get());
        OnGuiClosedCallback miniGameExit = () -> minigameActive.set(!minigameActive.get());
        MiniGameController miniGameController = new MiniGameController(miniGameExit);
        miniGameController.initGame();
        modifyStatusCommand.execute();
    }

}
