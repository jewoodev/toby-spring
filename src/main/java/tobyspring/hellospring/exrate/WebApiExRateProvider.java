package tobyspring.hellospring.exrate;

import com.fasterxml.jackson.databind.ObjectMapper;
import tobyspring.hellospring.dto.ExRateData;
import tobyspring.hellospring.exrate.vo.ExRate;
import tobyspring.hellospring.payment.ExRateProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.stream.Collectors;

public class WebApiExRateProvider implements ExRateProvider {
    @Override
    public ExRate getExRate(String currency) throws IOException {
        URL url = new URL("https://open.er-api.com/v6/latest/" + currency);
        var con = (HttpURLConnection) url.openConnection();
        var br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String response = br.lines().collect(Collectors.joining());
        br.close();

        var om = new ObjectMapper();
        var exRateData = om.readValue(response, ExRateData.class);
        BigDecimal krwRate = exRateData.rates().get("KRW");
        Instant nextUpdate = Instant.ofEpochSecond(exRateData.time_next_update_unix());
        LocalDateTime nextUpdateAt = LocalDateTime.ofInstant(nextUpdate, ZoneId.of("Asia/Seoul"));

        return new ExRate(krwRate, nextUpdateAt);
    }
}
