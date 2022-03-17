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
import treningsapp.core.Exercise;
import treningsapp.core.Sets;

public class ExerciseDeserializer extends JsonDeserializer<Exercise> {

    private SetsDeserializer setDeserializer = new SetsDeserializer();


    @Override
    public Exercise deserialize(JsonParser parser, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        TreeNode treeNode = parser.getCodec().readTree(parser);
        return deserialize((JsonNode) treeNode);
    }

    /**
     * derserilalixe jsonnode.
     * 
     * @param jsonNode taking a node to deserialize
     * @return a result
     */

    public Exercise deserialize(JsonNode jsonNode) {
        if (jsonNode instanceof ObjectNode) {
            ObjectNode objectNode = (ObjectNode) jsonNode;
            Exercise exercise = new Exercise("name");
            JsonNode nameNode = objectNode.get("name");
            if (nameNode instanceof TextNode) {
                exercise.setName(((TextNode) nameNode).asText());
            }
            JsonNode setsNode = objectNode.get("sets");
            if (setsNode instanceof ArrayNode) {
                for (JsonNode elementNode : ((ArrayNode) setsNode)) {
                    Sets set = setDeserializer.deserialize(elementNode);
                    if (set != null) {
                        exercise.addSet(set);
                    }

                }
            }
            return exercise;
        }
        return null;
    }

}
