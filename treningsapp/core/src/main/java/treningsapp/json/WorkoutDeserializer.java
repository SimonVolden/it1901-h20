package treningsapp.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import java.io.IOException;
import treningsapp.core.Exercise;
import treningsapp.core.Workout;

public class WorkoutDeserializer extends JsonDeserializer<Workout> {

    private ExerciseDeserializer exerciseDeserializer = new ExerciseDeserializer();

    @Override
    public Workout deserialize(JsonParser parser, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        TreeNode treeNode = parser.getCodec().readTree(parser);
        return deserialize((JsonNode) treeNode);
    }

    /**
     * a method to deserialize a jsonNode.
     * 
     * @param jsonNode takes an jsonNode object as input
     * @return a result
     */
    public Workout deserialize(JsonNode jsonNode) {
        if (jsonNode instanceof ObjectNode) {
            ObjectNode objectNode = (ObjectNode) jsonNode;
            Workout workout = new Workout("name");
            JsonNode nameNode = objectNode.get("name");
            if (nameNode instanceof TextNode) {
                workout.setName(((TextNode) nameNode).asText());
            }
            JsonNode isSavedNode = objectNode.get("isSaved");
            if (isSavedNode instanceof BooleanNode) {
                workout.setSaved(((BooleanNode) isSavedNode).asBoolean());
            }
            JsonNode exercisesNode = objectNode.get("exercises");
            if (exercisesNode instanceof ArrayNode) {
                for (JsonNode elementNode : (ArrayNode) exercisesNode) {
                    Exercise exercise = exerciseDeserializer.deserialize(elementNode);
                    if (exercise != null) {
                        workout.addExercise(exercise);
                    }
                }
            }
            return workout;
        }
        return null;
    }
}
