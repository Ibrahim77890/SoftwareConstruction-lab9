package graph;

import java.util.HashMap;
import java.util.Map;

/**
 * A class that represents a vertex in the graph.
 * Each vertex holds a label and a map of its outgoing edges (target vertices and edge weights).
 */
public class Vertex {
    private final String label;
    private final Map<String, Integer> targets = new HashMap<>();

    public Vertex(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public Map<String, Integer> getTargets() {
        return targets;
    }

    public int setTarget(String target, int weight) {
        return targets.put(target, weight);
    }

    public void removeTarget(String target) {
        targets.remove(target);
    }

    // Implement equals and hashCode based on label
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vertex vertex = (Vertex) obj;
        return label.equals(vertex.label); // Only comparing labels
    }

    @Override
    public int hashCode() {
        return label.hashCode(); // Ensure consistency with equals() method
    }

    @Override
    public String toString() {
        return label + " -> " + targets.toString();
    }
}
