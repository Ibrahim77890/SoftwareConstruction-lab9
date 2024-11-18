package poet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

import graph.ConcreteEdgesGraph; // Use ConcreteEdgesGraph instead of Graph
import graph.Graph;

/**
 * A graph-based poetry generator.
 * 
 * <p>GraphPoet generates poems by analyzing a corpus of text and creating a
 * weighted directed graph to determine relationships between words.
 */
public class GraphPoet {
    
    private final Graph<String> graph = new ConcreteEdgesGraph();
    
    // Abstraction function:
    //   Represents an affinity graph based on the word adjacency in the corpus.
    // Representation invariant:
    //   Vertices are non-empty, case-insensitive strings. Weights are positive integers.
    // Safety from rep exposure:
    //   The graph is encapsulated and never directly exposed.
    
    /**
     * Create a new poet with the graph from the corpus (as described above).
     * 
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */
    public GraphPoet(File corpus) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(corpus))) {
            String content = reader.lines().collect(Collectors.joining(" "));
            String[] words = content.split("\\s+");
            for (int i = 0; i < words.length - 1; i++) {
                String word1 = words[i].toLowerCase();
                String word2 = words[i + 1].toLowerCase();
                int currentWeight = graph.set(word1, word2, 0) + 1;
                graph.set(word1, word2, currentWeight);
            }
        }
        checkRep();
    }
    
    private void checkRep() {
        assert graph.vertices().stream().allMatch(word -> !word.isEmpty());
    }
    
    /**
     * Generate a poem.
     * 
     * @param input string from which to create the poem
     * @return poem (as described above)
     */
    public String poem(String input) {
        String[] words = input.split("\\s+");
        StringBuilder poem = new StringBuilder();

        for (int i = 0; i < words.length - 1; i++) {
            String word1 = words[i].toLowerCase();
            String word2 = words[i + 1].toLowerCase();

            // Add the first word in its original form
            poem.append(words[i]).append(" ");

            // Find the bridge word with the maximum two-edge-long path weight
            Set<String> candidates = graph.targets(word1).keySet();
            String bridge = null;
            int maxWeight = 0;
            for (String candidate : candidates) {
                if (graph.targets(candidate).containsKey(word2)) {
                    int weight = graph.targets(word1).get(candidate)
                            + graph.targets(candidate).get(word2);
                    if (weight > maxWeight) {
                        maxWeight = weight;
                        bridge = candidate;
                    }
                }
            }

            // Append the bridge word in lowercase if found
            if (bridge != null) {
                poem.append(bridge).append(" ");
            }
        }

        // Add the last word
        poem.append(words[words.length - 1]);
        return poem.toString();
    }
    
    @Override
    public String toString() {
        return "GraphPoet using graph: " + graph.toString();
    }
}
