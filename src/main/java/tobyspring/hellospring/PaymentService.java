package tobyspring.hellospring;

import com.fasterxml.jackson.databind.ObjectMapper;
import tobyspring.hellospring.dto.ExRateData;
import tobyspring.hellospring.vo.PaymentDecimal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class PaymentService {
    public static void main(String[] args) throws IOException {
        PaymentService paymentService = new PaymentService();
        Payment payment = paymentService.prepare(100L, "USD", BigDecimal.valueOf(50.7));
        System.out.println(payment);
    }

    public Payment prepare(Long orderId, String currency, BigDecimal foriegnCurAmount) throws IOException {
        BigDecimal krwRate = getExRate(currency);
        BigDecimal convertedAmount = foriegnCurAmount.multiply(krwRate);
        LocalDateTime validUntil = LocalDateTime.now().plusMinutes(30);

        var paymentDecimal = PaymentDecimal.builder()
                .foriegnCurAmount(foriegnCurAmount)
                .exRate(krwRate)
                .convertedAmount(convertedAmount)
                .build();

        return new Payment(orderId, currency, paymentDecimal, validUntil);
    }

    private BigDecimal getExRate(String currency) throws IOException {
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
