package persistence;

import org.json.JSONObject;
import model.Task;


//This interface is modelled off of the Writable interface in the JSON application example
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
