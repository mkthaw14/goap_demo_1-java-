import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        HashMap<String, Boolean> currentWorldState = new HashMap<>(Map.of(
                "has_key", false,
                "locked_door", true,
                "door_open", false));
        HashMap<String, Boolean> goalState = new HashMap<>(Map.of(
                "has_key", true,
                "locked_door", false,
                "door_open", true));

        Action grabKey = new Action("Grab Key", 0);
        grabKey.preConditions.put("has_key", false);
        grabKey.preConditions.put("locked_door", true);
        grabKey.preConditions.put("door_open", false);

        grabKey.effects.put("has_key", true);
        grabKey.effects.put("locked_door", true);
        grabKey.effects.put("door_open", false);

        Action unlockDoor = new Action("Unlock Door", 1);
        unlockDoor.preConditions.put("has_key", true);
        unlockDoor.preConditions.put("locked_door", true);
        unlockDoor.preConditions.put("door_open", false);

        unlockDoor.effects.put("has_key", true);
        unlockDoor.effects.put("locked_door", false);
        unlockDoor.effects.put("door_open", false);


        Action openDoor = new Action("Open Door", 2);
        openDoor.preConditions.put("has_key", true);
        openDoor.preConditions.put("locked_door", false);
        openDoor.preConditions.put("door_open", false);

        openDoor.effects.put("has_key", true);
        openDoor.effects.put("locked_door", false);
        openDoor.effects.put("door_open", true);


        Action breakDoor = new Action("Break Door", 3);
        breakDoor.preConditions.put("has_key", false);
        breakDoor.preConditions.put("locked_door", true);
        breakDoor.preConditions.put("door_open", false);

        breakDoor.effects.put("has_key", false);
        breakDoor.effects.put("locked_door", false);
        breakDoor.effects.put("door_open", true);

        List<Action> actionList = List.of(grabKey, unlockDoor, openDoor);
        Planner planner = new Planner(actionList, currentWorldState, goalState);
        System.out.println("plan success " + planner.plan());
    }
}