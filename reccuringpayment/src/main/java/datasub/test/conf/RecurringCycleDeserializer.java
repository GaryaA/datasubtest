package datasub.test.conf;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import datasub.test.domain.RecurringCycle;

import java.io.IOException;

public class RecurringCycleDeserializer extends JsonDeserializer<RecurringCycle> {

    @Override
    public RecurringCycle deserialize(JsonParser jp, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        String recCycle = node.asText();
        return RecurringCycle.fromString(recCycle);
    }
}
