package tobyspring.hellospring.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import tobyspring.hellospring.application.provided.exrate.dto.ErExRateData;
import tobyspring.hellospring.application.required.api.ExRateExtractor;

public class ErApiExRateExtractor implements ExRateExtractor {
    @Override
    public ErExRateData extract(String response) {
        var om = new ObjectMapper();
        ErExRateData erExRateData;
        try {
            erExRateData = om.readValue(response, ErExRateData.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return erExRateData;
    }
}
