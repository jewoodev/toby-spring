package tobyspring.hellospring.adapter.exrate.stub;

import tobyspring.hellospring.adapter.exrate.vo.ExRate;
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
