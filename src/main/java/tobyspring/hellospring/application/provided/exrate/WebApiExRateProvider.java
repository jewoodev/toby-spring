package tobyspring.hellospring.application.provided.exrate;

import tobyspring.hellospring.application.required.api.ApiTemplate;
import tobyspring.hellospring.adapter.ErApiExRateExtractor;
import tobyspring.hellospring.adapter.HttpClientApiExecutor;
import tobyspring.hellospring.application.provided.exrate.vo.ExRate;
import tobyspring.hellospring.domain.payment.ExRateProvider;

public class WebApiExRateProvider implements ExRateProvider {
    ApiTemplate apiTemplate = new ApiTemplate();

    @Override
    public ExRate getExRate(String currency) {
        String url = "https://open.er-api.com/v6/latest/" + currency;

        return apiTemplate.getExRate(url, new HttpClientApiExecutor(), new ErApiExRateExtractor());
    }
}
