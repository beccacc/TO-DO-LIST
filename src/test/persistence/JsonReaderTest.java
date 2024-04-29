package persistence;

import model.Planner;
import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//This test class is based off of the JsonReaderTest class in the JSON example program
class JsonReaderTest extends JsonTest {

    Planner P1;

    @BeforeEach
    private void doFirst() {
        P1 = new Planner();
    }

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            reader.read(P1);
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/planner1.json");
        try {
            Planner p  = new Planner("Becca's Planner");
            try {
                reader.read(p);
            } catch (NullPointerException nullPointerException) {
                //pass
            }
            assertEquals("Becca's Planner", p.getName());
            assertEquals(2, p.getNumTasks());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/planner1.json");
        try {
            Planner p = new Planner("Becca's Planner");
            try {
                reader.read(p);
            } catch (NullPointerException nullPointerException) {
                //pass
            }
            assertEquals("Becca's Planner", p.getName());
            List<Task> tasks =p.getTasks();
            assertEquals(2, tasks.size());
            checkTask("CompSci210", 1930, Planner.Day.WEDNESDAY, tasks.get(1));
            checkTask("BIOL140", 1400, Planner.Day.MONDAY, tasks.get(0));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}