package tobyspring.hellospring.application.provided.exrate.stub;

import tobyspring.hellospring.application.provided.exrate.vo.ExRate;
import tobyspring.hellospring.domain.payment.ExRateProvider;

public class SimpleExRateProviderStub implements ExRateProvider {
    private ExRate exRate;

    public SimpleExRateProviderStub(ExRate exRate) {
        this.exRate = exRate;
    }

    public ExRate getExRate() {
        return exRate;
    }

    public void setExRate(ExRate exRate) {
        this.exRate = exRate;
    }

    @Override
    public ExRate getExRate(String currency) {
        return exRate;
    }
}
