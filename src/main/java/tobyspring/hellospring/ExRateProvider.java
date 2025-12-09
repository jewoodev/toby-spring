package tobyspring.hellospring;

import tobyspring.hellospring.vo.ExRate;

import java.io.IOException;

public interface ExRateProvider {
    ExRate getExRate(String currency) throws IOException;
}
