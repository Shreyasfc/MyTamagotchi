package softwaredesign;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import static softwaredesign.FootballerName.C_RONALDO;
import static softwaredesign.FootballerName.L_MESSI;

public class FootballerDisplayer {

    private FootballerName chosenFootballer;

    private static final Map<FootballerName, String> footballerModelImagePath = Map.of(
            C_RONALDO, "src/main/java/softwaredesign/images/cristianobasic.png",
            L_MESSI, "src/main/java/softwaredesign/images/messibasic.png"
    );

    public void setChosenFootballer(FootballerName chosenFootballer) {

        this.chosenFootballer = chosenFootballer;

    }

    public FootballerName getChosenFootballer() {

        return this.chosenFootballer;

    }

    public String[] getAllFootballerModelsImagePath() {
        return Arrays.stream(FootballerName.values())
                .map(footballerModelImagePath::get)
                .toArray(String[]::new);
    }

    public String getOneFootballerModelImagePath(FootballerName chosenFootballer){

        return footballerModelImagePath.get(chosenFootballer);

    }

    public FootballerName getOneFootballerName(String imagePath) {

        Optional<FootballerName> optionalFootballerName = footballerModelImagePath.entrySet().stream()
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
