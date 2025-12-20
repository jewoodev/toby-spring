package tobyspring.hellospring.adapter.out.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConverter {
    private final ObjectMapper om;

    public JsonConverter() {
        om = new ObjectMapper();
    }

    public <T> T fromJson(String json, Class<T> type) {
        try {
            return om.readValue(json, type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
