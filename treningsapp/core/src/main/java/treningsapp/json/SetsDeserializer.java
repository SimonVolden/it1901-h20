package treningsapp.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import treningsapp.core.Sets;

public class SetsDeserializer extends JsonDeserializer<Sets> {

    @Override
    public Sets deserialize(JsonParser parser, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        TreeNode treeNode = parser.getCodec().readTree(parser);
        return deserialize((JsonNode) treeNode);
    }

    /**
     * A set method.
     * 
     * @param jsonNode Takes in a jsonNode
     */
    public Sets deserialize(JsonNode jsonNode) {
        if (jsonNode instanceof ObjectNode) {
            ObjectNode objectNode = (ObjectNode) jsonNode;
            Sets set = new Sets(0, (double) 0);
            JsonNode repnode = objectNode.get("repetition");
            if (repnode instanceof IntNode) {
                set.setRepetitions(((IntNode) repnode).asInt());
            }
            JsonNode weightnode = objectNode.get("weight");
            if (weightnode instanceof DoubleNode) {
                set.setWeight(((DoubleNode) weightnode).asDouble());
            }
            return set;
        }
        return null;
    }


}
