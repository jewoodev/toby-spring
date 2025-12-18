package tobyspring.hellospring.application.provided.exrate;

import tobyspring.hellospring.application.required.api.ApiTemplate;
import tobyspring.hellospring.domain.payment.vo.ExRate;
import tobyspring.hellospring.domain.payment.ExRateProvider;

public class WebApiExRateProvider implements ExRateProvider {
    private final ApiTemplate apiTemplate;

    public WebApiExRateProvider(ApiTemplate apiTemplate) {
        this.apiTemplate = apiTemplate;
    }

    @Override
    public ExRate getExRate(String currency) {
        String url = "https://open.er-api.com/v6/latest/" + currency;

        return apiTemplate.getForExRate(url);
    }
}
