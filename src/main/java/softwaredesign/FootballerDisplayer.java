package softwaredesign;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import static softwaredesign.FootballerName.C_RONALDO;
import static softwaredesign.FootballerName.L_MESSI;

public class FootballerDisplayer {

    private FootballerName chosenFootballer;

    private static final Map<FootballerName, String> FOOTBALLER_MODEL_IMAGE_PATH = Map.of(
            C_RONALDO, "./images/cristianobasic.png",
            L_MESSI, "./images/messibasic.png"
    );

    private static final Map<FootballerName, String> FOOTBALLER_MINI_GAME_IMAGE_PATH = Map.of(
            C_RONALDO, "src/main/java/softwaredesign/images/ronaldominigame.png",
            L_MESSI, "src/main/java/softwaredesign/images/messiminigame.png"
    );

    public void setChosenFootballer(FootballerName chosenFootballer) {

        this.chosenFootballer = chosenFootballer;

    }

    public FootballerName getChosenFootballer() {

        return this.chosenFootballer;

    }

    public String[] getAllFootballerModelsImagePath() {
        return Arrays.stream(FootballerName.values())
                .map(FOOTBALLER_MODEL_IMAGE_PATH::get)
                .toArray(String[]::new);
    }

    public String getOneFootballerModelImagePath(FootballerName chosenFootballer){

        return FOOTBALLER_MODEL_IMAGE_PATH.get(chosenFootballer);

    }

    public String getOneFootballerMiniGameImagePath(FootballerName chosenFootballer){

        return FOOTBALLER_MINI_GAME_IMAGE_PATH.get(chosenFootballer);

    }

    public FootballerName getFootballerNameByImagePath(String imagePath) {

        Optional<FootballerName> optionalFootballerName = FOOTBALLER_MODEL_IMAGE_PATH.entrySet().stream()
                .filter(entry -> entry.getValue().equals(imagePath))
                .map(Map.Entry::getKey)
                .findFirst();

        if (optionalFootballerName.isPresent()) {
            return optionalFootballerName.get();
        } else {
            throw new IllegalArgumentException("No FootballerName found for the provided imagePath: " + imagePath);
        }

    }

}
