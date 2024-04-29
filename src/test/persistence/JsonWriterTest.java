package persistence;

import model.Planner;
import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//this test class is modelled after the JsonWriterTest in the JSON example program

class JsonWriterTest extends JsonTest {
    //NOTE TO CompSci 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    List<Task> D2 = new ArrayList<>();
    List<Task> D4 = new ArrayList<>();

    @Test
    void testWriterInvalidFile() {
        try {
            Planner plan = new Planner("My Planner");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            Planner p = new Planner("My Planner");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.write(p);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkroom.json");
            reader.read(p);
            assertEquals("My Planner", p.getName());
            assertEquals(0, p.getNumTasks());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            Planner p = new Planner("My Planner");
            D4.add(new Task(1930, Planner.Day.WEDNESDAY, "CompSci210"));
            p.getPlan().put(Planner.Day.WEDNESDAY, D4);
            D2.add(new Task(1400, Planner.Day.MONDAY, "BIOL140"));
            p.getPlan().put(Planner.Day.MONDAY, D2);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWorkroom.json");
            writer.open();
            writer.write(p);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWorkroom.json");
            reader.read(p);
            assertEquals("My Planner", p.getName());
            List<Task> tasks = p.getTasks();
            assertEquals(2, tasks.size());
            checkTask("CompSci210", 1930, Planner.Day.WEDNESDAY, tasks.get(0));
            checkTask("BIOL140", 1400, Planner.Day.MONDAY, tasks.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}