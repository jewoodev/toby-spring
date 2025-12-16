package tobyspring.hellospring.adapter.exrate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import tobyspring.hellospring.adapter.exrate.dto.ExRateData;
import tobyspring.hellospring.adapter.exrate.vo.ExRate;
import tobyspring.hellospring.domain.payment.ExRateProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.stream.Collectors;

public class WebApiExRateProvider implements ExRateProvider {
    @Override
    public ExRate getExRate(String currency) {
        String url = "https://open.er-api.com/v6/latest/" + currency;

        URI uri;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        String response;
        try {
            response = executeApi(uri);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ExRateData exRateData;
        try {
            exRateData = parseExRate(response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


        BigDecimal krwRate = exRateData.rates().get("KRW");
        Instant nextUpdate = Instant.ofEpochSecond(exRateData.time_next_update_unix());
        LocalDateTime nextUpdateAt = LocalDateTime.ofInstant(nextUpdate, ZoneId.of("Asia/Seoul"));

        return new ExRate(krwRate, nextUpdateAt);
    }

    private static @NonNull String executeApi(URI uri) throws IOException {
        var con = (HttpURLConnection) uri.toURL().openConnection();

        String response;
        try (var br = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            response = br.lines().collect(Collectors.joining());
        }

        return response;
    }

    private static ExRateData parseExRate(String response) throws JsonProcessingException {
        var om = new ObjectMapper();
        ExRateData exRateData = om.readValue(response, ExRateData.class);

        return exRateData;
    }
}
