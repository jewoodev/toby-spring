package tobyspring.hellospring.application.provided.exrate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import tobyspring.hellospring.adapter.ApiExecuter;
import tobyspring.hellospring.application.provided.exrate.dto.ExRateData;
import tobyspring.hellospring.application.provided.exrate.vo.ExRate;
import tobyspring.hellospring.domain.payment.ExRateProvider;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class WebApiExRateProvider implements ExRateProvider {
    private final ApiExecuter apiExecuter;

    public WebApiExRateProvider(ApiExecuter apiExecuter) {
        this.apiExecuter = apiExecuter;
    }

    @Override
    public ExRate getExRate(String currency) {
        String url = "https://open.er-api.com/v6/latest/" + currency;

        return runApiForExRate(url);
    }

    private @NonNull ExRate runApiForExRate(String url) {
        URI uri;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        String response = apiExecuter.execute(uri);

        ExRateData exRateData;
        try {
            exRateData = extractExRate(response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


        BigDecimal krwRate = exRateData.rates().get("KRW");
        Instant nextUpdate = Instant.ofEpochSecond(exRateData.time_next_update_unix());
        LocalDateTime nextUpdateAt = LocalDateTime.ofInstant(nextUpdate, ZoneId.of("Asia/Seoul"));

        return new ExRate(krwRate, nextUpdateAt);
    }

    private ExRateData extractExRate(String response) throws JsonProcessingException {
        var om = new ObjectMapper();
        ExRateData exRateData = om.readValue(response, ExRateData.class);

        return exRateData;
    }
}
