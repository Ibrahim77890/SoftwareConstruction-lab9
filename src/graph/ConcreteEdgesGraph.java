package graph;

import java.util.*;

/**
 * A mutable weighted directed graph with labeled vertices and edges represented by strings.
 */
public class ConcreteEdgesGraph implements Graph<String> {
    private final Set<String> vertices = new HashSet<>();
    private final List<Edge> edges = new ArrayList<>();

    // Abstraction function:
    // - vertices represents the set of all nodes in the graph.
    // - edges represents directed edges with weights between the vertices.

    // Representation invariant:
    // - No duplicate vertices or edges.
    // - Edge weights are non-negative.

    private void checkRep() {
        for (Edge edge : edges) {
            assert edge.getWeight() >= 0 : "Edge weight must be non-negative";
            assert vertices.contains(edge.getSource()) : "Edge source must be a vertex";
            assert vertices.contains(edge.getTarget()) : "Edge target must be a vertex";
        }
    }

    @Override
    public boolean add(String vertex) {
        boolean added = vertices.add(vertex);
        checkRep();
        return added;
    }

    @Override
    public int set(String source, String target, int weight) {
        if (!vertices.contains(source)) add(source);
        if (!vertices.contains(target)) add(target);

        for (Edge edge : edges) {
            if (edge.getSource().equals(source) && edge.getTarget().equals(target)) {
                int oldWeight = edge.getWeight();
                edges.remove(edge);
                if (weight > 0) {
                    edges.add(new Edge(source, target, weight));
                }
                checkRep();
                return oldWeight;
            }
        }

        if (weight > 0) {
            edges.add(new Edge(source, target, weight));
        }
        checkRep();
        return 0;
    }

    @Override
    public boolean remove(String vertex) {
        boolean removed = vertices.remove(vertex);
        edges.removeIf(edge -> edge.getSource().equals(vertex) || edge.getTarget().equals(vertex));
        checkRep();
        return removed;
    }

    @Override
    public Set<String> vertices() {
        return new HashSet<>(vertices);
    }

    @Override
    public Map<String, Integer> sources(String target) {
        Map<String, Integer> sources = new HashMap<>();
        for (Edge edge : edges) {
            if (edge.getTarget().equals(target)) {
                sources.put(edge.getSource(), edge.getWeight());
            }
        }
        return sources;
    }

    @Override
    public Map<String, Integer> targets(String source) {
        Map<String, Integer> targets = new HashMap<>();
        for (Edge edge : edges) {
            if (edge.getSource().equals(source)) {
                targets.put(edge.getTarget(), edge.getWeight());
            }
        }
        return targets;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Graph with vertices: " + vertices + " and edges: ");
        for (Edge edge : edges) {
            builder.append(edge.toString()).append(", ");
        }
        return builder.toString();
    }
}
