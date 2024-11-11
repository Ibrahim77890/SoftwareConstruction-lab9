/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import org.junit.Test;

public abstract class GraphInstanceTest {

    // Abstract method to be overridden by subclasses to provide an empty Graph instance for testing
    public abstract Graph<String> emptyInstance();

    // Test: Ensure assertions are enabled
    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // should fail if assertions are not enabled
    }

    // Test: Ensure a new graph has no vertices
    @Test
    public void testInitialVerticesEmpty() {
        Graph<String> graph = emptyInstance();
        assertEquals("expected new graph to have no vertices", Collections.emptySet(), graph.vertices());
    }

    // Test: Add vertices and check if they exist in the graph
    @Test
    public void testAddVertex() {
        Graph<String> graph = emptyInstance();
        assertTrue("expected true on adding new vertex A", graph.add("A"));
        assertTrue("expected true on adding new vertex B", graph.add("B"));
        assertEquals("expected vertices A and B", Set.of("A", "B"), graph.vertices());
    }

    // Test: Add a duplicate vertex and check that it does not add again
    @Test
    public void testAddDuplicateVertex() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        assertFalse("expected false on adding duplicate vertex A", graph.add("A"));
        assertEquals("expected only one vertex A", Set.of("A"), graph.vertices());
    }

    // Test: Add edges and check if they exist with the correct weights
    @Test
    public void testSetEdge() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        
        // Set an edge from A to B with weight 5
        int previousWeight = graph.set("A", "B", 5);
        assertEquals("expected previous weight to be 0 (no edge existed before)", 0, previousWeight);
        assertEquals("expected edge weight to be 5", 5, (int) graph.sources("B").get("A"));
        assertEquals("expected edge weight to be 5", 5, (int) graph.targets("A").get("B"));
    }

    // Test: Remove a vertex and verify it no longer exists
    @Test
    public void testRemoveVertex() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 5);

        assertTrue("expected true on removing vertex A", graph.remove("A"));
        assertFalse("vertex A should no longer exist", graph.vertices().contains("A"));
        assertNull("edge from A to B should no longer exist", graph.sources("B").get("A"));
    }

    // Test: Check sources and targets for directed edges
    @Test
    public void testSourcesAndTargets() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.add("C");

        // Add edges A -> B and B -> C with respective weights
        graph.set("A", "B", 4);
        graph.set("B", "C", 6);

        // Check sources and targets
        assertEquals("expected source of B to be A with weight 4", Map.of("A", 4), graph.sources("B"));
        assertEquals("expected target of A to be B with weight 4", Map.of("B", 4), graph.targets("A"));
        
        assertEquals("expected source of C to be B with weight 6", Map.of("B", 6), graph.sources("C"));
        assertEquals("expected target of B to be C with weight 6", Map.of("C", 6), graph.targets("B"));
    }
}
