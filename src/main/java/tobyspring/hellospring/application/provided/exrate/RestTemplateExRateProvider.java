package tobyspring.hellospring.application.provided.exrate;

import org.springframework.web.client.RestTemplate;
import tobyspring.hellospring.application.provided.exrate.dto.ErExRateData;
import tobyspring.hellospring.domain.payment.vo.ExRate;
import tobyspring.hellospring.domain.payment.ExRateProvider;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class RestTemplateExRateProvider implements ExRateProvider {
    private final RestTemplate restTemplate;

    public RestTemplateExRateProvider(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ExRate getExRate(String currency) {
        String url = "https://open.er-api.com/v6/latest/" + currency;

        var erExRateData = restTemplate.getForObject(url, ErExRateData.class);

        var nextUpdateInstant = Instant.ofEpochSecond(erExRateData.time_next_update_unix());
        var nextUpdateAt = LocalDateTime.ofInstant(nextUpdateInstant, ZoneId.of("Asia/Seoul"));

        var exRate = new ExRate(erExRateData.rates().get("KRW"), nextUpdateAt);
        return exRate;
    }
}
