package tobyspring.hellospring;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import tobyspring.hellospring.dto.ExRateData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

@Component
public class WebApiExRateProvider implements ExRateProvider {
    @Override
    public BigDecimal getExRate(String currency) throws IOException {
        URL url = new URL("https://open.er-api.com/v6/latest/" + currency);
        var con = (HttpURLConnection) url.openConnection();
        var br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String response = br.lines().collect(Collectors.joining());
        br.close();

        var om = new ObjectMapper();
        var exRateData = om.readValue(response, ExRateData.class);
        BigDecimal krwRate = exRateData.rates().get("KRW");
        return krwRate;
    }
}
