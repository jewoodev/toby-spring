package tobyspring.hellospring.payment.stub;

import tobyspring.hellospring.exrate.vo.ExRate;
import tobyspring.hellospring.payment.ExRateProvider;

import java.io.IOException;

public class ExRateProviderStub implements ExRateProvider {
    private ExRate exRate;

    public ExRateProviderStub(ExRate exRate) {
        this.exRate = exRate;
    }

    public ExRate getExRate() {
        return exRate;
    }

    public void setExRate(ExRate exRate) {
        this.exRate = exRate;
    }

    @Override
    public ExRate getExRate(String currency) throws IOException {
        return exRate;
    }
}
