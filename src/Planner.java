import java.util.*;

public class Planner {
    public List<Action> availableActions;
    public Map<String, Boolean> currentWorldState;
    public Map<String, Boolean> goalState;

    public Planner() {}
    public Planner(List<Action> availableActions, Map<String, Boolean> currentWorldState, Map<String, Boolean> goalState) {
        this.availableActions = availableActions;
        this.currentWorldState = currentWorldState;
        this.goalState = goalState;
    }

    public boolean plan() {
        ActionNode parentNode = new ActionNode(null, null, currentWorldState, 0);
        List<ActionNode> leaves = new ArrayList<>();
        boolean foundOne = buildActionTree(parentNode, leaves, availableActions, parentNode.worldState);
        return foundOne;
    }

    private boolean buildActionTree(ActionNode parent, List<ActionNode> leaves, List<Action> availableActions, Map<String, Boolean> currentWorldState) {
        boolean foundOne = false;
        for(var action: availableActions) {
            if(matchAllCondition(action.preConditions, currentWorldState)) {
                HashMap<String, Boolean> newWorldState = new HashMap<>(currentWorldState);
                overwriteState(newWorldState, action.effects);
                ActionNode node = new ActionNode(action, parent, newWorldState, action.cost + parent.cost);
                //System.out.println("New World State >>> " + newWorldState);
                System.out.println("Found Action >>>> " + node);
                if(matchAllCondition(newWorldState, goalState)) {
                    leaves.add(node);
                    foundOne = true;
                }
                else{
                    List<Action> newActionList = new ArrayList<>(availableActions);
                    newActionList.remove(action);
                    boolean found = buildActionTree(node, leaves, newActionList, newWorldState);
                    if(found)
                        foundOne = true;
                }

            }
        }
        return foundOne;
    }

    private boolean matchAllCondition(Map<String, Boolean> condition1, Map<String, Boolean> condition2) {

        for(Map.Entry<String, Boolean> condition: condition1.entrySet()) {
            if(condition2.get(condition.getKey()) == null) {
                System.out.println("Such key not exist");
                return false;
            }

            if(condition2.get(condition.getKey()) != condition.getValue()) {
                return false;
            }
        }
        return true;
    }

    private void overwriteState(Map<String, Boolean> oldValues, Map<String, Boolean> newValues) {
        for(Map.Entry<String, Boolean> newState: newValues.entrySet()) {
            oldValues.put(newState.getKey(), newState.getValue());
        }
    }
}
