package softwaredesign;

public class EventManager {

    private void startGame(){
        // call footballer constructor?
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
}
