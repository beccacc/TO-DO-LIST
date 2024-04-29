package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.JsonReader;
import persistence.JsonWriter;
import persistence.Writable;

import java.io.FileNotFoundException;
import java.util.*;

//*Planner class consists of a map of tasks and fields of JsonReader and Writer for bidirectional association
public class Planner implements Writable {

    private Map<Day, List<Task>> plan;
    private String name;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private static String JSON_STORE = "./data/planner1.json";


    //Planner is made up of a name and a hashmap of events and days
    //EFFECTS: creates a new planner with the given name and an empty hashmap
    public Planner(String string) {
        name = string;
        plan = new HashMap<>();
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
    }

    //EFFECTS: creates a new planner with an empty string as the name and an empty hashmap
    public Planner() {
        this("");
    }


    //MODIFIES: this
    //EFFECTS: clears all tasks from the planner
    public void clear() {
        plan.clear();
    }


    //EFFECTS: day object for planner, 7 options for days, 1 for unknown
    public enum Day {
        SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, UNKNOWN
    }

    //EFFECTS: returns plan
    public Map<Day, List<Task>> getPlan() {
        return plan;
    }


    //EFFECTS: returns how many tasks are listed as incomplete for the given day
    public int howManyTasksToDoOnDay(Day d) {
        if (plan.get(d) == null) {
            return 0;
        }
        List<Task> tasks = plan.get(d);
        List<Task> incomplete = new ArrayList<>();
        for (Task t : tasks) {
            if (!t.checkCompleted()) {
                incomplete.add(t);
            }
        }
        try {
            return incomplete.size();
        } catch (NullPointerException e) {
            return 0;
        }
    }

    //EFFECTS: returns the name of the planner
    public String getName() {
        return name;
    }

    //EFFECTS: returns an ArrayList of the tasks in planner
    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        for (List<Task> task : plan.values()) {
            tasks.addAll(task);
        }
        return tasks;
    }


    //EFFECTS: returns the number of tasks in the planner
    public int getNumTasks() {
        int i = 0;
        for (List<Task> t : plan.values()) {
            i = i + t.size();
        }
        return i;
    }

    //modeled off of toJson from the JSON example code
    //EFFECTS: takes a task in the planner and converts it to a JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("tasks", tasksToJson());
        return json;
    }

    //modeled off of toJson from the JSON example code
    //EFFECTS: returns tasks in this planner as a JSON array
    private JSONArray tasksToJson() {
        JSONArray jsonArray = new JSONArray();
        int i = 1;
        Day d;
        String day;
        for (List<Task> tasks : plan.values()) {
            for (Task t : tasks) {
                d = findDay(i);
                day = dayToString(d);
                jsonArray.put(t.toJson());
            }
            i++;
        }
        return jsonArray;
    }


    //EFFECTS: takes in an int and returns the day corresponding with it
    //         1-7 corresponds with SUNDAY - SATURDAY
    //         else returns UNKNOWN
    public Day findDay(int i) {
        if (i == 1) {
            return Planner.Day.SUNDAY;
        } else if (i == 2) {
            return Planner.Day.MONDAY;
        } else if (i == 3) {
            return Planner.Day.TUESDAY;
        } else if (i == 4) {
            return Planner.Day.WEDNESDAY;
        } else if (i == 5) {
            return Planner.Day.THURSDAY;
        } else if (i == 6) {
            return Planner.Day.FRIDAY;
        } else if (i == 7) {
            return Planner.Day.SATURDAY;
        } else {
            return Planner.Day.UNKNOWN;
        }
    }

    //EFFECTS: takes in a day and returns that day as a string
    public String dayToString(Day d) {
        if (d == Day.SUNDAY) {
            return "SUNDAY";
        } else if (d == Day.MONDAY) {
            return "MONDAY";
        } else if (d == Day.TUESDAY) {
            return "TUESDAY";
        } else if (d == Day.WEDNESDAY) {
            return "WEDNESDAY";
        } else if (d == Day.THURSDAY) {
            return "THURSDAY";
        } else if (d == Day.FRIDAY) {
            return "FRIDAY";
        } else if (d == Day.SATURDAY) {
            return "SATURDAY";
        } else {
            return "UNKNOWN";
        }
    }


    //MODIFIES: this
    //EFFECTS: given a day time and name, adds a new task to the planner
    public void addTask(String name, int time, Day day) {
        Task task = new Task(time, day, name);
        if (plan.containsKey(day)) {
            plan.get(day).add(task);
        } else {
            List<Task> tasks1 = new ArrayList<>();
            tasks1.add(task);
            plan.put(day, tasks1);
        }
    }

    //EFFECTS: given a day and time, finds the task in the planner returns it
    public Task returnTask(Day day, int time) {
        Task task = null;
        List<Task> tasks = new ArrayList<>();
        Collection<List<Task>> listOfTasks = plan.values();
        for (List<Task> tl : listOfTasks) {
            tasks.addAll(tl);
        }
        for (Task t : tasks) {
            if (t.getDay() == day) {
                if (t.getTime() == time) {
                    task = t;
                }
            }
        }
        return task;
    }

    //MODIFIES: this
    //EFFECTS: given a day and time of a task and a new time for the task, finds the task in the planner
    //         and changes the current time to the given new time
    public void changeTime(Day day, int currentTime, int newTime) {
        Task taskToBeChanged = returnTask(day, currentTime);
        if (!(taskToBeChanged == null)) {
            plan.get(day).remove(taskToBeChanged);
            List<Task> tasks2 = plan.get(day);
            taskToBeChanged.setTime(newTime);
            tasks2.add(taskToBeChanged);
        } else {
            System.out.println("Task not found");
        }
    }

    //MODIFIES: this
    //EFFECTS: given a day and time, finds the task in the planner and deletes it from the planner
    public void deleteTask(Day day, int time) {
        Task taskToBeDeleted = returnTask(day, time);
        if (!(taskToBeDeleted == null)) {
            plan.get(day).remove(taskToBeDeleted);
        } else {
            System.out.println("Task not found");
        }
    }

    //MODIFIES: this
    //EFFECTS: given a day and time, finds the task in the planner and marks it complete
    public void markTaskComplete(Day day, int time) {
        Task taskToBeCompleted = returnTask(day, time);
        if (!(taskToBeCompleted == null)) {
            plan.get(day).remove(taskToBeCompleted);
            List<Task> tasks2 = plan.get(day);
            taskToBeCompleted.markComplete();
            tasks2.add(taskToBeCompleted);
        } else {
            System.out.println("Task not found");
        }
    }

    //EFFECTS: returns JSONReader
    public JsonReader getJsonReader() {
        return jsonReader;
    }

    // EFFECTS: saves the planner to file
    public void savePlanner() {
        try {
            jsonWriter.open();
            jsonWriter.write(this);
            jsonWriter.close();
            System.out.println("Saved " + getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }
}

