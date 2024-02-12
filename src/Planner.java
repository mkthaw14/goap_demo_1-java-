import java.util.*;

public class Planner {
    public List<Action> availableActions;
    public Map<String, Boolean> currentWorldState; // currentWorldState is current belief of the agent which is unique to every agent
    public Map<String, Boolean> goalState; // The goal is what the agent what to do which is also unique to every agent
    /*
       Every agent has its own worldState and goal
     */

    public Planner() {}
    public Planner(List<Action> availableActions, Map<String, Boolean> currentWorldState, Map<String, Boolean> goalState) {
        this.availableActions = availableActions;
        this.currentWorldState = currentWorldState;
        this.goalState = goalState;
    }

    public boolean plan() {
        ActionNode parentNode = new ActionNode(null, null, currentWorldState, 0);
        List<ActionNode> leaves = new ArrayList<>(); // each node has previous node or parent node when we add a node to list, it would like adding tail of linkedlist  to the list
        boolean foundOne = buildActionTree(parentNode, leaves, availableActions, parentNode.worldState); // world state is the current initial state of agent
        return foundOne;
    }


    private boolean buildActionTree(ActionNode parent, List<ActionNode> leaves, List<Action> availableActions, Map<String, Boolean> currentWorldState) {
        boolean foundOne = false;
        for(var action: availableActions) {
            if(matchAllCondition(action.preConditions, currentWorldState)) { // If precondtion match with current world state (previous action node world state)
                HashMap<String, Boolean> newWorldState = new HashMap<>(currentWorldState);
                overwriteState(newWorldState, action.effects); // after doing this action, the new world state would be the effect of this action
                ActionNode node = new ActionNode(action, parent, newWorldState, action.cost + parent.cost);
                //System.out.println("New World State >>> " + newWorldState);
                System.out.println("Found Action >>>> " + action);
                if(matchAllCondition(newWorldState, goalState)) { // if the effect of action match with goal state
                    leaves.add(node); // register linkedlist of nodes to array list (adding tail node to list) [action3 {root=action2 {root=action1 } } ]
                                        /* The data structure is as follows
                                         * Note order doesn't need to be sequential
                                         * let consider action1 is initial action
                                         * action9 and 5 are possible action to satisfy the goal
                                         * action4 has two possible branching i.e action9 and 6
                                         * leaves : [
                                         * 0: [action9 => action4 => action3 => action1]
                                         * 1: [action5 => action6 => action4]
                                         * ]*/
                    foundOne = true;
                }
                else{
                    List<Action> newActionList = new ArrayList<>(availableActions);
                    newActionList.remove(action); // it will not find the same action
                                                    // or action with similar preconditons again
                                                    // also avoiding infinite loop
                    boolean found = buildActionTree(node, leaves, newActionList, newWorldState); // it would find a new child node for this action
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
