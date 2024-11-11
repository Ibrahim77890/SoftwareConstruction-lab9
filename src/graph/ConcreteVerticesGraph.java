package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Graph using vertices and edges.
 */
public class ConcreteVerticesGraph implements Graph<String> {
    
    private final List<Vertex> vertices = new ArrayList<>();
    
    // Abstraction function:
    //   Represents a weighted, directed graph where each vertex is a String
    //   and edges have weights represented by integers.
    //
    // Representation invariant:
    //   - No duplicate vertices in the vertices list.
    //   - All weights are non-negative.
    //
    // Safety from rep exposure:
    //   - vertices is private and final.
    //   - Methods return defensive copies or immutable views of data.
    
    // Constructor
    public ConcreteVerticesGraph() {
        checkRep();
    }
    
    // checkRep
    private void checkRep() {
        Set<String> vertexLabels = new HashSet<>();
        for (Vertex vertex : vertices) {
            assert vertex != null;
            assert vertexLabels.add(vertex.getLabel()) : "Duplicate vertex detected";
            Map<String, Integer> targets = vertex.getTargets();
            for (Integer weight : targets.values()) {
                assert weight >= 0 : "Edge weights must be non-negative";
            }
        }
    }
    
    @Override
    public boolean add(String vertex) {
        for (Vertex v : vertices) {
            if (v.getLabel().equals(vertex)) {
                return false;  // Vertex already exists
            }
        }
        vertices.add(new Vertex(vertex)); // Add the new vertex
        checkRep();
        return true;
    }
    
    @Override
    public int set(String source, String target, int weight) {
        Vertex sourceVertex = null;
        Vertex targetVertex = null;
        
        // Find or create source and target vertices
        for (Vertex v : vertices) {
            if (v.getLabel().equals(source)) {
                sourceVertex = v;
            } else if (v.getLabel().equals(target)) {
                targetVertex = v;
            }
        }
        if (sourceVertex == null) {
            sourceVertex = new Vertex(source);
            vertices.add(sourceVertex);
        }
        if (targetVertex == null) {
            targetVertex = new Vertex(target);
            vertices.add(targetVertex);
        }

        // Update edge weight
        int previousWeight = sourceVertex.setTarget(target, weight);
        checkRep();
        return previousWeight;
    }

    
    @Override
    public boolean remove(String vertex) {
        Vertex toRemove = null;
        for (Vertex v : vertices) {
            if (v.getLabel().equals(vertex)) {
                toRemove = v;
                break;
            }
        }
        if (toRemove == null) {
            return false;
        }
        vertices.remove(toRemove);
        for (Vertex v : vertices) {
            v.removeTarget(vertex);  // Remove any edge pointing to the removed vertex
        }
        checkRep();
        return true;
    }

    
    @Override
    public Set<String> vertices() {
        Set<String> vertexLabels = new HashSet<>();
        for (Vertex v : vertices) {
            vertexLabels.add(v.getLabel());
        }
        return vertexLabels;
    }
    
    @Override
    public Map<String, Integer> sources(String target) {
        Map<String, Integer> sources = new HashMap<>();
        for (Vertex v : vertices) {
            Integer weight = v.getTargets().get(target);
            if (weight != null && weight > 0) {
                sources.put(v.getLabel(), weight);
            }
        }
        return sources;
    }
    
    @Override
    public Map<String, Integer> targets(String source) {
        for (Vertex v : vertices) {
            if (v.getLabel().equals(source)) {
                return new HashMap<>(v.getTargets());
            }
        }
        return new HashMap<>();
    }
    
    @Override
    public String toString() {
        StringBuilder graphRepresentation = new StringBuilder("Graph: \n");
        for (Vertex v : vertices) {
            String targetString = v.getTargets().isEmpty() ? "{}" : v.getTargets().toString();
            graphRepresentation.append(v.getLabel()).append(" -> ").append(targetString).append("\n");
        }
        return graphRepresentation.toString();
    }

}
