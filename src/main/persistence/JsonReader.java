package persistence;

import model.Planner;
import model.Task;
import org.json.*;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

//This class is modelled off of the JSonReader class in the JSON application example
// Represents a reader that reads planner from JSON data stored in file
public class JsonReader {
    private String source;
    private Planner planner;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads planner from file
    // throws IOException if an error occurs reading data from file
    public void read(Planner planner) throws IOException {
        this.planner = planner;
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        parsePlanner(jsonObject, planner);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses planner from JSON object and updates it
    private void parsePlanner(JSONObject jsonObject, Planner planner) {
        this.planner = planner;
        planner.clear();
        addTasks(planner, jsonObject);
    }

    // MODIFIES: planner
    // EFFECTS: parses tasks from JSON object and adds them to planner
    private void addTasks(Planner p, JSONObject jsonObject) {
        this.planner = planner;
        JSONArray jsonArray = jsonObject.getJSONArray("tasks");
        for (Object json : jsonArray) {
            JSONObject nextTask = (JSONObject) json;
            addTask(p, nextTask);
        }
    }

    // MODIFIES: planner
    // EFFECTS: parses task from JSON object and adds it to planner
    private void addTask(Planner planner, JSONObject jsonObject) {
        this.planner = planner;
        String name = jsonObject.getString("name");
        int time = jsonObject.getInt("time");
        Planner.Day day = jsonObject.getEnum(Planner.Day.class, "day");
        Task task = new Task(time, day, name);
        if (planner.getPlan().containsKey(day)) {
            planner.getPlan().get(day).add(task);
        } else {
            List<Task> tasks1 = new ArrayList<>();
            tasks1.add(task);
            planner.getPlan().put(day, tasks1);
        }
    }
}
