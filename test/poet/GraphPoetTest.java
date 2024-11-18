package poet;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class GraphPoetTest {

    /**
     * Test that assertions are enabled.
     */
    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false : "Assertions must be enabled!";
    }

    /**
     * Test basic poem generation with a valid corpus containing bridge words.
     */
    @Test
    public void testBasicPoemGeneration() throws IOException {
        File corpus = new File("test/poet/simple-corpus.txt");
        GraphPoet poet = new GraphPoet(corpus);

        String input = "Seek to explore new and exciting synergies!";
        String expected = "Seek to explore strange new and exciting synergies!";
        assertEquals("Generated poem should match the expected output", expected, poet.poem(input));
    }

    /**
     * Test poem generation when there are no bridge words in the corpus.
     */
    @Test
    public void testNoBridgeWords() throws IOException {
        File corpus = new File("test/poet/disjoint-corpus.txt");
        GraphPoet poet = new GraphPoet(corpus);

        String input = "Completely unrelated words.";
        assertEquals("No transformation for disjoint corpus", input, poet.poem(input));
    }

    /**
     * Test poem generation with an empty input string.
     */
    @Test
    public void testEmptyInput() throws IOException {
        File corpus = new File("test/poet/simple-corpus.txt");
        GraphPoet poet = new GraphPoet(corpus);

        String input = "";
        String expected = "";
        assertEquals("Empty input should return an empty string", expected, poet.poem(input));
    }

    /**
     * Test that the program handles case-insensitive input and corpus correctly.
     */
    @Test
    public void testCaseInsensitivity() throws IOException {
        File corpus = new File("test/poet/simple-corpus.txt");
        GraphPoet poet = new GraphPoet(corpus);

        String input = "seek TO EXPLORE new AND exciting synergies!";
        String expected = "seek TO EXPLORE strange new AND exciting synergies!";
        assertEquals("Poem generation should be case-insensitive", expected, poet.poem(input));
    }

    /**
     * Test that the program handles special characters and punctuation correctly.
     */
    @Test
    public void testSpecialCharacters() throws IOException {
        File corpus = new File("test/poet/special-corpus.txt");
        GraphPoet poet = new GraphPoet(corpus);

        String input = "Code, debug and deploy!";
        String expected = "Code, debug thoroughly and deploy!";
        assertEquals("Special characters should not disrupt poem generation", expected, poet.poem(input));
    }

    /**
     * Test poem generation with bridge words appearing in the middle of the input string.
     */
    @Test
    public void testBridgeWordsInMiddle() throws IOException {
        File corpus = new File("test/poet/middle-corpus.txt");
        GraphPoet poet = new GraphPoet(corpus);

        String input = "Connecting ideas bridges gaps.";
        String expected = "Connecting ideas build bridges across gaps.";
        assertEquals("Bridge word should appear in the middle of the poem", expected, poet.poem(input));
    }
}
