package tobyspring.hellospring.adapter.exrate;

import tobyspring.hellospring.adapter.exrate.vo.ExRate;
import tobyspring.hellospring.domain.payment.ExRateProvider;

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
