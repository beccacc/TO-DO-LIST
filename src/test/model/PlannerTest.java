package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlannerTest {
    Planner P1;
    Planner P2 = new Planner();
    Task T1;
    Task T2;
    Task T3;
    List<Task> D1 = new ArrayList<>();
    List<Task> D2 = new ArrayList<>();
    List<Task> D3 = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        P1 = new Planner();
        T1 = new Task();
        T1.setTime(100);
        T1.setName("Chem Test");
        T1.markComplete();
        T2 = new Task(700, Planner.Day.MONDAY, "Wake Up!");
        T3 = new Task("Go to Bed");
        T3.setTime(1800);
        D1.add(T1);
        P1.getPlan().put(Planner.Day.MONDAY, D1);
        D1.add(T2);
        D2.add(T3);
        D3.add(T2);
        P1.getPlan().put(Planner.Day.FRIDAY, D2);
    }

    @Test
    public void testCheckCompletedTrue() {
        assertTrue(T1.checkCompleted());
    }

    @Test
    public void testCheckCompletedFalse() {
        assertFalse(T2.checkCompleted());
    }

    @Test
    public void testHowManyTasksOnDayZero() {
        assertEquals(0, P1.howManyTasksToDoOnDay(Planner.Day.THURSDAY));
    }


    @Test
    public void testHowManyTasksOnDayOne() {
        assertEquals(1, P1.howManyTasksToDoOnDay(Planner.Day.FRIDAY));
    }


    @Test
    public void testHowManyTasksOnDayMultiple() {
        assertEquals(1, P1.howManyTasksToDoOnDay(Planner.Day.MONDAY));
    }

    @Test
    public void testFindDay() {
        assertEquals(Planner.Day.SUNDAY, P1.findDay(1));
        assertEquals(Planner.Day.MONDAY, P1.findDay(2));
        assertEquals(Planner.Day.TUESDAY, P1.findDay(3));
        assertEquals(Planner.Day.WEDNESDAY, P1.findDay(4));
        assertEquals(Planner.Day.THURSDAY, P1.findDay(5));
        assertEquals(Planner.Day.FRIDAY, P1.findDay(6));
        assertEquals(Planner.Day.SATURDAY, P1.findDay(7));
        assertEquals(Planner.Day.UNKNOWN, P1.findDay(0));
    }

    @Test
    public void testDayToString() {
        assertEquals("SUNDAY", P1.dayToString(Planner.Day.SUNDAY));
        assertEquals("MONDAY", P1.dayToString(Planner.Day.MONDAY));
        assertEquals("TUESDAY", P1.dayToString(Planner.Day.TUESDAY));
        assertEquals("WEDNESDAY", P1.dayToString(Planner.Day.WEDNESDAY));
        assertEquals("THURSDAY", P1.dayToString(Planner.Day.THURSDAY));
        assertEquals("FRIDAY", P1.dayToString(Planner.Day.FRIDAY));
        assertEquals("SATURDAY", P1.dayToString(Planner.Day.SATURDAY));
        assertEquals("UNKNOWN", P1.dayToString(Planner.Day.UNKNOWN));
    }

    @Test
    public void testDeleteTask() {
        assertEquals(3, P1.getNumTasks());
        P1.deleteTask(Planner.Day.MONDAY, 700);
        assertEquals(2, P1.getNumTasks());
    }

    @Test
    public void testAddTask() {
        assertEquals(3, P1.getNumTasks());
        P1.addTask("Becca", 100, Planner.Day.SUNDAY);
        assertEquals(4, P1.getNumTasks());
    }

    @Test
    public void testAddTaskDayExists() {
        assertEquals(3, P1.getNumTasks());
        P1.addTask("becca", 100, Planner.Day.MONDAY);
        assertEquals(4, P1.getNumTasks());
    }

    @Test
    public void testChangeTime() {
        assertEquals(700, T2.getTime());
        P1.changeTime(Planner.Day.MONDAY, 700, 100);
        assertEquals(100, T2.getTime());
    }

    @Test
    public void testMarkTaskComplete() {
        assertFalse(T2.checkCompleted());
        P1.markTaskComplete(Planner.Day.MONDAY, 700);
        assertTrue(T2.checkCompleted());
        P1.markTaskComplete(Planner.Day.TUESDAY, 514);
    }
}