package datasub.test.conf;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import datasub.test.domain.RecurringCycle;

import java.io.IOException;

public class RecurringCycleSerializer extends JsonSerializer<RecurringCycle> {

    @Override
    public void serialize(RecurringCycle value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.getJsonName());
    }
}
