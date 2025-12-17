package tobyspring.hellospring.domain.payment;

import tobyspring.hellospring.application.provided.exrate.vo.ExRate;

public interface ExRateProvider {
    ExRate getExRate(String currency);
}
