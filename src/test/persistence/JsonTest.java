package persistence;

import model.Planner;
import model.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;

//This test class is based off of the JsonTest class in the JSON example program
public class JsonTest {
    protected void checkTask(String name, int time, Planner.Day day, Task task) {
        assertEquals(name, task.getName());
        assertEquals(time, task.getTime());
        assertEquals(day, task.getDay());
    }
}
