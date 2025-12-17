package tobyspring.hellospring.application.provided.exrate.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ErExRateData(
        String result,
        Map<String, BigDecimal> rates,
        long time_next_update_unix
) {
}
