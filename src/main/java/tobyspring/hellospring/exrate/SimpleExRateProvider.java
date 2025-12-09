package tobyspring.hellospring.exrate;

import tobyspring.hellospring.exrate.vo.ExRate;
import tobyspring.hellospring.payment.ExRateProvider;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SimpleExRateProvider implements ExRateProvider {
    @Override
    public ExRate getExRate(String currency) {
        if (currency.equals("USD")) {
            return new ExRate(BigDecimal.valueOf(1000), LocalDateTime.now().plusMinutes(30));
        }

        throw new IllegalArgumentException("Unsupported currency: " + currency);
    }
}
