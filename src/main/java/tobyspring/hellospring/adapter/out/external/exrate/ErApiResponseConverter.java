package tobyspring.hellospring.adapter.out.external.exrate;

import tobyspring.hellospring.adapter.out.converter.JsonConverter;
import tobyspring.hellospring.adapter.out.converter.ResponseConverter;
import tobyspring.hellospring.adapter.out.external.exrate.dto.ErApiResponse;

public class ErApiResponseConverter implements ResponseConverter<ErApiResponse> {
    private final JsonConverter jsonConverter;

    public ErApiResponseConverter(JsonConverter jsonConverter) {
        this.jsonConverter = jsonConverter;
    }

    @Override
    public ErApiResponse convert(String input) {
        return jsonConverter.fromJson(input, ErApiResponse.class);
    }
}
