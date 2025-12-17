package tobyspring.hellospring.application.required.api;

import tobyspring.hellospring.application.provided.exrate.dto.ErExRateData;
import tobyspring.hellospring.application.provided.exrate.vo.ExRate;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class ApiTemplate {
    public ExRate getExRate(String url, ApiExecutor apiExecutor, ExRateExtractor exRateExtractor) {
        URI uri;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        String response = apiExecutor.execute(uri);

        ErExRateData erExRateData = exRateExtractor.extract(response);

        BigDecimal krwRate = erExRateData.rates().get("KRW");
        Instant nextUpdate = Instant.ofEpochSecond(erExRateData.time_next_update_unix());
        LocalDateTime nextUpdateAt = LocalDateTime.ofInstant(nextUpdate, ZoneId.of("Asia/Seoul"));

        return new ExRate(krwRate, nextUpdateAt);
    }
}
