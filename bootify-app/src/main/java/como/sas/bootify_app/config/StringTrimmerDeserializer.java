package como.sas.bootify_app.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.SneakyThrows;


/**
 * Trim all incoming string values and provide null for empty strings.
 */
public class StringTrimmerDeserializer extends JsonDeserializer<String> {

    @Override
    @SneakyThrows
    public String deserialize(final JsonParser jsonParser, final DeserializationContext context) {
        String value = jsonParser.getValueAsString();
        if (value == null) {
            return null;
        }
        value = value.trim();
        return value.isEmpty() ? null : value;
    }

}
