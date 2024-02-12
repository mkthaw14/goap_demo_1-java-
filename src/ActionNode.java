import java.util.HashMap;
import java.util.Map;

public class ActionNode {
    public Map<String, Boolean> worldState;
    public ActionNode parent;
    public Action action;
    public int cost;

    public ActionNode() {}
    public ActionNode(Action action, ActionNode parent, Map<String, Boolean> worldState, int cost) {
        this.action = action;
        this.parent = parent;
        this.worldState = worldState;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "ActionNode{" +
                " parent=" + parent +
                ", action=" + action +
                '}';
    }

}
