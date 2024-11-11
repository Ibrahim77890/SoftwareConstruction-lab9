package graph;

/**
 * Immutable class representing an edge in a directed weighted graph.
 */
class Edge {
    private final String source;
    private final String target;
    private final int weight;

    /**
     * Constructor to initialize an edge with a source, target, and weight.
     * @param source the source vertex
     * @param target the target vertex
     * @param weight the weight of the edge
     */
    public Edge(String source, String target, int weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
        checkRep();
    }

    // Representation invariant:
    // - weight must be non-negative
    private void checkRep() {
        assert weight >= 0 : "Edge weight must be non-negative";
    }

    /** @return the source vertex */
    public String getSource() {
        return source;
    }

    /** @return the target vertex */
    public String getTarget() {
        return target;
    }

    /** @return the weight of the edge */
    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return source + " --(" + weight + ")--> " + target;
    }
}
