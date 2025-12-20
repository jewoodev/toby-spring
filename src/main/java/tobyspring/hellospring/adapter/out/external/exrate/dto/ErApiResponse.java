package tobyspring.hellospring.adapter.out.external.exrate.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import tobyspring.hellospring.adapter.out.external.exception.ResponseInvalidException;

import java.math.BigDecimal;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ErApiResponse(
        String result,
        Map<String, BigDecimal> rates,
        long time_next_update_unix
) {
    public ErApiResponse {
        if (!(result.equals("success")))
            throw new ResponseInvalidException("API CALL FAILED: API=er.api.com, result=" + result);
    }
}
