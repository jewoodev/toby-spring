package tobyspring.hellospring.domain.payment;

import tobyspring.hellospring.adapter.exrate.vo.ExRate;

import java.io.IOException;

public interface ExRateProvider {
    ExRate getExRate(String currency) throws IOException;
}
