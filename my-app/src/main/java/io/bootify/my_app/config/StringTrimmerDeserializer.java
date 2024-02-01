package io.bootify.my_app.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;


/**
 * Trim all incoming string values and provide null for empty strings.
 */
public class StringTrimmerDeserializer extends JsonDeserializer<String> {

    @Override
    public String deserialize(final JsonParser jsonParser, final DeserializationContext context)
            throws IOException {
        String value = jsonParser.getValueAsString();
        if (value == null) {
            return null;
        }
        value = value.trim();
        return value.isEmpty() ? null : value;
    }

}
