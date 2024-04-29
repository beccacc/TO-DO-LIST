package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskTest {
    Task T1;
    Task T2;

    @BeforeEach
    public void doFirst() {
        T1 = new Task(1930, Planner.Day.WEDNESDAY, "CompSci210");
        T2 = new Task(1400, Planner.Day.MONDAY, "Biol140");
    }

    @Test
    public void testToString() {
        assertEquals("Task{time=1930, day=WEDNESDAY, name='CompSci210', complete=false}", T1.toString());
        assertEquals("Task{time=1400, day=MONDAY, name='Biol140', complete=false}", T2.toString());
    }

    @Test
    public void testSetDay() {
        assertEquals(Planner.Day.MONDAY, T2.getDay());
        T2.setDay(Planner.Day.WEDNESDAY);
        assertEquals(Planner.Day.WEDNESDAY, T2.getDay());
    }
}
