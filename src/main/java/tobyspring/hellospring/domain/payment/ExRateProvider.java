package tobyspring.hellospring.domain.payment;

import tobyspring.hellospring.domain.payment.vo.ExRate;

public interface ExRateProvider {
    ExRate getExRate(String currency);
}
