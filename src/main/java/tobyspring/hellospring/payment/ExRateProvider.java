package tobyspring.hellospring.payment;

import tobyspring.hellospring.exrate.vo.ExRate;

import java.io.IOException;

public interface ExRateProvider {
    ExRate getExRate(String currency) throws IOException;
}
