package treningsapp.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import java.io.IOException;
import treningsapp.core.Workout;
import treningsapp.core.WorkoutProgram;

public class WorkoutProgramDeserializer extends JsonDeserializer<WorkoutProgram> {

    private WorkoutDeserializer workoutDeserializer = new WorkoutDeserializer();

    @Override
    public WorkoutProgram deserialize(JsonParser parser, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        TreeNode treeNode = parser.getCodec().readTree(parser);
        return deserialize((JsonNode) treeNode);
    }

    /**
     * Dserilalize method.
     * 
     * @param jsonNode takes in a jsonNode
     * @return a result
     */
    public WorkoutProgram deserialize(JsonNode jsonNode) {
        if (jsonNode instanceof ObjectNode) {
            ObjectNode objectNode = (ObjectNode) jsonNode;
            WorkoutProgram workoutProgram = new WorkoutProgram("name");
            JsonNode nameNode = objectNode.get("name");
            if (nameNode instanceof TextNode) {
                workoutProgram.setName(((TextNode) nameNode).asText());
            }
            JsonNode workoutsNode = objectNode.get("workouts");
            if (workoutsNode instanceof ArrayNode) {
                for (JsonNode elementNode : ((ArrayNode) workoutsNode)) {
                    Workout workout = workoutDeserializer.deserialize(elementNode);
                    if (workout != null) {
                        workoutProgram.addWorkouts(workout);
                    }
                }
            }
            return workoutProgram;
        }
        return null;
    }
}
