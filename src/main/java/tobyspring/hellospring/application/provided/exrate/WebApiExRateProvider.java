package tobyspring.hellospring.application.provided.exrate;

import lombok.NonNull;
import tobyspring.hellospring.adapter.ApiExecutor;
import tobyspring.hellospring.adapter.ExRateExtractor;
import tobyspring.hellospring.adapter.ErApiExRateExtractor;
import tobyspring.hellospring.adapter.SimpleApiExecutor;
import tobyspring.hellospring.application.provided.exrate.dto.ErExRateData;
import tobyspring.hellospring.application.provided.exrate.vo.ExRate;
import tobyspring.hellospring.domain.payment.ExRateProvider;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class WebApiExRateProvider implements ExRateProvider {
    @Override
    public ExRate getExRate(String currency) {
        String url = "https://open.er-api.com/v6/latest/" + currency;

        return runApiForExRate(url, new SimpleApiExecutor(), new ErApiExRateExtractor());
    }

    private @NonNull ExRate runApiForExRate(String url, ApiExecutor apiExecutor, ExRateExtractor exRateExtractor) {
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
