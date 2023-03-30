package softwaredesign;

import java.util.*;

public class Footballer {

    public static final Map<Status, Integer> playerStatus = new HashMap<>();

    Footballer(){
        for(Status stat : Status.values()){
            playerStatus.put(stat, 0);
        }
    }

    private void increaseStatus(Status stat){
        if(!playerStatus.containsKey(stat)){
            System.out.println("Key doesn't exist");
            return;
        }
        playerStatus.put(stat, playerStatus.get(stat) + 1);
    }

    private int getStatus(Status stat){
        return playerStatus.getOrDefault(stat, -1);
    }


}
