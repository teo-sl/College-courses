package search.interfaces;

import java.util.List;

public interface Node {
    int getDepth();
    void setFather(Node n);
    List<Node> getPath();
    int getEur();
    boolean isGoal();
    Node getFather();
    void computeEur();

    List<Node> getSons();
}
