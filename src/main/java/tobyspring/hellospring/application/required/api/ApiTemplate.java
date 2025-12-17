package tobyspring.hellospring.application.required.api;

import tobyspring.hellospring.adapter.ErApiExRateExtractor;
import tobyspring.hellospring.adapter.HttpClientApiExecutor;
import tobyspring.hellospring.application.provided.exrate.dto.ErExRateData;
import tobyspring.hellospring.application.provided.exrate.vo.ExRate;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class ApiTemplate {
    private final ApiExecutor apiExecutor;
    private final ExRateExtractor exRateExtractor;

    public ApiTemplate(ApiExecutor apiExecutor, ExRateExtractor exRateExtractor) {
        this.apiExecutor = apiExecutor;
        this.exRateExtractor = exRateExtractor;
    }

    public ApiTemplate() {
        this.apiExecutor = new HttpClientApiExecutor();
        this.exRateExtractor = new ErApiExRateExtractor();
    }

    public ExRate getForExRate(String url) {
        return this.getForExRate(url, this.apiExecutor, this.exRateExtractor);
    }

    public ExRate getForExRate(String url, ApiExecutor apiExecutor) {
        return this.getForExRate(url, apiExecutor, this.exRateExtractor);
    }

    public ExRate getForExRate(String url, ExRateExtractor exRateExtractor) {
        return this.getForExRate(url, this.apiExecutor, exRateExtractor);
    }

    public ExRate getForExRate(String url, ApiExecutor apiExecutor, ExRateExtractor exRateExtractor) {
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
