package model;

import org.json.JSONObject;

//*Task class constructs an object with name time day and boolean of complete-ness
public class Task {
    private int time;
    private String name;
    private boolean complete;
    private Planner.Day day;

    //MODIFIES: this
    //EFFECTS: creates a task object, given a time and name
    public Task(int t, Planner.Day d, String s) {
        time = t;
        name = s;
        day = d;
        complete = false;
    }

    //MODIFIES: this
    //EFFECTS: creates a task object, given a  name
    public Task(String s) {
        this(-1, Planner.Day.UNKNOWN, s);
    }

    //MODIFIES: this
    //EFFECTS: creates a task object
    public Task() {
        this(-1, Planner.Day.UNKNOWN, "");
    }

    //MODIFIES: this
    //EFFECTS: marks a task as completed
    public void markComplete() {
        complete = true;
    }

    //REQUIRES: int between 0 and 2400
    //MODIFIES: this
    //EFFECTS: sets time
    public void setTime(int t) {
        time = t;
    }


    //MODIFIES: this
    //EFFECTS: sets day
    public void setDay(Planner.Day d) {
        day = d;
    }

    //MODIFIES: this
    //EFFECTS: sets name
    public void setName(String s) {
        name = s;
    }

    //EFFECTS: returns true if task has been completed, returns false otherwise
    public boolean checkCompleted() {
        return (complete);
    }

    //EFFECTS: returns name
    public String getName() {
        return name;
    }

    //EFFECTS: returns time on a 24 hour clock
    public int getTime() {
        return time;
    }


    //EFFECTS: returns task as a string
    @Override
    public String toString() {
        return "Task{" + "time=" + time + ", day=" + day + ", name='" + name + '\''
                + ", complete=" + complete + '}';
    }

    //EFFECTS: converts task to a JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("time", time);
        json.put("day", day);
        return json;
    }


    //EFFECTS: returns day
    public Planner.Day getDay() {
        return day;
    }
}