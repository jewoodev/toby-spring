package tobyspring.hellospring.adapter.out.external.api;

import tobyspring.hellospring.adapter.out.converter.JsonConverter;
import tobyspring.hellospring.adapter.out.external.exrate.ErApiResponseConverter;
import tobyspring.hellospring.adapter.out.external.exrate.ExRateConverter;
import tobyspring.hellospring.adapter.out.external.exrate.dto.ErApiResponse;
import tobyspring.hellospring.application.required.api.ApiExecutor;
import tobyspring.hellospring.application.required.api.ApiTemplate;
import tobyspring.hellospring.domain.payment.vo.ExRate;

import java.net.URI;

public class HttpExRateApiTemplate extends ApiTemplate {
    private final ApiExecutor apiExecutor;
    private final ErApiResponseConverter responseConverter;
    private final ExRateConverter exRateConverter;

    public HttpExRateApiTemplate(ApiExecutor apiExecutor, JsonConverter jsonConverter) {
        this.apiExecutor = apiExecutor;
        this.responseConverter = new ErApiResponseConverter(jsonConverter);
        this.exRateConverter = new ExRateConverter();
    }

    @Override
    protected String executeApi(URI uri) {
        return apiExecutor.execute(uri);
    }

    @Override
    protected ErApiResponse convertResponse(String response) {
        return responseConverter.convert(response);
    }

    @Override
    protected ExRate convertToExRate(ErApiResponse apiResponse) {
        return exRateConverter.convert(apiResponse);
    }
}
