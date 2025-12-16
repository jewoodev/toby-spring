package tobyspring.hellospring.domain.payment;

import tobyspring.hellospring.adapter.exrate.vo.ExRate;

public interface ExRateProvider {
    ExRate getExRate(String currency);
}
