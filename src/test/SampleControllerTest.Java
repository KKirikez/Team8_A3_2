import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SampleControllerTest {

    @Test
    public void testCreateToyFromString_Figure() {
        String toyString = "1234567890;Figure;Test Brand;9.99;5;3;A";
        Toy toy = createToyFromString(toyString);
        assertTrue(toy instanceof Figures);
        assertEquals("1234567890", toy.getSerialNumber());
        assertEquals("Test Brand", toy.getBrand());
        assertEquals(9.99, toy.getPrice());
        assertEquals(5, toy.getAvailableCount());
        assertEquals(3, toy.getAgeAppropriate());
        assertEquals("A", ((Figures) toy).getClassification());
    }

    @Test
    public void testCreateToyFromString_Animal() {
        String toyString = "1234567890;Animal;Test Brand;9.99;5;3;A;Test Material";
        Toy toy = createToyFromString(toyString);
        assertTrue(toy instanceof Animals);
        assertEquals("1234567890", toy.getSerialNumber());
        assertEquals("Test Brand", toy.getBrand());
        assertEquals(9.99, toy.getPrice());
        assertEquals(5, toy.getAvailableCount());
        assertEquals(3, toy.getAgeAppropriate());
        assertEquals("A", ((Animals) toy).getClassification());
        assertEquals("Test Material", ((Animals) toy).getMaterial());
    }

    @Test
    public void testCreateToyFromString_Puzzle() {
        String toyString = "1234567890;Puzzle;Test Brand;9.99;5;3;A";
        Toy toy = createToyFromString(toyString);
        assertTrue(toy instanceof Puzzles);
        assertEquals("1234567890", toy.getSerialNumber());
        assertEquals("Test Brand", toy.getBrand());
        assertEquals(9.99, toy.getPrice());
        assertEquals(5, toy.getAvailableCount());
        assertEquals(3, toy.getAgeAppropriate());
        assertEquals("A", ((Puzzles) toy).getType());
    }

    @Test
    public void testCreateToyFromString_BoardGame() {
        String toyString = "1234567890;Board Game;Test Brand;9.99;5;3;2;4;Test Designers";
        Toy toy = createToyFromString(toyString);
        assertTrue(toy instanceof Boardgames);
        assertEquals("1234567890", toy.getSerialNumber());
        assertEquals("Test Brand", toy.getBrand());
        assertEquals(9.99, toy.getPrice());
        assertEquals(5, toy.getAvailableCount());
        assertEquals(3, toy.getAgeAppropriate());
        assertEquals(2, ((Boardgames) toy).getMinPlayers());
        assertEquals(4, ((Boardgames) toy).getMaxPlayers());
        assertEquals("Test Designers", ((Boardgames) toy).getDesigners());
    }

    }
//package test;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertNull;
//
//public class SampleControllerTest {
//
//    @Test
//    public void testCreateToyFromString_Figure() {
//        // Prepare test data
//        String toyString = "1234567890;Figure;Test Brand;9.99;5;3;A";
//
//        // Call the method under test
//        Toy toy = createToyFromString(toyString);
//
//        // Verify the result
//        assertNotNull(toy);
//        assertEquals(Figures.class, toy.getClass());
//        assertEquals("1234567890", toy.getSerialNumber());
//        assertEquals("Test Brand", toy.getBrand());
//        assertEquals(9.99, toy.getPrice());
//        assertEquals(5, toy.getAvailableCount());
//        assertEquals(3, toy.getAgeAppropriate());
//        assertEquals("A", ((Figures) toy).getClassification());
//    }
//
//    @Test
//    public void testCreateToyFromString_Animal() {
//        // Prepare test data
//        String toyString = "1234567890;Animal;Test Brand;9.99;5;3;A;Small";
//
//        // Call the method under test
//        Toy toy = createToyFromString(toyString);
//
//        // Verify the result
//        assertNotNull(toy);
//        assertEquals(Animals.class, toy.getClass());
//        assertEquals("1234567890", toy.getSerialNumber());
//        assertEquals("Test Brand", toy.getBrand());
//        assertEquals(9.99, toy.getPrice());
//        assertEquals(5, toy.getAvailableCount());
//        assertEquals(3, toy.getAgeAppropriate());
//        assertEquals("A", ((Animals) toy).getMaterial());
//        assertEquals("Small", ((Animals) toy).getSize());
//    }
//
//    @Test
//    public void testCreateToyFromString_Puzzle() {
//        // Prepare test data
//        String toyString = "1234567890;Puzzle;Test Brand;9.99;5;3;A";
//
//        // Call the method under test
//        Toy toy = createToyFromString(toyString);
//
//        // Verify the result
//        assertNotNull(toy);
//        assertEquals(Puzzles.class, toy.getClass());
//        assertEquals("1234567890", toy.getSerialNumber());
//        assertEquals("Test Brand", toy.getBrand());
//        assertEquals(9.99, toy.getPrice());
//        assertEquals(5, toy.getAvailableCount());
//        assertEquals(3, toy.getAgeAppropriate());
//        assertEquals("A", ((Puzzles) toy).getType());
//    }
//
//    @Test
//    public void testCreateToyFromString_BoardGame() {
//        // Prepare test data
//        String toyString = "1234567890;Board Game;Test Brand;9.99;5;3;2;4;Test Designers";
//
//        // Call the method under test
//        Toy toy = createToyFromString(toyString);
//
//        // Verify the result
//        assertNotNull(toy);
//        assertEquals(Boardgames.class, toy.getClass());
//        assertEquals("1234567890", toy.getSerialNumber());
//        assertEquals("Test Brand", toy.getBrand());
//        assertEquals(9.99, toy.getPrice());
//        assertEquals(5, toy.getAvailableCount());
//        assertEquals(3, toy.getAgeAppropriate());
//        assertEquals(2, ((Boardgames) toy).getMinPlayers());
//        assertEquals(4, ((Boardgames) toy).getMaxPlayers());
//        assertEquals("Test Designers", ((Boardgames) toy).getDesigners());
//    }
//
//    @Test
//    public void testCreateToyFromString_InvalidType() {
//        // Prepare test data
//        String toyString = "1234567890;InvalidType;Test Brand;9.99;5;3;A";
//
//        // Call the method under test
//        Toy toy = createToyFromString(toyString);
//
//        // Verify the result
//        assertNull(toy);
//    }
//
//    // Add more test cases for other methods in SampleController class
//}