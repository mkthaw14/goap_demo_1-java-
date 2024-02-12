import java.util.HashMap;
import java.util.Map;

public class Action {
    public String name;
    public int cost;
    public Map<String, Boolean> preConditions = new HashMap<>();
    public Map<String, Boolean> effects = new HashMap<>();

    public Action() {}
    public Action(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Action{" +
                "name='" + name + '\'' +
                ", cost=" + cost +
                '}';
    }
}
