package graph;

import org.junit.Test;
import static org.junit.Assert.*;

public class ConcreteVerticesGraphTest {

    @Test
    public void testAddVertex() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        assertTrue(graph.add("A"));
        assertFalse(graph.add("A")); // Should not add duplicate
    }

    @Test
    public void testSetEdge() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 5);
        assertEquals(5, (int) graph.targets("A").get("B"));
    }


    @Test
    public void testRemoveVertex() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 3);
        graph.remove("A");
        assertFalse(graph.vertices().contains("A"));
        assertNull(graph.sources("B").get("A"));
    }

    @Test
    public void testToString() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 3);
        String expected = "Graph: \nA -> {B=3}\nB -> {}\n";
        assertEquals(expected, graph.toString());
    }
}
